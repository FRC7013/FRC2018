package frc.team7013.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.team7013.robot.robot.Robot;
import frc.team7013.robot.robot.RobotConst;

public class DefaultChassisCommand extends Command{

    public DefaultChassisCommand() {
        requires(Robot.chassisSubsystem);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {

        if (Robot.oi.getSpeedPidEnabled()) {
            Robot.chassisSubsystem.enableSpeedPids();
        }
        else {
            Robot.chassisSubsystem.disableSpeedPids();
        }

        double leftDriveSpeed = 0;
        double rightDriveSpeed = 0;

        leftDriveSpeed = Robot.oi.getLeftSpeed();
        rightDriveSpeed = Robot.oi.getRightSpeed();

        leftDriveSpeed = scaleValue(leftDriveSpeed);
        rightDriveSpeed = scaleValue(rightDriveSpeed);

        Robot.chassisSubsystem.setSpeed(leftDriveSpeed,rightDriveSpeed);
    }

    private double scaleValue(double value) {
        double outputValue = value;

        if(RobotConst.TELEOP_DRIVE_IS_SQUARED) {
            outputValue = value*Math.abs(value);
        }
        else {
            outputValue = value;
        }
        return value;
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
