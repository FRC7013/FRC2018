package frc.team7013.robot.subsystems;

import frc.team7013.robot.commands.drive.DefaultChassisCommand;
import frc.team7013.robot.robot.RobotConst;
import frc.team7013.robot.robot.RobotMap;
import frc.team7013.robot.TPackage.sensors.encoder.TEncoder;
import frc.team7013.robot.TPackage.sensors.encoder.TPwmEncoder;
import frc.team7013.robot.TPackage.sensors.gyro.TAnalogGyro;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedController;
import frc.team7013.robot.TPackage.speedcontroller.TPwmSpeedControllerType;
import frc.team7013.robot.TPackage.subsystem.TGryoDriveSubsystem;

public class ChassisSubsystem extends TGryoDriveSubsystem {

    public ChassisSubsystem() {

        super(
                new TAnalogGyro(0),
                new TPwmSpeedController(TPwmSpeedControllerType.SPARK, RobotMap.LEFT_DRIVE_MOTOR_PORT, RobotConst.LEFT_MOTOR_ORIENTATION),
                new TPwmSpeedController(TPwmSpeedControllerType.SPARK,RobotMap.RIGHT_DRIVE_MOTOR_PORT,RobotConst.RIGHT_MOTOR_ORIENTATION),
                RobotConst.DRIVE_GYRO_PID_KP,
                RobotConst.DRIVE_GYRO_PID_KI);

        // Get the encoders attached to the CAN bus speed controller.
        TEncoder leftEncoder  = new TPwmEncoder(RobotMap.LEFT_DRIVE_ENCODER_A_DIO_PORT, RobotMap.LEFT_DRIVE_ENCODER_B_DIO_PORT);
        TEncoder rightEncoder = new TPwmEncoder(RobotMap.RIGHT_DRIVE_ENCODER_A_DIO_PORT, RobotMap.RIGHT_DRIVE_ENCODER_B_DIO_PORT);

        super.setEncoders(
                leftEncoder,  RobotConst.LEFT_ENCODER_ORIENTATION,
                rightEncoder, RobotConst.RIGHT_ENCODER_ORIENTATION,
                RobotConst.DRIVE_SPEED_PID_KP,
                RobotConst.MAX_DRIVE_SPEED);

    }

    @Override
    public void init() {
            TAnalogGyro gyro = (TAnalogGyro) super.gyro;
            gyro.setSensitivity(0.0016133333333333334);
    };

    // Initialize the default command for the Chassis subsystem.
    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DefaultChassisCommand());
    }

    // ********************************************************************************************************************
    // Update the SmartDashboard
    // ********************************************************************************************************************
    // Periodically update the dashboard and any PIDs or sensors
    @Override
    public void updatePeriodic() {


        super.updatePeriodic();
    }

}
