package frc.team7013.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;

public class Drive {

    private static Constants constants;
    private static Spark sparksLeft, sparksRight;
    private static Joystick driver_joy ;
    private static double speed_multiplier = 1;
    private static Encoder encoder_left, encoder_right;

    Drive(Joystick driver_joy){
            sparksLeft = new Spark(constants.sparks_left);
            sparksRight = new Spark(constants.sparks_right);

        this.driver_joy = driver_joy;
        encoder_left = new Encoder(constants.encoder_left[0], constants.encoder_left[1]);
        encoder_right = new Encoder(constants.encoder_right[0], constants.encoder_right[1]);

    }
    public static void doDrive(){
        toggleSpeed();
        double left = driver_joy.getRawAxis(constants.joy_left_Y);
        double right = driver_joy.getRawAxis(constants.joy_right_Y);

        sparksLeft.set(left/speed_multiplier);
        sparksRight.set(right/speed_multiplier);
    }
    public static boolean doPIDDrive(){

        return false;
    }
    private static void toggleSpeed(){
        if(driver_joy.getRawButtonPressed(constants.joy_button_rightBumper))
            speed_multiplier = 2;
        else if(driver_joy.getRawButtonPressed(constants.joy_button_leftBumper))
            speed_multiplier = 1;
    }

    public static int getEncoderLeft(){ return encoder_left.get(); }
    public static int getEncoderRight(){ return  encoder_right.get(); }
    public static boolean getSpeedMultiplier(){ return (speed_multiplier==2)?true:false; }
    public static double getSpeed(){ return (encoder_left.getRate() + encoder_right.getRate())/2; }
}
