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

    public Auto(int AutonSelector, Drive drive, Lift lift, Intake intake){
        switch(AutonSelector){
            case 1:
                line_cross = new LineCross(drive);
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            default:

                break;
        }
    }
    public static void doAuto(){

    }

}
