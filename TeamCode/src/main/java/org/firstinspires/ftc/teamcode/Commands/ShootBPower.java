package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class ShootBPower extends InstantCommand {

    public ShootBPower(Shooter shooter, double power)
    {

        super(()-> shooter.setPower(power));
    }
}
