package frc.team7013.robot.auto.actions;

import frc.team7013.robot.subsystems.Intake;

public class ScoreCubeAction extends RunOnceAction {

    @Override
    public void runOnce() {
        Intake.getInstance().setWantedState(Intake.WantedState.SCORE);
    }
}
