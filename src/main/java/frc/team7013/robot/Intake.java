package frc.team7013.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

public class Intake {

    private static Spark[] Sparks_intake_left, Sparks_intake_right;
    private static DigitalInput cube_checker;
    private static boolean has_cube;

    Intake(){

    }
    public static void updateCube(){
        has_cube = cube_checker.get();
    }
    public static void getCube(){

    }
    public static void ejectCube(){

    }
    public static void doManual(){

    }
    public static boolean getHasCube(){
        return has_cube;
    }
}
