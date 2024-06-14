package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Utils.Configuration;

public class Shooter extends SubsystemBase {

    DcMotorEx shooterMotor;
    DcMotorEx shooterMotor2;
    Servo shooterAngle;
    public Shooter(HardwareMap hardwareMap) {
        this.shooterMotor = hardwareMap.get(DcMotorEx.class, "shooter");
        this.shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.shooterMotor2 = hardwareMap.get(DcMotorEx.class, "shooter2");
        this.shooterAngle = hardwareMap.get(Servo.class, "shooterAngle");

    }

    public void setPower(double power) {
        shooterMotor.setPower(power);
        shooterMotor2.setPower(power);
    }
    public void setPosition(double position){
        shooterAngle.setPosition(position);
    }

}

