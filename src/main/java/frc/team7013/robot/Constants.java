package frc.team7013.robot;

public class Constants {

    //Joystick
    public static int driver_joy_port = 0;
    public static int operator_joy_port = 1;

    public static int joy_button_A = 0;
    public static int joy_button_B = 1;
    public static int joy_button_X = 2;
    public static int joy_button_Y = 3;
    public static int joy_button_leftBumper = 4;
    public static int joy_button_rightBumper = 5;
    public static int joy_button_Back = 6;
    public static int joy_button_Start = 7;
    public static int joy_button_leftStick = 8;
    public static int joy_button_rightStick = 9;
    public static int joy_left_X = 0;
    public static int joy_left_Y = 1;
    public static int joy_right_X = 4;
    public static int joy_right_Y = 5;
    public static int joy_left_trigger = 2;
    public static int joy_right_trigger = 3;

    //Drive
    public static int[] sparks_left = {0,0};
    public static int[] sparks_right = {0,0};
    public static double drive_Kp =0;
    public static double drive_Kd = 0;
    public static double drive_Ki = 0;
    public static int[] encoder_left = {0,0};
    public static int[] encoder_right = {0,0};

    //Lift
    public static int[] sparks_arm = {0,0};
    public static int[] sparks_telescope = {0,0};
    public static int[] encoder_arm = {0,0};
    public static int[] encoder_telescope = {0,0};
    public static double arm_Kp = 0;
    public static double arm_Kd = 0;
    public static double arm_Ki = 0;
    public static double telescope_Kp = 0;
    public static double telescope_Kd = 0;
    public static double telescope_Ki = 0;

    //Intake
    public static int[] sparks_intake_left = {0,0};
    public static int[] sparks_intake_right = {0,0};
    public static int box_button = 0;

    //Setpoint
    public static int setpoint_floor_front = 0;
    public static int setpoint_floor_rear = 0;
    public static int setpoint_porttal_front = 0;
    public static int setpoint_portal_rear = 0;
    public static int setpoint_switch_front = 0;
    public static int setpoint_switch_rear = 0;
    public static int setpoint_scale_front = 0;
    public static int setpoint_scale_rear = 0;

    //Encoder Cuttoffs
    public static int cuttoff_talon_mag_encoder = 0;
    public static int cuttoff_greyhill_encoder = 0;
    public static int cuttoff_cimCoder_encoder = 0;
    public static int cuttoff_versa_encoder = 0;

}
