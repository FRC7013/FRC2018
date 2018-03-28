package frc.team7013.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.team7013.robot.DriveConst;
import frc.team7013.robot.Robot;

public class DefaultChassisCommand extends Command {

    public DefaultChassisCommand() {
        requires(Robot.chassisSubsystem);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.chassisSubsystem.disableSpeedPids();

        double leftSpeed = Robot.oi.getLeftSpeed();
        double rightSpeed = Robot.oi.getRightSpeed();

        leftSpeed = scaleValue(leftSpeed);
        rightSpeed = scaleValue(rightSpeed);

        if(Robot.oi.getDriveHalfSpeed()) {
            leftSpeed = leftSpeed * 0.5;
            rightSpeed = rightSpeed * 0.5;
        }

        Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
    }

    private double scaleValue(double value) {
        if(DriveConst.DRIVE_SQUARED) {
            value = value * Math.abs(value);
        }
        value = value*DriveConst.TELEOP_MAX_DRIVE_SPEED;
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
