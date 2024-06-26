package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Utils.Configuration;

public class Intake {
    private final DcMotorEx intakeMotor;
    private final Servo intakeAngle;
    private final DigitalChannel limitSwitch;

    public Intake.Roller roller;
    public Intake.Lifter lifter;
    public Intake(HardwareMap hardwareMap){
        intakeMotor = (DcMotorEx) hardwareMap.dcMotor.get(Configuration.INTAKE_MOTOR);
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeAngle = hardwareMap.servo.get(Configuration.INTAKE_SERVO);
        limitSwitch = hardwareMap.digitalChannel.get(Configuration.CONVEYOR_SWITCH);
        roller = new Roller();
        lifter = new Lifter();
        lifter.setPosition(LifterPosition.INIT);
    }

    public enum LifterPosition { //Pixels From Top
        INIT(1), DEFAULT(0.743), STANDBY(0.501), FIRST_PIXEL(0.407), SECOND_PIXEL(0.3888), THIRD_PIXEL(0.355), FOURTH_PIXEL(0.325);
        final double servoPosition;
        LifterPosition(double servoPosition) { this.servoPosition = servoPosition; }
        public double getServoPositionAsDouble() {
            return servoPosition;
        }
    }

    public class Lifter extends SubsystemBase {
        private LifterPosition currentPosition = LifterPosition.DEFAULT;
        public void setPosition(LifterPosition position) {
            currentPosition = position;
            intakeAngle.setPosition(position.getServoPositionAsDouble());
        }

        public LifterPosition getPosition() {
            return currentPosition;
        }
    }

    public class Roller extends SubsystemBase {
        public final double COLLECT_POWER = 1;
        public final double PURPLE_PIXEL_FAR_RED_POWER = 0.7;
        public final double PURPLE_PIXEL_FAR_BLUE_POWER = 1;
        public final double PURPLE_PIXEL_CLOSE_RED_POWER = -0.5;
        public final double PURPLE_PIXEL_CLOSE_BLUE_POWER = -0.4;
        public final double EJECT_POWER = -0.9;

        private int pixelCount;
        public boolean isSwitchOnRelease = false;

        public void setPixelCount(int pixelCount) {
            this.pixelCount = pixelCount; //set starting pixel count;
        }

        public int getPixelCount() {
            return pixelCount;
        }
        public void setPower(double power) {
            intakeMotor.setPower(power);
        }
        public boolean currentSwitchState() {
            return limitSwitch.getState();
        }

        //On Release
        private void updatePixelCount() {
            if (!currentSwitchState()) {
                if (isSwitchOnRelease) {
                    pixelCount++;
                    isSwitchOnRelease = false;
                }
            } else {
                isSwitchOnRelease = true;
            }
        }

        public boolean isRobotFull() {
            return getPixelCount() >= 2;
        }
        @Override
        public void periodic() {
            updatePixelCount();
        }

        public void stop() {
            setPower(0);
        }
    }
}
