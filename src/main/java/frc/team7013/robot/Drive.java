package frc.team7013.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import frc.team7013.robot.Auton.Auto;
import frc.team7013.robot.Util.PID;

public class Drive {

    private static Constants constants;
    private static Spark sparksLeft, sparksRight;
    private static Joystick driver_joy ;
    private static double speed_multiplier = 1;
    private static Encoder encoder_left, encoder_right;
    private static double linear_setpoint;
    private static PID pid_drive;

   //constructors
    Drive(Joystick driver_joy){
        sparksLeft = new Spark(constants.sparks_left);
        sparksRight = new Spark(constants.sparks_right);
        encoder_left = new Encoder(constants.encoder_left[0], constants.encoder_left[1]);
        encoder_right = new Encoder(constants.encoder_right[0], constants.encoder_right[1], true);
        linear_setpoint = 0;
        this.driver_joy = driver_joy;
        pid_drive = new PID(constants.drive_Kp, constants.drive_Kd, constants.drive_Ki, constants.cimcoder_cuttoff);
        pid_drive.newSetpoint(0);
        encoder_right.setDistancePerPulse(constants.distance_per_tick);
        encoder_left.setDistancePerPulse(constants.distance_per_tick);
    } //done

    //functionality
    public static void doDrive(){
        toggleSpeed();
        double left = driver_joy.getRawAxis(constants.joy_left_Y);
        double right = driver_joy.getRawAxis(constants.joy_right_Y);

        sparksLeft.set(((left>constants.joy_deadzone)?left:0)/speed_multiplier);
        sparksRight.set(-((right>constants.joy_deadzone)?right:0)/speed_multiplier);
    } //done
    public static boolean doAutoLinearDrive(){ //TODO: I really need to be tested
        pid_drive.newSetpoint(linear_setpoint);
        sparksLeft.set(pid_drive.getOutput());
        sparksRight.set(-pid_drive.getOutput());
        return pid_drive.doPID((int)Math.round(encoder_left.getDistance() + encoder_right.getDistance()/2));
    }
    //public static boolean doAutoRotationDrive(){//TODO: I'm going to be a bitch to make nice
    // }
    private static void toggleSpeed(){
        if(driver_joy.getRawButtonPressed(constants.joy_button_rightBumper))
            speed_multiplier = 2;
        else if(driver_joy.getRawButtonPressed(constants.joy_button_leftBumper))
            speed_multiplier = 1;
    } //done

    //get and sets
    public void zeroDriveEncoders(){ encoder_right.reset(); encoder_left.reset(); }
    public void setLinearSetpoint(double linear_setpoint){ this.linear_setpoint = linear_setpoint; }
    public static int getEncoderLeft(){ return encoder_left.get(); }
    public static int getEncoderRight(){ return  encoder_right.get(); }
    public static boolean getSpeedMultiplier(){ return (speed_multiplier==2)?true:false; }
    public static double getSpeed(){ return (encoder_left.getRate() + encoder_right.getRate())/2; }
}
