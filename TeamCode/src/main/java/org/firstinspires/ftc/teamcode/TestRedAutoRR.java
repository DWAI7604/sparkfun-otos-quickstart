package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
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
        Pose2d beginPose = new Pose2d(9, -57, Math.toRadians(270));
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        drive.updatePoseEstimate();

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        //Place 1
                        .strafeTo(new Vector2d(2, -26))
                        .waitSeconds(1)
                        .lineToY(-32)
                        .splineTo(new Vector2d(48, -44), Math.toRadians(90))
                        .waitSeconds(1)
                        .lineToYLinearHeading(-50, Math.toRadians(270))
                        .strafeTo(new Vector2d(60, -44))
                        .waitSeconds(1)
                        .lineToY(-40)
                        .strafeTo(new Vector2d(40, -70))
                        .waitSeconds(1)
                        //Place2
                        .strafeTo(new Vector2d(0, -25))
                        .waitSeconds(1)
                        .lineToY(-32)
                        .splineTo(new Vector2d(58, -44), Math.toRadians(90))
                        .waitSeconds(1)
                        .lineToYLinearHeading(-50, Math.toRadians(270))
                        .strafeTo(new Vector2d(60, -44))
                        .waitSeconds(1)
                        .lineToY(-40)
                        .strafeTo(new Vector2d(40, -70))
                        .waitSeconds(1)
                        //Place 3
                        .strafeTo(new Vector2d(-2, -25))
                        .build());
    }


}
