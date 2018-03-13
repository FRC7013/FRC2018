package frc.team7013.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team7013.robot.commands.drive.ArcCommand;
import frc.team7013.robot.commands.drive.DriveDistanceCommand;
import frc.team7013.robot.commands.drive.RotateToAngleCommand;
import frc.team7013.robot.oi.GameData;

public class AutonomousCommand extends CommandGroup {

    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';

    public AutonomousCommand() {
        char closeSwitch = GameData.getCloseSwitch();
        if(closeSwitch == LEFT) {
            addSequential(new ArcCommand(80, 0, 290, 0.7));
            addSequential(new ArcCommand(60, 290, 350, 0.7));
            addSequential(new ShootCubeCommand());
        }
        if(closeSwitch == RIGHT) {
            addSequential(new ArcCommand(60, 0, 50, 0.7));
            addSequential(new ArcCommand(60, 50, 10, 0.7));
            addSequential(new ShootCubeCommand());
        }
    }
}
