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
@Autonomous(name = "SpecAuto", group = "RRAutos")
public class SpecimenAuto extends RobotLinearOpMode{
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
    private double xPosition = 9;
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
        clawServo.setPosition(0.01);
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.5);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.5);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.67);

        //drive to first place position
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .lineToY(-38)
                        .build()
        );

        yPosition = -37;
        activePose = new Pose2d(xPosition, yPosition, heading);

        //Place #1

        placeSpecimen1();

        //Push 2 reds into Observation zone and drive to pick up position
        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .lineToY(-39)
                        .setTangent(Math.toRadians(0))
                        .lineToX(35)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-17)
                        .setTangent(Math.toRadians(0))
                        .lineToX(45)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-45)
                        .lineToY(-17)
                        .setTangent(Math.toRadians(0))
                        .lineToX(54)
                        .setTangent(Math.toRadians(90))
                        .lineToY(-51)
                        .lineToYLinearHeading(-44, Math.toRadians(-90))
                        .strafeTo(new Vector2d(40, -61))

//                        .strafeTo(new Vector2d(38, -52))
//                        .setTangent(Math.toRadians(90))
//                        .lineToY(-57)

                        .build()
        );

        xPosition = 40;
        yPosition = -61;

        pickUp();

        activePose = new Pose2d(xPosition, yPosition, heading);

        //place 2
        strafeToPlacePositionAndPlace(2, drive, activePose);

        xPosition = 7;
        yPosition = -37;
        heading = Math.toRadians(-90);
        activePose = new Pose2d(xPosition, yPosition, heading);

        strafeToPickupPosition(drive, activePose);

        xPosition = 39;
        yPosition = -61;
        heading = Math.toRadians(-90);
        activePose = new Pose2d(xPosition, yPosition, heading);

        strafeToPlacePositionAndPlace(3, drive, activePose);

        xPosition = 5;
        yPosition = -37;
        heading = Math.toRadians(-90);
        activePose = new Pose2d(xPosition, yPosition, heading);

        strafeToPickupPosition(drive, activePose);

        xPosition = 39;
        yPosition = -61;
        heading = Math.toRadians(-90);
        activePose = new Pose2d(xPosition, yPosition, heading);

        strafeToPlacePositionAndPlace(4, drive, activePose);

        xPosition = 3;
        yPosition = -37;
        heading = Math.toRadians(-90);
        activePose = new Pose2d(xPosition, yPosition, heading);

        //Park

        hSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        hSlide.setPower(0.9);

        Actions.runBlocking(
                drive.actionBuilder(activePose)
                        .strafeToLinearHeading(new Vector2d(20, -47), Math.toRadians(-45))

                        .build()
        );

        sleep(50000);


//        Actions.runBlocking(
//                drive.actionBuilder(activePose)
//                        .setTangent(Math.toRadians(90))
//                        .lineToY(-56)
//                        .build()
//        );
//
//        xPosition = 55.5;
//        yPosition = -56;
//
//        activePose = new Pose2d(xPosition, yPosition, heading);
//
//        //Pick up #1
//        rightFrontDriveMotor.setPower(-0.3);
//        rightBackDriveMotor.setPower(-0.3);
//        leftFrontDriveMotor.setPower(-0.3);
//        leftBackDriveMotor.setPower(-0.3);
//        sleep(200);
//        //close claw and lift to ready position
//        clawServo.setDirection(Servo.Direction.FORWARD);
//        clawServo.setPosition(0.01);
//        rightFrontDriveMotor.setPower(0);
//        rightBackDriveMotor.setPower(0);
//        leftFrontDriveMotor.setPower(0);
//        leftBackDriveMotor.setPower(0);
//        armServoRight.setDirection(Servo.Direction.REVERSE);
//        armServoRight.setPosition(0.5);
//        armServoLeft.setDirection(Servo.Direction.FORWARD);
//        armServoLeft.setPosition(0.5);
//        sleep(100);
//        wristServo.setPosition(0.67);
//
//        //drive to place position
//
//        Actions.runBlocking(
//                drive.actionBuilder(activePose)
//                        .setTangent(Math.toRadians(90))
//                        .strafeTo(new Vector2d(0, -37))
//
//                        .build()
//        );
//
//        yPosition = -34;
//        xPosition = 0;
//        activePose = new Pose2d(xPosition, yPosition, heading);
//
//        //Place #2
//        rightFrontDriveMotor.setPower(0.3);
//        rightBackDriveMotor.setPower(0.3);
//        leftFrontDriveMotor.setPower(0.3);
//        leftBackDriveMotor.setPower(0.3);
//        sleep(300);
//        //Swing arm forward to place position, wait, open claw
//        armServoRight.setDirection(Servo.Direction.REVERSE);
//        armServoRight.setPosition(0.3);
//        armServoLeft.setDirection(Servo.Direction.FORWARD);
//        armServoLeft.setPosition(0.3);
////        wristServo.setDirection(Servo.Direction.FORWARD);
////        wristServo.setPosition(0.01);
//        sleep(200);
//        rightFrontDriveMotor.setPower(0);
//        rightBackDriveMotor.setPower(0);
//        leftFrontDriveMotor.setPower(0);
//        leftBackDriveMotor.setPower(0);
//        clawServo.setDirection(Servo.Direction.FORWARD);
//        clawServo.setPosition(0.7);
//        sleep(100);
//
//        //swing arm back to pick up position
//        armServoRight.setDirection(Servo.Direction.REVERSE);
//        armServoRight.setPosition(0.85);
//        armServoLeft.setDirection(Servo.Direction.FORWARD);
//        armServoLeft.setPosition(0.85);
//        wristServo.setDirection(Servo.Direction.FORWARD);
//        wristServo.setPosition(0.01);
//
//        //Drive to pickup position
//        Actions.runBlocking(
//                drive.actionBuilder(activePose)
//                        .setTangent(Math.toRadians(90))
//                        .strafeTo(new Vector2d(38, -54))
//                        .setTangent(Math.toRadians(90))
//                        .lineToY(-57.5)
//
//                        .build()
//        );
//
//        xPosition = 38;
//        yPosition = -57.5;
//
//        activePose = new Pose2d(new Vector2d(xPosition, yPosition), heading);
//
//        //Pick up #2
//
//        //close claw and lift to ready position
//        clawServo.setDirection(Servo.Direction.FORWARD);
//        clawServo.setPosition(0.01);
//        armServoRight.setDirection(Servo.Direction.REVERSE);
//        armServoRight.setPosition(0.5);
//        armServoLeft.setDirection(Servo.Direction.FORWARD);
//        armServoLeft.setPosition(0.5);
//        sleep(100);
//        wristServo.setPosition(0.67);
//
//        //drive to place position
//        Actions.runBlocking(
//                drive.actionBuilder(activePose)
//                        .setTangent(Math.toRadians(90))
//                        .strafeTo(new Vector2d(-2.5, -37))
//
//                        .build()
//        );
//
//        yPosition = -34;
//        xPosition = -2.5;
//        activePose = new Pose2d(xPosition, yPosition, heading);
//
//        //Place #3
//        rightFrontDriveMotor.setPower(0.3);
//        rightBackDriveMotor.setPower(0.3);
//        leftFrontDriveMotor.setPower(0.3);
//        leftBackDriveMotor.setPower(0.3);
//        sleep(300);
//        //Swing arm forward to place position, wait, open claw
//        armServoRight.setDirection(Servo.Direction.REVERSE);
//        armServoRight.setPosition(0.3);
//        armServoLeft.setDirection(Servo.Direction.FORWARD);
//        armServoLeft.setPosition(0.3);
////        wristServo.setDirection(Servo.Direction.FORWARD);
////        wristServo.setPosition(0.01);
//        sleep(200);
//        rightFrontDriveMotor.setPower(0);
//        rightBackDriveMotor.setPower(0);
//        leftFrontDriveMotor.setPower(0);
//        leftBackDriveMotor.setPower(0);
//        clawServo.setDirection(Servo.Direction.FORWARD);
//        clawServo.setPosition(0.7);
//        sleep(100);
//
//        //swing arm back to pick up position
//        armServoRight.setDirection(Servo.Direction.REVERSE);
//        armServoRight.setPosition(0.85);
//        armServoLeft.setDirection(Servo.Direction.FORWARD);
//        armServoLeft.setPosition(0.85);
//        wristServo.setDirection(Servo.Direction.FORWARD);
//        wristServo.setPosition(0.01);
//
//        //drive to pickup position
//        Actions.runBlocking(
//                drive.actionBuilder(activePose)
//                        .setTangent(Math.toRadians(90))
//                        .strafeTo(new Vector2d(38, -54))
//                        .setTangent(Math.toRadians(90))
//                        .lineToY(-58)
//
//                        .build()
//        );
//
//        xPosition = 38;
//        yPosition = -58;
//
//        activePose = new Pose2d(new Vector2d(xPosition, yPosition), heading);
//
//        //Pick up #3
//
//        //close claw and lift to ready position
//        clawServo.setDirection(Servo.Direction.FORWARD);
//        clawServo.setPosition(0.01);
//        armServoRight.setDirection(Servo.Direction.REVERSE);
//        armServoRight.setPosition(0.5);
//        armServoLeft.setDirection(Servo.Direction.FORWARD);
//        armServoLeft.setPosition(0.5);
//        sleep(100);
//        wristServo.setPosition(0.67);
//
//        //drive to place position
//        Actions.runBlocking(
//                drive.actionBuilder(activePose)
//                        .strafeTo(new Vector2d(-4, -34))
//
//                        .build()
//        );
//
//        yPosition = -37;
//        xPosition = -4;
//        activePose = new Pose2d(xPosition, yPosition, heading);
//
//        //Place #4
//        rightFrontDriveMotor.setPower(0.3);
//        rightBackDriveMotor.setPower(0.3);
//        leftFrontDriveMotor.setPower(0.3);
//        leftBackDriveMotor.setPower(0.3);
//        sleep(300);
//        //Swing arm forward to place position, wait, open claw
//        armServoRight.setDirection(Servo.Direction.REVERSE);
//        armServoRight.setPosition(0.3);
//        armServoLeft.setDirection(Servo.Direction.FORWARD);
//        armServoLeft.setPosition(0.3);
////        wristServo.setDirection(Servo.Direction.FORWARD);
////        wristServo.setPosition(0.01);
//        sleep(200);
//        rightFrontDriveMotor.setPower(0);
//        rightBackDriveMotor.setPower(0);
//        leftFrontDriveMotor.setPower(0);
//        leftBackDriveMotor.setPower(0);
//        clawServo.setDirection(Servo.Direction.FORWARD);
//        clawServo.setPosition(0.7);
//        sleep(100);
//
//        //swing arm back to pick up position
//
//        //Park
//        hSlide.setDirection(DcMotorSimple.Direction.REVERSE);
//        hSlide.setPower(0.9);
//        sleep(100);
//        intakeServo.setPosition(0.7);
//        sleep(100);

        telemetry.addData("voltage", "%.1f volts", new Func<Double>() { @Override public Double value() { return getBatteryVoltage(); } });
        telemetry.update();
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

    public void strafeToPickupPosition(PinpointDrive dr, Pose2d actPose){
        Actions.runBlocking(
                dr.actionBuilder(actPose)
                        .strafeTo(new Vector2d(39, -61))

                        .build()
        );

        pickUp();
    }

    public void strafeToPlacePositionAndPlace(int placeNum, PinpointDrive dr, Pose2d actPose){
        double pXPos;

        if (placeNum == 2){
            pXPos = 7;
        }
        else if (placeNum == 3){
            pXPos = 5;
        }
        else if (placeNum == 4){
            pXPos = 3;
        }
        else if (placeNum == 5){
            pXPos = 1;
        }
        else{
            pXPos = -1;
        }

        Actions.runBlocking(
                dr.actionBuilder(actPose)
                        .strafeToLinearHeading(new Vector2d(pXPos, -37), Math.toRadians(-90))

                        .build()
        );

        placeSpecimen();
    }

    public void placeSpecimen(){
        //Swing arm forward to place position, wait, open claw
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.8);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.8);
        sleep(500);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.7);
        sleep(100);

        //swing arm back to pick up position
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.21);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.21);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.01);
    }

    public void placeSpecimen1(){

        rightFrontDriveMotor.setPower(0.3);
        rightBackDriveMotor.setPower(0.3);
        leftFrontDriveMotor.setPower(0.3);
        leftBackDriveMotor.setPower(0.3);
        sleep(200);
        rightFrontDriveMotor.setPower(0);
        rightBackDriveMotor.setPower(0);
        leftFrontDriveMotor.setPower(0);
        leftBackDriveMotor.setPower(0);
        //Swing arm forward to place position, wait, open claw
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.25);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.25);
        sleep(300);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.7);
        sleep(100);

        //swing arm back to pick up position
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.21);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.21);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.01);
    }

}
