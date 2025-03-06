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
@Autonomous(name = "FiveSpecTest", group = "RRAutos")
public class FiveSpecAutoTest extends RobotLinearOpMode{
    //    private DcMotor leftFrontDriveMotor = null;
//    private DcMotor leftBackDriveMotor = null;
//    private DcMotor rightFrontDriveMotor = null;
//    private DcMotor rightBackDriveMotor = null;
//    DcMotor slideUpTop;
//    DcMotor slideUpBottom;
//    DcMotor hSlide;
//    DcMotor intakeMotor;
//    private Servo clawServo;
//    private Servo wristServo;
//    private Servo armServoLeft;
//    private Servo armServoRight;
//    private Servo intakeServo;
    private double xPosition = 9;
    private double yPosition = -65;
    private double heading = Math.toRadians(90);
    private Pose2d activePose;
    double getBatteryVoltage() { double result = Double.POSITIVE_INFINITY; for (VoltageSensor sensor : hardwareMap.voltageSensor) { double voltage = sensor.getVoltage(); if (voltage > 0) { result = Math.min(result, voltage); } } return result; }

    @Override
    public void runOpMode(){
//        rightFrontDriveMotor = hardwareMap.get(DcMotor.class, "rightFrontDriveMotor");
//        leftBackDriveMotor = hardwareMap.get(DcMotor.class, "leftFrontDriveMotor");
//        rightBackDriveMotor = hardwareMap.get(DcMotor.class, "rightBackDriveMotor");
//        leftFrontDriveMotor = hardwareMap.get(DcMotor.class, "leftBackDriveMotor");
//        slideUpTop = hardwareMap.get(DcMotor.class, "slideUpTop");
//        slideUpBottom = hardwareMap.get(DcMotor.class, "slideUpBottom");
//        hSlide = hardwareMap.get(DcMotor.class, "hSlide");
//        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
//
//        clawServo = hardwareMap.get(Servo.class, "clawServo");
//        wristServo = hardwareMap.get(Servo.class, "wristServo");
//        armServoLeft = hardwareMap.get(Servo.class, "armServoLeft");
//        armServoRight = hardwareMap.get(Servo.class, "armServoRight");
//        intakeServo = hardwareMap.get(Servo.class, "intakeServo");
//        clawServo.setDirection(Servo.Direction.REVERSE);

//        rightFrontDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
//        leftFrontDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
//        rightBackDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
//        leftBackDriveMotor.setDirection(DcMotorEx.Direction.REVERSE);

//        leftBackDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftFrontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightBackDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightFrontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        slideUpTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        slideUpBottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        hSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        declareHardwareProperties();

        activePose = new Pose2d(xPosition, yPosition, heading);
        PinpointDrive drive = new PinpointDrive(hardwareMap, activePose);
        drive.updatePoseEstimate();

        waitForStart();

        initialMovements();
        activePose = new Pose2d(xPosition, yPosition, heading);

        //drive to place position
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .lineToY(-40)

                        .build()
        );

        yPosition = -38;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //Place #1
        placeSpecimen();

        //drive to intake position #1
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .strafeToLinearHeading(new Vector2d(10, -41), Math.toRadians(20))
                        .setTangent(Math.toRadians(20))
                        .lineToX(28)

                        .build()
        );

        xPosition = 26.914;
        yPosition = -34.844;
        heading = Math.toRadians(Math.toRadians(20));

        intakeServo.setPosition(0.4);
        intakeMotor.setPower(-0.9);
        hSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        hSlide.setPower(0.8);
        sleep(800);

        hSlide.setPower(0);
        sleep(50000);

        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .setTangent(Math.toRadians(90))
                        .lineToY(22)

                        .build()
        );

        xPosition = 21.276;
        yPosition = -36.896;
        heading = Math.toRadians(20);
        activePose = new Pose2d(xPosition, yPosition, heading);

        turnToDepositSample(drive, activePose);

        sleep(50000);

        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .setTangent(Math.toRadians(20))
                        .lineToX(28)

                        .build()
        );

        turnToDepositSample(drive, activePose);

        hSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        hSlide.setPower(0);
        intakeServo.setPosition(0.35);
        intakeMotor.setPower(-0.9);

        //intake sample #1
        intakeSample();

        //deposit sample #1
        turnToDepositSample(drive, activePose);

        yPosition = -50;
        heading = Math.toRadians(-55);
        activePose = new Pose2d(xPosition, yPosition, heading);

        //drive to intake position #2
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .setTangent(Math.toRadians(90))
                        .strafeToLinearHeading(new Vector2d(54, -43), Math.toRadians(60))

                        .build()
        );

        xPosition = 54;
        yPosition = -43;
        heading = Math.toRadians(60);
        activePose = new Pose2d(xPosition, yPosition, heading);

        //intake sample #2
        intakeSample();

        //deposit sample #2
        turnToDepositSample(drive, activePose);

        yPosition = -50;
        heading = Math.toRadians(-50);
        activePose = new Pose2d(xPosition, yPosition, heading);

        //drive to intake position #3
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-37)
                        .splineTo(new Vector2d(30, -36), Math.toRadians(30))
                        .strafeToLinearHeading(new Vector2d(61, -43), Math.toRadians(50))

                        .build()
        );

        xPosition = 60;
        yPosition = -43;
        heading = Math.toRadians(50);
        activePose = new Pose2d(xPosition, yPosition, heading);

        //intake sample #3
        intakeSample();

        //deposit sample #3
        turnToDepositSample(drive, activePose);

        yPosition = -50;
        heading = Math.toRadians(-50);
        activePose = new Pose2d(xPosition, yPosition, heading);

        //drive to wall and pick up #1
        pickUp1(drive, activePose);

        xPosition = 38;
        yPosition = -57.5;
        heading = Math.toRadians(90);
        activePose = new Pose2d(xPosition, yPosition, heading);
        hSlide.setPower(0.1);
        intakeMotor.setPower(0);


        //place #2
        strafeToPlacePositionAndPlace(2, drive, activePose);
        xPosition = 2.5;
        yPosition = -34;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //pickup #2
        strafeToPickupPosition(drive, activePose);
        xPosition = 38;
        yPosition = -57.5;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //place #3
        strafeToPlacePositionAndPlace(3, drive, activePose);
        xPosition = 0.5;
        yPosition = -34;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //pickup #3
        strafeToPickupPosition(drive, activePose);
        xPosition = 38;
        yPosition = -57.5;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //place #4
        strafeToPlacePositionAndPlace(4, drive, activePose);
        xPosition = -1;
        yPosition = -34;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //pickup #4
        strafeToPickupPosition(drive, activePose);
        xPosition = 38;
        yPosition = -57.5;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //place #5
        strafeToPlacePositionAndPlace(5, drive, activePose);
        xPosition = -2.5;
        yPosition = -34;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //Park
        hSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        hSlide.setPower(0.9);
        sleep(100);
        intakeServo.setPosition(0.7);
        sleep(100);

        telemetry.addData("voltage", "%.1f volts", new Func<Double>() { @Override public Double value() { return getBatteryVoltage(); } });
        telemetry.update();
    }

    public void initialMovements(){
        hSlide.setPower(0.1);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.01);
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.5);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.5);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.67);
    }

    public Pose2d setActivePoseX(double xPos){
        xPosition = xPos;
        return new Pose2d(xPosition, yPosition, heading);
    }

    public Pose2d setActivePoseY(double yPos){
        yPosition = yPos;
        return new Pose2d(xPosition, yPosition, heading);
    }

    public Pose2d setActivePoseXY(double xPos, double yPos){
        xPosition = xPos;
        yPosition = yPos;
        return new Pose2d(xPosition, yPosition, heading);
    }

    public Pose2d setActivePoseTotal(double xPos, double yPos, double head){
        xPosition = xPos;
        yPosition = yPos;
        heading = Math.toRadians(head);
        return new Pose2d(xPosition, yPosition, heading);
    }

    public void intakeSample(){
        intakeServo.setPosition(0.4);
        intakeMotor.setPower(-0.9);
        hSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        hSlide.setPower(0.8);
        sleep(600);
        hSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        hSlide.setPower(0.8);
        intakeServo.setPosition(0.7);
    }

    public void turnToDepositSample(PinpointDrive dr, Pose2d actPose){
        hSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        hSlide.setPower(0);

        Actions.runBlocking(
                dr.actionBuilder(actPose)
                        .setTangent(Math.toRadians(90))
                        .lineToYLinearHeading(-50, Math.toRadians(-55))

                        .build()
        );

        intakeMotor.setPower(0.7);
        sleep(500);
    }

    public void pickUp1(PinpointDrive dr, Pose2d actPose){
        Actions.runBlocking(
                dr.actionBuilder(actPose)
                        .strafeToLinearHeading(new Vector2d(38, -54), Math.toRadians(90))
                        .setTangent(Math.toRadians(90))
                        .lineToY(-57.5)

                        .build()
        );

        pickUp();
    }

    public void pickUp(){
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.01);
        sleep(100);
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.5);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.5);
        sleep(100);
        wristServo.setPosition(0.67);
    }

    public void placeSpecimen(){
        rightFrontDriveMotor.setPower(0.3);
        rightBackDriveMotor.setPower(0.3);
        leftFrontDriveMotor.setPower(0.3);
        leftBackDriveMotor.setPower(0.3);
        sleep(200);
        //Swing arm forward to place position, wait, open claw
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.3);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.3);
        sleep(200);
        rightFrontDriveMotor.setPower(0);
        rightBackDriveMotor.setPower(0);
        leftFrontDriveMotor.setPower(0);
        leftBackDriveMotor.setPower(0);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.7);
        sleep(100);

        //swing arm back to pick up position
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.85);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.85);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.01);
    }

    public void strafeToPickupPosition(PinpointDrive dr, Pose2d actPose){
        Actions.runBlocking(
                dr.actionBuilder(actPose)
                        .strafeTo(new Vector2d(38, -57.5))

                        .build()
        );

        pickUp();
    }

    public void strafeToPlacePositionAndPlace(int placeNum, PinpointDrive dr, Pose2d actPose){
        double pXPos;

        if (placeNum == 2){
            pXPos = 2.5;
        }
        else if (placeNum == 3){
            pXPos = 0.5;
        }
        else if (placeNum == 4){
            pXPos = -1;
        }
        else if (placeNum == 5){
            pXPos = -2.5;
        }
        else{
            pXPos = -4;
        }

        Actions.runBlocking(
                dr.actionBuilder(actPose)
                        .strafeTo(new Vector2d(pXPos, -34))

                        .build()
        );

        placeSpecimen();
    }

}
