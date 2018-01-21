package frc.team7013.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import frc.team7013.robot.Util.PID;

public class Lift {

    private static Constants constants;
    private static Spark[] sparks_arm;
    private static Spark spark_telescope;
    private static Joystick operator_joy;
    private static Encoder encoder_arm, encoder_telescope;
    private static String arm_indicator = "", telescope_indicator = "";
    private static boolean manual_indicator = false;
    private static PID pid_arm, pid_telescope;

    Lift(){

    }

    public static void zeroEncoder(){

    }
    public static void doLift(){
        if(!operator_joy.getRawButtonPressed(constants.joy_button_Back)){ //front to position
            if(operator_joy.getRawButtonPressed(constants.joy_button_A)){ //floor
                pid_arm.newSetpoint(constants.setpoint_floor_front);
                arm_indicator = "Floor front";
            }
            else if(operator_joy.getRawButtonPressed(constants.joy_button_B)){ //portal

            }
            else if(operator_joy.getRawButtonPressed(constants.joy_button_X)){ //switch

            }
            else if(operator_joy.getRawButtonPressed(constants.joy_button_Y)){ //scale

            }
        }
        else{ //rear to position

        }
        pid_arm.doPID(encoder_arm.getRaw());
    }
    public static void doManual(){
        if(operator_joy.getRawButtonPressed(constants.joy_button_Start)){ //manual engage
            manual_indicator = true;
            if(operator_joy.getRawButtonPressed(constants.joy_button_leftStick))
                spark_telescope.set(.5);
            else if(operator_joy.getRawButtonPressed(constants.joy_button_rightBumper))
                spark_telescope.set(-.5);
            else if(operator_joy.getRawAxis(constants.joy_right_trigger) > .25){
                for(Spark spark : sparks_arm)
                    spark.set(operator_joy.getRawAxis(constants.joy_right_trigger));
            }
            else if(operator_joy.getRawAxis(constants.joy_left_trigger) > .25){
                for(Spark spark : sparks_arm)
                    spark.set(-operator_joy.getRawAxis(constants.joy_left_trigger));
            }
        }
        else
            manual_indicator = false;
    }
    public static int getEncoderArm(){ return encoder_arm.get();  }
    public static int getEncoderTelescope(){ return encoder_telescope.get(); }
    public static String getArmIndicator(){ return arm_indicator; }
    public static String getTelescopeIndicator(){ return telescope_indicator; }

}
