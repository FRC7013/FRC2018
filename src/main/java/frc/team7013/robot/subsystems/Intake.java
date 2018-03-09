package frc.team7013.robot.subsystems;

import frc.team7013.robot.TPackage.loop.Loop;
import frc.team7013.robot.TPackage.loop.Looper;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.robot.RobotConst;
import frc.team7013.robot.robot.RobotMap;

public class Intake extends Subsystem{

    private static Intake mInstance;

    public static Intake getInstance() {
        if(mInstance == null) {
            mInstance = new Intake();
        }
        return mInstance;
    }

    public enum WantedState {
        ACQUIRE,
        SCORE,
        HOLD
    }

    private enum SystemState {
        INTAKE,
        EXHAUST,
        STOWED
    }

    private final TPwmSpeedController leftIntake, rightIntake;

    private WantedState mWantedState;
    private SystemState mSystemState;

    private Intake() {
        leftIntake = new TPwmSpeedController(TPwmSpeedControllerType.SPARK,RobotMap.LEFT_INTAKE_MOTOR_PORT,RobotConst.LEFT_INTAKE_MOTOR_INVERSION);
        rightIntake = new TPwmSpeedController(TPwmSpeedControllerType.SPARK,RobotMap.RIGHT_INTAKE_MOTOR_PORT,RobotConst.RIGHT_INTAKE_MOTOR_INVERSION);
    }

    @Override
    public void outputToSmartDashboard() {
    }

    @Override
    public void stop() {
        setWantedState(WantedState.HOLD);
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        Loop loop = new Loop() {
            private double mCurrentStateStartTime;

            @Override
            public void onStart(double timestamp) {
                synchronized (Intake.this) {
                    mSystemState = SystemState.STOWED;
                    mWantedState = WantedState.HOLD;
                }
            }

            @Override
            public void onLoop(double timestamp) {
                synchronized (Intake.this) {
                    SystemState newState;
                    switch (mSystemState) {
                        case INTAKE:
                            newState = handleIntaking();
                            break;
                        case EXHAUST:
                            newState = handleExhausting();
                            break;
                        case STOWED:
                            newState = handleStowing();
                            break;
                            default:
                                System.out.println("ERROR: Unexpected intake system state: " + mSystemState);
                                newState = mSystemState;
                                break;
                    }

                    if (newState != mSystemState) {
                        System.out.println(timestamp + ": Changed state: " + mSystemState + " -> " + newState);
                        mSystemState = newState;
                    }
                }
            }

            @Override
            public void onStop(double timestamp) {
                mWantedState = WantedState.HOLD;
                mSystemState = SystemState.STOWED;
                stop();
            }
        };
        enabledLooper.register(loop);
    }

    //handles
    private SystemState handleStowing() {
        setIntakeMotors(-0.3);
        switch(mWantedState) {
            case ACQUIRE:
                return SystemState.INTAKE;
            case SCORE:
                return SystemState.EXHAUST;
                default:
                    return SystemState.STOWED;
        }
    }

    private SystemState handleIntaking() {
        setIntakeMotors(1.0);
        switch(mWantedState) {
            case HOLD:
                return SystemState.STOWED;
            default:
                return SystemState.INTAKE;
        }
    }

    private SystemState handleExhausting() {
        setIntakeMotors(-1.0);
        switch (mWantedState) {
            case HOLD:
                return SystemState.STOWED;
            case ACQUIRE:
                return SystemState.INTAKE;
                default:
                    return SystemState.EXHAUST;
        }
    }

    public synchronized void setWantedState(WantedState wanted) {
        mWantedState = wanted;
    }

    public synchronized void reset() {
        mWantedState = WantedState.HOLD;
        mSystemState = SystemState.STOWED;
    }

    private void setIntakeMotors(double value) {
        leftIntake.set(value);
        rightIntake.set(value);
    }

}
