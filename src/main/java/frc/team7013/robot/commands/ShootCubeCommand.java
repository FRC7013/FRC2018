package frc.team7013.robot.commands;

import frc.team7013.robot.Robot;
import frc.team7013.robot.commands.drive.TSafeCommand;
import frc.team7013.robot.subsystems.ChassisSubsystem;

public class ShootCubeCommand extends TSafeCommand {

    public ShootCubeCommand() {
        super(0.5);
        requires(Robot.chassisSubsystem);
    }

    public void initialize() {
        Robot.chassisSubsystem.setIntakeOnAuto();
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
