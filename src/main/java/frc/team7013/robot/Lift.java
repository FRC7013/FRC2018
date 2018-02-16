package frc.team7013.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.*;
import frc.team7013.robot.Auton.Auto;
import frc.team7013.robot.Util.PID;

public class Lift {

    private static Constants constants;
    private static Spark sparks_arm;
    private static PWMTalonSRX talon_telescope;
    private static Joystick operator_joy;
    private static AnalogInput arm_pot, telescope_pot;
    private static String position_indicator = "";
    private static int arm_setpoint = 0;
    private static boolean manual_indicator = false;
    private static PID pid_arm, pid_telescope;
    private static double arm_speed, telescope_speed;
    private static Auto auton;
    private static DriverStation driver_station;

    Lift(Joystick operator_joy){
        this.operator_joy = operator_joy;
        initLift();
    } //done
    Lift(Auto auton){
        this.auton = auton;
        initLift();
    } //done
    private static void initLift(){
        sparks_arm = new Spark(constants.sparks_arm);
        talon_telescope = new PWMTalonSRX(constants.talon_telescope);
        arm_pot = new AnalogInput(constants.arm_pot);
        telescope_pot = new AnalogInput(constants.telescope_pot);
        pid_arm = new PID(constants.arm_Kp, constants.arm_Kd, constants.arm_Ki, constants.potentiometer_deadzone);
        arm_speed = 0;
        telescope_speed = 0;

    } //done

    public static void doLift(){
        if(!doManual()){//check if in manual first
            if(driver_station.isAutonomous()){ //TODO: write sender for auton

            }
            else{
                updateLift();
                pid_arm.newSetpoint(arm_setpoint);
                if(!pid_arm.doPID(arm_pot.getValue())&&(operator_joy.getRawButtonPressed(constants.joy_button_leftStick)&&operator_joy.getRawButtonPressed(constants.joy_button_rightStick)))
                    pid_arm.newSetpoint(arm_pot.getValue());
                arm_speed = pid_arm.getOutput();
                pid_telescope.newSetpoint(telescopeLenCalc(armRotationToAngles(telescopeLenCalc(arm_pot.getValue()))));
                if(!pid_telescope.doPID((int) Math.round(telescope_pot.getValue()*constants.telecope_scaling_factor))&&(operator_joy.getRawButtonPressed(constants.joy_button_leftStick)&&operator_joy.getRawButtonPressed(constants.joy_button_rightStick)))
                    pid_telescope.newSetpoint(telescope_pot.getValue());
                telescope_speed = pid_telescope.getOutput();
            }
        }
        setVelocities();
    }
    private static void updateLift(){
            if(!operator_joy.getRawButtonPressed(constants.joy_button_Back)){ //front to position
                if(operator_joy.getRawButtonPressed(constants.joy_button_A)){ //floor
                    arm_setpoint = constants.setpoint_floor_front;
                    position_indicator = "Front Floor";
                }
                else if(operator_joy.getRawButtonPressed(constants.joy_button_B)){ //portal
                    arm_setpoint = constants.setpoint_portal_front;
                    position_indicator = "Front Portal";
                }
                else if(operator_joy.getRawButtonPressed(constants.joy_button_X)){ //switch
                    arm_setpoint = constants.setpoint_switch_front;
                    position_indicator = "Front Switch";
                }
                else if(operator_joy.getRawButtonPressed(constants.joy_button_Y)){ //scale
                    arm_setpoint = constants.setpoint_scale_front;
                    position_indicator = "Front Scale";
                }
            }
            else{ //rear to position
                if(operator_joy.getRawButtonPressed(constants.joy_button_A)){ //floor
                    arm_setpoint = constants.setpoint_floor_rear;
                    position_indicator = "Rear Floor";
                }
                else if(operator_joy.getRawButtonPressed(constants.joy_button_B)){ //portal
                    arm_setpoint = constants.setpoint_portal_rear;
                    position_indicator = "Rear Portal";
                }
                else if(operator_joy.getRawButtonPressed(constants.joy_button_X)){ //switch
                    arm_setpoint = constants.setpoint_switch_rear;
                    position_indicator = "Rear Switch";
                }
                else if(operator_joy.getRawButtonPressed(constants.joy_button_Y)){ //scale
                    arm_setpoint = constants.setpoint_scale_rear;
                    position_indicator = "Rear Scale";

                }
            }
    } //done
    private static boolean doManual(){
        if(operator_joy.getRawButtonPressed(constants.joy_button_Start)){ //manual engage
            manual_indicator = true;
            if(operator_joy.getRawButtonPressed(constants.joy_button_leftStick))
                telescope_speed = .5;
            else if(operator_joy.getRawButtonPressed(constants.joy_button_rightBumper))
                telescope_speed = -.5;
            else
                telescope_speed = 0;
            if(operator_joy.getRawAxis(constants.joy_right_trigger) > .25)
                arm_speed = .5;
            else if(operator_joy.getRawAxis(constants.joy_left_trigger) > .25)
                arm_speed = -.5;
            else
                arm_speed = 0;
        }
        else
            manual_indicator = false;

        return manual_indicator;
    } //done
    private static void setVelocities(){
        sparks_arm.set(arm_speed);
        talon_telescope.set(telescope_speed);
    } //done
    private static int armRotationToAngles(int current){
        return (int) Math.round((current - constants.arm_val_at_min_angle) * constants.arm_scaling_factor - constants.arm_min_angle);
    }
    private static int telescopeLenCalc(int angle){
        if(angle > 120)
            angle = 180 - angle;
        else if(angle > 60 && angle < 120)
            angle = 60;

        return (int) Math.round(constants.maximum_length/Math.cos(Math.toRadians(angle)) - constants.minimum_length);
    } //done

    public static String getPositionIndicator(){ return position_indicator; }
    public static boolean getManualIndicator(){ return manual_indicator; }
    public static int getArmAngle(){ return armRotationToAngles(arm_pot.getValue()); }
    public static int getTelescopePot(){ return telescope_pot.getValue(); }
    public static int getArmRaw(){ return arm_pot.getValue(); }

}
