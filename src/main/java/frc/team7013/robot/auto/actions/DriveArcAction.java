package frc.team7013.robot.auto.actions;

import frc.team7013.robot.subsystems.Drive;

public class DriveArcAction implements Action {

    private Drive mDrive = Drive.getInstance();

    public DriveArcAction(double dist, double startDirection, double endDirection, double speed) {
        //CONSTRUCTOR
    }

    @Override
    public void start() {
        //Start
    }

    @Override
    public void update() {
        //Update
    }

    @Override
    public boolean isFinished() {
        return THING;
    }

    @Override
    public void done() {
        //End, when finished
    }
}
