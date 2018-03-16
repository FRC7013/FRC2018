package frc.team7013.robot.commands.drive;


import frc.team7013.robot.Robot;

public class DriveTimeCommand extends TSafeCommand {

    private double driveSpeed;

    public DriveTimeCommand(double speed, double timeout) {
        super(timeout);
        driveSpeed = speed;
        requires(Robot.chassisSubsystem);
    }

    public void initialize() {
        Robot.chassisSubsystem.setSpeed(driveSpeed,driveSpeed);
    }

    public void execute() {
        Robot.chassisSubsystem.setSpeed(driveSpeed,driveSpeed);
    }

    protected boolean isFinished() {
        if(super.isFinished()) {
            return true;
        }
        return false;
    }

}
