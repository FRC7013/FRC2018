package frc.team7013.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team7013.robot.TPackage.loop.Looper;
import frc.team7013.robot.constants.RobotConst;
import frc.team7013.robot.oi.OI;
import frc.team7013.robot.oi.GameData;
import frc.team7013.robot.subsystems.*;
import frc.team7013.robot.subsystems.Intake.WantedState;

import java.util.Arrays;

public class Robot extends IterativeRobot {

    CHECK THAT DRIVE INITIALIZATION IS CORRECT;

    private static OI oi;

    //Get subsystem instances
    private Intake mIntake = Intake.getInstance();
    private Lift mLift = Lift.getInstance();
    private Drive mDrive = Drive.getInstance();

    //Create subsystem manager
    private final SubsystemManager mSubsystemManager = new SubsystemManager(
            Arrays.asList(Intake.getInstance(),Lift.getInstance(), Elevator.getInstance(), Arm.getInstance(),Drive.getInstance())
    );

    private Looper mEnabledLooper = new Looper();

    private Robot() {

    }

    public void zeroAllSensors(){
        mSubsystemManager.zeroSensors();
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        oi = new OI();
        mSubsystemManager.registerEnabledLoops(mEnabledLooper);
        zeroAllSensors();
    }

    @Override
    public void autonomousInit() {

        // Initialize the game data
        GameData.init();

        zeroAllSensors();
        mLift.setWantedState(Lift.WantedState.SWITCH);
        mIntake.reset();
        mIntake.setWantedState(WantedState.HOLD);

        mEnabledLooper.start();

    }

    @Override
    public void autonomousPeriodic() {

        // Update the OI before running the commands
        oi.updatePeriodic();

        allPeriodic();
    }

    @Override
    public void teleopInit() {
        mEnabledLooper.start();
        zeroAllSensors();
    }

    @Override
    public void teleopPeriodic() {

        // Update the OI before running the commands
        oi.updatePeriodic();

        //Drive base
        double leftThrottle = oi.getLeftSpeed();
        double rightThrottle = oi.getRightSpeed();

        if(RobotConst.TELEOP_DRIVE_IS_SQUARED) { //Square drive if enabled
            leftThrottle = leftThrottle * Math.abs(leftThrottle);
            rightThrottle = rightThrottle * Math.abs(rightThrottle);
        }

        mDrive.setOpenLoop(leftThrottle,rightThrottle);

        boolean intakeCube = oi.getIntake();
        boolean scoreCube = oi.getExtake();

        if(intakeCube) {
            mIntake.setWantedState(WantedState.ACQUIRE);
        } else if(scoreCube) {
            mIntake.setWantedState(WantedState.SCORE);
        } else {
            mIntake.setWantedState(WantedState.HOLD);
        }

        OI.LIFT_POSITION liftPosition = oi.getLiftPosition();
        switch (liftPosition) {
            case INTAKE:
                mLift.setWantedState(Lift.WantedState.INTAKE);
                break;
            case STOW:
                mLift.setWantedState(Lift.WantedState.STOW);
                break;
            case SWITCH:
                mLift.setWantedState(Lift.WantedState.SWITCH);
                break;
            case SCALE:
                mLift.setWantedState(Lift.WantedState.SCALE);
                break;
                default:
                    mLift.setWantedState(Lift.WantedState.INTAKE);
                    break;
        }

        allPeriodic();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
        mEnabledLooper.stop();
        mSubsystemManager.stop();

    }

    @Override
    public void disabledPeriodic() {
        zeroAllSensors();
        allPeriodic();
    }

    @Override
    public void testPeriodic() {
    }

    public void allPeriodic() {
        mSubsystemManager.outputToSmartDashboard();
        mSubsystemManager.writeToLog();
        mEnabledLooper.outputToSmartDashboard();

    }

}