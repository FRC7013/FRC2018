package frc.team7013.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.team7013.robot.TPackage.loop.Loop;
import frc.team7013.robot.TPackage.loop.Looper;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.robot.ElevatorArmConst;
import frc.team7013.robot.robot.RobotConst;
import frc.team7013.robot.robot.RobotMap;

public class Arm extends Subsystem {

    private static Arm mInstance;

    public static Arm getInstance() {
        if(mInstance == null) {
            mInstance = new Arm();
        }
        return mInstance;
    }

    private final TPwmSpeedController armMotor1,armMotor2;
    private final AnalogPotentiometer armPotentiometer;

    private double armSetpoint;
    private double integral = 0;
    private double previousError;

    private Arm() {
        armMotor1 = new TPwmSpeedController(TPwmSpeedControllerType.SPARK, RobotMap.ARM_MOTOR_PORT_1, RobotConst.ARM_MOTOR_1_INVERSION);
        armMotor2 = new TPwmSpeedController(TPwmSpeedControllerType.SPARK,RobotMap.ARM_MOTOR_PORT_2, RobotConst.ARM_MOTOR_2_INVERSION);
        armPotentiometer = new AnalogPotentiometer(RobotMap.ARM_POTENTIOMETER_ANALOG_PORT); //add inversion parameter
    }

    @Override
    public void outputToSmartDashboard() {
    }

    @Override
    public void stop() {
        setArmMotors(0.0);
    }

    @Override
    public void zeroSensors() {
        //Don't do anything
    }

    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        Loop loop = new Loop() {
            @Override
            public void onStart(double timestamp) {
                synchronized (Arm.this) {
                    armSetpoint = getArmPosition();
                    previousError = getArmPosition() - armSetpoint;
                    stop();
                }
            }

            @Override
            public void onLoop(double timestamp) {
                synchronized (Arm.this) {
                    setArmMotors(getArmPIDOutput());
                }
            }

            @Override
            public void onStop(double timestamp) {
                stop();
            }
        };
        enabledLooper.register(loop);
    }

    public void setSetpoint(double value) {
        armSetpoint = value;
    }

    public boolean setpointReached() {
        return (Math.abs(armSetpoint - getArmPosition()) < 0.1);
    }

    private double getArmPIDOutput() {
        double outputValue = 0;

        double error = getArmPosition() - armSetpoint;

        integral += error*0.005; //Multiply by time interval (1/200 s)

        double derivative = (error - previousError) / 0.005;
        previousError = error;

        outputValue = error*ElevatorArmConst.ARM_KP + integral*ElevatorArmConst.ARM_KI + derivative*ElevatorArmConst.ARM_KD;

        if(outputValue > ElevatorArmConst.ARM_MAX_SPEED) { outputValue = ElevatorArmConst.ARM_MAX_SPEED; }
        if(outputValue < -ElevatorArmConst.ARM_MAX_SPEED) { outputValue = -ElevatorArmConst.ARM_MAX_SPEED; }

        return outputValue;
    }

    private double getArmPosition() {
        double position = (getArmPotentiometer() - ElevatorArmConst.ARM_POTENTIOMETER_MIN) /
                (ElevatorArmConst.ARM_POTENTIOMETER_MAX - ElevatorArmConst.ARM_POTENTIOMETER_MIN);
        return position;
    }

    private double getArmPotentiometer() {
        double value = armPotentiometer.get();
        if(RobotConst.ARM_POTENTIOMETER_INVERSION) {
            value = -value;
        }
        return value;
    }

    private void setArmMotors(double value) {
        armMotor1.set(value);
        armMotor2.set(value);
    }

}
