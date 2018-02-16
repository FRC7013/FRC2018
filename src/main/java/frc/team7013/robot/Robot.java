package frc.team7013.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import frc.team7013.robot.Util.SmartDashThing;
import frc.team7013.robot.Auton.*;


public class Robot extends IterativeRobot {
    private Constants constants;
    private Drive robot_drive;
    private Joystick driver_joy, operator_joy;
    private Lift lift;
    private Intake intake;
    private SmartDashThing smart_dash;
    private Auto auton;


    @Override
    public void robotInit() {
        driver_joy = new Joystick(constants.driver_joy_port);
        operator_joy = new Joystick(constants.operator_joy_port);
        robot_drive = new Drive(driver_joy);
        lift = new Lift(operator_joy);
        intake = new Intake(driver_joy);
        smart_dash = new SmartDashThing(robot_drive, lift, intake);
        smart_dash.updateDash();
    } //done
    @Override
    public void autonomousInit() {//TODO: finish auto init
        smart_dash.updateDash();
        robot_drive.zeroDriveEncoders();
        auton = smart_dash.autonSelector.getSelected();
    }//done
    @Override
    public void autonomousPeriodic() { //TODO: finish auto periodic
        smart_dash.updateDash();
        auton.doAuto();
    }//done
    @Override
    public void teleopInit() {//TODO: finish tele init
        smart_dash.updateDash();
    }//done
    @Override
    public void teleopPeriodic() { //TODO: finish tele periodic
        smart_dash.updateDash();
        intake.doIntake();
        lift.doLift();
        robot_drive.doDrive();
    }//done

    @Override
    public void disabledPeriodic() {
        smart_dash.updateDash();
    }//done

    //probably never going to use these
    @Override
    public void testPeriodic() { }
    @Override
    public void disabledInit() { }
    @Override
    public void testInit() { }
}