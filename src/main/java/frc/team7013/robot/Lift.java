package frc.team7013.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;

public class Lift {

    private static Constants constants;
    private static VictorSP[] victors_arm;
    private static VictorSP victor_telescope;
    private static Joystick operator_joy;
    private static Encoder encoder_arm, encoder_telescope;
    private static String arm_indicator = "", telescope_indicator = "";

    Lift(){

    }

    public static void zeroEncoder(){

    }
    public static void doLift(){
        if(operator_joy.getRawButtonPressed(constants.joy_button_A)){
            arm_indicator = "Floor";
        }
        else if(operator_joy.getRawButtonPressed(constants.joy_button_B)){
            arm_indicator = "Switch";
        }
        else if(operator_joy.getRawButtonPressed(constants.joy_button_Y)){
            arm_indicator = "Scale";
        }
    }
    public static void doTelescope(){
        if(operator_joy.getRawButtonPressed(constants.joy_button_leftBumper)){
            telescope_indicator = "Down";
        }
        else if(operator_joy.getRawButtonPressed(constants.joy_button_rightBumper)){
            telescope_indicator = "Up";
        }
    }
    public static void doManual(){

    }
    public static int getEncoderArm(){ return encoder_arm.get();  }
    public static int getEncoderTelescope(){ return encoder_telescope.get(); }
    public static String getArmIndicator(){ return arm_indicator; }
    public static String getTelescopeIndicator(){ return telescope_indicator; }

}
