package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.SubSystems.Elevator;

public class ElevatorGetToHeight extends CommandBase {

    Elevator elevator;
    private double height;

    public ElevatorGetToHeight(Elevator elevator, double height) {
        this.elevator = elevator;
        this.height = height;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.getPidController().setSetPoint(height); // Desired destination
    }

    @Override
    public void execute() {
        elevator.setPower(elevator.getPidController().calculate(elevator.getHeight())); // Calculation of the force at a point
    }

    @Override
    public boolean isFinished() {
        return elevator.getPidController().atSetPoint(); // Have we reached the location?
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setPower(0);
    }
}
