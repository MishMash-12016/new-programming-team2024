package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDController;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class ShootByRPM extends CommandBase {

    Shooter shooter;
    double RPM;
    PIDController pid;

    public ShootByRPM(Shooter shooter, double RPM) {
        this.shooter = shooter;
        this.RPM = RPM;
        this.pid = new PIDController(0, 0, 0);
    }


    @Override
    public void execute() {
        double output = pid.calculate(shooter.getVelocity(), RPM);
        shooter.setPower(output);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setPower(0);
    }
}
