package frc.team7013.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team7013.robot.LiftConst;
import frc.team7013.robot.Robot;
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

    public enum PathType {
        MOTION_PROFILE,
        PID
    }

    public enum SecondAction {
        SWITCH,
        VAULT
    }

    //SET AUTO MODE HERE!!!!!!!
    AutoSide autoMode = AutoSide.LEFT;
    AutoType autoType = AutoType.SWITCH;
    PathType pathType = PathType.MOTION_PROFILE;
    SecondAction secondAction = SecondAction.SWITCH;

    public AutonomousCommand() {
        char closeSwitch = GameData.getCloseSwitch();
        char scale = GameData.getScale();

        if(pathType == PathType.MOTION_PROFILE) {
            Robot.chassisSubsystem.disableSpeedPids(); //MIGHT NEED TO REMOVE THIS!!!
            if(closeSwitch == LEFT) {
                addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.SWITCH,0.01));
                addSequential(new PathCommand("/home/lvuser/LeftSwitch1_left_detailed.csv","/home/lvuser/LeftSwitch1_right_detailed.csv", PathCommand.DIRECTION.FORWARDS,false));
                addSequential(new ShootCubeCommand());
                addSequential(new PathCommandPart2("/home/lvuser/LeftSwitch2_left_detailed.csv","/home/lvuser/LeftSwitch2_right_detailed.csv"));
                addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.INTAKE,0.01));
                addSequential(new PathCommand("/home/lvuser/LeftSwitch3_left_detailed.csv","/home/lvuser/LeftSwitch3_right_detailed.csv", PathCommand.DIRECTION.FORWARDS,true));
                addSequential(new IntakeCubeCommand());

                if(secondAction == SecondAction.SWITCH) {
                    addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.SWITCH,0.01));
                    addSequential(new PathCommandPart2("/home/lvuser/LeftSwitch4_left_detailed.csv","/home/lvuser/LeftSwitch4_right_detailed.csv"));
                    addSequential(new PathCommand("/home/lvuser/LeftSwitch5_left_detailed.csv","/home/lvuser/LeftSwitch5_right_detailed.csv", PathCommand.DIRECTION.FORWARDS,false));
                    addSequential(new ShootCubeCommand());
                }

                if(secondAction == SecondAction.VAULT) {
                    addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.FAR,0.01));
                    addSequential(new PathCommandPart2("/home/lvuser/LeftSwitchVault4_left_detailed.csv","/home/lvuser/LeftSwitchVault4_right_detailed.csv"));
                    addSequential(new ShootCubeCommand());
                }

            }
            if(closeSwitch == RIGHT) {
                addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.SWITCH,0.01));
                addSequential(new PathCommand("/home/lvuser/RightSwitch1_left_detailed.csv","/home/lvuser/RightSwitch1_right_detailed.csv", PathCommand.DIRECTION.FORWARDS,false));
                addSequential(new ShootCubeCommand());
                addSequential(new PathCommandPart2("/home/lvuser/RightSwitch2_left_detailed.csv","/home/lvuser/RightSwitch2_right_detailed.csv"));
                addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.INTAKE,0.01));
                addSequential(new PathCommand("/home/lvuser/RightSwitch3_left_detailed.csv","/home/lvuser/RightSwitch3_right_detailed.csv", PathCommand.DIRECTION.FORWARDS,true));
                addSequential(new IntakeCubeCommand());

                if(secondAction == SecondAction.SWITCH) {
                    addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.SWITCH,0.01));
                    addSequential(new PathCommandPart2("/home/lvuser/RightSwitch4_left_detailed.csv","/home/lvuser/RightSwitch4_right_detailed.csv"));
                    addSequential(new PathCommand("/home/lvuser/RightSwitch5_left_detailed.csv","/home/lvuser/RightSwitch5_right_detailed.csv", PathCommand.DIRECTION.FORWARDS,false));
                    addSequential(new ShootCubeCommand());
                }

                if(secondAction == SecondAction.VAULT) {
                    addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.FAR,0.01));
                    addSequential(new PathCommandPart2("/home/lvuser/LeftSwitchVault4_left_detailed.csv","/home/lvuser/LeftSwitchVault4_right_detailed.csv"));
                    addSequential(new ShootCubeCommand());
                }

            }
                    }

        if(pathType == PathType.PID) {
            if(autoType == AutoType.SWITCH) {
                if(autoMode == AutoSide.LEFT) {
                    addSequential(new DriveDistanceCommand(-300,0,-0.5,15,false));  //should be -320
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
                    addSequential(new DriveDistanceCommand(-300,0,-0.5,15,false));
                    //addSequential(new DriveDistanceCommand(320,0,0.5,15,false));
                }
            }
            if(autoType == AutoType.SCALE) {
                if(scale == LEFT) {
                    addSequential(new AccelerateDistanceCommand(320, 0, 0.55, 10.0, false));
                    addSequential(new ArcCommand(-85, 0, 30, -0.4)); //I think keep speed negative
                    //and distance positive
                    addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.SCALE,3.0));
                    addSequential(new ShootCubeCommand());

                }
                if(scale == RIGHT) {


                    //addSequential(new AccelerateDistanceCommand(310, 0, 0.45, 10, false));
                    //addSequential(new ArcCommand(-85, 0, 90, -0.13));
                    Robot.chassisSubsystem.setGyroAngle(90);
                    addSequential(new DriveDistanceCommand(-160, 90, -0.45, 10.0, false));
                    addSequential(new ArcCommand(-100, 90, 0, -0.3));
                    addSequential(new SetArmCommand(LiftConst.LIFT_POSITION.SCALE,3.0));
                    addSequential(new ShootCubeCommand());
                }
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
