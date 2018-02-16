package frc.team7013.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import frc.team7013.robot.Util.SmartDashThing;
import frc.team7013.robot.Auton.*;


public class Robot extends IterativeRobot {
    public static Constants constants;
    public static Drive robot_drive;
    public static Joystick driver_joy, operator_joy;
    public static Lift lift;
    public static Intake intake;
    public static SmartDashThing smart_dash;
    public static Auto auton;


    @Override
    public void robotInit() { //TODO: finish robot init
        driver_joy = new Joystick(constants.driver_joy_port);
        operator_joy = new Joystick(constants.operator_joy_port);
        robot_drive = new Drive(driver_joy);
        auton = new Auto(smart_dash.autonSelector);
    }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() {//TODO: finish auto init
        intake = new Intake();
    }

    @Override
    public void teleopInit() {//TODO: finish tele init
        intake = new Intake(operator_joy);
    }

    @Override
    public void testInit() { }


    @Override
    public void disabledPeriodic() {

    }
    
    @Override
    public void autonomousPeriodic() { //TODO: finish auto periodic

    }

    @Override
    public void teleopPeriodic() { //TODO: finish tele periodic

    }

    @Override
    public void testPeriodic() { }
}