package org.firstinspires.ftc.teamcode.Commands.armCommands.elevator;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Commands.armCommands.elbow.ElbowGetToPosition;
import org.firstinspires.ftc.teamcode.Commands.intakeLifter.IntakeSetLifterPosition;
import org.firstinspires.ftc.teamcode.RobotControl;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class ElevatorJoystick extends CommandBase {

    private final double ELBOW_POS = 1;
    private final double TOLERANCE = 0.1;

    RobotControl robot;
    double power;

    public ElevatorJoystick(double power, RobotControl robot) {
        this.power = power;
        this.robot = robot;
    }

    @Override
    public void initialize() {
        if(Math.abs(robot.elbow.getServoPosition() - ELBOW_POS) > TOLERANCE) {
            new ElbowGetToPosition(robot.elbow, ELBOW_POS).schedule();
            new IntakeSetLifterPosition(robot.intake.lifter, Intake.LifterPosition.INIT).schedule();
        }
        robot.elevator.setPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        robot.elevator.stop();
    }
}
