package frc.team7013.robot;

public class DriveConst {

    public static final double TELEOP_MAX_DRIVE_SPEED = 0.65;

    //Drive squared?
    public static final boolean DRIVE_SQUARED = true;

    //Drive motor inversion
    public static final boolean LEFT_DRIVE_INVERSION = true;
    public static final boolean RIGHT_DRIVE_INVERSION = false;

    //Drive encoder inversion
    public static final boolean LEFT_DRIVE_ENCODER_INVERSION = true;
    public static final boolean RIGHT_DRIVE_ENCODER_INVERSION = false;

    public static final double DRIVE_GYRO_PID_KP = 0.1;
    public static final double DRIVE_GYRO_PID_KI = 0;

    public static final double DRIVE_SPEED_PID_KP = 0.75;
    public static final double MAX_DRIVE_ENCODER_SPEED = 1800;
}
