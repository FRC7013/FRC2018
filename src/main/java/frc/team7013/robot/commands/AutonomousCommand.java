package frc.team7013.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team7013.robot.commands.drive.*;
import frc.team7013.robot.oi.GameData;

public class AutonomousCommand extends CommandGroup {

    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';

    public AutonomousCommand() {
        char closeSwitch = GameData.getCloseSwitch();
        //addSequential(new DriveDistanceCommand(320,0,0.5,15,false));

        if(closeSwitch == LEFT) {
            addSequential(new ArcCommand(60, 0, 310, 0.7));
            addSequential(new ArcCommand(60, 310, 0, 0.7));
            addSequential(new ShootCubeCommand());
        }
        if(closeSwitch == RIGHT) {
            addSequential(new ArcCommand(40, 0, 50, 0.7));
            addSequential(new ArcCommand(60, 50, 350, 0.7));
            addSequential(new ShootCubeCommand());
        }
    }

    /*
    FOR MATCH 41:
    if(closeSwitch == LEFT) {
            addSequential(new ArcCommand(60, 0, 310, 0.7));
            addSequential(new ArcCommand(60, 310, 0, 0.7));
            addSequential(new ShootCubeCommand());
        }
        if(closeSwitch == RIGHT) {
            addSequential(new ArcCommand(40, 0, 50, 0.7));
            addSequential(new ArcCommand(60, 50, 350, 0.7));
            addSequential(new ShootCubeCommand());
        }
     */
}
