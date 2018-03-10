package frc.team7013.robot.constants;

import frc.team7013.robot.TPackage.sensors.limitSwitch.TLimitSwitch;

public class ElevatorArmConst {

    //*************************
    //Elevator Constants
    //*************************
    public static final double ELEVATOR_MAX_SPEED = 0.5;
    public static final TLimitSwitch.DefaultState LIMIT_SWITCH_DEFAULT_STATE = TLimitSwitch.DefaultState.TRUE;

    public static final double ELEVATOR_INTAKE_POSITION = 0.37;
    public static final double ELEVATOR_SWITCH_POSITION = 0.1;
    public static final double ELEVATOR_STOW_POSITION = 0.1;
    public static final double ELEVATOR_SCALE_POSITION = 0.92;

    public static final double ELEVATOR_ENCODER_MAX = 2954;

    public static final double ELEVATOR_KP = 5;
    public static final double ELEVATOR_KD = 0;
    public static final double ELEVATOR_KI = 0;


    //*************************
    //Arm Constants
    //*************************
    public static final double ARM_MAX_SPEED = 0.9;

    public static final double ARM_POTENTIOMETER_MIN = 1480;
    public static final double ARM_POTENTIOMETER_MAX = 2955;

    public static final double ARM_INTAKE_POSITION = -0.05;
    public static final double ARM_SWITCH_POSITION = 0.1;
    public static final double ARM_STOW_POSITION = 0.02;
    public static final double ARM_SCALE_POSITION = 0.4;

    public static final double ARM_KP = 8;
    public static final double ARM_KD = 0;
    public static final double ARM_KI = 0;

}
