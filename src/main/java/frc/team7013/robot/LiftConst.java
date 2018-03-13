package frc.team7013.robot;

public class LiftConst {

    public enum LIFT_POSITION {
        INTAKE,
        SWITCH,
        SCALE,
        STOW
    }

    //Arm Max/Min Values
    public static final double ARM_POT_MIN = ;
    public static final double ARM_POT_MAX = ;

    //Arm PID Values
    public static final double ARM_PID_KP = 1;
    public static final double ARM_MAX_SPEED = 0.4;

    //Arm setpoints
    public static final double ARM_INTAKE_SETPOINT = 0.05;
    public static final double ARM_SWITCH_SETPOINT = 0.15;
    public static final double ARM_SCALE_SETPOINT = 0.40;
    public static final double ARM_STOW_SETPOINT = 0.10;

    //Elevator setpoints
    public static final double ELEVATOR_INTAKE_SETPOINT = 0.37;
    public static final double ELEVATOR_SWITCH_SETPOINT = 0.10;
    public static final double ELEVATOR_SCALE_SETPOINT = 0.90;
    public static final double ELEVATOR_STOW_SETPOINT = 0.10;


    public static final boolean ARM_MOTOR_1_INVERSION = true;
    public static final boolean ARM_MOTOR_2_INVERSION = false;
    public static final boolean ELEVATOR_MOTOR_INVERSION = false;

    public static final boolean ELEVATOR_ENCODER_INVERSION = false;
}
