package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.Math;
@Autonomous(name = "LM2Auto", group = "advanced")
public class AutoNotAStateMachineAutoLM2 extends LinearOpMode{
    Pose2d startPose = new Pose2d(8.75, -57, Math.toRadians(270));
    Pose2d afterFirstPlacePose = new Pose2d(7.5, -24, Math.toRadians(270));

    @Override
    public void runOpMode() throws InterruptedException {
        PinpointDrive drive = new PinpointDrive(hardwareMap, startPose);
        drive.updatePoseEstimate();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(new ParallelAction(
                drive.actionBuilder(startPose)

                        .strafeTo(new Vector2d(7.5,-24))
                        .build(),
                //lift arm
                new SequentialAction(
                        //lower arm to place
                )
        ));

    }
}
