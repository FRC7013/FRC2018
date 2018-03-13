package frc.team7013.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.team7013.robot.Robot;

public class TSafeCommand extends Command {

    private final double maxTimeSec;

    public TSafeCommand(double maxTimeSec) {
        this.maxTimeSec = maxTimeSec;
    }

    protected boolean isFinished() {
        if (isCancelled() || isTimedOut()) {
            return true;
        }
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isTimedOut() {
        if (timeSinceInitialized() >= maxTimeSec) {
            return true;
        }
        return false;
    }
}
