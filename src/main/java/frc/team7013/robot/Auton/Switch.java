package frc.team7013.robot.Auton;

import frc.team7013.robot.Constants;
import frc.team7013.robot.Drive;
import frc.team7013.robot.Intake;
import frc.team7013.robot.Lift;

public class Switch {

    private static Constants constants;
    private static Drive robot_drive;
    private static Lift lift;
    private static Intake intake;
    private static int auto_index, drive_setpoint_index;
    private static boolean switch_location;

    public Switch(boolean switch_location, Drive robor_drive, Lift lift, Intake intake){
        this.switch_location = switch_location;
        this.robot_drive = robor_drive;
        this.lift = lift;
        this.intake = intake;
        auto_index = 0;
        drive_setpoint_index = 0;
    }
    public static void doSwitch(){
        switch (auto_index){

        }
    }
}
