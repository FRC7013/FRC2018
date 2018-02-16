package frc.team7013.robot.Auton;

import frc.team7013.robot.Constants;
import frc.team7013.robot.Drive;

public class LineCross {

    private static Constants constants;
    private static Drive robot_drive;
    private static int auto_index, drive_setpoint_index;

    LineCross(Drive robot_drive){
        this.robot_drive = robot_drive;
        auto_index = 0;
        drive_setpoint_index = 0;
    }
    public static void doLineCross() {
        switch (auto_index) {
            case 0: //go to a fixed position and stop
                robot_drive.setLinearSetpoint(constants.line_cross_drive_setpoints[drive_setpoint_index]);
                if (robot_drive.doAutoLinearDrive())
                    auto_index++;
                break;
        }
    }
}
