package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Shooter shooter) {
        super(
                new ChangeServoAngle(shooter, 0.5),
                new WaitCommand(1000),
                new ShooterSetPower(shooter, 1),
                new WaitCommand(1500),
                new ShooterSetPower(shooter , 0),
                new ChangeServoAngle(shooter, 0)
        );
        addRequirements(shooter);
    }
}
