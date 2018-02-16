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

    private  Drive drive;
    private  Lift lift;
    private  Intake intake;
    private  boolean location = false;
    public  SendableChooser<Auto> autonSelector;


    public SmartDashThing(Drive drive, Lift lift, Intake intake){
        this.lift = lift;
        this.drive = drive;
        this.intake = intake;
        autonSelector = new SendableChooser<>();
        autonSelector.addDefault("Do nothing", new Auto());
        autonSelector.addObject("Line Cross", new LineCross(drive));
        autonSelector.addObject("Switch", new Switch(drive, lift, intake));
        autonSelector.addObject("Scale", new Scale(drive, lift, intake));
        autonSelector.addObject("Scale And Switch", new ScaleAndSwitch(drive, lift, intake));
    } //done
    public  void updateDash(){
        //Pre Match-stuff
        SmartDashboard.putBoolean("Arm In Starting Position", lift.getArmAngle() == 90);
        SmartDashboard.putData("Auton Selector",autonSelector);
        //intake
        //SmartDashboard.putBoolean("Has Cube Left:", intake.get_has_cube_left());
        //SmartDashboard.putBoolean("Has Cube Right", intake.get_has_cube_right());
        //lift
        SmartDashboard.putBoolean("Manual Mode", lift.getManualIndicator());
        SmartDashboard.putString("Arm Location:", lift.getPositionIndicator());
        SmartDashboard.putNumber("Arm Angle", lift.getArmAngle());
        SmartDashboard.putNumber("Arm Raw:", lift.getArmRaw());
        SmartDashboard.putNumber("Telescope Position:", lift.getTelescopePot());
        //drive
        SmartDashboard.putBoolean("Speed Multiplier:", drive.getSpeedMultiplier());
    } //done
}
