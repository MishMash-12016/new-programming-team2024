package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter extends SubsystemBase {

    DcMotorEx motor;
    Servo servo;

    public Shooter(HardwareMap hardwareMap) {
        this.motor = hardwareMap.get(DcMotorEx.class, "shooter");
        this.servo = hardwareMap.get(Servo.class, "servo");
        this.register();
    }

    public void setPower(double power) {
        motor.setPower(power);
    }

    public void setPosition(double angle){
        servo.setPosition(angle);
    }
}
