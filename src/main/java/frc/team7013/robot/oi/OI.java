package frc.team7013.robot.oi;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */


import frc.team7013.robot.TPackage.oi.*;

public class OI {

    public AutoSelector autoSelector = new AutoSelector();

    private TGameController gameController = new TGameController_Logitech(0);
    private TGameController operatorController = new TGameController_Logitech(1);


    //Driver Controller
    public double getLeftSpeed() {
        return gameController.getAxis(TStick.LEFT, TAxis.Y);
    }

    public double getRightSpeed() {
        return gameController.getAxis(TStick.RIGHT, TAxis.Y);
    }

    public boolean getIntakeButton() {
        return operatorController.getButton(TButton.A);
    }

    public boolean getSwitchButton() {
        return operatorController.getButton(TButton.X);
    }

    public boolean getScaleButton() {
        return operatorController.getButton(TButton.Y);
    }

    public boolean getStowButton() {
        return operatorController.getButton(TButton.B);
    }

    public boolean getIntakeCubeButton() {
        return gameController.getButton(TButton.LEFT_BUMPER);
    }

    public boolean getExtakeCubeButton() {
        return gameController.getButton(TButton.RIGHT_BUMPER);
    }

    public boolean getHomeArmButton() {
        return operatorController.getButton(TButton.RIGHT_BUMPER);
    }

    public double getElevatorAdjust() {
        return operatorController.getAxis(TStick.RIGHT,TAxis.Y);
    }

    public boolean getElevatorUp() {
        return operatorController.getButton(TButton.LEFT_BUMPER);
    }

    public boolean getElevatorDown() {
        return operatorController.getButton(TButton.RIGHT_BUMPER);
    }

    public boolean getEnablePid() {
        return gameController.getButton(TButton.A);
    }

    public boolean getDisablePid() {
        return gameController.getButton(TButton.B);
    }

    public void updatePeriodic() {
    }


}