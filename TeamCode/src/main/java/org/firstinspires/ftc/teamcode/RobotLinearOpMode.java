package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.text.method.MovementMethod;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import com.qualcomm.robotcore.util.ElapsedTime;

import android.app.Activity;
import android.graphics.Color;
import android.text.method.MovementMethod;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;



/*
This class is where we write all our methods needed across all programs. As you'll notice most of our classes extend
RobotLinearOpMode (this class) and not LinearOpMode, that is intentional
*/

/**
 * This file is where each method should be written or referenced. Then when writing Autos and TeleOps,
 * programmers need to make files that extends RobotLinearOpMode instead of extending LinearOpMode.
 */

@Autonomous(name="RobotLinearOpMode", group="Linear Opmode")
@Disabled
public abstract class RobotLinearOpMode extends LinearOpMode {

    // Construction //
    public RobotLinearOpMode() {

    }

    //Declaration of drive motors
    DcMotor rightFrontDriveMotor;
    DcMotor leftFrontDriveMotor;
    DcMotor rightBackDriveMotor;
    DcMotor leftBackDriveMotor;

    //Declaration of other motors
    DcMotor slideUpTop;
    DcMotor slideUpBottom;
    DcMotor hSlide;
    DcMotor intakeMotor;

    //Declaration of servos
    Servo clawServo;
    Servo wristServo;
    Servo armServoLeft;
    Servo armServoRight;
    Servo intakeServo;

    //Declaration of any other sensors
    NormalizedColorSensor colorSensor;
    AprilTagProcessor aprilTag;
    VisionPortal visionPortal;

    //It's good to declare any sensors you might need as you aren't required to instantiate them later


    //Any other variables needed
    boolean USE_WEBCAM = false;  // true for webcam, false for phone camera
    boolean aWasPressed = false;
    boolean bWasPressed = false;
    boolean xWasPressed = false;
    boolean yWasPressed = false;
    long waitTime = 0;
    boolean placeAndPark;
    private ElapsedTime runtime = new ElapsedTime();

    //This method can be called in every class you use to instantiate motors, servos, and sensors
    public void declareHardwareProperties() {

        //Motor instantiation: "hardwareMap.get(DcMotor.class" is used to say its a motor and "rightFrontDriveMotor"
        //is the device name (must match your robot configuration)
        rightFrontDriveMotor = hardwareMap.get(DcMotor.class, "rightFrontDriveMotor");
        leftBackDriveMotor = hardwareMap.get(DcMotor.class, "leftFrontDriveMotor");
        rightBackDriveMotor = hardwareMap.get(DcMotor.class, "rightBackDriveMotor");
        leftFrontDriveMotor = hardwareMap.get(DcMotor.class, "leftBackDriveMotor");
        slideUpTop = hardwareMap.get(DcMotor.class, "slideUpTop");
        slideUpBottom = hardwareMap.get(DcMotor.class, "slideUpBottom");
        hSlide = hardwareMap.get(DcMotor.class, "hSlide");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        //Servo instantiation: Same as motor but with "Servo.class"
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        wristServo = hardwareMap.get(Servo.class, "wristServo");
        armServoLeft = hardwareMap.get(Servo.class, "armServoLeft");
        armServoRight = hardwareMap.get(Servo.class, "armServoRight");
        intakeServo = hardwareMap.get(Servo.class, "intakeServo");

        //Servos technically have directions, but all this line does is switch which end of direction range is 0 and which
        //is 1.0
        clawServo.setDirection(Servo.Direction.REVERSE);

        //Set the directions the motors were spin (test with basic linear op mode)
        rightFrontDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
        leftFrontDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rightBackDriveMotor.setDirection(DcMotorEx.Direction.FORWARD);
        leftBackDriveMotor.setDirection(DcMotorEx.Direction.REVERSE);
        slideUpTop.setDirection(DcMotorEx.Direction.REVERSE);

        //The .BRAKE function makes it so when you set the power to 0, the motor tries to hold itself in place (less drift)
        leftBackDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideUpTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideUpBottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //This method can be used to declare variables before hitting the start button on your driver station
    //for example: you instantiate and then set a delay before your robot starts (used to avoid collisions) or have
    // 1 auto class and use buttons to set which branch of code to run. Call this code before the wait for start method
    // call and after your motor instantiation
    public void declareAutoVariables(){
        if(gamepad1.a&&!aWasPressed) {
            waitTime+=500;
            aWasPressed=true;
        } else if(!gamepad1.a&&aWasPressed) {
            aWasPressed=false;
        }

        if(gamepad1.b&&!bWasPressed&&waitTime>=500) {
            waitTime-=500;
            bWasPressed=true;
        } else if(!gamepad1.b&&bWasPressed) {
            bWasPressed=false;
        }

        if(gamepad1.x&&!xWasPressed) {
            placeAndPark = true;
            xWasPressed=true;
        } else if(!gamepad1.x&&xWasPressed) {
            xWasPressed = false;
        }

        if(gamepad1.y&&!yWasPressed) {
            placeAndPark = false;
            yWasPressed=true;
        } else if(!gamepad1.y&&yWasPressed) {
            yWasPressed=false;
        }

        telemetry.addData("Wait Duration (A to increase, B to decrease)",waitTime);
        telemetry.addData("place and park", placeAndPark);
        telemetry.update();
    }

    //Method to drive the robot autonomously using encoders, (see main lesson doc for examples)
    public void encoderDrive(double power, double inches, MOVEMENT_DIRECTION movement_direction) {

        //Specifications of hardware
        final double WHEEL_DIAMETER_INCHES = 3.77953;
        final double WHEEL_CIRCUMFERENCE_INCHES = (WHEEL_DIAMETER_INCHES * 3.141592653589793);
        final double GEAR_RATIO = 19.2;
        final double COUNTS_PER_ROTATION_AT_MOTOR = 537.7;
        final double TICKS_PER_ROTATION = (COUNTS_PER_ROTATION_AT_MOTOR);
        final double TICKS_PER_INCH = (TICKS_PER_ROTATION) / (WHEEL_CIRCUMFERENCE_INCHES);

        //Target # of ticks for each motor
        int leftFrontTarget;
        int rightFrontTarget;
        int leftBackTarget;
        int rightBackTarget;

        //Resets motor encoders to 0 ticks
        leftFrontDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the target # of ticks by intaking the number of desired inches of movement and converting to ticks
        leftFrontTarget = leftFrontDriveMotor.getCurrentPosition() + (int) (inches * TICKS_PER_INCH);
        rightFrontTarget = rightFrontDriveMotor.getCurrentPosition() + (int) (inches * TICKS_PER_INCH);
        leftBackTarget = leftBackDriveMotor.getCurrentPosition() + (int) (inches * TICKS_PER_INCH);
        rightBackTarget = rightBackDriveMotor.getCurrentPosition() + (int) (inches * TICKS_PER_INCH);

        if (movement_direction == MOVEMENT_DIRECTION.FORWARD) {

            //Sets the target # of ticks to the target position of the motors
            leftFrontDriveMotor.setTargetPosition(-leftFrontTarget);
            rightFrontDriveMotor.setTargetPosition(rightFrontTarget - (int)(rightFrontTarget * 0.0016605117));
            leftBackDriveMotor.setTargetPosition(leftBackTarget);
            rightBackDriveMotor.setTargetPosition(rightBackTarget - (int)(rightBackTarget * 0.0016605117));

            //Tells the motors to drive until they reach the target position
            leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Sets the motor powers to the power entered on use
            leftFrontDriveMotor.setPower(power);
            rightFrontDriveMotor.setPower(power);
            leftBackDriveMotor.setPower(power);
            rightBackDriveMotor.setPower(power);

            while (leftBackDriveMotor.isBusy() && opModeIsActive()) {

            }

            //Kills the motors to prepare for next call of method
            leftFrontDriveMotor.setPower(0);
            rightFrontDriveMotor.setPower(0);
            leftBackDriveMotor.setPower(0);
            rightBackDriveMotor.setPower(0);


        }

        if (movement_direction == MOVEMENT_DIRECTION.REVERSE) {

            //Sets the target # of ticks to the target position of the motors
            leftFrontDriveMotor.setTargetPosition(leftFrontTarget );
            rightFrontDriveMotor.setTargetPosition(-rightFrontTarget + (int)(rightFrontTarget * 0.0016605117));
            leftBackDriveMotor.setTargetPosition(-leftBackTarget);
            rightBackDriveMotor.setTargetPosition(-rightBackTarget + (int)(rightBackTarget * 0.0016605117));

            //Tells the motors to drive until they reach the target position
            leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Sets the motor powers to the power entered on use
            leftFrontDriveMotor.setPower(power);
            rightFrontDriveMotor.setPower(power);
            leftBackDriveMotor.setPower(power);
            rightBackDriveMotor.setPower(power);

            while (leftBackDriveMotor.isBusy() && opModeIsActive()) {

            }
            //Kills the motors to prepare for next call of method
            leftFrontDriveMotor.setPower(0);
            rightFrontDriveMotor.setPower(0);
            leftBackDriveMotor.setPower(0);
            rightBackDriveMotor.setPower(0);
        }

        if (movement_direction == MOVEMENT_DIRECTION.STRAFE_RIGHT) {

            //Sets the target # of ticks to the target position of the motors
            leftFrontDriveMotor.setTargetPosition(-leftFrontTarget * 2 );
            rightFrontDriveMotor.setTargetPosition(2*(-rightFrontTarget + (int)(rightFrontTarget * 0.0016605117)));
            leftBackDriveMotor.setTargetPosition(-leftBackTarget * 2 );
            rightBackDriveMotor.setTargetPosition((rightBackTarget - (int)(rightBackTarget * 0.0016605117))*2);


            //Tells the motors to drive until they reach the target position
            leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Sets the motor powers to the power entered on use
            leftFrontDriveMotor.setPower(power);
            rightFrontDriveMotor.setPower(power);
            leftBackDriveMotor.setPower(power);
            rightBackDriveMotor.setPower(power);

            while (leftBackDriveMotor.isBusy() && opModeIsActive()) {

            }

            //Kills the motors to prepare for next call of method
            leftFrontDriveMotor.setPower(0);
            rightFrontDriveMotor.setPower(0);
            leftBackDriveMotor.setPower(0);
            rightBackDriveMotor.setPower(0);
        }

        if (movement_direction == MOVEMENT_DIRECTION.STRAFE_LEFT) {

            //Sets the target # of ticks to the target position of the motors
            leftFrontDriveMotor.setTargetPosition(leftFrontTarget * 2);
            rightFrontDriveMotor.setTargetPosition((rightFrontTarget - (int)(rightFrontTarget * 0.0016605117))*2);
            leftBackDriveMotor.setTargetPosition(leftBackTarget * 2);
            rightBackDriveMotor.setTargetPosition(-(rightBackTarget + (int)(rightBackTarget * 0.0016605117))* 2);

            //Tells the motors to drive until they reach the target position
            leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Sets the motor powers to the power entered on use
            leftFrontDriveMotor.setPower(power);
            rightFrontDriveMotor.setPower(power);
            leftBackDriveMotor.setPower(power);
            rightBackDriveMotor.setPower(power);

            while (leftBackDriveMotor.isBusy() && opModeIsActive()) {

            }

            //Kills the motors to prepare for next call of method
            leftFrontDriveMotor.setPower(0);
            rightFrontDriveMotor.setPower(0);
            leftBackDriveMotor.setPower(0);
            rightBackDriveMotor.setPower(0);
        }

        leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Kills the motors to prepare for next call of method
        leftFrontDriveMotor.setPower(0);
        rightFrontDriveMotor.setPower(0);
        leftBackDriveMotor.setPower(0);
        rightBackDriveMotor.setPower(0);
    }

    //This code pairs with encoder drive to turn the robot in place a certain amount of degrees
    public void encoderTurn(double power, double degrees, TURN_DIRECTION turn_direction) {

        //Declaration of important variables

        final double WHEEL_DIAMETER_INCHES = 3.77953;
        final double WHEEL_CIRCUMFERENCE_INCHES = (WHEEL_DIAMETER_INCHES * 3.141592653589793);
        final double TICKS_PER_ROTATION = (537.7);
        final double ROBOT_LENGTH = 16;
        final double ROBOT_WIDTH = 16;
        final double TICKS_PER_INCH = (TICKS_PER_ROTATION/WHEEL_CIRCUMFERENCE_INCHES);

        final double TURNING_DISTANCE = ((degrees/360) * 3.141592653589793 * Math.sqrt((Math.pow(ROBOT_LENGTH, 2)) + Math.pow(ROBOT_WIDTH, 2)));
        final double TICK_TARGET = TURNING_DISTANCE * TICKS_PER_INCH;


        declareHardwareProperties();

        //Target # of ticks for each motor
        int leftFrontTarget;
        int rightFrontTarget;
        int leftBackTarget;
        int rightBackTarget;

        leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Resets motor encoders to 0 ticks
        leftFrontDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the taret # of ticks by intaking the number of desired inches of movement and converting to ticks
        leftFrontTarget = leftFrontDriveMotor.getCurrentPosition() + (int) (TICK_TARGET);
        rightFrontTarget = rightFrontDriveMotor.getCurrentPosition() + (int) (TICK_TARGET);
        leftBackTarget = leftBackDriveMotor.getCurrentPosition() + (int) (TICK_TARGET);
        rightBackTarget = rightBackDriveMotor.getCurrentPosition() + (int) (TICK_TARGET);

        if(turn_direction == TURN_DIRECTION.TURN_RIGHT) {

            //Sets the target # of ticks to the target position of the motors
            leftFrontDriveMotor.setTargetPosition(-leftFrontTarget);
            rightFrontDriveMotor.setTargetPosition(-rightFrontTarget);
            leftBackDriveMotor.setTargetPosition(leftBackTarget);
            rightBackDriveMotor.setTargetPosition(-rightBackTarget);

            //Tells the motors to drive until they reach the target position
            leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Sets the motor powers to the power entered on use
            leftFrontDriveMotor.setPower(power);
            rightFrontDriveMotor.setPower(power);
            leftBackDriveMotor.setPower(power);
            rightBackDriveMotor.setPower(power);

            while (rightFrontDriveMotor.isBusy() && opModeIsActive()) {

            }

            leftFrontDriveMotor.setPower(0);
            rightFrontDriveMotor.setPower(0);
            leftBackDriveMotor.setPower(0);
            rightBackDriveMotor.setPower(0);
        }

        if(turn_direction == TURN_DIRECTION.TURN_LEFT) {

            //Sets the target # of ticks to the target position of the motors
            leftFrontDriveMotor.setTargetPosition(leftFrontTarget);
            rightFrontDriveMotor.setTargetPosition(rightFrontTarget);
            leftBackDriveMotor.setTargetPosition(-leftBackTarget );
            rightBackDriveMotor.setTargetPosition(rightBackTarget );

            //Tells the motors to drive until they reach the target position
            leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Sets the motor powers to the power entered on use
            leftFrontDriveMotor.setPower(power);
            rightFrontDriveMotor.setPower(power);
            leftBackDriveMotor.setPower(power);
            rightBackDriveMotor.setPower(power);

            while (rightFrontDriveMotor.isBusy() && opModeIsActive()) {

            }

            leftFrontDriveMotor.setPower(0);
            rightFrontDriveMotor.setPower(0);
            leftBackDriveMotor.setPower(0);
            rightBackDriveMotor.setPower(0);

        }

        leftFrontDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

//      PID Code that kind of worked, mess around with it
//    public void EncoderSlide(double TargetPos, int TargetTime, int UpdateSpeed, double Kp, double Kd)
//    {
//        //TargetPos is the goal position in inches
//        //TargetTime is the target amount of milliseconds before the goal is reached
//        //UpdateSpeed is the amount of milleseconds between updates
//
//        //Specifications of hardware
//        final double WHEEL_DIAMETER_INCHES = 1.625;
//        final double WHEEL_CIRCUMFERENCE_INCHES = (WHEEL_DIAMETER_INCHES * 3.141592653589793);
//        final double COUNTS_PER_ROTATION_AT_MOTOR = 537.7;
//        final double TICKS_PER_ROTATION = (COUNTS_PER_ROTATION_AT_MOTOR);
//        final double TICKS_PER_INCH = (TICKS_PER_ROTATION) / (WHEEL_CIRCUMFERENCE_INCHES);
//        final double UncertaintyThreshold = 999999999;
//        //final double Kp = 0.01; //Proportional gain.
//        final double Ki = 0; //Integral gain.
//        //final double Kd = 0.02; //Derivative gain.
//
//        int TargetPosInTicks = (int)(TargetPos * TICKS_PER_INCH);
//        int TimeElapsed = 0;
//        int Current = 0;
//        Current = slideUp.getCurrentPosition();
//
//        float Target = Current;//Current target in ticks
//
//        double LastActive = 0;
//        double Error = 0;
//        double de = 0;
//        double ErrorSum = 0;
//        double Power = 0;
//        double PrevPower = 0;
//
//        telemetry.addData("Power", Power);
//        telemetry.addData("CurrentPos", Current);
//        telemetry.addData("CurrentTarget", Target);
//        telemetry.addData("Target", TargetPosInTicks);
//
//
//        //Sets the target # of ticks to the target position of the motors
//        //slideUp.setTargetPosition(TargetPosInTicks);
//
//        //slideUp.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        slideUp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        slideUp2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        Power = (TargetPosInTicks > Target ? 0.1 : -0.1);
//
//        slideUp.setPower(Power);
//        slideUp2.setPower(Power);
//
//        while (TimeElapsed <= TargetTime && TimeElapsed - LastActive <= 250)
//        {
//            PrevPower = Power;
//            Current = slideUp.getCurrentPosition();
//
//            de = Error;
//
//            Error = TargetPosInTicks - Current;
//            //ErrorSum += 0.5 * (Error + de) * UpdateSpeed;
//            ErrorSum += Error * (TimeElapsed / 1000.0);
//            de = (Error - de) / (TimeElapsed / 1000.0);
//
//            /*Power = -Power;
//
//            Power += Kp * Error;
//
//            Power += Ki * ErrorSum;
//
//            Power += Kd * de;
//
//            double Dif = Power - PrevPower;
//            Dif = Dif < -0.02 ? -0.02 : Dif > 0.02 ? 0.02 : Dif;
//
//            Power = PrevPower + Dif;
//
//            //PrevPower = Power;*/
//            Power = Kp * Error + Ki * ErrorSum * Kd * de;
//
//            Power = (Power < -2) ? -2 : (Power > 2 ? 2 : Power);
//
//            /*if (Math.abs(Error) < UncertaintyThreshold)
//            {*/
//            Target = ((TargetPosInTicks - Current) / Math.abs((float)TargetTime - TimeElapsed));
//            //}
//
//            slideUp.setPower(Power);
//            slideUp2.setPower(Power);
//
//            telemetry.addData("UnboundPower", PrevPower);
//            telemetry.addData("Power", Power);
//            telemetry.addData("CurrentPos", Current);
//            telemetry.addData("CurrentTarget", Target);
//            telemetry.addData("Target", TargetPosInTicks);
//            telemetry.addData("RemainingDist", (TargetPosInTicks - Current));
//            telemetry.addData("RemainingTime", (TargetTime - TimeElapsed));
//            telemetry.addData("TimeElapsed", TimeElapsed);
//            telemetry.update();
//
//            sleep(UpdateSpeed);
//            TimeElapsed += UpdateSpeed;
//
//            if (Math.abs(Power) > 0.1 && Math.abs(Current - TargetPosInTicks) > 10)
//            {
//                LastActive = TimeElapsed;
//            }
//        }
//
//        slideUp.setPower(0);
//        slideUp2.setPower(0);
//    }

   //Encoder movement of 1 motor (the slide motor). This is basically encoder drive but only effecting 1 motor instead of 4
    public void encoderSlideForward(double power, double inches, MOVEMENT_DIRECTION movement_direction) {


        //Specifications of hardware
        final double WHEEL_DIAMETER_INCHES = 1.5291339;
        final double WHEEL_CIRCUMFERENCE_INCHES = (WHEEL_DIAMETER_INCHES * 3.141592653589793);
        final double GEAR_RATIO = 19.2;
        final double COUNTS_PER_ROTATION_AT_MOTOR = 537.7;
        final double TICKS_PER_ROTATION = (COUNTS_PER_ROTATION_AT_MOTOR);
        final double TICKS_PER_INCH = (TICKS_PER_ROTATION) / (WHEEL_CIRCUMFERENCE_INCHES);

        //Target # of ticks for each motor
        int target;

        //Resets motor encoders to 0 ticks
        hSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the target # of ticks by intaking the number of desired inches of movement and converting to ticks
        target = hSlide.getCurrentPosition() + (int) (inches * TICKS_PER_INCH);

        if (movement_direction == MOVEMENT_DIRECTION.FORWARD) {

            //Sets the target # of ticks to the target position of the motors
            hSlide.setTargetPosition(target);

            //Tells the motors to drive until they reach the target position
            hSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Sets the motor powers to the power entered on use
            hSlide.setPower(power);

            while (hSlide.isBusy() && opModeIsActive()) {

            }

            //Kills the motors to prepare for next call of method
            hSlide.setPower(0);
        }

        if (movement_direction == MOVEMENT_DIRECTION.REVERSE) {

            //Sets the target # of ticks to the target position of the motors
            hSlide.setTargetPosition(-target);

            //Tells the motors to drive until they reach the target position
            hSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Sets the motor powers to the power entered on use
            hSlide.setPower(-power);

            while (hSlide.isBusy() && opModeIsActive()) {

            }
            //Kills the motors to prepare for next call of method
            hSlide.setPower(0);
        }

        hSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Kills the motors to prepare for next call of method
        hSlide.setPower(0);
    }

    //Same as above but this was for a slide extending upwards and not forwards, same code tho
    public void encoderSlideUp(double power, double inches, MOVEMENT_DIRECTION movement_direction) {
        //Specifications of hardware
        final double WHEEL_DIAMETER_INCHES = 1.625984;
        final double WHEEL_CIRCUMFERENCE_INCHES = (WHEEL_DIAMETER_INCHES * 3.141592653589793);
        final double GEAR_RATIO = 19.2;
        final double COUNTS_PER_ROTATION_AT_MOTOR = 537.7;
        final double TICKS_PER_ROTATION = (COUNTS_PER_ROTATION_AT_MOTOR);
        final double TICKS_PER_INCH = (TICKS_PER_ROTATION) / (WHEEL_CIRCUMFERENCE_INCHES);

        //Target # of ticks for each motor
        int target;

        //Resets motor encoders to 0 ticks
        slideUpTop.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideUpBottom.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Sets the target # of ticks by intaking the number of desired inches of movement and converting to ticks
        target = slideUpTop.getCurrentPosition() + (int) (inches * TICKS_PER_INCH) * (movement_direction == MOVEMENT_DIRECTION.FORWARD ? 1 : -1);
        power = movement_direction == MOVEMENT_DIRECTION.FORWARD ? power : -power;

        //Sets the target # of ticks to the target position of the motors
        slideUpTop.setTargetPosition(target);
        slideUpBottom.setTargetPosition(target);

        //Tells the motors to drive until they reach the target position
        slideUpTop.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideUpBottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Sets the motor powers to the power entered on use
        slideUpTop.setPower(power);
        slideUpBottom.setPower(power);

        while (slideUpTop.isBusy() && slideUpBottom.isBusy() && opModeIsActive()) {

        }

        //Kills the motors to prepare for next call of method
        slideUpTop.setPower(0);
        slideUpBottom.setPower(0);

        slideUpTop.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideUpBottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //This method runs 1 motor (the motor attached to a slide) for a specific amount of time. This code can be modified to
    //move multiple motors at once based on time

//    public void encoderSlideUpTime(double power, double seconds, MOVEMENT_DIRECTION movement_direction){
//
//        //set runtime to 0
//        runtime.reset();
//
//        //for however long you specify in the parameters:
//        while (runtime.seconds() < seconds){
//            if (movement_direction == MOVEMENT_DIRECTION.FORWARD){
//                //set the motor power
//                slideUp.setPower(power);
//            }
//            else{
//                slideUp.setPower(-power);
//            }
//        }
//
//        //set the power back to 0 after time expires
//        slideUp.setPower(0);
//    }

    //Same as above code for encoder slide up time
    public void encoderSlideForwardTime(double power, double seconds, MOVEMENT_DIRECTION movement_direction){
        runtime.reset();
        while (runtime.seconds() < seconds){
            if (movement_direction == MOVEMENT_DIRECTION.FORWARD){
                hSlide.setPower(power);
            }
            else{
                hSlide.setPower(-power);
            }
        }
        hSlide.setPower(0);
    }

    //sets up color sensor for color sensor drive method below
    public float colorSensor() {
        int colorValue = 0;



        View relativeLayout;


        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);


        // You can give the sensor a gain value, will be multiplied by the sensor's raw value before the
        // normalized color values are calculated. Color sensors (especially the REV Color Sensor V3)
        // can give very low values (depending on the lighting conditions), which only use a small part
        // of the 0-1 range that is available for the red, green, and blue values. In brighter conditions,
        // you should use a smaller gain than in dark conditions. If your gain is too high, all of the
        // colors will report at or near 1, and you won't be able to determine what color you are
        // actually looking at. For this reason, it's better to err on the side of a lower gain
        // (but always greater than  or equal to 1).
        float gain = 10;

        // Once per loop, we will update this hsvValues array. The first element (0) will contain the
        // hue, the second element (1) will contain the saturation, and the third element (2) will
        // contain the value. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
        // for an explanation of HSV color.
        //final float[] hsvValues = new float[3];



        // Get a reference to our sensor object. It's recommended to use NormalizedColorSensor over
        // ColorSensor, because NormalizedColorSensor consistently gives values between 0 and 1, while
        // the values you get from ColorSensor are dependent on the specific sensor you're using.


        // If possible, turn the light on in the beginning (it might already be on anyway,
        // we just make sure it is if we can).
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }



        // Loop until we are asked to stop





        // Show the gain value via telemetry
        //telemetry.addData("Gain", gain);

        // Tell the sensor our desired gain value (normally you would do this during initialization,
        // not during the loop)
        colorSensor.setGain(gain);


        // Get the normalized colors from the sensor
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        /* Use telemetry to display feedback on the driver station. We show the red, green, and blue
         * normalized values from the sensor (in the range of 0 to 1), as well as the equivalent
         * HSV (hue, saturation and value) values. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
         * for an explanation of HSV color. */

        // Update the hsvValues array by passing it to Color.colorToHSV()
//            Color.colorToHSV(colors.toColor(), hsvValues);
        telemetry.addLine()
                .addData("Red", "%.3f", colors.red)
                .addData("Green", "%.3f", colors.green)
                .addData("Blue", "%.3f", colors.blue);
        telemetry.addLine();
//                    .addData("Hue", "%.3f", hsvValues[0])
//                    .addData("Saturation", "%.3f", hsvValues[1])
//                    .addData("Value", "%.3f", hsvValues[2]);
//            telemetry.addData("Alpha", "%.3f", colors.alpha);

        /* If this color sensor also has a distance sensor, display the measured distance.
         * Note that the reported distance is only useful at very close range, and is impacted by
         * ambient light and surface reflectivity. */
//            if (colorSensor instanceof DistanceSensor) {
//                telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
//            }

        if (colors.blue >= .080) {
            colorValue = 1;
        } else if (colors.red > .070) {
            colorValue = 2;
        }

        return(colorValue);
    }


    //This code drives the robot forward at a constant speed until a color sensor on the robot detects a certain color
    //typically red or blue or white (colors of tape on field) however this code is for red or blue tape. Comments end
    //after first set of loops because its all the same for the most part (forward vs backwards, red vs blue etc
    public void colorSensorDrive(double power, MOVEMENT_DIRECTION movement_direction, TAPE_COLOR tape_color) {
        //If searching for red
        if (tape_color == TAPE_COLOR.RED_TAPE) {
            //move forward
            if (movement_direction == MOVEMENT_DIRECTION.FORWARD) {
                while (opModeIsActive() && colorSensor() != 2) {
                    leftFrontDriveMotor.setPower(power);
                    leftBackDriveMotor.setPower(power);
                    rightFrontDriveMotor.setPower(power);
                    rightBackDriveMotor.setPower(power);

                    //move forward until color is detected and stop movement
                    if (colorSensor() == 2) {
                        motorKill();
                        //This is a trick used to stop the robot in place with no drift, force the motors to spin backwards slightly
                        encoderDrive(power, .5, MOVEMENT_DIRECTION.REVERSE);
                    }

                }
            } else if (movement_direction == MOVEMENT_DIRECTION.REVERSE) {
                while (opModeIsActive() && colorSensor() != 2) {
                    leftFrontDriveMotor.setPower(-power);
                    leftBackDriveMotor.setPower(-power);
                    rightFrontDriveMotor.setPower(-power);
                    rightBackDriveMotor.setPower(-power);

                    if (colorSensor() == 2) {
                        motorKill();
                        encoderDrive(power, .5, MOVEMENT_DIRECTION.FORWARD);
                    }
                }
            } else if (movement_direction == MOVEMENT_DIRECTION.STRAFE_LEFT) {
                while (opModeIsActive() && colorSensor() != 2) {
                    leftFrontDriveMotor.setPower(power);
                    leftBackDriveMotor.setPower(-power);
                    rightFrontDriveMotor.setPower(power);
                    rightBackDriveMotor.setPower(-power);

                    if (colorSensor() == 2) {
                        motorKill();
                    }
                }
            } else if (movement_direction == MOVEMENT_DIRECTION.STRAFE_RIGHT) {
                while (opModeIsActive() && colorSensor() != 2) {
                    leftFrontDriveMotor.setPower(-power);
                    leftBackDriveMotor.setPower(power);
                    rightFrontDriveMotor.setPower(-power);
                    rightBackDriveMotor.setPower(power);

                    if (colorSensor() == 2) {
                        motorKill();
                    }
                }
            }
        } else if (tape_color == TAPE_COLOR.BLUE_TAPE) {
            if (movement_direction == MOVEMENT_DIRECTION.FORWARD) {
                while (opModeIsActive() && colorSensor() != 1) {
                    leftFrontDriveMotor.setPower(power);
                    leftBackDriveMotor.setPower(power);
                    rightFrontDriveMotor.setPower(power);
                    rightBackDriveMotor.setPower(power);

                    if (colorSensor() == 1) {
                        motorKill();
                        encoderDrive(power, .5, MOVEMENT_DIRECTION.REVERSE);
                    }
                }
            } else if (movement_direction == MOVEMENT_DIRECTION.REVERSE) {
                while (opModeIsActive() && colorSensor() != 1) {
                    leftFrontDriveMotor.setPower(-power);
                    leftBackDriveMotor.setPower(-power);
                    rightFrontDriveMotor.setPower(-power);
                    rightBackDriveMotor.setPower(-power);

                    if (colorSensor() == 1) {
                        motorKill();
                        encoderDrive(power, .5, MOVEMENT_DIRECTION.FORWARD);
                    }
                }
            } else if (movement_direction == MOVEMENT_DIRECTION.STRAFE_LEFT) {
                while (opModeIsActive() && colorSensor() != 1) {
                    leftFrontDriveMotor.setPower(power);
                    leftBackDriveMotor.setPower(-power);
                    rightFrontDriveMotor.setPower(power);
                    rightBackDriveMotor.setPower(-power);

                    if (colorSensor() == 1) {
                        motorKill();
                    }
                }
            } else if (movement_direction == MOVEMENT_DIRECTION.STRAFE_RIGHT) {
                while (opModeIsActive() && colorSensor() !=1) {
                    leftFrontDriveMotor.setPower(-power);
                    leftBackDriveMotor.setPower(power);
                    rightFrontDriveMotor.setPower(-power);
                    rightBackDriveMotor.setPower(power);

                    if (colorSensor() == 1) {
                        motorKill();
                    }
                }
            }
        }
    }

    //This method is used with the one beneath it, this method basically defines which distance sensor is being used
    //if you have multiple
    public double distanceSensor(SENSOR_DIRECTION sensor_direction) {
        DistanceSensor sensorDistance = null;
        double distance;

        if (sensor_direction == SENSOR_DIRECTION.REAR) {
            sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance_rear");

            Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor) sensorDistance;


        } else if (sensor_direction == SENSOR_DIRECTION.FRONT) {
            sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance_front");

            Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor) sensorDistance;


        } else if (sensor_direction == SENSOR_DIRECTION.LEFT) {
            sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance_left");

            Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor) sensorDistance;


        } else if (sensor_direction == SENSOR_DIRECTION.RIGHT) {
            sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance_right");

            Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor) sensorDistance;

        }
        // you can use this as a regular DistanceSensor.
        return sensorDistance.getDistance(DistanceUnit.INCH);



        // you can also cast this to a Rev2mDistanceSensor if you want to use added
        // methods associated with the Rev2mDistanceSensor class.

    }

    //drive the robot until it becomes a certain distance away from a wall. This worked 2 years ago and hasn't been
    //used since so expect to trouble shoot. Commented lines left in case 2 lines don't work and trouble shooting is needed
    public void distSensorDrive(double inputPower, double distanceFromObjectCM, MOVEMENT_DIRECTION movement_direction) {

        double power;
        double dist = distanceSensor(SENSOR_DIRECTION.REAR);
//        if (dist > 10) {
//            dist = 10;
//        }

//        if (movement_direction == MOVEMENT_DIRECTION.REVERSE) {
//
//            while (opModeIsActive() && distanceSensor(SENSOR_DIRECTION.REAR) > distanceFromObjectCM) {
//                double distanceToSpeed = (Math.atan(distanceSensor(SENSOR_DIRECTION.REAR) - distanceFromObjectCM))/4;
//                if (distanceToSpeed > inputPower) {
//                    power = inputPower;
//                } else {
//                    power = distanceToSpeed;
//                }
//                leftFrontDriveMotor.setPower(-power);
//                rightFrontDriveMotor.setPower(-power);
//                leftBackDriveMotor.setPower(-power);
//                rightBackDriveMotor.setPower(-power);
//
//                if (distanceSensor(SENSOR_DIRECTION.REAR) <= distanceFromObjectCM) {
//                    motorKill();
//                }
//                telemetry.addData("Power", power);
//                telemetry.update();
//            }

        //Only one sensor was on the robot so no need to discern direction
        //Use encoder drive to drive a set amount of inches away from an object
        //Example: your 20 inches away and want to be 3 inches away, 20 - 3 = 17 inches of travel
        encoderDrive(inputPower, dist - distanceFromObjectCM, MOVEMENT_DIRECTION.REVERSE);
        encoderDrive(.1, 1, MOVEMENT_DIRECTION.REVERSE);



//                    leftFrontDriveMotor.setPower(-power);
//                    rightFrontDriveMotor.setPower(-power);
//                    leftBackDriveMotor.setPower(-power);
//                    rightBackDriveMotor.setPower(-power);
//                    sleep(200);
//                } while (distanceFromObjectCM > distanceSensor(SENSOR_DIRECTION.REAR));


//            while (distanceFromObjectCM >= distanceSensor(SENSOR_DIRECTION.REAR)) {
//
//                double power = Math.log(distanceSensor(SENSOR_DIRECTION.REAR));
//                if (power > inputPower) {
//                    power = inputPower;
//                }
//                leftFrontDriveMotor.setPower(power);
//                rightFrontDriveMotor.setPower(power);
//                leftBackDriveMotor.setPower(power);
//                rightBackDriveMotor.setPower(power);
//                sleep(50);
//            }
//            if (distanceFromObjectCM <= distanceSensor(SENSOR_DIRECTION.REAR)) {
//                motorKill();
//            }
    }

    //sets all motor powers to 0
    public void motorKill() {
        //Kills the motors to prepare for next call of method
        leftFrontDriveMotor.setPower(0);
        rightFrontDriveMotor.setPower(0);
        leftBackDriveMotor.setPower(0);
        rightBackDriveMotor.setPower(0);
    }

    //These are all the enumerations for this class, if you don't know what enumerations are just google it

    enum MOVEMENT_DIRECTION {
        STRAFE_LEFT,
        STRAFE_RIGHT,
        FORWARD,
        REVERSE,
    }

    enum TURN_DIRECTION {
        TURN_LEFT,
        TURN_RIGHT

    }

    enum LIFT_DIRECTION {
        DOWN,
        UP
    }

    enum SENSOR_DIRECTION {
        FRONT,
        REAR,
        LEFT,
        RIGHT

    }

    enum TAPE_COLOR {
        RED_TAPE,
        BLUE_TAPE
    }



}
