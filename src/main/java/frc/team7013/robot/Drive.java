package frc.team7013.robot;

import edu.wpi.first.wpilibj.*;
import frc.team7013.robot.Auton.Auto;
import frc.team7013.robot.Util.PID;

public class Drive {
    
    private Spark sparksLeft, sparksRight;
    private  Joystick driver_joy ;
    private  double speed_multiplier = 1;
    private  Encoder encoder_left, encoder_right;
    private  double linear_setpoint;
    private  PID pid_drive;
    public static AnalogGyro drive_gyro = new AnalogGyro(Constants.drive_gyro);


    //constructors
    Drive(Joystick driver_joy){
        this.driver_joy = driver_joy;
        sparksLeft = new Spark(Constants.sparks_left);
        sparksRight = new Spark(Constants.sparks_right);
        encoder_left = new Encoder(Constants.encoder_left[0], Constants.encoder_left[1]);
        encoder_right = new Encoder(Constants.encoder_right[0], Constants.encoder_right[1], true);
        linear_setpoint = 0;
        pid_drive = new PID(Constants.drive_Kp, Constants.drive_Kd, Constants.drive_Ki, Constants.cimcoder_cuttoff);
        pid_drive.newSetpoint(0);
        encoder_right.setDistancePerPulse(Constants.distance_per_tick);
        encoder_left.setDistancePerPulse(Constants.distance_per_tick);
        drive_gyro.initGyro();
        drive_gyro.calibrate();
        drive_gyro.reset();
        drive_gyro.setSensitivity(Constants.proportionality);

    } //done

    //functionality
    public void doDrive(){
        toggleSpeed();
        double right = driver_joy.getRawAxis(Constants.joy_left_Y);
        double left = driver_joy.getRawAxis(Constants.joy_right_Y);

        sparksLeft.set(((Math.abs(left)>Constants.joy_deadzone)?left:0)/speed_multiplier);
        sparksRight.set(-((Math.abs(right)>Constants.joy_deadzone)?right:0)/speed_multiplier);
    } //done
    public boolean doAutoLinearDrive(){ //TODO: I really need to be tested
        pid_drive.newSetpoint(linear_setpoint);
        sparksLeft.set(pid_drive.getOutput());
        sparksRight.set(-pid_drive.getOutput());
        return pid_drive.doPID((int)Math.round(encoder_left.getDistance() + encoder_right.getDistance()/2));
    }
    //public  boolean doAutoRotationDrive(){//TODO: I'm going to be a bitch to make nice
    // }
    private void toggleSpeed(){
        if(Math.abs(driver_joy.getRawAxis(Constants.joy_left_trigger))>.5)
            speed_multiplier = 2;
        else if(Math.abs(driver_joy.getRawAxis(Constants.joy_right_trigger))>.5)
            speed_multiplier = 1;
    } //done

    //get and sets
    public void zeroDriveEncoders(){ encoder_right.reset(); encoder_left.reset(); }
    public void setLinearSetpoint(double linear_setpoint){ this.linear_setpoint = linear_setpoint; }
    public int getEncoderLeft(){ return encoder_left.get(); }
    public int getEncoderRight(){ return  encoder_right.get(); }
    public boolean getSpeedMultiplier(){ return speed_multiplier == 2; }
    public double getSpeed(){ return (encoder_left.getRate() + encoder_right.getRate())/2; }
    public double getGyro(){ return drive_gyro.getAngle(); }
}
