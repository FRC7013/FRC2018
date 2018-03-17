package frc.team7013.robot.commands;

import frc.team7013.robot.Robot;
import frc.team7013.robot.commands.drive.TSafeCommand;

public class IntakeCubeCommand extends TSafeCommand {

    public IntakeCubeCommand() {
        super(2);
        requires(Robot.chassisSubsystem);
    }

    public void initialize() {
        Robot.chassisSubsystem.setIntakeReverseAuto();
    }

    public void end() {
        Robot.chassisSubsystem.setIntakeOffAuto();
    }

    protected boolean isFinished() {
        if(super.isFinished()) {
            return true;
        }
        return false;
    }

}