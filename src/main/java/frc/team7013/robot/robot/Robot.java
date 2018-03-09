package frc.team7013.robot.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team7013.robot.TPackage.loop.CrashTracker;
import frc.team7013.robot.TPackage.loop.Looper;
import frc.team7013.robot.commands.AutonomousCommand;
import frc.team7013.robot.oi.OI;
import frc.team7013.robot.oi.GameData;
import frc.team7013.robot.subsystems.Intake;
import frc.team7013.robot.subsystems.SubsystemManager;
import frc.team7013.robot.subsystems.Intake.WantedState;

import java.util.Arrays;

public class Robot extends IterativeRobot {

    public static OI oi;

    //Get subsystem instances
    private Intake mIntake = Intake.getInstance();

    private final SubsystemManager mSubsystemManager = new SubsystemManager(Arrays.asList(Intake.getInstance()));

    private Looper mEnabledLooper = new Looper();

    private Robot() {

    }

    public void zeroAllSensors() {
        mSubsystemManager.zeroSensors();
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        try() {
            oi = new OI();

            mSubsystemManager.registerEnabledLoops(mEnabledLooper);
        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
        zeroAllSensors();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        updatePeriodic();
    }

    @Override
    public void autonomousInit() {

        // Initialize the game data
        GameData.init();

        // Turn on the drive pids
        Robot.oi.setSpeedPidToggle(true);
        chassisSubsystem.enableSpeedPids();

        // Reset the gyro and the encoders
        Robot.chassisSubsystem.setGyroAngle(0);
        Robot.chassisSubsystem.resetEncoders();

        // Initialize the robot command after initializing the game data
        // because the game data will be used in the auto command.
        autoCommand = new AutonomousCommand();
        autoCommand.start();
    }

    @Override
    public void autonomousPeriodic() {

        // Update the OI before running the commands
        oi.updatePeriodic();

        Scheduler.getInstance().run();

        // Update all subsystems after running commands
        updatePeriodic();
    }

    @Override
    public void teleopInit() {
        try {
            mEnabledLooper.start();
            zeroAllSensors();
        } catch(Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }

        // Turn off the drive PIDs
        // Save the battery in teleop by using the
        // SpeedController built in braking.
        Robot.oi.setSpeedPidToggle(false);
        chassisSubsystem.disableSpeedPids();

    }

    @Override
    public void teleopPeriodic() {

        // Update the OI before running the commands
        oi.updatePeriodic();

        try {
            double timestamp = Timer.getFPGATimestamp();

            //Drive base
            double leftThrottle = oi.getLeftSpeed();
            double rightThrottle = oi.getRightSpeed();

            boolean intakeCube = oi.getIntake();
            boolean scoreCube = oi.getExtake();

            if(intakeCube) {
                mIntake.setWantedState(WantedState.ACQUIRE;
            } else if(scoreCube) {
                mIntake.setWantedState(WantedState.SCORE);
            } else {
                mIntake.setWantedState(WantedState.HOLD);
            }

            allPeriodic();
        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
    }

    @Override
    public void testPeriodic() {
    }

    private void updatePeriodic() {
        chassisSubsystem.updatePeriodic();
        elevatorArmSubsystem.updatePeriodic();
        intakeSubsystem.updatePeriodic();
    }

    public void allPeriodic() {
        mSubsystemManager.outputToSmartDashboard();
        mSubsystemManager.writeToLog();
        mEnabledLooper.outputToSmartDashboard();

    }

}