package org.firstinspires.ftc.teamcode.SubSystems.Swerve;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.DoubleSupplier;

public class Module extends SubsystemBase{
    private DcMotorEx speedMotor;
    private CRServo angleServo;
    private DoubleSupplier angleEncoder;
    private PIDController pidController = new PIDController(1,1,1);
    double targetAngle = 0;
    double speed = 0;
    private final double OFFSET;
    private final double TICKS_PER_ROTATION = 1;


    public Module(HardwareMap hardwareMap,String motorName,String servoName ,double OFFSET){
        this.speedMotor = (DcMotorEx) hardwareMap.dcMotor.get(motorName);
        this.angleEncoder = () -> speedMotor.getCurrentPosition();
        this.angleServo = (CRServo) hardwareMap.servo.get(servoName);
        this.OFFSET = OFFSET;
    }
    /**
     * @return servo angle in radians
     * **/
    public double getAngle() {return this.angleEncoder.getAsDouble() / TICKS_PER_ROTATION * 2 * Math.PI;}
    public void setSpeed(double speed){this.speed = speed;}
    public void setAngle(double angle){this.targetAngle = angle;}


    @Override
    public void periodic() {
        angleServo.setPower(pidController.calculate(getAngle(),targetAngle));
        speedMotor.setPower(speed);
        FtcDashboard.getInstance().getTelemetry().addData("", pidController.atSetPoint() );
    }


}
