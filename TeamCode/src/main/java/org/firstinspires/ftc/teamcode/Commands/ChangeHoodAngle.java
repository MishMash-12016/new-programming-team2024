package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

import java.util.function.DoubleSupplier;

public class ChangeHoodAngle extends InstantCommand
{

    public ChangeHoodAngle(Shooter shooter, double angle)
    {

        super(()-> shooter.setPosition(angle));

    }
}

