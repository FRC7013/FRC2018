package frc.team7013.robot.commands.drive;

import frc.team7013.robot.Robot;
import frc.team7013.robot.subsystems.ChassisSubsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CharacterizeDriveCommand extends TSafeCommand {

    private double speed;
    private String writeCSVFile = "/home/lvuser/writtenFile.csv";
    private BufferedWriter writer;

    public CharacterizeDriveCommand(double speed, double timeOut) {
        super(timeOut);
        this.speed = -speed;
        try {
            writer = new BufferedWriter(new FileWriter(writeCSVFile));
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize() {
        Robot.chassisSubsystem.disableSpeedPids();
        Robot.chassisSubsystem.setSpeed(speed,speed);
        try {
            writer.write("Left Distance,Right Distance,Left Speed,Right Speed");
            writer.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        Robot.chassisSubsystem.setSpeed(speed,speed);
        double leftDistance = Robot.chassisSubsystem.getLeftEncoderFeetDistance();
        double rightDistance = Robot.chassisSubsystem.getRightEncoderFeetDistance();
        double leftSpeed = Robot.chassisSubsystem.getLeftEncoderFeetSpeed();
        double rightSpeed = Robot.chassisSubsystem.getRightEncoderFeetSpeed();

        String sLeftDistance = String.valueOf(leftDistance);
        String sRightDistance = String.valueOf(rightDistance);
        String sLeftSpeed = String.valueOf(leftSpeed);
        String sRightSpeed = String.valueOf(rightSpeed);

        try {
            writer.write(sLeftDistance + "," + sRightDistance + "," + sLeftSpeed + "," + sRightSpeed);
            writer.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    protected void end() {
        Robot.chassisSubsystem.setSpeed(0.0,0.0);
        try {
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean isFinished() {
        if(super.isFinished()) {
            return true;
        }
        return false;
    }

}
