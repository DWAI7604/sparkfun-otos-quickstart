package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

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
        Pose2d beginPose = new Pose2d(-5, 60, Math.toRadians(90));
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        drive.updatePoseEstimate();

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .lineToYSplineHeading(33, Math.toRadians(0))
                        .waitSeconds(2)
                        .setTangent(Math.toRadians(90))
                        .lineToY(48)
                        .setTangent(Math.toRadians(0))
                        .lineToX(32)
                        .strafeTo(new Vector2d(44.5, 30))
                        .turn(Math.toRadians(180))
                        .lineToX(47.5)
                        .waitSeconds(3)
                        .build());
    }


}
