package frc.team7013.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.team7013.robot.TPackage.loop.Loop;
import frc.team7013.robot.TPackage.loop.Looper;
import frc.team7013.robot.TPackage.sensors.encoder.TPwmEncoder;
import frc.team7013.robot.TPackage.sensors.limitSwitch.TLimitSwitch;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.robot.ElevatorArmConst;
import frc.team7013.robot.robot.RobotConst;
import frc.team7013.robot.robot.RobotMap;

public class Lift extends Subsystem {

    private double armPosition;

    private static Lift mInstance;

    public static Lift getInstance() {
        if(mInstance == null) {
            mInstance = new Lift();
        }
        return mInstance;
    }

    private final Elevator mElevator = Elevator.getInstance();
    private final Arm mArm = Arm.getInstance();

    public enum WantedState {
        INTAKE,
        STOW,
        SWITCH,
        SCALE
    }

    private WantedState mWantedState;

    private Loop mLoop = new Loop() {

        @Override
        public void onStart(double timestamp) {
            synchronized (Lift.this) {
                mWantedState = WantedState.SWITCH;
            }
        }

        @Override
        public void onLoop(double timestamp) {
            synchronized (Lift.this) {
                switch(mWantedState) {
                    case INTAKE:
                        mElevator.setPosition(Elevator.WantedPosition.INTAKE);
                        armPosition = ElevatorArmConst.ARM_INTAKE_POSITION;
                        break;
                    case STOW:
                        mElevator.setPosition(Elevator.WantedPosition.STOW);
                        armPosition = ElevatorArmConst.ARM_STOW_POSITION;
                        break;
                    case SWITCH:
                        mElevator.setPosition(Elevator.WantedPosition.SWITCH);
                        armPosition = ElevatorArmConst.ARM_SWITCH_POSITION;
                        break;
                    case SCALE:
                        mElevator.setPosition(Elevator.WantedPosition.SCALE);
                        armPosition = ElevatorArmConst.ARM_SCALE_POSITION;
                        break;
                        default:
                            System.out.println("ERROR: Unexpected lift wanted state: " + mWantedState);
                            mElevator.setPosition(Elevator.WantedPosition.SWITCH);
                            armPosition = ElevatorArmConst.ARM_SWITCH_POSITION;
                }

                if(!mArm.setpointReached()) { //Arm is far from setpoint: wait to home elevator before moving arm
                    if(!mElevator.getIsElevatorHomed()) { //Home elevator
                        armStay();
                        mElevator.setControlType(Elevator.ControlType.HOMING);
                    }
                    else { //Elevator homed, need to rotate arm to desired angle before we extend elevator
                        mElevator.setControlType(Elevator.ControlType.HOMING);
                        setArmSetpoint(wherewewanttobe);
                    }
                }
                else { //Arm is close to setpoint: maintain position and raise elevator
                    setArmSetpoint(wherewewanttobe); //Do this as a state  machine in Arm class with STAY and MOVE States
                    mElevator.setControlType(Elevator.ControlType.PID_CONTROL);
                }

            }
        }

        @Override
        public void onStop(double timestamp) {
            stop();
        }

    };

    @Override
    public void outputToSmartDashboard() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void zeroSensors() {
        //not sure if should zero elevatorEncoder here
    }

    public synchronized void setWantedState(WantedState wanted) {
        mWantedState = wanted;
    }

    public synchronized void reset() {
        mWantedState = WantedState.STOW;
    }




}
