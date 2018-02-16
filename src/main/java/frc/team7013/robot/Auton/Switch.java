package frc.team7013.robot.Auton;

import frc.team7013.robot.Constants;
import frc.team7013.robot.Drive;
import frc.team7013.robot.Intake;
import frc.team7013.robot.Lift;

public class Switch extends Auto {
    private  Drive robot_drive;
    private  Lift lift;
    private  Intake intake;
    private  int drive_setpoint_index;
    private  boolean switch_location;

    public Switch(Drive robor_drive, Lift lift, Intake intake){
        this.switch_location = false;
        this.robot_drive = robor_drive;
        this.lift = lift;
        this.intake = intake;
        drive_setpoint_index = 0;
    }
    public void doAuto(){

    }
}
