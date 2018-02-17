package frc.team7013.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
//TODO: Test the doIntake() function.
public class Intake {
    private  Spark sparks_intake_left, sparks_intake_right;
    private  DigitalInput cube_checker_left, cube_checker_right;
    private  boolean has_cube_left, has_cube_right;
    private  Joystick driver_joy;
    private  int state;
    private  boolean previous_state;

    //constructors
    Intake(Joystick driver_joy){
        this.driver_joy = driver_joy;
        sparks_intake_left = new Spark(Constants.sparks_intake_left);
        sparks_intake_right = new Spark(Constants.sparks_intake_right);
        state = 0;
        previous_state = false;
    } //done

    //functionality

    public  void doIntake(){
        if(!DriverStation.getInstance().isAutonomous()){ state = 0; }
        if(driver_joy.getRawButton(Constants.joy_button_rightBumper) || state == 1){
            sparks_intake_right.set(Constants.intake_speed);
            sparks_intake_left.set(Constants.intake_speed);
        }
        else if(driver_joy.getRawButton(Constants.joy_button_leftBumper)|| state == -1){
            sparks_intake_left.set(-Constants.intake_speed);
            sparks_intake_right.set(-Constants.intake_speed);
        }
        else{
            sparks_intake_right.set(0);
            sparks_intake_left.set(0);
        }

    } //done
    //get and sets
    public void setIntakeState(int state){ this.state = state; }
}
