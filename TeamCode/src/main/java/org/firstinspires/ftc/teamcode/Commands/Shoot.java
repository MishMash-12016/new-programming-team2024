package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Shooter shooter) {
        super(
                new ChangeHoodAngle(shooter, 0.5),
                new WaitCommand(1000),
                new ShootBPower(shooter, 1),
                new WaitCommand(1000),
                new ShootBPower(shooter, 0),
                new ChangeHoodAngle(shooter, 0)

        );
    }
}
