package frc.team7013.robot.Util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team7013.robot.*;
import frc.team7013.robot.Auton.*;
import openrio.powerup.MatchData;

import java.util.Locale;

//TODO: Finish the whole thing
public class SmartDashThing {

    private static SmartDashboard smart_dash;
    private static Drive drive;
    private static Lift lift;
    private static Intake intake;
    private static Joystick driver_joy, operator_joy;
    private static boolean location = false;
    public static int autonSelector;

    public SmartDashThing(Drive drive, Lift lift, Intake intake, Joystick driver_joy, Joystick operator_joy){

        this.lift = lift;
        this.drive = drive;
        this.intake = intake;
        this.driver_joy = driver_joy;
        this.operator_joy = operator_joy;
        smart_dash = new SmartDashboard();

    }
    public static void updateDash(){
        autonSelector = (int) smart_dash.getNumber("Auton Selector \n1 - Line Cross \n2 - Switch \n3 - Scale \n4 - Scale and Switch", 0);
        //location
        location = smart_dash.getBoolean("Robot Location", false);//false is to the left of our portal, true is to the right of the portal
        //intake
        smart_dash.putBoolean("Has Cube:", intake.get_has_cube_left()); //fix me
        //lift
        smart_dash.putBoolean("Manual Mode", lift.getManualIndicator());
        smart_dash.putString("Arm Location:", lift.getPositionIndicator());
        smart_dash.putNumber("Arm Position (Degrees):", lift.getArmAngle());
        smart_dash.putNumber("Arm Raw:", lift.getArmRaw());
        smart_dash.putNumber("Telescope Position:", lift.getTelescopePot());
        //drive
        smart_dash.putBoolean("Speed Multiplier:", drive.getSpeedMultiplier());

    }
    public static String
}
