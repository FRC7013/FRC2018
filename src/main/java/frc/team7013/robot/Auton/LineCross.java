package frc.team7013.robot.Auton;

import frc.team7013.robot.Constants;
import frc.team7013.robot.Drive;

public class LineCross extends Auto {

    private Constants constants;
    private Drive robot_drive;
    private int auto_index, drive_setpoint_index;

    public LineCross(Drive robot_drive){
        this.robot_drive = robot_drive;
        auto_index = 0;
        drive_setpoint_index = 0;
    }

    @Override
    public void doAuto() {
        robot_drive.setLinearSetpoint(constants.line_cross_drive_setpoints[drive_setpoint_index]);
        if (robot_drive.doAutoLinearDrive())
            auto_index++;
        }
    }

