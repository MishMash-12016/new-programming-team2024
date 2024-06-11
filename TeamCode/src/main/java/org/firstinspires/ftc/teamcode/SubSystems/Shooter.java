package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter extends SubsystemBase {
    DcMotorEx motor;

    public Shooter(HardwareMap hardwareMap) {
        this.motor = hardwareMap.get(DcMotorEx.class, "shooter");

    }

    public void setPower(double power) {
        motor.setPower(power);
    }


}
