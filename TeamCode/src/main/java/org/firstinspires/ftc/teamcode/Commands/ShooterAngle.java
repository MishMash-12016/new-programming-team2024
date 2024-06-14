package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.robotcore.robocol.Command;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class ShooterAngle extends CommandBase {

    Shooter shooter;
    DoubleSupplier position;

    public ShooterAngle(Shooter shooter, DoubleSupplier position) {
        this.shooter = shooter;
        this.position = position;
        this.addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.setPosition(position.getAsDouble());
    }
}
