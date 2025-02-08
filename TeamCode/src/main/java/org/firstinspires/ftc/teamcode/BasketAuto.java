package org.firstinspires.ftc.teamcode;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Func;

@Config
@Autonomous(name = "BasketAuto", group = "RRAutos")
public class BasketAuto extends RobotLinearOpMode{
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
    private double xPosition = -32;
    private double yPosition = -65;
    private double heading = Math.toRadians(0);
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
                        .lineToX(-54)
                        .setTangent(Math.toRadians(90))
                        .strafeTo(new Vector2d(-36, -36))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-12)
                        .setTangent(Math.toRadians(0))
                        .lineToXLinearHeading(-42, Math.toRadians(-15))
                        .setTangent(Math.toRadians(90))
                        .strafeTo(new Vector2d(-52, -56))
                        .strafeToLinearHeading(new Vector2d(-42, -12), Math.toRadians(0))
                        .setTangent(Math.toRadians(0))
                        .lineToX(-54)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-56)
                        .lineToY(-12)
                        .setTangent(Math.toRadians(0))
                        .lineToX(-64)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-54)
                        .strafeTo(new Vector2d(-36, -12))
                        .setTangent(Math.toRadians(0))
                        .lineToX(-26)

                        .build()
        );

        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.5);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.5);
        hSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        hSlide.setPower(0.9);
        sleep(100);
        intakeServo.setPosition(0.7);
        sleep(100);
        hSlide.setPower(0);

        telemetry.addData("voltage", "%.1f volts", new Func<Double>() { @Override public Double value() { return getBatteryVoltage(); } });
        telemetry.update();
    }


}
