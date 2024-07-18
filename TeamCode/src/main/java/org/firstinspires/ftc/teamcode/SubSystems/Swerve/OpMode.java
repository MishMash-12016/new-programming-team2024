package org.firstinspires.ftc.teamcode.SubSystems.Swerve;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.DoubleSupplier;

public class OpMode extends CommandOpMode {
    Module fr = new Module(hardwareMap,"frMotor","frServo",6);
    Module fl = new Module(hardwareMap,"flMotor","flServo",6);
    Module br = new Module(hardwareMap,"brMotor","brServo",6);
    Module bl = new Module(hardwareMap,"blMotor","blServo",6);

    Vector2d turn;
    Vector2d strafe;

    private double rightStickX() {
        return gamepad1.right_stick_x;
    }
    private double rightStickY() {
        return gamepad1.right_stick_y;
    }
    private double leftStickX() {
        return gamepad1.left_stick_x;
    }
    private double leftStickY() {
        return gamepad1.left_stick_y;
    }

    @Override
    public void initialize() {
        DoubleSupplier rightStickX = () -> rightStickX();
        DoubleSupplier rightStickY = () -> rightStickY();
        turn = new Vector2d(rightStickX.getAsDouble(),rightStickY.getAsDouble());

        DoubleSupplier leftStickX = () -> leftStickX();
        DoubleSupplier leftStickY = () -> leftStickY();
        turn = new Vector2d(leftStickX.getAsDouble(),leftStickY.getAsDouble());

    }

    @Override
    public void run() {
        super.run();
    }


}


