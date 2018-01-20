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
    public static int joy_left_Trigger = 2;
    public static int joy_right_Trigger = 3;

    //Drive
    public static int[] victors_left = {0,0};
    public static int[] victors_right = {0,0};
    public static double drive_Kp =0;
    public static double drive_Kd = 0;
    public static double drive_Ki = 0;
    public static int[] encoder_left = {0,0};
    public static int[] encoder_right = {0,0};

    //Lift
    public static int[] victors_arm = {0,0};
    public static int[] victors_telescope = {0,0};
    public static int[] encoder_arm = {0,0};
    public static int[] encoder_telescope = {0,0};
    public static double arm_Kp = 0;
    public static double arm_Kd = 0;
    public static double arm_Ki = 0;
    public static double telescope_Kp = 0;
    public static double telescope_Kd = 0;
    public static double telescope_Ki = 0;

    //Intake
    public static int[] victors_intake_left = {0,0};
    public static int[] victors_intake_right = {0,0};
    public static int box_button = 0;

    //Ramps
    public static int solenoid_ramps = 0;

    //Setpoint
    public static int arm_setpoint_scale = 0;
    public static int arm_setpoint_switch = 0;
    public static int arm_setpoint_floor = 0;

    //Encoder Cuttoffs
    public static int cuttoff_talon_mag_encoder = 0;
    public static int cuttoff_greyhill_encoder = 0;
    public static int cuttoff_cimCoder_encoder = 0;
    public static int cuttoff_versa_encoder = 0;

}
