package frc.team7013.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team7013.robot.LiftConst;
import frc.team7013.robot.Robot;
import frc.team7013.robot.RobotMap;
import frc.team7013.robot.TPackage.sensors.encoder.TPwmEncoder;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.LiftConst.LIFT_POSITION;
import frc.team7013.robot.commands.arm.DefaultArmCommand;
import frc.team7013.robot.subsystems.ArmSubsystem;

public class ArmSubsystem extends Subsystem{

    TPwmSpeedController armMotor1, armMotor2, elevatorMotor;
    TPwmEncoder elevatorEncoder;
    AnalogPotentiometer armPotentiometer;

    private LIFT_POSITION liftPosition;

    public ArmSubsystem() {
        armMotor1 = new TPwmSpeedController(TPwmSpeedControllerType.SPARK, RobotMap.ARM_MOTOR_1_PORT, LiftConst.ARM_MOTOR_1_INVERSION);
        armMotor2 = new TPwmSpeedController(TPwmSpeedControllerType.SPARK,RobotMap.ARM_MOTOR_2_PORT, LiftConst.ARM_MOTOR_2_INVERSION);
        elevatorMotor = new TPwmSpeedController(TPwmSpeedControllerType.TALON, RobotMap.ELEVATOR_MOTOR_PORT, LiftConst.ELEVATOR_MOTOR_INVERSION);

        elevatorEncoder = new TPwmEncoder(RobotMap.ELEVATOR_ENCODER_PORT_A,RobotMap.ELEVATOR_ENCODER_PORT_B,LiftConst.ELEVATOR_ENCODER_INVERSION);
        armPotentiometer = new AnalogPotentiometer(RobotMap.ARM_POTENTIOMETER_PORT);
    }

    public void init() {

    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DefaultArmCommand());
    }

    private void moveArm() {
        double wantedPosition = returnArmSetpoint();
        double motorOutput = calculatePIDArm(wantedPosition);
        setArmMotors(motorOutput);
    }

    private double calculatePIDArm(double setpoint) {
        double position = getArmPosition();
        SmartDashboard.putNumber("PID Arm Setpoint",setpoint);
        double error = position - setpoint;
        SmartDashboard.putNumber("Arm PID error",error);
        double motorOutput = LiftConst.ARM_PID_KP*error;

        if(motorOutput > LiftConst.ARM_MAX_SPEED) {
            motorOutput = LiftConst.ARM_MAX_SPEED;
        }
        if(motorOutput < -LiftConst.ARM_MAX_SPEED) {
            motorOutput = -LiftConst.ARM_MAX_SPEED;
        }

        if(liftPosition == LIFT_POSITION.SCALE) {
            if(Math.abs(getArmPosition() - LiftConst.ARM_SCALE_SETPOINT)<0.1) {
                motorOutput = motorOutput * 0.7;
            }
        }

        return motorOutput;

    }

    private double getArmPosition() {
        double position = (armPotentiometer.get() - LiftConst.ARM_POT_MIN) / (LiftConst.ARM_POT_MAX - LiftConst.ARM_POT_MIN);
        return position;
    }

    public void setArmPosition(LIFT_POSITION position) {
        liftPosition = position;
    }

    private double returnArmSetpoint() {
        switch(liftPosition) {
            case INTAKE:
                if(getElevatorPosition() > 0.5) {
                    return LiftConst.ARM_SCALE_SETPOINT;
                }
                return LiftConst.ARM_INTAKE_SETPOINT;
            case SWITCH:
                if(getElevatorPosition() > 0.5) {
                    return LiftConst.ARM_SCALE_SETPOINT;
                }
                return LiftConst.ARM_SWITCH_SETPOINT;
            case SCALE:
                return LiftConst.ARM_SCALE_SETPOINT;
            case FAR:
                return LiftConst.ARM_FAR_SETPOINT;
            case STOW:
                if(getElevatorPosition() > 0.5) {
                    return LiftConst.ARM_SCALE_SETPOINT;
                }
                return LiftConst.ARM_STOW_SETPOINT;
                default:
                    System.out.println("ERROR: INVALID LIFTPOSITION");
                    return 0.2;
        }
    }

    private void setArmMotors(double value) {
        armMotor1.set(value);
        armMotor2.set(value);
    }






    //ELEVATOR
    private void moveElevator() {
        double wantedPosition = returnElevatorSetpoint();
        double motorOutput = calculatePIDElevator(wantedPosition);
        setElevatorMotor(motorOutput);
    }

    private double calculatePIDElevator(double setpoint) {
        double position = getElevatorPosition();
        double error = setpoint - position;
        double motorOutput = LiftConst.ELEVATOR_PID_KP * error;

        if(motorOutput > LiftConst.ELEVATOR_MAX_SPEED) {
            motorOutput = LiftConst.ELEVATOR_MAX_SPEED;
        }
        if(motorOutput < -LiftConst.ELEVATOR_MAX_SPEED) {
            motorOutput = -LiftConst.ELEVATOR_MAX_SPEED;
        }

        return motorOutput;
    }

    private double getElevatorPosition() {
        return -elevatorEncoder.get() / LiftConst.ELEVATOR_ENCODER_MAX;
    }


    private double returnElevatorSetpoint() {

        switch(liftPosition) {
            case INTAKE:
                if(getArmPosition() > LiftConst.ARM_INTAKE_SETPOINT + 0.2) {
                    return LiftConst.ELEVATOR_STOW_SETPOINT;
                }
                return LiftConst.ELEVATOR_INTAKE_SETPOINT;
            case SWITCH:
                return LiftConst.ELEVATOR_SWITCH_SETPOINT;
            case FAR:
                return LiftConst.ELEVATOR_FAR_SETPOINT;
            case SCALE:
                if(getArmPosition() < 0.45) {
                    return LiftConst.ELEVATOR_STOW_SETPOINT;
                }
                return LiftConst.ELEVATOR_SCALE_SETPOINT;
            case STOW:
                return LiftConst.ELEVATOR_STOW_SETPOINT_REAL;
            default:
                System.out.println("ERROR: INVALID LIFTPOSITION");
                return 0.2;
        }
    }

    boolean pidState = true;

    public void setPidState(boolean state) {
        pidState = state;
    }

    private void setElevatorMotor(double value) {

        if(Robot.oi.getEnablePid()) {
            pidState = true;
        }
        if(Robot.oi.getDisablePid()) {
            pidState = false;
        }

        if(pidState) {
            if(Robot.oi.getHomeArmButton()) {
                elevatorMotor.set(-0.4);
                resetElevatorEncoder();
            } else {
                elevatorMotor.set(value);
            }

        } else {
            if(Robot.oi.getElevatorUp()) {
                elevatorMotor.set(-0.4);
            }
            else if(Robot.oi.getElevatorDown()) {
                elevatorMotor.set(0.4);
            }
            else {
                if(getArmPosition() < (LiftConst.ARM_SWITCH_SETPOINT - 0.03)) {
                    elevatorMotor.set(-0.2);
                }
                else {
                    elevatorMotor.set(0.0);
                }
            }
        }
    }


    public void resetElevatorEncoder() {
        elevatorEncoder.reset();
    }

    public void updatePeriodic() {
        updateSmartDashboard();
        moveArm();
        moveElevator();
    }

    public void updateSmartDashboard() {
        SmartDashboard.putNumber("Arm Potentiometer Value",armPotentiometer.get());
        SmartDashboard.putNumber("Elevator Encoder Value",elevatorEncoder.get());
        SmartDashboard.putNumber("Arm Position",getArmPosition());
        SmartDashboard.putNumber("Elevator Position",getElevatorPosition());
    }

}
