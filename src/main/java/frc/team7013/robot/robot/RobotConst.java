package frc.team7013.robot.robot;

public class RobotConst {

    public static final boolean INVERTED = true;
    public static final boolean NOT_INVERTED = false;
    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';

    //*********************************************************
    // Teleop Drive Constants
    //*********************************************************
    public static final boolean TELEOP_DRIVE_IS_SQUARED = true;

    //*********************************************************
    // Drive Constants
    //*********************************************************
    public static final boolean RIGHT_MOTOR_ORIENTATION = NOT_INVERTED;
    public static final boolean LEFT_MOTOR_ORIENTATION = INVERTED;

    public static final boolean RIGHT_ENCODER_ORIENTATION = INVERTED;
    public static final boolean LEFT_ENCODER_ORIENTATION = NOT_INVERTED;

    public static final double MAX_DRIVE_SPEED = 1800; //Encoder counts per second


    public static final double DRIVE_GYRO_PID_KP = 0.05;
    public static final double DRIVE_GYRO_PID_KI = 0.001;


    public static final double DRIVE_SPEED_PID_KP = 0.75;
    public static final double DRIVE_ENCODER_COUNTS_PER_INCH = 5.09375;

    //********************************************************
    // Arm Constants
    //********************************************************
    public static final boolean ARM_MOTOR_1_INVERSION = INVERTED;
    public static final boolean ARM_MOTOR_2_INVERSION = INVERTED;

    public static final boolean ARM_POTENTIOMETER_INVERSION =NOT_INVERTED;

    //********************************************************
    // Elevator Constants
    //********************************************************
    public static final boolean ELEVATOR_MOTOR_INVERSION = NOT_INVERTED;

    public static final boolean ELEVATOR_ENCODER_INVERSION = INVERTED;

    //********************************************************
    // Elevator Constants
    //********************************************************
    public static final boolean LEFT_INTAKE_MOTOR_INVERSION = INVERTED;
    public static final boolean RIGHT_INTAKE_MOTOR_INVERSION = INVERTED;

    /*
    public static final double ELEVATOR_ENCODER_COUNTS_PER_INCH;
    public static final double ARM_POTENTIOMETER_VOLTS_PER_DEGREE;*/

    public static enum Direction { FORWARD, BACKWARD };



}
