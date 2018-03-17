package frc.team7013.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team7013.robot.LiftConst;
import frc.team7013.robot.commands.arm.SetArmCommand;
import frc.team7013.robot.commands.drive.*;
import frc.team7013.robot.oi.GameData;

public class AutonomousCommand extends CommandGroup {

    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';

    public enum AutoSide {
        LEFT,
        CENTRE,
        RIGHT
    }

    public enum AutoType {
        SWITCH,
        SCALE
    }

    //SET AUTO MODE HERE!!!!!!!
    AutoSide autoMode = AutoSide.CENTRE;
    AutoType autoType = AutoType.SWITCH;

    public AutonomousCommand() {
        char closeSwitch = GameData.getCloseSwitch();
        char scale = GameData.getScale();

        if(autoType == AutoType.SWITCH) {
            if(autoMode == AutoSide.LEFT) {
                addSequential(new DriveDistanceCommand(320,0,0.5,15,false));
                //addSequential(new DriveDistanceCommand(320,0,0.5,15,false));
            }
            if(autoMode == AutoSide.CENTRE) {
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
            if(autoMode == AutoSide.RIGHT) {
                addSequential(new DriveDistanceCommand(320,0,0.5,15,false));
                //addSequential(new DriveDistanceCommand(320,0,0.5,15,false));
            }
        }
        if(autoType == AutoType.SCALE) {
            if(scale == LEFT) {
                addSequential(new AccelerateDistanceCommand(190, 0, 0.7, 5.0, false));
                addSequential(new ArcCommand(120, 0, 40, 0.4));
                addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.SCALE,2.5));
                addSequential(new ShootCubeCommand());
                addSequential(new BackupCommand(25));
                addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.INTAKE,2.5));
                addSequential(new RotateToAngleCommand(150, 0.5));
                addSequential(new DriveDistanceCommand(54, 150, 0.5, 7.0, false));
                addSequential(new IntakeCubeCommand());

            }
            if(scale == RIGHT) {
                addSequential(new AccelerateDistanceCommand(160, 0, 1.0, 3.0, false));
                addSequential(new ArcCommand(100, 0, 90, 0.5));
                addSequential(new DriveDistanceCommand(120, 90, 1.0, 5.0, false));
                addSequential(new ArcCommand(100, 90, 0, 0.5));
                addSequential(new DriveDistanceCommand(10, 0, 0.5, 5.0, false));
                addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.SCALE,2.5));
                addSequential(new ShootCubeCommand());
            }
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
