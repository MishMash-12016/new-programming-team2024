package org.firstinspires.ftc.teamcode.Commands.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.ArmPosition;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousOpMode;
import org.firstinspires.ftc.teamcode.Commands.armCommands.cartridge.CartridgeSetState;
import org.firstinspires.ftc.teamcode.Commands.armCommands.multiSystem.ArmGetToPosition;
import org.firstinspires.ftc.teamcode.Commands.armCommands.turret.RotateTurretByPID;
import org.firstinspires.ftc.teamcode.Commands.auto.trajectoryUtils.Trajectories;
import org.firstinspires.ftc.teamcode.Commands.auto.trajectoryUtils.TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.Commands.intakeRoller.IntakeRotate;
import org.firstinspires.ftc.teamcode.Commands.intakeRoller.ResetPixelCount;
import org.firstinspires.ftc.teamcode.Commands.utilCommands.DetectionSideCommandSwitch;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.RobotControl;
import org.firstinspires.ftc.teamcode.SubSystems.Cartridge;
import org.firstinspires.ftc.teamcode.Utils.AllianceColor;
import org.firstinspires.ftc.teamcode.Utils.DetectionSide;

public class ScoringCommand extends SequentialCommandGroup {

    static RobotControl robot = AutonomousOpMode.robot;

    public ScoringCommand(Command scoringCommand, Command secondScoringCommand, RobotControl robot, Integer numOfCycle) {
        ScoringCommand.robot = robot;
        addCommands(
                new ParallelCommandGroup(
                        getTrajectoryCommand(robot, numOfCycle),
                        new ConditionalCommand(
                                new IntakeRotate(robot.intake.roller, robot.intake.roller.EJECT_POWER).withTimeout(1700),
                                new InstantCommand(),
                                () -> numOfCycle != 0 /*&& robot.intake.roller.isRobotFull()*/
                        ),
                        new WaitCommand(getWaitTime(robot, numOfCycle)).andThen(
                                new ConditionalCommand(
                                        new ArmGetToPosition(robot, ArmPosition.SCORING, robot.allianceColor == AllianceColor.RED),
                                        new ArmGetToPosition(robot, ArmPosition.SAFE_PLACE, true),
                                        () -> robot.teamPropDetector.getTeamPropSide() != DetectionSide.CLOSE & numOfCycle == 0
                                ),
                                new InstantCommand(() -> RotateTurretByPID.DEADLINE_FOR_TURRET = 700),//todo maybe will work with less time
                                new ConditionalCommand(
                                        new ConditionalCommand(
                                                new ConditionalCommand(
                                                        new ConditionalCommand(
                                                                new WaitCommand(2550), //cycles far red
                                                                new WaitCommand(2700), //cycles red
                                                                () -> robot.teamPropDetector.getTeamPropSide() == DetectionSide.FAR
                                                        ),
                                                        new WaitCommand(1500), //close yellow red
                                                        () -> numOfCycle != 0
                                                ),
                                                new ConditionalCommand(
                                                        new WaitCommand(2400), //cycles blue
                                                        new WaitCommand(1200), //close yellow blue
                                                        () -> numOfCycle != 0
                                                ),
                                                () -> robot.allianceColor == AllianceColor.RED
                                        ),
                                        new InstantCommand(),
                                        () -> (numOfCycle != 0) || robot.teamPropDetector.getTeamPropSide() == DetectionSide.CLOSE
                                ),
                                scoringCommand
                        )
                ),
                new WaitCommand(500),
                new ConditionalCommand(
                        new CartridgeSetState(robot.cartridge, Cartridge.State.AUTONOMOUS_OPEN),
                        new SequentialCommandGroup(
                                new CartridgeSetState(robot.cartridge, Cartridge.State.OPEN),
                                new WaitCommand(500),
                                new CartridgeSetState(robot.cartridge, Cartridge.State.AUTONOMOUS_OPEN)
                        ),
                        () -> numOfCycle == 0
                ),
                new ResetPixelCount(robot),
                new InstantCommand(() -> RotateTurretByPID.DEADLINE_FOR_TURRET = 2000),
                new ConditionalCommand(
                        new WaitCommand(1000),
                        new WaitCommand(300),
                        () -> numOfCycle == 1
                )
// <- todo remove this when we need elevator               secondScoringCommand
        );
    }


    private long getWaitTime(RobotControl robot, int numOfCycle) {
        if(robot.allianceColor == AllianceColor.RED) {
            return numOfCycle == 0 ? 1900 : 1400;
        } else {
            return 1700;
        }
    }

    private Command getTrajectoryCommand(RobotControl robot, int numOfCycle) {
        return new ConditionalCommand(
                new ConditionalCommand(
                        new DetectionSideCommandSwitch(
                                new TrajectoryFollowerCommand(YELLOW_SIDE_FAR_RED, robot.autoDriveTrain),
                                new TrajectoryFollowerCommand(YELLOW_SIDE_CENTER_RED, robot.autoDriveTrain),
                                new TrajectoryFollowerCommand(YELLOW_FRONT_RED, robot.autoDriveTrain),
                                () -> robot.teamPropDetector.getTeamPropSide()
                        ),
                        new TrajectoryFollowerCommand(CYCLES_FRONT_RED, robot.autoDriveTrain).andThen(resetPoseEstimateFront(robot)),
                        () -> numOfCycle == 0
                ),
                new ConditionalCommand(
                        new DetectionSideCommandSwitch(
                                new TrajectoryFollowerCommand(YELLOW_SIDE_FAR_BLUE, robot.autoDriveTrain).andThen(resetPoseEstimateOnSide(robot)),
                                new TrajectoryFollowerCommand(YELLOW_SIDE_CENTER_BLUE, robot.autoDriveTrain).andThen(resetPoseEstimateOnSide(robot)),
                                new TrajectoryFollowerCommand(YELLOW_FRONT_BLUE, robot.autoDriveTrain).andThen(resetPoseEstimateFront(robot)),
                                () -> robot.teamPropDetector.getTeamPropSide()
                        ),
                        new TrajectoryFollowerCommand(CYCLES_FRONT_BLUE, robot.autoDriveTrain).andThen(resetPoseEstimateFront(robot)),
                        () -> numOfCycle == 0
                ),
                () -> robot.allianceColor == AllianceColor.RED
        );
    }

    private Command resetPoseEstimateFront(RobotControl robot) {
        return new InstantCommand(() ->
                robot.autoDriveTrain.setPoseEstimate(new Pose2d(
                        robot.autoDriveTrain.getPoseEstimate().getX(),
                        Trajectories.realBackdropFront.getY(),
                        robot.autoDriveTrain.getPoseEstimate().getHeading()
                ))
        );
    }

    private Command resetPoseEstimateOnSide(RobotControl robot) {
        return new ConditionalCommand(
                new InstantCommand(() ->
                        robot.autoDriveTrain.setPoseEstimate(new Pose2d(
                                Trajectories.realBackdropFarPoseRed.getX(),
                                Trajectories.realBackdropFarPoseRed.getY(),
                                robot.autoDriveTrain.getPoseEstimate().getHeading()
                        ))
                ),
                new InstantCommand(() ->
                    robot.autoDriveTrain.setPoseEstimate(new Pose2d(
                            Trajectories.realBackdropFarPoseBlue.getX(),
                            Trajectories.realBackdropFarPoseBlue.getY(),
                            robot.autoDriveTrain.getPoseEstimate().getHeading()
                    ))
                ),
                () -> robot.allianceColor == AllianceColor.RED
        );
    }

    //Go to backdrop depending on alliance color.


    static final TrajectorySequence YELLOW_SIDE_FAR_RED = robot.autoDriveTrain.trajectorySequenceBuilder(LeaveSpikeMark.FAR_RED.end())
                .setTangent(Math.toRadians(-90))
                .splineToSplineHeading(
                        new Pose2d(Trajectories.stackPoseRed.getX() + 3, -15, Math.toRadians(90)),
                        Math.toRadians(-90) //Tangent
                )
                .splineToConstantHeading(
                        new Vector2d(-9, -40),
                        Math.toRadians(-90) //Tangent
                )
                .splineToConstantHeading(
                        new Vector2d(-22, -60),
                        Math.toRadians(180), //Tangent
                        Trajectories.reduceVelocity(0.6),
                        Trajectories.reduceAcceleration(0.6)
                )
                .build();

    static final TrajectorySequence YELLOW_SIDE_CENTER_RED = robot.autoDriveTrain.trajectorySequenceBuilder(LeaveSpikeMark.CENTER_RED.end())
            .setTangent(Math.toRadians(-90))
            .splineToSplineHeading(
                    new Pose2d(Trajectories.stackPoseRed.getX() + 3, -15, Math.toRadians(90)),
                    Math.toRadians(-90) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(-9, -40),
                    Math.toRadians(-90) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(-21, -59),
                    Math.toRadians(180), //Tangent
                    Trajectories.reduceVelocity(0.6),
                    Trajectories.reduceAcceleration(0.6)
            )
            .build();

    static final TrajectorySequence YELLOW_FRONT_RED = robot.autoDriveTrain.trajectorySequenceBuilder(LeaveSpikeMark.CLOSE_RED.end())
            .setTangent(Math.toRadians(-40))
            .splineToConstantHeading(
                    new Vector2d(-11, 30),
                    Math.toRadians(-90)
            )
            .splineToConstantHeading(
                    new Vector2d(-12, -25),
                    Math.toRadians(-95) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(-30, -30),
                    Math.toRadians(180) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(-42, -35),
                    Math.toRadians(-90), //Tangent
                    Trajectories.reduceVelocity(0.4),
                    Trajectories.reduceAcceleration(0.4)
            )
            .splineToConstantHeading(
                    new Vector2d(-43.5, -51),
                    Math.toRadians(-90), //Tangent
                    Trajectories.reduceVelocity(0.4),
                    Trajectories.reduceAcceleration(0.4)
            )
            .build();

    static final TrajectorySequence CYCLES_FRONT_RED = robot.autoDriveTrain.trajectorySequenceBuilder(LeaveSpikeMark.CLOSE_RED.end())
            .setTangent(Math.toRadians(-70))
            .splineToConstantHeading(
                    new Vector2d(Trajectories.stackPoseRed.getX() + 1, -25),
                    Math.toRadians(-95) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(-30, -30),
                    Math.toRadians(180), //Tangent
                    Trajectories.reduceVelocity(0.7),
                    Trajectories.reduceAcceleration(0.7)
            )
            .splineToConstantHeading(
                    new Vector2d(-35, -35),
                    Math.toRadians(-90), //Tangent
                    Trajectories.reduceVelocity(0.4),
                    Trajectories.reduceAcceleration(0.4)
            )
            .splineToConstantHeading(
                    new Vector2d(-35, -50),
                    Math.toRadians(-90), //Tangent
                    Trajectories.reduceVelocity(0.3),
                    Trajectories.reduceAcceleration(0.3)
            )
            .build();



    //BLUE


    static final TrajectorySequence YELLOW_SIDE_FAR_BLUE = robot.autoDriveTrain.trajectorySequenceBuilder(LeaveSpikeMark.FAR_BLUE.end())
            .setTangent(Math.toRadians(270))
            .splineToSplineHeading(
                    new Pose2d(Trajectories.stackPoseBlue.getX() - 3, -15, Math.toRadians(90)),
                    Math.toRadians(270) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(9, -40),
                    Math.toRadians(270) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(24, -61),
                    Math.toRadians(0), //Tangent
                    Trajectories.reduceVelocity(0.6),
                    Trajectories.reduceAcceleration(0.6)
            )
            .build();

    static final TrajectorySequence YELLOW_SIDE_CENTER_BLUE = robot.autoDriveTrain.trajectorySequenceBuilder(LeaveSpikeMark.CENTER_BLUE.end())
            .setTangent(Math.toRadians(270))
            .splineToSplineHeading(
                    new Pose2d(Trajectories.stackPoseBlue.getX() + 3, -15, Math.toRadians(90)),
                    Math.toRadians(270) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(6, -40),
                    Math.toRadians(270) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(25, -59),
                    Math.toRadians(0), //Tangent
                    Trajectories.reduceVelocity(0.6),
                    Trajectories.reduceAcceleration(0.6)
            )
            .build();

    static final TrajectorySequence YELLOW_FRONT_BLUE = robot.autoDriveTrain.trajectorySequenceBuilder(LeaveSpikeMark.CLOSE_BLUE.end())
            .setTangent(Math.toRadians(220))
            .splineToConstantHeading(
                    new Vector2d(9, 30),
                    Math.toRadians(270)
            )
            .splineToConstantHeading(
                    new Vector2d(12, -25),
                    Math.toRadians(275) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(30, -30),
                    Math.toRadians(0) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(45, -35),
                    Math.toRadians(270), //Tangent
                    Trajectories.reduceVelocity(0.4),
                    Trajectories.reduceAcceleration(0.4)
            )
            .splineToConstantHeading(
                    new Vector2d(45, -52),
                    Math.toRadians(270), //Tangent
                    Trajectories.reduceVelocity(0.4),
                    Trajectories.reduceAcceleration(0.4)
            )
            .build();

    static final TrajectorySequence CYCLES_FRONT_BLUE = robot.autoDriveTrain.trajectorySequenceBuilder(CollectFromStack.SECOND_BITE_FAR_BLUE.end())
            .setTangent(Math.toRadians(-90))
            .splineToConstantHeading(
                    new Vector2d(Trajectories.stackPoseBlue.getX() - 3, -20),
                    Math.toRadians(275) //Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(30, -24),
                    Math.toRadians(0)//Tangent
            )
            .splineToConstantHeading(
                    new Vector2d(35, -35),
                    Math.toRadians(270), //Tangent
                    Trajectories.reduceVelocity(0.7),
                    Trajectories.reduceAcceleration(0.7)
            )
            .splineToConstantHeading(
                    new Vector2d(35, -51),
                    Math.toRadians(270), //Tangent
                    Trajectories.reduceVelocity(0.3),
                    Trajectories.reduceAcceleration(0.3)
            )
            .build();

}
