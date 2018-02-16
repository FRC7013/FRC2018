package frc.team7013.robot.Util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team7013.robot.*;
import frc.team7013.robot.Auton.*;
import openrio.powerup.MatchData;

import java.util.Locale;

public class SmartDashThing {

    private static SmartDashboard smart_dash;
    private static Drive drive;
    private static Lift lift;
    private static Intake intake;
    private static boolean location = false;
    public static int autonSelector;

    public SmartDashThing(Drive drive, Lift lift, Intake intake){
        this.lift = lift;
        this.drive = drive;
        this.intake = intake;
        smart_dash = new SmartDashboard();
    } //done
    public static void updateDash(){
        //Pre Match-stuff
        smart_dash.putBoolean("Arm In Starting Position", (lift.getArmAngle() == 90)?true:false);
        autonSelector = (int) smart_dash.getNumber("Auton Selector \n1 - Line Cross \n2 - Switch \n3 - Scale \n4 - Scale and Switch", 0);

        //intake
        smart_dash.putBoolean("Has Cube Left:", intake.get_has_cube_left());
        smart_dash.putBoolean("Has Cube Right", intake.get_has_cube_right());
        //lift
        smart_dash.putBoolean("Manual Mode", lift.getManualIndicator());
        smart_dash.putString("Arm Location:", lift.getPositionIndicator());
        smart_dash.putNumber("Arm Angle", lift.getArmAngle());
        smart_dash.putNumber("Arm Raw:", lift.getArmRaw());
        smart_dash.putNumber("Telescope Position:", lift.getTelescopePot());
        //drive
        smart_dash.putBoolean("Speed Multiplier:", drive.getSpeedMultiplier());
    } //done
}
