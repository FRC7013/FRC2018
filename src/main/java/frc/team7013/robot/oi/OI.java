package frc.team7013.robot.oi;

import frc.team7013.robot.TPackage.oi.*;

/*
DRIVER CONTROLLER:
    Sticks:
        Left Stick Y Axis   = Left Drive Speed
        Right Stick Y Axis  = Right Drive Speed
        CONSIDER USING L&R STICK PRESS? -> for tip cube command
    Buttons:
        Right Bumper        = Intake Cube
        Left Bumper         = Extake Cube

OPERATOR CONTROLLER:
    Sticks:

    Buttons:
        A                   = Intake Position
        X                   = Switch Position
        Y                   = Scale Position
        B                   = Stow Position
 */

public class OI {
    public enum LIFT_POSITION {
        INTAKE,SWITCH,SCALE,STOW;
    }

    private LIFT_POSITION liftPosition = LIFT_POSITION.INTAKE;

    public AutoSelector autoSelector = new AutoSelector();

    private TGameController gameController = new TGameController_Logitech(0);
    private TGameController operatorController = new TGameController_Logitech(1);

    private TToggle pidToggle = new TToggle(gameController, TStick.RIGHT);

    private TButtonPressDetector intakeLiftPositionButtonPress =
            new TButtonPressDetector(operatorController, TButton.A);

    private TButtonPressDetector switchLiftPositionButtonPress =
            new TButtonPressDetector(operatorController,TButton.X);

    private TButtonPressDetector scaleLiftPositionButtonPress =
            new TButtonPressDetector(operatorController,TButton.Y);

    private TButtonPressDetector stowLiftPositionButtonPress =
            new TButtonPressDetector(operatorController,TButton.B);

    public double getLeftSpeed() {
        return gameController.getAxis(TStick.LEFT, TAxis.Y);
    }

    public double getRightSpeed() {
        return gameController.getAxis(TStick.RIGHT,TAxis.Y);
    }

    public boolean getSpeedPidEnabled() {
        return pidToggle.get();
    }

    public void setSpeedPidToggle(boolean state) {
        pidToggle.set(state);
    }

    //Operator Controls
    public boolean getIntakeLiftPositionButton() {
        return intakeLiftPositionButtonPress.get();
    }

    public boolean getSwitchLiftPositionButton() {
        return switchLiftPositionButtonPress.get();
    }

    public boolean getScaleLiftPositionButton() {
        return scaleLiftPositionButtonPress.get();
    }

    public boolean getStowLiftPositionButton() {
        return stowLiftPositionButtonPress.get();
    }

    private void updateLiftPosition() {
        if(getIntakeLiftPositionButton()) {
            liftPosition = LIFT_POSITION.INTAKE;
        }
        if(getSwitchLiftPositionButton()) {
            liftPosition = LIFT_POSITION.SWITCH;
        }
        if(getScaleLiftPositionButton()) {
            liftPosition = LIFT_POSITION.SCALE;
        }
        if(getStowLiftPositionButton()) {
            liftPosition = LIFT_POSITION.STOW;
        }
    }

    public LIFT_POSITION getLiftPosition() {
        return liftPosition;
    }

    public void setLiftPosition(LIFT_POSITION liftPosition) {this.liftPosition = liftPosition;}


    //Intake Controls
    public boolean getIntake() {
        return gameController.getButton(TButton.RIGHT_BUMPER);
    }

    public boolean getExtake() {
        return gameController.getButton(TButton.LEFT_BUMPER);
    }

    public void updatePeriodic() {
        pidToggle.updatePeriodic();
        updateLiftPosition();
    }

}
