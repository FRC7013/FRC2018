package frc.team7013.robot.subsystems;

import frc.team7013.robot.TPackage.loop.Loop;
import frc.team7013.robot.TPackage.loop.Looper;
import frc.team7013.robot.TPackage.pid.TSpeedPID;
import frc.team7013.robot.TPackage.sensors.encoder.TPwmEncoder;
import frc.team7013.robot.TPackage.sensors.gyro.TAnalogGyro;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.constants.RobotMap;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.constants.RobotConst;

public class Drive extends Subsystem {

    private static Drive mInstance = new Drive();

    public static Drive getInstance() {
        return mInstance;
    }

    //The control states of the drive system
    public enum DriveControlState {
        OPEN_LOOP,  //open loop control
        VELOCITY_SETPOINT,   //velocity PID control
    }

    //Control states
    private DriveControlState mDriveControlState;

    //Hardware
    private final TPwmSpeedController mLeftDrive, mRightDrive;
    private final TPwmEncoder mLeftEncoder, mRightEncoder;
    private final TAnalogGyro mGyro;

    //PID
    private final TSpeedPID leftSpeedPid;
    private final TSpeedPID rightSpeedPid;
    boolean speedPidsActive = false;

    private final Loop mLoop = new Loop() {
        @Override
        public void onStart(double timestamp) {
            synchronized (Drive.this) {
                setOpenLoop(0,0);
                mGyro.reset();
            }
        }
        //Maybe have drive PIDs in a separate loop?
        @Override
        public void onLoop(double timestamp) {
            synchronized (Drive.this) {
                System.out.println("Drive alive!");
            }
        }

        @Override
        public void onStop(double timestamp) {
            stop();
        }
    };

    private Drive() {
        //Instantiate hardware
        mGyro = new TAnalogGyro(0);
        mLeftEncoder = new TPwmEncoder(RobotMap.LEFT_DRIVE_ENCODER_A_DIO_PORT, RobotMap.LEFT_DRIVE_ENCODER_B_DIO_PORT, RobotConst.LEFT_ENCODER_ORIENTATION);
        mRightEncoder = new TPwmEncoder(RobotMap.RIGHT_DRIVE_ENCODER_A_DIO_PORT, RobotMap.RIGHT_DRIVE_ENCODER_B_DIO_PORT, RobotConst.RIGHT_ENCODER_ORIENTATION);
        mLeftDrive = new TPwmSpeedController(TPwmSpeedControllerType.SPARK, RobotMap.LEFT_DRIVE_MOTOR_PORT, RobotConst.LEFT_MOTOR_ORIENTATION);
        mRightDrive = new TPwmSpeedController(TPwmSpeedControllerType.SPARK,RobotMap.RIGHT_DRIVE_MOTOR_PORT,RobotConst.RIGHT_MOTOR_ORIENTATION);

        mGyro.setSensitivity(0.0016133333333333334);

        leftSpeedPid = new TSpeedPID(RobotConst.DRIVE_SPEED_PID_KP);
        rightSpeedPid = new TSpeedPID(RobotConst.DRIVE_SPEED_PID_KP);
        disableSpeedPids();

        setOpenLoop(0,0);
    }

    private void disableSpeedPids() {
        if(speedPidsActive) {
            leftSpeedPid.disable();
            rightSpeedPid.disable();
            speedPidsActive = false;
        }
    }

    private void enableSpeedPids() {
        if(mLeftEncoder == null || mRightEncoder == null) { //If there are no encoders, don't enable pids
            return;
        }
        if(!speedPidsActive) {
            leftSpeedPid.enable();
            rightSpeedPid.enable();
            speedPidsActive = true;
        }
    }

    @Override
    public void registerEnabledLoops(Looper in) {
        in.register(mLoop);
    }

    //Set up open loop control
    public synchronized void setOpenLoop(double leftSignal, double rightSignal) {
        if(mDriveControlState != DriveControlState.OPEN_LOOP) {
            speedPidsActive = false;
            disableSpeedPids();
            mDriveControlState = DriveControlState.OPEN_LOOP;
        }
        mLeftDrive.set(leftSignal);
        mRightDrive.set(rightSignal);
    }

    @Override
    public synchronized void stop() {
        setOpenLoop(0,0);
    }

    @Override
    public void outputToSmartDashboard() {
    }

    public synchronized void resetEncoders() {
        mLeftEncoder.reset();
        mRightEncoder.reset();
    }

    @Override
    public void zeroSensors() {
        resetEncoders();
        mGyro.reset();
    }

    public synchronized void setVelocitySetpoint(double leftSpeed, double rightSpeed) { //Needs to be called at 200Hz, NOT IN THIS CLASS
        if(mDriveControlState != DriveControlState.VELOCITY_SETPOINT) {
            speedPidsActive = true;
            enableSpeedPids();
            mDriveControlState = DriveControlState.VELOCITY_SETPOINT;
        }
        updateVelocitySetpoint(leftSpeed, rightSpeed);
    }

    private synchronized void updateVelocitySetpoint(double leftSpeed, double rightSpeed) {
        leftSpeedPid.setSetpoint(leftSpeed);
        rightSpeedPid.setSetpoint(rightSpeed);

        leftSpeedPid.calculate(getLeftVelocityUnitless() / RobotConst.MAX_DRIVE_SPEED);
        rightSpeedPid.calculate(getRightVelocityUnitless() / RobotConst.MAX_DRIVE_SPEED);

        mLeftDrive.set(leftSpeedPid.get());
        mRightDrive.set(rightSpeedPid.get());

    }

    public double getLeftDistanceInches() {
        return rotationsToInches(mLeftEncoder.get());
    }

    public double getRightDistanceInches() {
        return rotationsToInches(mRightEncoder.get());
    }

    private static double rotationsToInches(double rotations) {
        return rotations * (RobotConst.DRIVE_WHEEL_DIAMETER * Math.PI);
    }

    public double getLeftVelocityUnitless() {
        return mLeftEncoder.getRate();
    }

    public double getRightVelocityUnitless() {
        return mRightEncoder.getRate();
    }

    public double getLeftRightAverageVelocityUnitless() {
        return (mLeftEncoder.getRate() + mRightEncoder.getRate())/2;
    }

}
