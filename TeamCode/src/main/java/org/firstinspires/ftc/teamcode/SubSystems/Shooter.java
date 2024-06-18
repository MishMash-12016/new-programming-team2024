package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter extends SubsystemBase {

    DcMotorEx motor1;
    DcMotorEx motor2;
    Servo servo;

    public Shooter(HardwareMap hardwareMap) {
        this.motor1 = hardwareMap.get(DcMotorEx.class, "shooter");
        this.motor2 = hardwareMap.get(DcMotorEx.class, "shooter2");
        this.servo = hardwareMap.get(Servo.class, "servo");
        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        this.register();
    }

    public void setPower(double power) {
        motor1.setPower(power);
        motor2.setPower(power);
    }

    public void setPosition(double angle){
        servo.setPosition(angle);
    }

}
