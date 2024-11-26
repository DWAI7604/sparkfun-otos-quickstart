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
    Pose2d startPose = new Pose2d(9, -63.5, Math.toRadians(270));
    Pose2d beforePickupPose = new Pose2d(57.5, -44, Math.toRadians(90));

    @Override
    public void runOpMode() throws InterruptedException {
        PinpointDrive drive = new PinpointDrive(hardwareMap, startPose);
        drive.updatePoseEstimate();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                drive.actionBuilder(startPose)
                        .strafeTo(new Vector2d(4.5,-36))
                        .lineToY(-38)
                        .splineTo(new Vector2d(36,-36), Math.toRadians(0))
                        .setTangent(Math.toRadians(90))
                        .lineToYLinearHeading(-14, Math.toRadians(0))
                        .setTangent(Math.toRadians(180))
                        .lineToX(47.5)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-48)
                        .lineToY(-14)
                        .setTangent(Math.toRadians(180))
                        .lineToX(57.5)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-48)
                        .lineToY(-44)
                        .strafeToLinearHeading(new Vector2d(36, -57), Math.toRadians(90))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-63)
                        .lineToY(-60)
                        .strafeToLinearHeading(new Vector2d(3, -45), Math.toRadians(270))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-36)
                        .lineToY(-38)
                        .strafeToLinearHeading(new Vector2d(36, -57), Math.toRadians(90))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-63)
                        .lineToY(-60)
                        .strafeToLinearHeading(new Vector2d(1, -45), Math.toRadians(270))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-36)
                        .lineToY(-38)
                        .strafeToLinearHeading(new Vector2d(36, -57), Math.toRadians(90))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-63)
                        .lineToY(-60)
                        .strafeToLinearHeading(new Vector2d(-1, -45), Math.toRadians(270))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-36)



                        //.lineToYLinearHeading(-24, Math.toRadians(270))

                        .build());

    }

}
