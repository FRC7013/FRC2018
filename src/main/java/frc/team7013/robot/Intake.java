package frc.team7013.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
//TODO: finish the whole thing
public class Intake {

    private static Constants constants;
    private static Spark sparks_intake_left, sparks_intake_right;
    private static DigitalInput cube_checker_left, cube_checker_right;
    private static boolean has_cube_left, has_cube_right;
    private static Joystick driver_joy;

    Intake(Joystick driver_joy){
        this.driver_joy = driver_joy;
        sparks_intake_left = new Spark(constants.sparks_intake_left);
        sparks_intake_right = new Spark(constants.sparks_intake_right);
        cube_checker_left = new DigitalInput(constants.box_button_left);
        cube_checker_right = new DigitalInput(constants.box_button_right);
    } //done
    private static void updateCube(){
        has_cube_left = cube_checker_left.get();
        has_cube_right = cube_checker_right.get();
    } //done
    public static void doIntake(){
        updateCube();
        if(driver_joy.getRawAxis(constants.joy_right_trigger)>.5){
            sparks_intake_left.set(constants.intake_speed);
            sparks_intake_right.set(constants.intake_speed);
        }
        else if(driver_joy.getRawAxis(constants.joy_left_trigger)>.5){
            sparks_intake_left.set(-constants.intake_speed);
            sparks_intake_right.set(-constants.intake_speed);
        }
        else{
            sparks_intake_left.set(0);
            sparks_intake_right.set(0);
        }

    } //done

    public static boolean get_has_cube_left(){ return has_cube_left; }
    public static boolean get_has_cube_right(){ return has_cube_right; }
}
