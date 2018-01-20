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
    private static Ramps ramps;
    private static Joystick driver_joy, operator_joy;

    SmartDashThing(Drive drive, Lift lift, Intake intake, Ramps ramps){

        this.ramps = ramps;
        this.drive = drive;
        this.intake = intake;
        this.ramps = ramps;
        smart_dash = new SmartDashboard();
    }

    public static void updateDash(){
        smart_dash.putBoolean("Has Cube:", intake.getHasCube());
        smart_dash.putNumber("Arm Position:", lift.getEncoderArm());
        smart_dash.putString("Arm Location:", lift.getArmIndicator());
        smart_dash.putNumber("Telescope Position:", lift.getEncoderTelescope());
        smart_dash.putString("Telescope Location:", lift.getTelescopeIndicator());
        smart_dash.putBoolean("Speed Multiplier:", drive.getSpeedMultiplier());

    }
}
