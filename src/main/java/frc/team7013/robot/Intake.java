package frc.team7013.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
//TODO: Test the doIntake() function.
public class Intake {

    private  Constants constants;
    private  Spark sparks_intake_left, sparks_intake_right;
    private  DigitalInput cube_checker_left, cube_checker_right;
    private  boolean has_cube_left, has_cube_right;
    private  Joystick driver_joy;
    private  int state;
    private  boolean previous_state;

    //constructors
    Intake(Joystick driver_joy){
        this.driver_joy = driver_joy;
        sparks_intake_left = new Spark(constants.sparks_intake_left);
        sparks_intake_right = new Spark(constants.sparks_intake_right);
        state = 0;
        previous_state = false;
    } //done

    //functionality
    private  void updateCube(){
        has_cube_left = cube_checker_left.get();
    } //done
    public  void doIntake(){
        updateCube();
        if(!DriverStation.getInstance().isAutonomous()){ state = 0; }
        if(buttonMagic(driver_joy.getRawAxis(constants.joy_right_trigger)>.5) || state == 1){
            sparks_intake_right.set(constants.intake_speed);
            sparks_intake_left.set(constants.intake_speed);
        }
        else if(buttonMagic(driver_joy.getRawAxis(constants.joy_left_trigger)>.5)|| state == -1){
            sparks_intake_left.set(-constants.intake_speed);
            sparks_intake_right.set(-constants.intake_speed);
        }
        else{
            sparks_intake_right.set(0);
            sparks_intake_left.set(0);
        }

    } //done
    private  boolean buttonMagic(boolean currentState){
        if(currentState != previous_state && currentState)
            previous_state = true;
        else if(currentState == previous_state && currentState)
            previous_state = false;
        return previous_state;
    } //done
    //get and sets
    public void setIntakeState(int state){ this.state = state; }
    public  boolean get_has_cube_left(){ return has_cube_left; }
    public  boolean get_has_cube_right(){ return has_cube_right; }
}
