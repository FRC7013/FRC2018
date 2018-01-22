package frc.team7013.robot.Util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team7013.robot.*;
import openrio.powerup.MatchData;

public class SmartDashThing {

    private static SmartDashboard smart_dash;
    private static Drive drive;
    private static Lift lift;
    private static Intake intake;
    private static Joystick driver_joy, operator_joy;

    SmartDashThing(Drive drive, Lift lift, Intake intake, Joystick driver_joy, Joystick operator_joy){

        this.lift = lift;
        this.drive = drive;
        this.intake = intake;
        this.driver_joy = driver_joy;
        this.operator_joy = operator_joy;

        smart_dash = new SmartDashboard();
    }

    public static void updateDash(){
        boolean[] zerod = {lift.getArmZero(), lift.getTelescopeZero()};
        //intake
        smart_dash.putBoolean("Has Cube:", intake.getHasCube());
        //lift
        smart_dash.putBoolean("Manual Mode", lift.getManualIndicator());
        smart_dash.putString("Arm Location:", lift.getPositionIndicator());
        smart_dash.putNumber("Arm Position:", lift.getEncoderArm());
        smart_dash.putNumber("Telescope Position:", lift.getEncoderTelescope());
        smart_dash.putBooleanArray("Lift Zero:", zerod);
        //drive
        smart_dash.putBoolean("Speed Multiplier:", drive.getSpeedMultiplier());

    }
}
