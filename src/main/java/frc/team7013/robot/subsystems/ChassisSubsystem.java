package frc.team7013.robot.subsystems;

import frc.team7013.robot.DriveConst;
import frc.team7013.robot.RobotMap;
import frc.team7013.robot.TPackage.sensors.encoder.TEncoder;
import frc.team7013.robot.TPackage.sensors.encoder.TPwmEncoder;
import frc.team7013.robot.TPackage.sensors.gyro.TAnalogGyro;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.TPackage.subsystem.TGryoDriveSubsystem;
import frc.team7013.robot.commands.drive.DefaultChassisCommand;

public class ChassisSubsystem extends TGryoDriveSubsystem {

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

    @Override
    public void updatePeriodic() {
        super.updatePeriodic();
    }

}
