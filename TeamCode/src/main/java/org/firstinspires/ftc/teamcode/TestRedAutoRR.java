package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.ParallelAction;
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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "TestRedRR", group = "Autonomous")
public class TestRedAutoRR extends RobotLinearOpMode{
    private double Kp = 0.04;
    private double Ki = 0.4;
    private double Kd = 2;
    private double xPosition = 9;
    private double yPosition = -57;
    private double heading = Math.toRadians(270);
    private DcMotor slideUp;
    private DcMotor slideUp2;
    private Servo clawServo;

    @Override
    public void runOpMode(){
        slideUp = hardwareMap.get(DcMotor.class, "slideUp");
        slideUp2 = hardwareMap.get(DcMotor.class, "slideUp2");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawServo.setDirection(Servo.Direction.REVERSE);
        slideUp.setDirection(DcMotorSimple.Direction.REVERSE);

        Pose2d beginPose = new Pose2d(xPosition, yPosition, heading);
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        drive.updatePoseEstimate();

        waitForStart();


        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .strafeTo(new Vector2d(4.5, -36))
                        .lineToY(-38)
                        .splineTo(new Vector2d(36,-36), Math.toRadians(0))
                        .setTangent(Math.toRadians(90))
                        .lineToYLinearHeading(-6, Math.toRadians(0))
                        .setTangent(Math.toRadians(180))
                        .lineToX(44.5)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-48)
                        .lineToY(-6)
                        .setTangent(Math.toRadians(180))
                        .lineToX(54.5)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-48)
                        .lineToY(-6)
                        .setTangent(Math.toRadians(180))
                        .lineToX(61)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-52)
                        .build()
        );

        clawServo.setPosition(60);
        sleep(4000);

    }


}
