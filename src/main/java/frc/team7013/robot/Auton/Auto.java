package frc.team7013.robot.Auton;

import frc.team7013.robot.Drive;
import frc.team7013.robot.Intake;
import frc.team7013.robot.Lift;

public class Auto {

    //All of our autonomous routines will start to the right of the vault (from the
    // ds perspective). The robot will be flat against the wall, and the seam
    // between the ds and the opposing alliance portal will be our reference point.

    private static LineCross line_cross;
    private static Scale scale;
    private static Switch _switch;
    private static ScaleAndSwitch scale_switch;
    private static int auto_index;

    public Auto(){
    }
    public Auto(LineCross line_cross){
        this.line_cross = line_cross;
        auto_index = 1;
    }
    public Auto(Scale scale){
        this.scale = scale;
        auto_index = 2;
    }
    public Auto(Switch _switch){
        this._switch = _switch;
        auto_index = 3;
    }
    public Auto(ScaleAndSwitch scale_switch){
        this.scale_switch = scale_switch;
        auto_index = 4;
    }
    public static void doAuto(){

    }

}
