package frc.team7013.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
//TODO: finish the whole thing
public class Intake {

    private static Spark Sparks_intake_left, Sparks_intake_right;
    private static DigitalInput cube_checker_left, cube_checker_right;
    private static boolean has_cube_left, has_cube_right;

    Intake(){

    }
    public static void updateCube(){
        has_cube_left = cube_checker_left.get();
        has_cube_right = cube_checker_right.get();
    }
    public static void getCube(){

    }
    public static void ejectCube(){

    }
    public static void doManual(){

    }

    public static boolean get_has_cube_left(){ return has_cube_left; }
    public static boolean get_has_cube_right(){ return has_cube_right; }
}
