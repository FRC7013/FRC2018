package frc.team7013.robot.commands.drive;

import frc.team7013.robot.DriveConst;
import frc.team7013.robot.Robot;

public class BackupCommand extends TSafeCommand {

    private final double distanceEncoderCounts;

    public BackupCommand(double distance) {
        super(5.0);
        this.distanceEncoderCounts =
                Math.abs(distance) * DriveConst.ENCODER_COUNTS_PER_INCH;
    }

    protected void initialize() {
        super.initialize();
        Robot.chassisSubsystem.resetEncoders();
        Robot.chassisSubsystem.setSpeed(-.25, -.25);
    }

    protected boolean isFinished() {

        if (super.isFinished()) {
            return true;
        }

        if (-Robot.chassisSubsystem.getEncoderDistance() < -distanceEncoderCounts) {
            return true;
        }

        return false;
    }
}