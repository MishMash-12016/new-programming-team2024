package org.firstinspires.ftc.teamcode.MMLib.PID;

import com.arcrobotics.ftclib.controller.PIDController;

import org.firstinspires.ftc.teamcode.MMLib.Subsystems.MMPowerSubsystem;

public abstract class MMPIDSubsystem extends MMPowerSubsystem<Double> {

    private final PIDController pidController;

    /**
     * default constructor to create the {@link PIDController} object
     * @param kP the P coefficient
     * @param kI the I coefficient
     * @param kD the D coefficient
     * @param tolerance the controller's tolerance (what is "at point")
     */
    public MMPIDSubsystem(double kP, double kI, double kD, double tolerance) {
        pidController = new PIDController(kP, kI, kD);
        pidController.setTolerance(tolerance);
    }

    /**
     * this constructor can be used in case u want to insert ur own pid controller
     * @param pidController ur pid controller object
     */
    public MMPIDSubsystem(PIDController pidController) {
        this.pidController = pidController;
    }

    public PIDController getPidController() {
        return pidController;
    }

    /**
     * this method should return the value to be fed into the PID controller
     * @return subsystem value
     */
    public abstract double getCurrentValue();

    /**
     * this method will help u add a kF component (optional).
     * @return the power to feed to the system
     */
    public double getFeedForwardPower() {
        return 0;
    }

    /**
     * this method will be called once the pidCommand isFinished (atSetPoint)
     * <p>
     * this is also called when ur done tuning the FF power (using the {@link MMTuningFFCommand})
     */
    public void stop() {}

}
