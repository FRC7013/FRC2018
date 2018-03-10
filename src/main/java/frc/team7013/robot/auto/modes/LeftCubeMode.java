package frc.team7013.robot.auto.modes;

import frc.team7013.robot.auto.AutoModeBase;
import frc.team7013.robot.auto.AutoModeEndedException;
import frc.team7013.robot.auto.actions.DriveArcAction;

public class LeftCubeMode extends AutoModeBase{

    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new DriveArcAction(100,0,310,0.6));
    }

}
