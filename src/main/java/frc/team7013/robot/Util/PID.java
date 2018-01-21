package frc.team7013.robot.Util;

public class PID {

    private double Kp, Kd, Ki, output, previous_Kd, accum_Ki;
    private int error, setpoint, cutoff_val;

    PID(double Kp, double Kd, double Ki, int cutoff_val){
        this.Kp = Kp;
        this.Kd = Kd;
        this.Ki = Ki;
        this.cutoff_val = cutoff_val;
        setpoint = 0;
    }

    public void newSetpoint(int setpoint){
        this.setpoint = setpoint;
        error = 0;
        output = 0;
        previous_Kd = 0;
        accum_Ki = 0;
    }

    public void doPID(int current){

        error = setpoint - current; //calculate error

        accum_Ki += error; //calculate the integral of the Ki term
        previous_Kd -= error; //calculate the derivate of the Kd term

        output = (error * Kp) + (accum_Ki * Ki) + (previous_Kd * Kd);

        if(error > cutoff_val)
            output = (Math.abs(output) >= 1.0)?(output / Math.abs(output)):(output) ;
        else
            output = 0;


    }
    public double getOutput(){ return output; }
}
