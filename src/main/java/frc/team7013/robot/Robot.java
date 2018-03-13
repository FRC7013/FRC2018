package frc.team7013.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team7013.robot.oi.GameData;
import frc.team7013.robot.oi.OI;
import frc.team7013.robot.subsystems.ArmSubsystem;
import frc.team7013.robot.subsystems.ChassisSubsystem;

public class Robot extends IterativeRobot {

    public static final ChassisSubsystem chassisSubsystem = new ChassisSubsystem();
    public static final ArmSubsystem armSubsystem = new ArmSubsystem();
    public static OI oi;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        oi = new OI();

        armSubsystem.init();
        chassisSubsystem.init();

        armSubsystem.setArmPosition(LiftConst.LIFT_POSITION.STOW);
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
        GameData.init();

        chassisSubsystem.enableSpeedPids();

        Robot.chassisSubsystem.setGyroAngle(0);
        Robot.chassisSubsystem.resetEncoders();

        Robot.armSubsystem.resetElevatorEncoder();

    }

    @Override
    public void autonomousPeriodic() {
        oi.updatePeriodic();
        Scheduler.getInstance().run();
        updatePeriodic();
    }

    @Override
    public void teleopInit() {
        chassisSubsystem.disableSpeedPids();
    }

    @Override
    public void teleopPeriodic() {
        oi.updatePeriodic();
        Scheduler.getInstance().run();
        updatePeriodic();
    }

    @Override
    public void testPeriodic() {
    }

    private void updatePeriodic() {
        chassisSubsystem.updatePeriodic();
        armSubsystem.updatePeriodic();
    }

}