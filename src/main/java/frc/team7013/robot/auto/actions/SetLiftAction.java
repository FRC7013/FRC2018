package frc.team7013.robot.auto.actions;

import frc.team7013.robot.subsystems.Lift;

public class SetLiftAction extends RunOnceAction{

    Lift.WantedState mWantedState;

    public SetLiftAction(Lift.WantedState state) {
        mWantedState = state;
    }

    @Override
    public synchronized void runOnce() {
        Lift.getInstance().setWantedState(mWantedState);
    }

}
