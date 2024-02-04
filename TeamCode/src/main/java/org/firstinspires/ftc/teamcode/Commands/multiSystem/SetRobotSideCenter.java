package org.firstinspires.ftc.teamcode.Commands.multiSystem;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.ArmPosition;
import org.firstinspires.ftc.teamcode.ArmPositionSelector;
import org.firstinspires.ftc.teamcode.Side;
import org.firstinspires.ftc.teamcode.SubSystems.AntiTurret;
import org.firstinspires.ftc.teamcode.SubSystems.Elbow;
import org.firstinspires.ftc.teamcode.SubSystems.Elevator;
import org.firstinspires.ftc.teamcode.SubSystems.Extender;
import org.firstinspires.ftc.teamcode.SubSystems.Turret;

public class SetRobotSideCenter extends SequentialCommandGroup {
    public SetRobotSideCenter(Elevator elevator, Elbow elbow, Extender extender, Turret turret, AntiTurret antiTurret) {
        super(
                new InstantCommand(() -> ArmPositionSelector.setRobotSide(Side.CENTER)),
                new ArmGetToPosition(elevator, elbow, extender, turret, antiTurret, ArmPosition.SAFE_PLACE, false)
        );
    }
}