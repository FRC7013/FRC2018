package frc.team7013.robot;

public class LiftConst {

    public enum LIFT_POSITION {
        INTAKE,
        SWITCH,
        SCALE,
        STOW
    }

    //Arm Max/Min Values
    public static final double ARM_POT_MIN = 0.15; //To reset zero: keep elevator all the way in, contact to frame and look at value
                                                   //on dashboard
    public static final double ARM_POT_MAX = ARM_POT_MIN + 0.40; //Should be + 0.40

    //Arm PID Values
    public static final double ARM_PID_KP = 25;
    public static final double ARM_MAX_SPEED = 0.7;

    //Elevator Max Value
    public static final double ELEVATOR_ENCODER_MAX = 394;

    //Elevator PID Values
    public static final double ELEVATOR_PID_KP = 6;
    public static final double ELEVATOR_MAX_SPEED = 0.5;

    //Arm setpoints
    public static final double ARM_INTAKE_SETPOINT = -0.015;
    public static final double ARM_SWITCH_SETPOINT = 0.15;
    public static final double ARM_SCALE_SETPOINT = 0.45;
    public static final double ARM_STOW_SETPOINT = 0.05;

    //Elevator setpoints
    public static final double ELEVATOR_INTAKE_SETPOINT = 0.30;
    public static final double ELEVATOR_SWITCH_SETPOINT = 0.10;
    public static final double ELEVATOR_SCALE_SETPOINT = 0.90;
    public static final double ELEVATOR_STOW_SETPOINT = 0.05;


    public static final boolean ARM_MOTOR_1_INVERSION = true;
    public static final boolean ARM_MOTOR_2_INVERSION = false;
    public static final boolean ELEVATOR_MOTOR_INVERSION = false;

    public static final boolean ELEVATOR_ENCODER_INVERSION = true;
}
