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
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.opencv.core.Mat;

@Config
@Autonomous(name = "RedAuto", group = "RRAutos")
public class TestRedRR extends RobotLinearOpMode{
//    private DcMotor leftFrontDriveMotor = null;
//    private DcMotor leftBackDriveMotor = null;
//    private DcMotor rightFrontDriveMotor = null;
//    private DcMotor rightBackDriveMotor = null;
    DcMotor slideUpTop;
    DcMotor slideUpBottom;
    DcMotor hSlide;
    DcMotor intakeMotor;
    private Servo clawServo;
    private Servo wristServo;
    private Servo armServoLeft;
    private Servo armServoRight;
    private Servo intakeServo;
    private double xPosition = -1;
    private double yPosition = -65;
    private double heading = Math.toRadians(90);
    double getBatteryVoltage() { double result = Double.POSITIVE_INFINITY; for (VoltageSensor sensor : hardwareMap.voltageSensor) { double voltage = sensor.getVoltage(); if (voltage > 0) { result = Math.min(result, voltage); } } return result; }

    @Override
    public void runOpMode(){
//        rightFrontDriveMotor = hardwareMap.get(DcMotor.class, "rightFrontDriveMotor");
//        leftBackDriveMotor = hardwareMap.get(DcMotor.class, "leftFrontDriveMotor");
//        rightBackDriveMotor = hardwareMap.get(DcMotor.class, "rightBackDriveMotor");
//        leftFrontDriveMotor = hardwareMap.get(DcMotor.class, "leftBackDriveMotor");
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

//        rightFrontDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
//        leftFrontDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
//        rightBackDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
//        leftBackDriveMotor.setDirection(DcMotorEx.Direction.REVERSE);

//        leftBackDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftFrontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightBackDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightFrontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideUpTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideUpBottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        declareHardwareProperties();

        Pose2d activePose = new Pose2d(xPosition, yPosition, heading);
        PinpointDrive drive = new PinpointDrive(hardwareMap, activePose);
        drive.updatePoseEstimate();

        waitForStart();

        //set up for first place
        hSlide.setPower(0.1);
        intakeServo.setPosition(0.7);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.1);
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.3);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.3);

        //drive to first place position
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .lineToY(-38)
                        .build()
        );

        yPosition = -34;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //Place #1

        rightFrontDriveMotor.setPower(0.3);
        rightBackDriveMotor.setPower(0.3);
        leftFrontDriveMotor.setPower(0.3);
        leftBackDriveMotor.setPower(0.3);
        //Swing arm forward to place position, wait, open claw
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.65);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.65);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.01);
        sleep(600);
        rightFrontDriveMotor.setPower(0);
        rightBackDriveMotor.setPower(0);
        leftFrontDriveMotor.setPower(0);
        leftBackDriveMotor.setPower(0);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.7);

        //swing arm back to pick up position
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.02);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.02);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.63);

        //Push 2 reds into Observation zone and drive to pick up position
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .lineToY(-37)
                        .setTangent(Math.toRadians(0))
                        .lineToX(35)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-17)
                        .setTangent(Math.toRadians(0))
                        .lineToX(46)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-50)
                        .lineToY(-17)
                        .setTangent(Math.toRadians(0))
                        .lineToX(56)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-50)
                        .strafeTo(new Vector2d(38, -52))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-57)

                        .build()
        );

        xPosition = 38;
        yPosition = -57;

        activePose = new Pose2d(xPosition, yPosition, heading);

        //Pick up #1

        //close claw and lift to ready position
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.1);
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.3);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.3);

        //drive to place position

        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .setTangent(Math.toRadians(90))
                        .strafeTo(new Vector2d(0, -36))

                        .build()
        );

        yPosition = -34;
        xPosition = 0;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //Place #2
        rightFrontDriveMotor.setPower(0.3);
        rightBackDriveMotor.setPower(0.3);
        leftFrontDriveMotor.setPower(0.3);
        leftBackDriveMotor.setPower(0.3);
        //Swing arm forward to place position, wait, open claw
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.65);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.65);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.01);
        sleep(600);
        rightFrontDriveMotor.setPower(0);
        rightBackDriveMotor.setPower(0);
        leftFrontDriveMotor.setPower(0);
        leftBackDriveMotor.setPower(0);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.7);

        //swing arm back to pick up position
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.02);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.02);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.63);

        //Drive to pickup position
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .setTangent(Math.toRadians(90))
                        .strafeTo(new Vector2d(26, -47))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-57)

                        .build()
        );

        xPosition = 26;
        yPosition = -57;

        activePose = new Pose2d(new Vector2d(xPosition, yPosition), heading);

        //Pick up #2

        //close claw and lift to ready position
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.1);
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.3);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.3);

        //drive to place position
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .setTangent(Math.toRadians(90))
                        .strafeTo(new Vector2d(-2.5, -36))

                        .build()
        );

        yPosition = -34;
        xPosition = -2.5;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //Place #3
        rightFrontDriveMotor.setPower(0.3);
        rightBackDriveMotor.setPower(0.3);
        leftFrontDriveMotor.setPower(0.3);
        leftBackDriveMotor.setPower(0.3);
        //Swing arm forward to place position, wait, open claw
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.65);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.65);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.01);
        sleep(600);
        rightFrontDriveMotor.setPower(0);
        rightBackDriveMotor.setPower(0);
        leftFrontDriveMotor.setPower(0);
        leftBackDriveMotor.setPower(0);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.7);

        //swing arm back to pick up position
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.02);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.02);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.63);

        //drive to pickup position
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .setTangent(Math.toRadians(90))
                        .strafeTo(new Vector2d(26, -47))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-58)

                        .build()
        );

        xPosition = 26;
        yPosition = -58;

        activePose = new Pose2d(new Vector2d(xPosition, yPosition), heading);

        //Pick up #3

        //close claw and lift to ready position
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.1);
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.3);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.3);

        //drive to place position
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .strafeTo(new Vector2d(-3.5, -36))

                        .build()
        );

        yPosition = -34;
        xPosition = -3.5;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //Place #4
        rightFrontDriveMotor.setPower(0.3);
        rightBackDriveMotor.setPower(0.3);
        leftFrontDriveMotor.setPower(0.3);
        leftBackDriveMotor.setPower(0.3);
        //Swing arm forward to place position, wait, open claw
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.65);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.65);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.01);
        sleep(600);
        rightFrontDriveMotor.setPower(0);
        rightBackDriveMotor.setPower(0);
        leftFrontDriveMotor.setPower(0);
        leftBackDriveMotor.setPower(0);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.7);

        //swing arm back to pick up position
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.02);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.02);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.63);

        //Park
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .strafeTo(new Vector2d(40, -62))

                        .build()
        );
        telemetry.addData("voltage", "%.1f volts", new Func<Double>() { @Override public Double value() { return getBatteryVoltage(); } });

    }


}
