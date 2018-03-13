package frc.team7013.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team7013.robot.LiftConst;
import frc.team7013.robot.Robot;
import frc.team7013.robot.subsystems.ArmSubsystem;

public class DefaultArmCommand extends Command {

    public DefaultArmCommand() {
        requires(Robot.armSubsystem);
    }

    @Override
    public void initialize() {

    }

    protected void execute() {
        if(Robot.oi.getIntakeButton()) {
            Robot.armSubsystem.setArmPosition(LiftConst.LIFT_POSITION.INTAKE);
        }
        if(Robot.oi.getSwitchButton()) {
            Robot.armSubsystem.setArmPosition(LiftConst.LIFT_POSITION.SWITCH);
        }
        if(Robot.oi.getScaleButton()) {
            Robot.armSubsystem.setArmPosition(LiftConst.LIFT_POSITION.SCALE);
        }
        if(Robot.oi.getStowButton()) {
            Robot.armSubsystem.setArmPosition(LiftConst.LIFT_POSITION.STOW);
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
