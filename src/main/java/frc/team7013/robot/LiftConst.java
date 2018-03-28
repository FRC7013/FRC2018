package frc.team7013.robot;

public class LiftConst {

    public enum LIFT_POSITION {
        INTAKE,
        SWITCH,
        SCALE,
        STOW,
        FAR
    }

    //Arm Max/Min Values
    public static final double ARM_POT_MIN = 0.200;//0.15; //To reset zero: keep elevator all the way in, contact to frame and look at value
                                                   //on dashboard
    public static final double ARM_POT_MAX = ARM_POT_MIN + 0.40; //Should be + 0.40 //ELEVATOR POT ANALOG VALUES

    //Arm PID Values
    public static final double ARM_PID_KP = 25;
    public static final double ARM_MAX_SPEED = 1.0;

    //Elevator Max Value
    public static final double ELEVATOR_ENCODER_MAX = -527;

    //Elevator PID Values
    public static final double ELEVATOR_PID_KP = 6;
    public static final double ELEVATOR_MAX_SPEED = 0.8;

    //Arm setpoints
    public static final double ARM_INTAKE_SETPOINT = 0.094;
    public static final double ARM_SWITCH_SETPOINT = 0.26;
    public static final double ARM_SCALE_SETPOINT = 0.53;
    public static final double ARM_STOW_SETPOINT = 0.177;
    public static final double ARM_FAR_SETPOINT = 0.98;

    //Elevator setpoints
    public static final double ELEVATOR_INTAKE_SETPOINT = 0.400;
    public static final double ELEVATOR_SWITCH_SETPOINT = 0.10;
    public static final double ELEVATOR_SCALE_SETPOINT = 0.97;
    public static final double ELEVATOR_STOW_SETPOINT = 0.10;
    public static final double ELEVATOR_STOW_SETPOINT_REAL = 0.39;
    public static final double ELEVATOR_FAR_SETPOINT = 0.30;


    public static final boolean ARM_MOTOR_1_INVERSION = true;
    public static final boolean ARM_MOTOR_2_INVERSION = false;
    public static final boolean ELEVATOR_MOTOR_INVERSION = false;

    public static final boolean ELEVATOR_ENCODER_INVERSION = true;
}
