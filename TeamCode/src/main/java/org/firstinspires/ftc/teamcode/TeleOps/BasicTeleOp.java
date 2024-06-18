package org.firstinspires.ftc.teamcode.TeleOps;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.ShootWithHood;
import org.firstinspires.ftc.teamcode.Commands.ToggleShooter;
import org.firstinspires.ftc.teamcode.SubSystems.Shooter;

@TeleOp(name = "Teleop")
public class BasicTeleOp extends CommandOpMode {

    // subsystems here
    Shooter shooter;

    @Override
    public void initialize() {
        //init gamepad
        GamepadEx gamepadEx1 = new GamepadEx(gamepad1);

        //init subsystem
        //Ex: subsystem  = new Subsystem(hardwareMap)
        shooter = new Shooter(hardwareMap);


        //setup buttons
        //Ex: gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whenActive(new SomethingCommand());
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ShootWithHood(shooter));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.A).toggleWhenActive(new ToggleShooter(shooter, 1));


        //create new triggers
        //Ex: Trigger newTrigger = new Trigger(() -> gamepadEx1.getLeftY() > 0.5);
    }

    @Override
    public void run() {
        super.run();

        //setting up telemetry
        //telemetry.addData("name", value)



    }
}