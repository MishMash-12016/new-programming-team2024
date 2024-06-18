package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.apache.commons.math3.analysis.function.Power;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class ShooterSetPower extends InstantCommand {
    public ShooterSetPower(Shooter shooter, double power) {
        super(
                () -> shooter.setPower(power)
        );
    }
}
