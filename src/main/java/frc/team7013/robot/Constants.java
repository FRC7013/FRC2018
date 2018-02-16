package frc.team7013.robot;

public class Constants {

    //Joystick
    public static int driver_joy_port = 0;
    public static int operator_joy_port = 1;

    public static int joy_button_A = 1;
    public static int joy_button_B = 2;
    public static int joy_button_X = 3;
    public static int joy_button_Y = 4;
    public static int joy_button_leftBumper = 5;
    public static int joy_button_rightBumper = 6;
    public static int joy_button_Back = 7;
    public static int joy_button_Start = 8;
    public static int joy_button_leftStick = 9;
    public static int joy_button_rightStick = 10;
    public static int joy_left_X = 0;
    public static int joy_left_Y = 1;
    public static int joy_right_X = 4;
    public static int joy_right_Y = 5;
    public static int joy_left_trigger = 2;
    public static int joy_right_trigger = 3;

    public static double joy_deadzone = .1;

    //Drive
    public static int sparks_left = 0;
    public static int sparks_right = 1;
    public static double drive_Kp =0;
    public static double drive_Kd = 0;
    public static double drive_Ki = 0;
    public static int[] encoder_left = {0,1};
    public static int[] encoder_right = {2,3};
    public static double distance_per_tick = (6 * Math.PI)/((50 / 24) * 20); //circumference / ticks per rotation

    //Lift
    public static int sparks_arm = 2;
    public static int talon_telescope = 3;
    public static int arm_pot = 0;
    public static int telescope_pot = 1;
    public static double arm_scaling_factor = (1800 / 5)/(4096/10);
    public static double telecope_scaling_factor = 0;
    public static double minimum_length = 27.25;
    public static double maximum_length = 32;
    public static int arm_min_angle = -36;
    public static int arm_val_at_min_angle = 0;

    //Arm PID
    public static double arm_Kp = 0;
    public static double arm_Kd = 0;
    public static double arm_Ki = 0;

    //Telescope PID
    public static double telescope_Kp = 0;
    public static double telescope_Kd = 0;
    public static double telescope_Ki = 0;

    //Intake
    public static double intake_speed = 1.0;
    public static int sparks_intake_left = 4;
    public static int sparks_intake_right = 5;
    public static int box_button_left = 5;
    public static int box_button_right = 6;

    //Arm Setpoints, In terms of ticks
    public static int setpoint_floor_front = 0;
    public static int setpoint_floor_rear = 0;
    public static int setpoint_portal_front = 0;
    public static int setpoint_portal_rear = 0;
    public static int setpoint_switch_front = 0;
    public static int setpoint_switch_rear = 0;
    public static int setpoint_scale_front = 0;
    public static int setpoint_scale_rear = 0;
    public static int setpooint_starting_pos = 0;

    //Drive Setpoints, In terms of inches ?? maybe
    public static double[] line_cross_drive_setpoints = {0.0, 0.0};
    //Encoder Cuttoffs
    public static int cimcoder_cuttoff = 0;
    public static int potentiometer_deadzone = 0;

}
