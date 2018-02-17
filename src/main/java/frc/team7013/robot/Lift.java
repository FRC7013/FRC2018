package frc.team7013.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.*;
import frc.team7013.robot.Auton.Auto;
import frc.team7013.robot.Util.PID;

//TODO: test me, test all of me, please!!!!!
public class Lift {

    private  Spark sparks_arm, sparks_arm2;
    private  PWMTalonSRX talon_telescope;
    private  Joystick operator_joy;
    private  AnalogInput arm_pot, telescope_pot;
    private  String position_indicator = "";
    private  int arm_setpoint;
    private  boolean manual_indicator = false;
    private  PID pid_arm, pid_telescope;
    private  double arm_speed, telescope_speed;

    //constructors
    Lift(Joystick operator_joy) {
        this.operator_joy = operator_joy;
        sparks_arm = new Spark(Constants.sparks_arm);
        sparks_arm2 = new Spark(Constants.sparks_arm2);
        talon_telescope = new PWMTalonSRX(Constants.talon_telescope);
        arm_pot = new AnalogInput(Constants.arm_pot);
        telescope_pot = new AnalogInput(Constants.telescope_pot);
        pid_arm = new PID(Constants.arm_Kp, Constants.arm_Kd, Constants.arm_Ki, Constants.potentiometer_deadzone);
        arm_speed = 0;
        telescope_speed = 0;
        arm_setpoint = 0;
    }

    //functionality
    public  void doLift(){
        setVelocities();
        doManual();
        //if(!doManual()){//check if in manual first
          //  if(!DriverStation.getInstance().isAutonomous()) { updateLift(); }
//            pid_arm.newSetpoint(arm_setpoint);
//            if(!pid_arm.doPID(arm_pot.getValue())&&(operator_joy.getRawButtonPressed(Constants.joy_button_leftStick)&&operator_joy.getRawButtonPressed(Constants.joy_button_rightStick)))
//                pid_arm.newSetpoint(arm_pot.getValue());
//            arm_speed = pid_arm.getOutput();
//            pid_telescope.newSetpoint(telescopeLenCalc(armRotationToAngles(telescopeLenCalc(arm_pot.getValue()))));
//            if(!pid_telescope.doPID((int) Math.round(telescope_pot.getValue()*Constants.telecope_scaling_factor))&&(operator_joy.getRawButtonPressed(Constants.joy_button_leftStick)&&operator_joy.getRawButtonPressed(Constants.joy_button_rightStick)))
//                pid_telescope.newSetpoint(telescope_pot.getValue());
//            telescope_speed = pid_telescope.getOutput();
//        }
    } //done
    private  void updateLift(){
            if(!operator_joy.getRawButtonPressed(Constants.joy_button_Back)){ //front to position
                if(operator_joy.getRawButtonPressed(Constants.joy_button_A)){ //floor
                    arm_setpoint = Constants.setpoint_floor_front;
                    position_indicator = "Front Floor";
                }
                else if(operator_joy.getRawButtonPressed(Constants.joy_button_B)){ //portal
                    arm_setpoint = Constants.setpoint_portal_front;
                    position_indicator = "Front Portal";
                }
                else if(operator_joy.getRawButtonPressed(Constants.joy_button_X)){ //switch
                    arm_setpoint = Constants.setpoint_switch_front;
                    position_indicator = "Front Switch";
                }
                else if(operator_joy.getRawButtonPressed(Constants.joy_button_Y)){ //scale
                    arm_setpoint = Constants.setpoint_scale_front;
                    position_indicator = "Front Scale";
                }
            }
            else{ //rear to position
                if(operator_joy.getRawButtonPressed(Constants.joy_button_A)){ //floor
                    arm_setpoint = Constants.setpoint_floor_rear;
                    position_indicator = "Rear Floor";
                }
                else if(operator_joy.getRawButtonPressed(Constants.joy_button_B)){ //portal
                    arm_setpoint = Constants.setpoint_portal_rear;
                    position_indicator = "Rear Portal";
                }
                else if(operator_joy.getRawButtonPressed(Constants.joy_button_X)){ //switch
                    arm_setpoint = Constants.setpoint_switch_rear;
                    position_indicator = "Rear Switch";
                }
                else if(operator_joy.getRawButtonPressed(Constants.joy_button_Y)){ //scale
                    arm_setpoint = Constants.setpoint_scale_rear;
                    position_indicator = "Rear Scale";

                }
            }
    } //done
    private  boolean doManual(){
        if(operator_joy.getRawButton(Constants.joy_button_Start)){ //manual engage
            manual_indicator = true;
            if(operator_joy.getRawButton(Constants.joy_button_leftBumper))
                telescope_speed = .5;
            else if(operator_joy.getRawButton(Constants.joy_button_rightBumper))
                telescope_speed = -.5;
            else
                telescope_speed = 0;

            if(operator_joy.getRawButton(Constants.joy_button_Y))
                arm_speed = .5;
            else if(operator_joy.getRawButton(Constants.joy_button_X))
                arm_speed = -.5;
            else
                arm_speed = 0;
        }
        else
            manual_indicator = false;

        return manual_indicator;
    } //done
    private  void setVelocities(){
        sparks_arm.set(arm_speed);
        sparks_arm.set(-arm_speed);
        talon_telescope.set(telescope_speed);
    } //done
    private  int armRotationToAngles(int current){
        return (int) Math.round((current - Constants.arm_val_at_min_angle) * Constants.arm_scaling_factor + Constants.arm_min_angle);
    } //done
    private  int telescopeLenCalc(int angle){
        if(angle > 120)
            angle = 180 - angle;
        else if(angle > 60 && angle < 120)
            angle = 60;

        return (int) Math.round(Constants.maximum_length/Math.cos(Math.toRadians(angle)) - Constants.minimum_length);
    } //done

    //get and sets
    public void setArmSetpoint(int arm_setpoint){ this.arm_setpoint = arm_setpoint; }
    public  String getPositionIndicator(){ return position_indicator; }
    public  boolean getManualIndicator(){ return manual_indicator; }
    public  int getArmAngle(){ return armRotationToAngles(arm_pot.getValue()); }
    public  int getTelescopePot(){ return telescope_pot.getValue(); }
    public  int getArmRaw(){ return arm_pot.getValue(); }
    public  boolean getArmPidDone(){ return pid_arm.getPidDone(); }
    public  boolean getTeleIsDone(){ return pid_telescope.getPidDone(); }

}
