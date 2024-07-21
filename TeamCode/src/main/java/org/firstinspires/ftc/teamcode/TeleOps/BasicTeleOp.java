package org.firstinspires.ftc.teamcode.TeleOps;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Commands.ElevatorGetToHeight;
import org.firstinspires.ftc.teamcode.Commands.ShootByPower;
import org.firstinspires.ftc.teamcode.Commands.ShootBySupplier;
import org.firstinspires.ftc.teamcode.SubSystems.Elevator;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

@TeleOp(name = "Teleop")
public class BasicTeleOp extends CommandOpMode {

    // subsystems here
    Shooter shooter;
    Elevator elevator;

    @Override
    public void initialize() {
        //init gamepad
        GamepadEx gamepadEx1 = new GamepadEx(gamepad1);

        //init subsystem
        //Ex: subsystem  = new Subsystem(hardwareMap)
        shooter = new Shooter(hardwareMap);
        elevator = new Elevator(hardwareMap);

        //setup buttons
        //Ex: gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whenActive(new SomethingCommand());
        gamepadEx1.getGamepadButton(GamepadKeys.Button.X).whileActiveOnce(new ShootByPower(shooter,0.5));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.Y).whileActiveOnce(new ShootBySupplier(shooter,()->gamepadEx1.getRightY()));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.X).whenActive(new ElevatorGetToHeight(elevator, 10));

        //create new triggers
        //Ex: Trigger newTrigger = new Trigger(() -> gamepadEx1.getLeftY() > 0.5);
    }

    @Override
    public void run() {
        super.run();

        //setting up telemetry
        //telemetry.addData("name", value)
        telemetry.update();
    }
}