package frc.team7013.robot;

public class Constants {

    //Joystick
    public final static int driver_joy_port = 0;
    public final static int operator_joy_port = 1;

    public final static int joy_button_A = 1;
    public final static int joy_button_B = 2;
    public final static int joy_button_X = 3;
    public final static int joy_button_Y = 4;
    public final static int joy_button_leftBumper = 5;
    public final static int joy_button_rightBumper = 6;
    public final static int joy_button_Back = 7;
    public final static int joy_button_Start = 8;
    public final static int joy_button_leftStick = 9;
    public final static int joy_button_rightStick = 10;
    public final static int joy_left_X = 0;
    public final static int joy_left_Y = 1;
    public final static int joy_right_X = 4;
    public final static int joy_right_Y = 5;
    public final static int joy_left_trigger = 2;
    public final static int joy_right_trigger = 3;

    public final static double joy_deadzone = .1;

    //Drive
    public final static int sparks_left = 0;
    public final static int sparks_right = 1;
    public final static double drive_Kp =0;
    public final static double drive_Kd = 0;
    public final static double drive_Ki = 0;
    public final static int[] encoder_left = {0,1};
    public final static int[] encoder_right = {2,3};
    public final static double distance_per_tick = (6 * Math.PI)/((50 / 24) * 20); //circumference / ticks per rotation
    public final static int drive_gyro = 0;
    public final static double proportionality = 0.00165*352/360;

    //Lift
    public final static int sparks_arm = 2;
    public final static int sparks_arm2 = 3;
    public final static int talon_telescope = 6;
    public final static int arm_pot = 1;
    public final static int telescope_pot = 2;
    public final static double arm_scaling_factor = (1800 / 5)/(4096/10);
    public final static double telecope_scaling_factor = 0;
    public final static double minimum_length = 27.25;
    public final static double maximum_length = 32;
    public final static int arm_min_angle = -36;
    public final static int arm_val_at_min_angle = 0;

    //Arm PID
    public final static double arm_Kp = 0;
    public final static double arm_Kd = 0;
    public final static double arm_Ki = 0;

    //Telescope PID
    public final static double telescope_Kp = 0;
    public final static double telescope_Kd = 0;
    public final static double telescope_Ki = 0;

    //Intake
    public final static double intake_speed = 1.0;
    public final static int sparks_intake_left = 4;
    public final static int sparks_intake_right = 5;
    public final static int box_button_left = 5;
    public final static int box_button_right = 6;

    //Arm Setpoints, In terms of ticks
    public final static int setpoint_floor_front = 0;
    public final static int setpoint_floor_rear = 0;
    public final static int setpoint_portal_front = 0;
    public final static int setpoint_portal_rear = 0;
    public final static int setpoint_switch_front = 0;
    public final static int setpoint_switch_rear = 0;
    public final static int setpoint_scale_front = 0;
    public final static int setpoint_scale_rear = 0;
    public final static int setpooint_starting_pos = 0;

    //Drive Setpoints, In terms of inches ?? maybe
    public final static double[] line_cross_drive_setpoints = {0.0, 0.0};
    //Encoder Cuttoffs
    public final static int cimcoder_cuttoff = 0;
    public final static int potentiometer_deadzone = 0;

}
