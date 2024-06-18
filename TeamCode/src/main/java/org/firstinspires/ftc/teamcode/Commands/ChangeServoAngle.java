package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

public class ChangeServoAngle extends InstantCommand {
    public ChangeServoAngle(Shooter shooter, double angle) {
        super(() -> shooter.setPosition(angle)
        );
    }
}
