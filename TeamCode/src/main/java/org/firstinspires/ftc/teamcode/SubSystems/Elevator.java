package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Elevator extends SubsystemBase {
    DcMotorEx[] elevatorMotors;
    double TICKS_PER_ROTATION = 2048;
    double ROTATIONS_PER_CM = 1;

public Elevator(HardwareMap hardwareMap){
     this.elevatorMotors = new DcMotorEx[3];
     elevatorMotors[0] = hardwareMap.get(DcMotorEx.class, "elevatorMotor1");
     elevatorMotors[1] = hardwareMap.get(DcMotorEx.class, "elevatorMotor2");
     elevatorMotors[2] = hardwareMap.get(DcMotorEx.class, "elevatorMotor3");

    }
    public void setPower(double power) {
        for (DcMotorEx elevatorMotor : elevatorMotors) {
            elevatorMotor.setPower(power);
        }
    }
    public double getHeight(){
        double ticks = elevatorMotors[0].getCurrentPosition();
        double rotation = ticks / TICKS_PER_ROTATION;
        double cm = rotation / ROTATIONS_PER_CM;

        return /*your dicks length in*/ cm;
    }

}
