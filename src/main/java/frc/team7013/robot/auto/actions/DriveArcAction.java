package frc.team7013.robot.auto.actions;

import frc.team7013.robot.subsystems.Drive;

public class DriveArcAction implements Action {

    private Drive mDrive = Drive.getInstance();

    private double dist;
    private double startDirection;
    private double endDirection;
    private double turnangle;
    private double rSpeed;
    private double lSpeed;
    private double speed;
    private double rWidth = 69.69125;//cm  //MIGHT WANT TO MESS WITH THIS

    public DriveArcAction(double dist, double startDirection, double endDirection, double speed) {

        this.dist = dist;
        this.startDirection = startDirection;
        this.endDirection = endDirection;
        this.speed = speed;
        this.turnangle = (this.endDirection - this.startDirection);

        if(this.turnangle > 180){
            this.turnangle -= 360;
        }
        else if(this.turnangle < -180) {
            this.turnangle += 360;
        }
        if (this.endDirection < 0) {
            this.endDirection += 360;
        }
    }

    @Override
    public void start() {

        double radius = this.dist/Math.abs(Math.toRadians(this.turnangle));
        double fastSpeed = this.speed;
        double slowSpeed = ((radius-(this.rWidth/2))* Math.toRadians(this.turnangle)) /
                ((radius+(this.rWidth/2))* Math.toRadians(this.turnangle)) * this.speed;

        if (this.turnangle < 0) {
            this.lSpeed = slowSpeed;
            this.rSpeed = fastSpeed;
        }
        else if (this.turnangle > 0) {
            this.lSpeed = fastSpeed;
            this.rSpeed = slowSpeed;
        }

        mDrive.setVelocitySetpoint(-lSpeed,-rSpeed);

    }

    @Override
    public void update() {
        //DON'T UPDATE HERE!
    }

    @Override
    public boolean isFinished() {
        double error = this.endDirection - mDrive.getAngle();

        if (this.turnangle < 0) {
            error += 2;
        }
        else if (this.turnangle > 0) {
            error -= 2;
        }

        if (error > 180) {
            error -= 360;
        }
        else if (error < -180) {
            error += 360;
        }

        if(Math.abs(error) < 20) {
            System.out.println("Angle error: " + error);
        }

        if (Math.abs(error) <= 2) {
            return true;
        }
        return false;
    }

    @Override
    public void done() {
        //End, when finished
    }
}
