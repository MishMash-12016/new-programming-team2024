package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

import java.util.function.DoubleSupplier;

public class ShootAngleBySupplier extends CommandBase {
    Shooter shooter;
    DoubleSupplier angle;
    public ShootAngleBySupplier(Shooter shooter, DoubleSupplier angle) {
        this.shooter = shooter;
        this.angle = angle;
        this.addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.setPosition(angle.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setPosition(0);
    }
}
