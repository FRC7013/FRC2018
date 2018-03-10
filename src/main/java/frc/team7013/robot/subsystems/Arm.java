package frc.team7013.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.team7013.robot.TPackage.loop.Loop;
import frc.team7013.robot.TPackage.loop.Looper;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.constants.ElevatorArmConst;
import frc.team7013.robot.constants.RobotConst;
import frc.team7013.robot.constants.RobotMap;

public class Arm extends Subsystem {

    private static Arm mInstance;

    public static Arm getInstance() {
        if(mInstance == null) {
            mInstance = new Arm();
        }
        return mInstance;
    }

    public enum ControlType {
        PID_CONTROL,
        STOP,
        STAY
    }

    public enum WantedPosition {
        INTAKE,
        STOW,
        SWITCH,
        SCALE
    }

    private ControlType mControlType;
    private WantedPosition mWantedPosition;

    private final TPwmSpeedController armMotor1,armMotor2;
    private final AnalogPotentiometer armPotentiometer;

    private double armSetpoint;
    private double previousSetpoint;
    private double integral = 0;
    private double previousError;

    private Arm() {
        armMotor1 = new TPwmSpeedController(TPwmSpeedControllerType.SPARK, RobotMap.ARM_MOTOR_PORT_1, RobotConst.ARM_MOTOR_1_INVERSION);
        armMotor2 = new TPwmSpeedController(TPwmSpeedControllerType.SPARK,RobotMap.ARM_MOTOR_PORT_2, RobotConst.ARM_MOTOR_2_INVERSION);
        armPotentiometer = new AnalogPotentiometer(RobotMap.ARM_POTENTIOMETER_ANALOG_PORT); //add inversion parameter
    }

    @Override
    public synchronized void outputToSmartDashboard() {
    }

    @Override
    public synchronized void stop() {
        setControlType(ControlType.STOP);
    }

    @Override
    public synchronized void zeroSensors() {
        //Don't do anything
    }

    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        enabledLooper.register(new Loop() {
            @Override
            public void onStart(double timestamp) {
                synchronized (Arm.this) {
                    armSetpoint = getArmPosition();
                    previousError = getArmPosition() - armSetpoint;
                    mControlType = ControlType.STOP;
                    mWantedPosition = WantedPosition.STOW;
                }
            }

            @Override
            public void onLoop(double timestamp) {
                synchronized (Arm.this) {
                    System.out.println("Arm alive!");
                    switch(mWantedPosition) {
                        case INTAKE:
                            armSetpoint = ElevatorArmConst.ARM_INTAKE_POSITION;
                            break;
                        case STOW:
                            armSetpoint = ElevatorArmConst.ARM_STOW_POSITION;
                            break;
                        case SWITCH:
                            armSetpoint = ElevatorArmConst.ARM_SWITCH_POSITION;
                            break;
                        case SCALE:
                            armSetpoint = ElevatorArmConst.ARM_SCALE_POSITION;
                            break;
                            default:
                                System.out.println("ERROR: Unexpected arm position: " + mWantedPosition);
                                armSetpoint = ElevatorArmConst.ARM_INTAKE_POSITION;
                                break;
                    }

                    ControlType newState = mControlType;
                    switch(mControlType) {
                        case PID_CONTROL:
                            handlePIDControl();
                            break;
                        case STAY:
                            armSetpoint = getClosestPosition();
                            handlePIDControl();
                            break;
                        case STOP:
                            handleStop();
                            break;
                        default:
                            System.out.println("ERROR: Unexpected arm control type: " + mControlType);
                            handleStop();
                            break;
                    }


                }
            }

            @Override
            public void onStop(double timestamp) {
                mControlType = ControlType.STOP;
                stop();
            }
        });
    }

    private double getClosestPosition() {
        double position = getArmPosition();

        double intakeDistance = Math.abs(position - ElevatorArmConst.ARM_INTAKE_POSITION);
        double stowDistance = Math.abs(position - ElevatorArmConst.ARM_STOW_POSITION);
        double switchDistance = Math.abs(position - ElevatorArmConst.ARM_SWITCH_POSITION);
        double scaleDistance = Math.abs(position - ElevatorArmConst.ARM_SCALE_POSITION);

        if((intakeDistance < stowDistance) && (intakeDistance < switchDistance) && (intakeDistance < scaleDistance)) {
            return ElevatorArmConst.ARM_INTAKE_POSITION;
        }
        if((stowDistance < intakeDistance) && (stowDistance < switchDistance) && (stowDistance < scaleDistance)) {
            return ElevatorArmConst.ARM_STOW_POSITION;
        }
        if((switchDistance < intakeDistance) && (switchDistance < stowDistance) && (switchDistance < scaleDistance)) {
            return ElevatorArmConst.ARM_SWITCH_POSITION;
        }
        if((scaleDistance < intakeDistance) && (scaleDistance < stowDistance) && (scaleDistance < switchDistance)) {
            return ElevatorArmConst.ARM_SCALE_POSITION;
        }
        return ElevatorArmConst.ARM_INTAKE_POSITION;
    }

    //Handles
    private void handlePIDControl() {
        setArmMotors(getArmPIDOutput());
    }

    private void handleStop() {
        setArmMotors(0.0);
    }

    public synchronized void setControlType(ControlType type) {
        mControlType = type;
    }

    public synchronized void setPosition(WantedPosition position) {
        mWantedPosition = position;
    }

    public synchronized boolean setpointReached() {
        return (Math.abs(armSetpoint - getArmPosition()) < 0.1);
    }

    private double getArmPIDOutput() {
        double outputValue = 0;

        double error = getArmPosition() - armSetpoint;

        integral += error*0.005; //Multiply by time interval (1/200 s)

        double derivative = (error - previousError) / 0.005;
        previousError = error;

        outputValue = error*ElevatorArmConst.ARM_KP + integral*ElevatorArmConst.ARM_KI + derivative*ElevatorArmConst.ARM_KD;

        if(outputValue > ElevatorArmConst.ARM_MAX_SPEED) { outputValue = ElevatorArmConst.ARM_MAX_SPEED; }
        if(outputValue < -ElevatorArmConst.ARM_MAX_SPEED) { outputValue = -ElevatorArmConst.ARM_MAX_SPEED; }

        return outputValue;
    }

    private double getArmPosition() {
        double position = (getArmPotentiometer() - ElevatorArmConst.ARM_POTENTIOMETER_MIN) /
                (ElevatorArmConst.ARM_POTENTIOMETER_MAX - ElevatorArmConst.ARM_POTENTIOMETER_MIN);
        return position;
    }

    private double getArmPotentiometer() {
        double value = armPotentiometer.get();
        if(RobotConst.ARM_POTENTIOMETER_INVERSION) {
            value = -value;
        }
        return value;
    }

    private void setArmMotors(double value) {
        armMotor1.set(value);
        armMotor2.set(value);
    }

}
