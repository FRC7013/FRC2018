package frc.team7013.robot.commands.drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team7013.robot.Robot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PathCommand extends Command { //Does this run at 200Hz???

    private double kV = -1.0 / 11.39;
    private double kA = -0.06;
    private double kP =  2.3;

    private String leftcsvFile;
    private String rightcsvFile;
    private String lineLeft = "";
    private String lineRight = "";
    private String csvSplitBy = ",";
    private BufferedReader brleft = null;
    private BufferedReader brright = null;
    private boolean intake;

    public enum DIRECTION {
        FORWARDS,
        BACKWARDS
    }

    DIRECTION direction;

    public PathCommand(String leftCsvFile, String rightCsvFile, DIRECTION Direction, boolean intakeOn) {
        requires(Robot.chassisSubsystem);

        intake = intakeOn;
        leftcsvFile = leftCsvFile;
        rightcsvFile = rightCsvFile;

        direction = Direction;

        try {
            brleft = new BufferedReader(new FileReader(leftcsvFile));
            brright = new BufferedReader(new FileReader(rightcsvFile));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        Robot.chassisSubsystem.resetEncoders();
        try {
            if (((lineLeft = brleft.readLine()) != null) && ((lineRight = brright.readLine()) != null)) {
                String[] dataLeft = lineLeft.split(csvSplitBy);
                String[] dataRight = lineRight.split(csvSplitBy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute() {

    }

    protected void end() {

    }

    protected boolean isFinished() {
        try {
            if (((lineLeft = brleft.readLine()) != null) && ((lineRight = brright.readLine()) != null)) {

                String[] dataLeft = lineLeft.split(csvSplitBy);
                double positionLeft = Double.parseDouble(dataLeft[3]);
                double velocityLeft = Double.parseDouble(dataLeft[4]);
                double accelLeft = Double.parseDouble(dataLeft[5]);
                System.out.println("Left Position: " + positionLeft + ", Left Velocity: " + velocityLeft + "System Time: " + Timer.getFPGATimestamp());

                String[] dataRight = lineRight.split(csvSplitBy);
                double positionRight = Double.parseDouble(dataRight[3]);
                double velocityRight = Double.parseDouble(dataRight[4]);
                double accelRight = Double.parseDouble(dataRight[5]);
                System.out.println("Right Position: " + positionRight + ", Right Velocity: " + velocityRight + "System Time: " + Timer.getFPGATimestamp());

                double actualPositionLeft = Robot.chassisSubsystem.getLeftEncoderFeetDistance();
                double actualPositionRight = Robot.chassisSubsystem.getRightEncoderFeetDistance();

                double leftPositionError = positionLeft - actualPositionLeft;
                double rightPositionError = positionRight - actualPositionRight;
                System.out.println("Left error:" + leftPositionError);

                if(direction == DIRECTION.FORWARDS) {
                    Robot.chassisSubsystem.setSpeed(velocityLeft * kV + accelLeft*kA - kP*leftPositionError, velocityRight * kV  + accelRight*kA - kP*rightPositionError);
                    if(intake) {
                        Robot.chassisSubsystem.setIntakeReverseAuto();
                    } else {
                        Robot.chassisSubsystem.setIntakeOffAuto();
                    }
                    return false;
                }
                if(direction == DIRECTION.BACKWARDS) {
                    Robot.chassisSubsystem.setSpeed(-(velocityLeft * kV - kP*leftPositionError), -(velocityRight * kV - kP*rightPositionError));
                    return false;
                }

            }

            if ((brleft != null) || (brright != null)) {
                try {
                    brleft.close();
                    brright.close();
                    Robot.chassisSubsystem.setSpeed(0.0, 0.0);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Robot.chassisSubsystem.setSpeed(0.0,0.0);
        System.out.println("ERROR: Unexpected system state: PathCommand isFinished()");
        return false;
    }

}
