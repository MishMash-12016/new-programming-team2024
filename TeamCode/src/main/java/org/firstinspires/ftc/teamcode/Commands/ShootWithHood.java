package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class ShootWithHood extends SequentialCommandGroup {
    public ShootWithHood(Shooter shooter) {
        super(
                new ChangeServoAngle(shooter, 0.2),
                new WaitCommand(1000),
                new ShooterSetPower(shooter, 1)
        );
        addRequirements(shooter);
    }
}
