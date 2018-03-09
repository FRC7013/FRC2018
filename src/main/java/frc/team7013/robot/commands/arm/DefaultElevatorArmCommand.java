package frc.team7013.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.team7013.robot.robot.Robot;

public class DefaultElevatorArmCommand extends Command {

    public DefaultElevatorArmCommand() {
        requires(Robot.elevatorArmSubsystem);
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
