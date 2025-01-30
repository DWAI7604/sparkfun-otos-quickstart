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
import org.opencv.core.Mat;

@Config
@Autonomous(name = "BlueAuto", group = "Autonomous")
public class BlueAutoRR extends RobotLinearOpMode{
    private DcMotor leftFrontDriveMotor = null;
    private DcMotor leftBackDriveMotor = null;
    private DcMotor rightFrontDriveMotor = null;
    private DcMotor rightBackDriveMotor = null;
    DcMotor slideUpTop;
    DcMotor slideUpBottom;
    DcMotor hSlide;
    DcMotor intakeMotor;
    private Servo clawServo;
    private Servo wristServo;
    private Servo armServoLeft;
    private Servo armServoRight;
    private Servo intakeServo;
    private double xPosition = -64;
    private double yPosition = -0.5;
    private double heading = Math.toRadians(0);

    @Override
    public void runOpMode(){
        rightFrontDriveMotor = hardwareMap.get(DcMotor.class, "rightFrontDriveMotor");
        leftBackDriveMotor = hardwareMap.get(DcMotor.class, "leftFrontDriveMotor");
        rightBackDriveMotor = hardwareMap.get(DcMotor.class, "rightBackDriveMotor");
        leftFrontDriveMotor = hardwareMap.get(DcMotor.class, "leftBackDriveMotor");
        slideUpTop = hardwareMap.get(DcMotor.class, "slideUpTop");
        slideUpBottom = hardwareMap.get(DcMotor.class, "slideUpBottom");
        hSlide = hardwareMap.get(DcMotor.class, "hSlide");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        clawServo = hardwareMap.get(Servo.class, "clawServo");
        wristServo = hardwareMap.get(Servo.class, "wristServo");
        armServoLeft = hardwareMap.get(Servo.class, "armServoLeft");
        armServoRight = hardwareMap.get(Servo.class, "armServoRight");
        intakeServo = hardwareMap.get(Servo.class, "intakeServo");
        clawServo.setDirection(Servo.Direction.REVERSE);

        rightFrontDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
        leftFrontDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rightBackDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
        leftBackDriveMotor.setDirection(DcMotorEx.Direction.REVERSE);

        leftBackDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideUpTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideUpBottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        declareHardwareProperties();

        Pose2d activePose = new Pose2d(xPosition, yPosition, heading);
        PinpointDrive drive = new PinpointDrive(hardwareMap, activePose);
        drive.updatePoseEstimate();

        waitForStart();

        hSlide.setPower(0.1);
        intakeServo.setPosition(0.7);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.1);
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.3);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.3);

        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .lineToX(-35)

                        .build()
        );

        xPosition = -34.5;
        activePose = new Pose2d(xPosition, yPosition, heading);
        //encoderDrive(0.2, 1.5, MOVEMENT_DIRECTION.FORWARD);
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.65);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.65);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.01);
        sleep(600);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.7);

        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .lineToX(-40)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-31)
                        .setTangent(Math.toRadians(0))
                        .lineToXLinearHeading(-26.5,Math.toRadians(90))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-45)
                        .setTangent(Math.toRadians(0))
                        .lineToX(-35)
                        .lineToX(-25)
                        //.splineTo(new Vector2d(0,-30), Math.toRadians(0))
                        .waitSeconds(1)

                        //.strafeTo(new Vector2d(-36, -2))
                        //.lineToY(-36)
                        .waitSeconds(5)
                        .build()
        );

    }


}
