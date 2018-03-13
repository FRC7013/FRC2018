package frc.team7013.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team7013.robot.LiftConst;
import frc.team7013.robot.RobotMap;
import frc.team7013.robot.TPackage.sensors.encoder.TPwmEncoder;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.LiftConst.LIFT_POSITION;

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
        double error = position - setpoint;
        double motorOutput = LiftConst.ARM_PID_KP;

        if(motorOutput > LiftConst.ARM_MAX_SPEED) {
            motorOutput = LiftConst.ARM_MAX_SPEED;
        }
        if(motorOutput < -LiftConst.ARM_MAX_SPEED) {
            motorOutput = -LiftConst.ARM_MAX_SPEED;
        }

        return motorOutput;

    }

    private double getArmPosition() {
        return (armPotentiometer.get() - LiftConst.ARM_POT_MIN) / (LiftConst.ARM_POT_MAX - LiftConst.ARM_POT_MIN);
    }

    public void setArmPosition(LIFT_POSITION position) {
        liftPosition = position;
    }

    private double returnArmSetpoint() {
        switch(liftPosition) {
            case INTAKE:
                return LiftConst.ARM_INTAKE_SETPOINT;
                break;
            case SWITCH:
                return LiftConst.ARM_SWITCH_SETPOINT;
                break;
            case SCALE:
                return LiftConst.ARM_SCALE_SETPOINT;
                break;
            case STOW:
                return LiftConst.ARM_STOW_SETPOINT;
                break;
                default:
                    System.out.println("ERROR: INVALID LIFTPOSITION");
                    return 0.2;
                    break;
        }
    }

    private double returnElevatorSetpoint() {
        switch(liftPosition) {
            case INTAKE:
                return LiftConst.ELEVATOR_INTAKE_SETPOINT;
            break;
            case SWITCH:
                return LiftConst.ELEVATOR_SWITCH_SETPOINT;
            break;
            case SCALE:
                return LiftConst.ELEVATOR_SCALE_SETPOINT;
            break;
            case STOW:
                return LiftConst.ELEVATOR_STOW_SETPOINT;
            break;
            default:
                System.out.println("ERROR: INVALID LIFTPOSITION");
                return 0.2;
            break;
        }
    }

    private void setArmMotors(double value) {
        armMotor1.set(value);
        armMotor2.set(value);
    }






    //ELEVATOR
    private void moveElevator() {
        calculateElevatorSetpoint();
        double elevatorSetpoint = calculatePIDElevator(elevatorPosition);
        elevatorSetpoint = boundMotorOutput();
        setElevatorMotor(elevatorSetpoint);
    }

    private void setElevatorMotor(double value) {
        elevatorMotor.set(value);
    }


    public void resetElevatorEncoder() {
        elevatorEncoder.reset();
    }

    public void updatePeriodic() {
        moveArm();

        moveElevator(elevatorPosition);
    }

}
