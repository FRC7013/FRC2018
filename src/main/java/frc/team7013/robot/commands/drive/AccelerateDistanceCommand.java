package frc.team7013.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team7013.robot.DriveConst;
import frc.team7013.robot.Robot;

public class AccelerateDistanceCommand extends DriveDirectionCommand {

    double distance = 0; // in inches
    double stopDistanceEncoderCounts = 0; // encoder counts to stop at
    double accelerateDistance = 64; //Inches to ramp up over
    double speedSetpoint = 0;

    double inputSpeed;

    private final double STOPPING_ENCODER_COUNTS = 20; // to reduce stopping overshoot

    public AccelerateDistanceCommand(double distance, double direction, double speed,
                                double timeout, boolean brakeWhenFinished) {

        super(direction, speed, timeout, brakeWhenFinished);

        this.inputSpeed = speed;
        this.distance = distance;
        this.stopDistanceEncoderCounts =
                distance * DriveConst.ENCODER_COUNTS_PER_INCH - STOPPING_ENCODER_COUNTS;
        SmartDashboard.putNumber("stopDistanceEncoderCounts",stopDistanceEncoderCounts);
        //System.out.println(stopDistanceEncoderCounts);
    }

    protected void initialize() {
        //System.out.println("starting drive");
        Robot.chassisSubsystem.resetEncoders();
    }

    protected boolean isFinished() {

        if(-Robot.chassisSubsystem.getEncoderDistance()* DriveConst.ENCODER_COUNTS_PER_INCH < accelerateDistance) {
            if(speedSetpoint < inputSpeed) {
                speedSetpoint = speedSetpoint + 0.02;
            }
            else {
                speedSetpoint = inputSpeed;
            }
        }
        if(-Robot.chassisSubsystem.getEncoderDistance()* DriveConst.ENCODER_COUNTS_PER_INCH > (distance - accelerateDistance)) {
            if ((-Robot.chassisSubsystem.getEncoderDistance() * DriveConst.ENCODER_COUNTS_PER_INCH - distance) > 0) {
                speedSetpoint = 0;
            }
            else {
                speedSetpoint = inputSpeed*((distance - (-Robot.chassisSubsystem.getEncoderDistance()* DriveConst.ENCODER_COUNTS_PER_INCH))/accelerateDistance);
            }
        }

        super.setSpeedSetpoint(speedSetpoint);


        if (super.isFinished()) {
            return true;
        }

        SmartDashboard.putNumber("getEncoderDistance",-Robot.chassisSubsystem.getEncoderDistance());
        if (-Robot.chassisSubsystem.getEncoderDistance() > stopDistanceEncoderCounts) {
            //System.out.println("ending drive");
            return true;
        }

        return false;
    }
}