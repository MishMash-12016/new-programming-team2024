package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Elevator extends SubsystemBase {
    private final double TICKS_PER_REVOLUTION = 537.6;
    private final double SPROCKET_DIAMETER = 2.59;
    private final int NUM_LEVELS = 3;
    private final double kP = 0;
    private final double kI = 0;
    private final double kD = 0;
    private DcMotorEx motorRight;
    private DcMotorEx motorLeft;
    private PIDController pidController;

    public Elevator(HardwareMap hardwareMap) {
        this.motorRight = hardwareMap.get(DcMotorEx.class, "ElevatorRight");
        this.motorLeft = hardwareMap.get(DcMotorEx.class, "ElevatorLeft");
        this.pidController = new PIDController(kP, kI, kD);
        motorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //TODO: check which motor to reverse
    }


    public void setPower(double power) {
        motorRight.setPower(power);
        motorLeft.setPower(power);
    }
    public PIDController getPidController() {
        return pidController;
    }


    /**
     * give me the height of the elevator (Cm)
     * @return
     */
    public double getHeight() {
        double revolution = motorRight.getCurrentPosition() / TICKS_PER_REVOLUTION; // this is how many revolutions have passed, rev - revolution.
        double chainLengthenInRev = Math.PI * SPROCKET_DIAMETER; // peremeter
        double chainLength = chainLengthenInRev * revolution;
        return chainLength * NUM_LEVELS;
    }

}
