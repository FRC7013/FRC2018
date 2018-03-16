package frc.team7013.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team7013.robot.commands.drive.*;
import frc.team7013.robot.oi.GameData;

public class AutonomousCommand extends CommandGroup {

    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';

    public AutonomousCommand() {
        char closeSwitch = GameData.getCloseSwitch();
        /*addSequential(new DriveDistanceCommand(320,0,0.5,15,false));
        addSequential(new TSafeCommand(3));
        addSequential(new RotateToAngleCommand(90,0.5));
        addSequential(new DriveTimeCommand(0.5,4);
        addSequential(new ShootCubeCommand());*/
        if(closeSwitch == LEFT) {
            addSequential(new ArcCommand(60, 0, 310, 0.7));
            addSequential(new ArcCommand(60, 310, 350, 0.7));
            addSequential(new ShootCubeCommand());
        }
        if(closeSwitch == RIGHT) {
            addSequential(new ArcCommand(60, 0, 50, 0.7));
            addSequential(new ArcCommand(60, 50, 10, 0.7));
            addSequential(new ShootCubeCommand());
        }
    }
}
