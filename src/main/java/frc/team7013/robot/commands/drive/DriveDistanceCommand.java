package frc.team7013.robot.commands.drive;

import frc.team7013.robot.DriveConst;
import frc.team7013.robot.Robot;

public class DriveDistanceCommand extends DriveDirectionCommand {

    double distance = 0; // in inches
    double stopDistanceEncoderCounts = 0; // encoder counts to stop at

    private final double STOPPING_ENCODER_COUNTS = 20; // to reduce stopping overshoot

    public DriveDistanceCommand(double distance, double direction, double speed,
                                double timeout, boolean brakeWhenFinished) {

        super(direction, speed, timeout, brakeWhenFinished);

        this.distance = distance;
        this.stopDistanceEncoderCounts =
                distance * DriveConst.ENCODER_COUNTS_PER_INCH - STOPPING_ENCODER_COUNTS;
        //System.out.println(stopDistanceEncoderCounts);
    }

    protected void initialize() {
        //System.out.println("starting drive");
        Robot.chassisSubsystem.resetEncoders();
    }

    protected boolean isFinished() {

        if (super.isFinished()) {
            return true;
        }

        if (Robot.chassisSubsystem.getEncoderDistance() > stopDistanceEncoderCounts) {
            //System.out.println("ending drive");
            return true;
        }

        return false;
    }
}