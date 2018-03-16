package frc.team7013.robot.subsystems;

import frc.team7013.robot.DriveConst;
import frc.team7013.robot.Robot;
import frc.team7013.robot.RobotMap;
import frc.team7013.robot.TPackage.sensors.encoder.TEncoder;
import frc.team7013.robot.TPackage.sensors.encoder.TPwmEncoder;
import frc.team7013.robot.TPackage.sensors.gyro.TAnalogGyro;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.TPackage.subsystem.TGryoDriveSubsystem;
import frc.team7013.robot.commands.drive.DefaultChassisCommand;

public class ChassisSubsystem extends TGryoDriveSubsystem {

    TPwmSpeedController leftIntake, rightIntake;

    public ChassisSubsystem() {
        super(
                new TAnalogGyro(0),
                new TPwmSpeedController(
                        TPwmSpeedControllerType.SPARK,
                        RobotMap.LEFT_DRIVE_MOTOR_PORT,
                        DriveConst.LEFT_DRIVE_INVERSION
                ),
                new TPwmSpeedController(
                        TPwmSpeedControllerType.SPARK,
                        RobotMap.RIGHT_DRIVE_MOTOR_PORT,
                        DriveConst.RIGHT_DRIVE_INVERSION
                ),
                DriveConst.DRIVE_GYRO_PID_KP,
                DriveConst.DRIVE_GYRO_PID_KI
        );

        TEncoder leftEncoder = new TPwmEncoder(RobotMap.LEFT_ENCODER_PORT_A,RobotMap.LEFT_ENCODER_PORT_B,DriveConst.LEFT_DRIVE_ENCODER_INVERSION);
        TEncoder rightEncoder = new TPwmEncoder(RobotMap.RIGHT_ENCODER_PORT_A,RobotMap.RIGHT_ENCODER_PORT_B,DriveConst.RIGHT_DRIVE_ENCODER_INVERSION);

        super.setEncoders(
                leftEncoder, DriveConst.LEFT_DRIVE_ENCODER_INVERSION,
                rightEncoder, DriveConst.RIGHT_DRIVE_ENCODER_INVERSION,
                DriveConst.DRIVE_SPEED_PID_KP,
                DriveConst.MAX_DRIVE_ENCODER_SPEED
        );

        leftIntake = new TPwmSpeedController(TPwmSpeedControllerType.SPARK,5,false);
        rightIntake = new TPwmSpeedController(TPwmSpeedControllerType.SPARK,4,false);
    }

    @Override
    public void init() {
        TAnalogGyro gyro = (TAnalogGyro) super.gyro;
        gyro.setSensitivity(0.0017);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DefaultChassisCommand());
    }

    private void setIntake(double value) {
        leftIntake.set(value);
        rightIntake.set(value);
    }

    private boolean autoIntake = false;
    public void setIntakeOnAuto() {
        autoIntake = true;
    }
    public void setIntakeOffAuto() {
        autoIntake = false;
    }

    @Override
    public void updatePeriodic() {
        super.updatePeriodic();
        if(Robot.oi.getIntakeCubeButton()) {
            setIntake(-1.0);
        }
        else if(Robot.oi.getExtakeCubeButton()) {
            setIntake(1.0);
        }
        else if(autoIntake) {
            setIntake(0.6);
        }
        else {
            setIntake(-0.3);
        }
    }

}
