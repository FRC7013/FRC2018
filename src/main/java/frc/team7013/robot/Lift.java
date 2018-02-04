package frc.team7013.robot;

import edu.wpi.first.wpilibj.*;
import frc.team7013.robot.Util.PID;

public class Lift {

    private static Constants constants;
    private static Spark[] sparks_arm;
    private static VictorSP victor_telescope;
    private static Joystick operator_joy;
    private static Encoder encoder_arm, encoder_telescope;
    private static String position_indicator = "";
    private static boolean manual_indicator = false;
    private static PID pid_arm, pid_telescope;
    private static DigitalInput zero_arm, zero_telescope;

    Lift(Joystick operator_joy){

    }

    public static void zeroEncoders(){
        if(operator_joy.getRawButtonPressed(constants.joy_button_leftStick)&&operator_joy.getRawButtonPressed(constants.joy_button_rightStick)){
            encoder_arm.reset();
            encoder_telescope.reset();
        }
    }
    public static boolean doLift(){
        if(!operator_joy.getRawButtonPressed(constants.joy_button_Back)){ //front to position
            if(operator_joy.getRawButtonPressed(constants.joy_button_A)){ //floor
                pid_arm.newSetpoint(constants.setpoint_floor_front);
                position_indicator = "Floor front";
            }
            else if(operator_joy.getRawButtonPressed(constants.joy_button_B)){ //portal

            }
            else if(operator_joy.getRawButtonPressed(constants.joy_button_X)){ //switch

            }
            else if(operator_joy.getRawButtonPressed(constants.joy_button_Y)){ //scale

            }
        }
        else{ //rear to position
            if(operator_joy.getRawButtonPressed(constants.joy_button_A)){ //floor
                pid_arm.newSetpoint(constants.setpoint_floor_rear);
                position_indicator = "Floor rear";
            }
            else if(operator_joy.getRawButtonPressed(constants.joy_button_B)){ //portal

            }
            else if(operator_joy.getRawButtonPressed(constants.joy_button_X)){ //switch

            }
            else if(operator_joy.getRawButtonPressed(constants.joy_button_Y)){ //scale

            }
        }
        zeroEncoders();
        return pid_arm.doPID(encoder_arm.getRaw());

    }
    public static void doManual(){
        if(operator_joy.getRawButtonPressed(constants.joy_button_Start)){ //manual engage
            manual_indicator = true;
            if(operator_joy.getRawButtonPressed(constants.joy_button_leftStick))
                victor_telescope.set(.5);
            else if(operator_joy.getRawButtonPressed(constants.joy_button_rightBumper))
                victor_telescope.set(-.5);
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
    public static String getPositionIndicator(){ return position_indicator; }
    public static boolean getManualIndicator(){ return manual_indicator; }
    public static boolean getArmZero(){ return zero_arm.get(); }
    public static boolean getTelescopeZero(){ return zero_telescope.get(); }

}
