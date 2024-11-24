package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.Math;

@Autonomous(name = "StateMachineTest", group = "advanced")
public class AutoStateMachineLM3 extends LinearOpMode{
    enum VerticalStates { //
        IDLING, //default state, at any height
        EXTENDING,//self explanatory
        DEPOSITING,// retraction to place, eagle claw releases
        RETRACTING // normal retraction
    }

    enum HorizontalStates { //
        IDLING, //default state, not moving
        EXTENDING,//self explanatory
        RETRACTING//retract slides
    }

    enum IntakeStates { //
        IDLING, //no spin
        FORWARD, //to intake sample
        BACKWARDS //spit sample
    }

    enum DriveStates { //
        IDLING, // paused, should not really be used in auto
        Trajectory1 //Run all drive code for auto
    }

    DriveStates currentState = DriveStates.IDLING;

    Pose2d startPose = new Pose2d(8.75, -57, Math.toRadians(270));
    Pose2d afterFirstPlacePose = new Pose2d(7.5, -24, Math.toRadians(270));
    Pose2d inFrontOfSampleClose = new Pose2d(48.5, -48, Math.toRadians(90));
    Pose2d afterSample1Pickup = new Pose2d(48.5, -39.5, Math.toRadians(90));

    @Override
    public void runOpMode() throws InterruptedException {
        PinpointDrive drive = new PinpointDrive(hardwareMap, startPose);

        drive.updatePoseEstimate();

        Action action1 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(7.5,-24))
                .waitSeconds(0.5)
                .splineTo(new Vector2d(48.5, -48), Math.toRadians(90))
                .lineToY(-39.5)
                .lineToYLinearHeading(-44, Math.toRadians(270))
                .lineToYLinearHeading(-43, Math.toRadians(90))
                .strafeTo(new Vector2d(42,-68))
                .waitSeconds(0.5)
                .splineTo(new Vector2d(5, -42), Math.toRadians(270))
                .lineToY(-24)
                .waitSeconds(0.5)
                .splineTo(new Vector2d(57.5, -48), Math.toRadians(90))
                .lineToY(-39.5)
                .lineToYLinearHeading(-44, Math.toRadians(270))
                .lineToYLinearHeading(-43, Math.toRadians(90))
                .strafeTo(new Vector2d(42,-68))
                .waitSeconds(0.5)
                .splineTo(new Vector2d(2, -42), Math.toRadians(270))
                .lineToY(-24)
                .waitSeconds(0.5)

                .lineToYLinearHeading(-32, Math.toRadians(90))
                .strafeTo(new Vector2d(42, -68))
                .waitSeconds(0.5)
                .splineTo(new Vector2d(0, -42), Math.toRadians(270))
                .lineToY(-24)
                .waitSeconds(0.5)

                .build();

        waitForStart();

        if (isStopRequested()) return;

        currentState = DriveStates.Trajectory1;

        Actions.runBlocking(new ParallelAction(
                drive.actionBuilder(startPose)
                        .strafeTo(new Vector2d(7.5,-24))
                        .build(),
                        //lift arm
                new SequentialAction(
                        //lower arm to place
                )
        ));

        Actions.runBlocking(new ParallelAction(
                drive.actionBuilder(afterFirstPlacePose)
                        .splineTo(new Vector2d(48.5, -48), Math.toRadians(90))
                        .build(),
                        //lower arm
                new SequentialAction(
                        new ParallelAction(
                                drive.actionBuilder(inFrontOfSampleClose)
                                        .lineToY(-39.5)
                                        .build(),
                                        //intake spin in
                                new SequentialAction(
                                       drive.actionBuilder(afterSample1Pickup)
                                               .lineToYLinearHeading(-44, Math.toRadians(270))
                                               .build()
                                )
                        )
                )
        ));

        //Actions.runBlocking(new );
    }

}
