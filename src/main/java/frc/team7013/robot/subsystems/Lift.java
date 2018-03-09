package frc.team7013.robot.subsystems;

import frc.team7013.robot.TPackage.loop.Loop;
import frc.team7013.robot.TPackage.loop.Looper;

public class Lift extends Subsystem {

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

    private WantedState mWantedState = WantedState.SWITCH;

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
                        mArm.setPosition(Arm.WantedPosition.INTAKE);
                        break;
                    case STOW:
                        mElevator.setPosition(Elevator.WantedPosition.STOW);
                        mArm.setPosition(Arm.WantedPosition.STOW);
                        break;
                    case SWITCH:
                        mElevator.setPosition(Elevator.WantedPosition.SWITCH);
                        mArm.setPosition(Arm.WantedPosition.SWITCH);
                        break;
                    case SCALE:
                        mElevator.setPosition(Elevator.WantedPosition.SCALE);
                        mArm.setPosition(Arm.WantedPosition.SCALE);
                        break;
                        default:
                            System.out.println("ERROR: Unexpected LIFT wanted state: " + mWantedState);
                            mElevator.setPosition(Elevator.WantedPosition.SWITCH);
                            mArm.setPosition(Arm.WantedPosition.SWITCH);
                }

                if(!mArm.setpointReached()) { //Arm is far from setpoint: wait to home elevator before moving arm
                    if(!mElevator.getIsElevatorHomed()) { //Home elevator
                        mArm.setControlType(Arm.ControlType.STAY);
                        mElevator.setControlType(Elevator.ControlType.HOMING);
                    }
                    else { //Elevator homed, need to rotate arm to desired angle before we extend elevator
                        mElevator.setControlType(Elevator.ControlType.HOMING);
                        mArm.setControlType(Arm.ControlType.PID_CONTROL); //Position already set
                    }
                }
                else { //Arm is close to setpoint: maintain position and raise elevator
                    mArm.setControlType(Arm.ControlType.PID_CONTROL); //Position already set
                    mElevator.setControlType(Elevator.ControlType.PID_CONTROL);
                }

            }
        }

        @Override
        public void onStop(double timestamp) {
            stop();
        }

    };

    public synchronized void setWantedState(WantedState wantedState) {
        mWantedState = wantedState;
    }

    @Override
    public void outputToSmartDashboard() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void zeroSensors() {
    }

    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        enabledLooper.register(mLoop);
    }




}
