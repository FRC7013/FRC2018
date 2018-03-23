package frc.team7013.robot.commands.arm;

import frc.team7013.robot.LiftConst;
import frc.team7013.robot.Robot;
import frc.team7013.robot.commands.drive.TSafeCommand;

public class SetArmCommand extends TSafeCommand {

    LiftConst.LIFT_POSITION liftPosition;

    public SetArmCommand(LiftConst.LIFT_POSITION position,double time) {
        super(time);
        Robot.armSubsystem.setPidState(true);
        liftPosition = position;
    }

    public void initialize() {
        Robot.armSubsystem.setArmPosition(liftPosition);
    }

    public void end() {

    }

    protected boolean isFinished() {
        if(super.isFinished()) {
            return true;
        }
        return false;
    }
}
