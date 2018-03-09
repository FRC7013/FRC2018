package frc.team7013.robot.subsystems;

import frc.team7013.robot.TPackage.loop.Loop;
import frc.team7013.robot.TPackage.loop.Looper;
import frc.team7013.robot.TPackage.sensors.encoder.TPwmEncoder;
import frc.team7013.robot.TPackage.sensors.limitSwitch.TLimitSwitch;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.robot.ElevatorArmConst;
import frc.team7013.robot.robot.RobotConst;
import frc.team7013.robot.robot.RobotMap;

public class Elevator extends Subsystem {

    private static Elevator mInstance;

    public static Elevator getInstance() {
        if(mInstance == null) {
            mInstance = new Elevator();
        }
        return mInstance;
    }

    public enum ControlType {
        HOMING,
        PID_CONTROL,
        STOP
    }

    public enum WantedPosition {
        INTAKE,
        STOW,
        SWITCH,
        SCALE
    }

    private ControlType mControlType;
    private WantedPosition mWantedPosition;

    private final TPwmSpeedController elevatorMotor;
    private final TPwmEncoder elevatorEncoder;
    private final TLimitSwitch elevatorLimitSwitch;

    private double elevatorSetpoint;
    private double integral = 0;
    private double previousError;

    private Elevator() {
        elevatorMotor = new TPwmSpeedController(TPwmSpeedControllerType.TALON, RobotMap.ELEVATOR_MOTOR_PORT, RobotConst.ELEVATOR_MOTOR_INVERSION);
        elevatorEncoder = new TPwmEncoder(RobotMap.ELEVATOR_ENCODER_A_DIO_PORT,RobotMap.ELEVATOR_ENCODER_B_DIO_PORT,RobotConst.ELEVATOR_ENCODER_INVERSION);
        elevatorLimitSwitch = new TLimitSwitch(RobotMap.ELEVATOR_LIMIT_SWITCH_DIO_PORT, ElevatorArmConst.LIMIT_SWITCH_DEFAULT_STATE);
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
        //resetElevatorEncoder(); //Not sure if should reset encoder here
    }

    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        enabledLooper.register(new Loop() {
            @Override
            public void onStart(double timestamp) {
                synchronized (Elevator.this) {
                    resetElevatorEncoder();
                    elevatorSetpoint = getElevatorPosition();
                    previousError = getElevatorPosition() - elevatorSetpoint;
                    mControlType = ControlType.STOP;
                    mWantedPosition = WantedPosition.STOW;
                }
            }

            @Override
            public void onLoop(double timestamp) {
                synchronized (Elevator.this) {
                    switch(mWantedPosition) {
                        case INTAKE:
                            elevatorSetpoint = ElevatorArmConst.ELEVATOR_INTAKE_POSITION;
                            break;
                        case STOW:
                            elevatorSetpoint = ElevatorArmConst.ELEVATOR_STOW_POSITION;
                            break;
                        case SWITCH:
                            elevatorSetpoint = ElevatorArmConst.ELEVATOR_SWITCH_POSITION;
                            break;
                        case SCALE:
                            elevatorSetpoint = ElevatorArmConst.ELEVATOR_SCALE_POSITION;
                            break;
                            default:
                                System.out.println("ERROR: Unexpected elevator position: " + mWantedPosition);
                                elevatorSetpoint = ElevatorArmConst.ELEVATOR_INTAKE_POSITION;
                                break;
                    }

                    ControlType newState = mControlType;
                    switch(mControlType) {
                        case STOP:
                            handleStop();
                            break;
                        case HOMING:
                            handleHoming();
                            break;
                        case PID_CONTROL:
                            handlePIDControl();
                            break;
                        default:
                            System.out.println("ERROR: Unexpected elevator control type: " + mControlType);
                            handleStop();
                            break;
                    }

                    if (newState != mControlType) {
                        System.out.println(timestamp + ": Changed state: " + mControlType + " -> " + newState);
                        mControlType = newState;
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

    //handles
    private void handleStop() {
        elevatorMotor.set(0.0);
    }

    private void handleHoming() {
        if(!getElevatorLimitSwitch()) { //Not yet homed
            setElevatorMotor(-0.3);
            resetElevatorEncoder(); //NEED TO RESET HERE, once the limit switch is triggered this code WON'T RUN!
        }
        else { //This code here just in case it does get to this state, but READ ABOVE COMMENT
            setElevatorMotor(0.0);
            resetElevatorEncoder();
        }
    }

    private void handlePIDControl() {
        setElevatorMotor(getElevatorPIDOutput());
    }

    private double getElevatorPIDOutput() {
        double outputValue = 0;

        double error = getElevatorPosition() - elevatorSetpoint;

        integral += error*0.005; //Multiply by time interval (1/200 s)

        double derivative = (error - previousError) / 0.005;
        previousError = error;

        outputValue = error*ElevatorArmConst.ARM_KP + integral*ElevatorArmConst.ARM_KI + derivative*ElevatorArmConst.ARM_KD;

        if(outputValue > ElevatorArmConst.ARM_MAX_SPEED) { outputValue = ElevatorArmConst.ARM_MAX_SPEED; }
        if(outputValue < -ElevatorArmConst.ARM_MAX_SPEED) { outputValue = -ElevatorArmConst.ARM_MAX_SPEED; }

        return outputValue;
    }

    private double getElevatorPosition() {
        return getElevatorEncoder() / ElevatorArmConst.ELEVATOR_ENCODER_MAX;
    }

    public synchronized boolean getIsElevatorHomed() {
        return getElevatorLimitSwitch();
    }


    public synchronized void setControlType(ControlType type) {
        mControlType = type;
    }

    public synchronized void setPosition(WantedPosition position) {
        mWantedPosition = position;
    }

    public synchronized void reset() {
        mControlType = ControlType.STOP;
    }

    private void resetElevatorEncoder() {
        elevatorEncoder.reset();
    }

    private void setElevatorMotor(double value) {
        elevatorMotor.set(value);
    }

    private double getElevatorEncoder() {
        return elevatorEncoder.get();
    }

    private boolean getElevatorLimitSwitch() {
        return elevatorLimitSwitch.atLimit();
    }

}
