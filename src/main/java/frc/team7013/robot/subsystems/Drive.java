package frc.team7013.robot.subsystems;

import frc.team7013.robot.TPackage.loop.Loop;
import frc.team7013.robot.TPackage.loop.Looper;
import frc.team7013.robot.TPackage.sensors.encoder.TPwmEncoder;
import frc.team7013.robot.TPackage.sensors.gyro.TAnalogGyro;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.robot.RobotMap;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.robot.RobotConst;

public class Drive extends Subsystem {

    private static Drive mInstance = new Drive();

    public static Drive getInstance() {
        return mInstance;
    }

    //The control states of the drive system
    public enum DriveControlState {
        OPEN_LOOP,  //open loop control
        VELOCITY_SETPOINT   //velocity PID control
    }

    //Control states
    private DriveControlState mDriveControlState;

    //Hardware
    private final TPwmSpeedController mLeftDrive, mRightDrive;
    private final TPwmEncoder mLeftEncoder, mRightEncoder;
    private final TAnalogGyro mGyro;

    private final Loop mLoop = new Loop() {
        @Override
        public void onStart(double timestamp) {
            synchronized (Drive.this) {
                setOpenLoop(0,0);
                setVelocitySetpoint(0,0);
                mGyro.reset();
            }
        }
        //Maybe have drive PIDs in a separate loop?
        @Override
        public void onLoop(double timestamp) {
            synchronized (Drive.this) {
                switch(mDriveControlState) {
                    case OPEN_LOOP: //call setOpenLoop(leftSpeed, rightSpeed) IN TELEOP_PERIODIC
                        return;
                    case VELOCITY_SETPOINT:
                        DO SHIT
                        break;
                        default:
                            System.out.println("ERROR: Unexpected drive control state: " + mDriveControlState);
                            break;
                }
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

        setOpenLoop(0,0);
    }

    @Override
    public void registerEnabledLoops(Looper in) {
        in.register(mLoop);
    }

    //Set up open loop control
    public synchronized void setOpenLoop(double leftSignal, double rightSignal) {
        if(mDriveControlState != DriveControlState.OPEN_LOOP) {
            SET UP OPEN LOOP;
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

    public synchronized void setVelocitySetpoint(double leftSpeed, double rightSpeed) {
        if(mDriveControlState != DriveControlState.VELOCITY_SETPOINT) {
            SET UP VELOCITY SETPOINT;
            mDriveControlState = DriveControlState.VELOCITY_SETPOINT;
        }
        updateVelocitySetpoint(leftSpeed, rightSpeed);
    }

    private synchronized void updateVelocitySetpoint(double leftSpeed, double rightSpeed) {
        if(mDriveControlState != DriveControlState.VELOCITY_SETPOINT) {
            DO SPEED PID SHIT;
        } else {
            System.out.println("Hit a bad velocity control state");
            mLeftDrive.set(0);
            mRightDrive.set(0);
        }
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

}
