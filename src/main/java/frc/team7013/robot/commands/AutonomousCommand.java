package frc.team7013.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team7013.robot.commands.drive.*;
import frc.team7013.robot.oi.GameData;

public class AutonomousCommand extends CommandGroup {

    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';

    public enum AutoMode {
        LEFT,
        CENTRE,
        RIGHT
    }

    //SET AUTO MODE HERE!!!!!!!
    AutoMode autoMode = AutoMode.LEFT;

    public AutonomousCommand() {
        char closeSwitch = GameData.getCloseSwitch();

        if(autoMode == AutoMode.LEFT) {
            addSequential(new AccelerateDistanceCommand(320,0,0.5,15,false));
            //addSequential(new DriveDistanceCommand(320,0,0.5,15,false));
        }
        if(autoMode == AutoMode.CENTRE) {
            if(closeSwitch == LEFT) {
                addSequential(new ArcCommand(60, 0, 310, 0.7));
                addSequential(new ArcCommand(60, 310, 0, 0.7));
                addSequential(new ShootCubeCommand());
            }
            if(closeSwitch == RIGHT) {
                addSequential(new ArcCommand(45, 0, 30, 0.7));
                addSequential(new ArcCommand(44, 30, 10, 0.7));
                addSequential(new ShootCubeCommand());
            }
        }
        if(autoMode == AutoMode.RIGHT) {
            addSequential(new AccelerateDistanceCommand(320,0,0.5,15,false));
            //addSequential(new DriveDistanceCommand(320,0,0.5,15,false));
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
