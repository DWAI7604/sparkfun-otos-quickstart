package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;
import com.acmerobotics.roadrunner.Trajectory;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "TestRedRR", group = "Autonomous")
public class TestRedAutoRR extends LinearOpMode{
    @Override
    public void runOpMode(){
        Pose2d beginPose = new Pose2d(9, -57, Math.toRadians(90));
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        drive.updatePoseEstimate();

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .splineTo(new Vector2d(-2, 25), Math.toRadians(270))
                        .waitSeconds(2)
                        .lineToY(32)
                        .splineTo(new Vector2d(-48, 30), Math.toRadians(90))
                        .waitSeconds(2)
                        .lineToYLinearHeading(70, Math.toRadians(90))
                        .lineToYLinearHeading(60, Math.toRadians(270))
                        .splineTo(new Vector2d(-8, 26), Math.toRadians(270))
                        .lineToY(32)
                        .splineTo(new Vector2d(-48, 30), Math.toRadians(90))
                        .waitSeconds(2)
                        .lineToYLinearHeading(70, Math.toRadians(90))
                        .lineToYLinearHeading(60, Math.toRadians(270))
                        .splineTo(new Vector2d(-2, 26), Math.toRadians(270))
                        .build());
    }


}
