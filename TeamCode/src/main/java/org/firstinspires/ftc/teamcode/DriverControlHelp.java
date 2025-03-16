/* Copyright (c) 2021 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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

/*
 * This file contains an example of a Linear "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When a selection is made from the menu, the corresponding OpMode is executed.
 *
 * This particular OpMode illustrates driving a 4-motor Omni-Directional (or Holonomic) robot.
 * This code will work with either a Mecanum-Drive or an X-Drive train.
 * Both of these drives are illustrated at https://gm0.org/en/latest/docs/robot-design/drivetrains/holonomic.html
 * Note that a Mecanum drive must display an X roller-pattern when viewed from above.
 *
 * Also note that it is critical to set the correct rotation direction for each motor.  See details below.
 *
 * Holonomic drives provide the ability for the robot to move in three axes (directions) simultaneously.
 * Each motion axis is controlled by one Joystick axis.
 *
 * 1) Axial:    Driving forward and backward               Left-joystick Forward/Backward
 * 2) Lateral:  Strafing right and left                     Left-joystick Right and Left
 * 3) Yaw:      Rotating Clockwise and counter clockwise    Right-joystick Right and Left
 *
 * This code is written assuming that the right-side motors need to be reversed for the robot to drive forward.
 * When you first test your robot, if it moves backward when you push the left stick forward, then you must flip
 * the direction of all 4 motors (see code below).
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="AssistedTelOp", group="01Tele")

public class DriverControlHelp extends RobotLinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
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
    private boolean aPressed = false;
    private boolean bPressed = false;
    private boolean xPressed = false;
    private boolean yPressed = false;
    private boolean a2Pressed = false;
    private boolean b2Pressed = false;
    private boolean x2Pressed = false;
    private boolean y2Pressed = false;
    private boolean rBumpPressed1 = false;
    private boolean rBumpPressed2 = false;
    private boolean lBumpPressed1 = false;
    private boolean lBumpPressed2 = false;
    private boolean dPadUpPressed = false;
    private boolean dPadUpPressed2 = false;
    private boolean dPadDownPressed = false;
    private boolean dPadDownPressed2 = false;
    private boolean dPadLeftPressed = false;
    private boolean dPadRightPressed = false;
    private boolean dPadLeftPressed2 = false;
    private boolean dPadRightPressed2 = false;
    private boolean pickingUp;
    private int placeCount;

    private double Kp = 0.04;
    private double Ki = 0.4;
    private double Kd = 2;

    private double xPosition = 34;
    private double yPosition = -54;
    private double heading = Math.toRadians(90);
    private Pose2d activePose;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.

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

        //activePose = new Pose2d(xPosition, yPosition, heading);
//        PinpointDrive drive = new PinpointDrive(hardwareMap, activePose);
//        drive.updatePoseEstimate();


        // ########################################################################################
        // !!!            IMPORTANT Drive Information. Test your motor directions.            !!!!!
        // ########################################################################################
        // Most robots need the motors on one side to be reversed to drive forward.
        // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
        // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
        // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
        // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
        // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
        // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.
//        leftFront.setDirection(DcMotor.Direction.REVERSE);
//        leftBack.setDirection(DcMotor.Direction.REVERSE);
//        rightFront.setDirection(DcMotor.Direction.FORWARD);
//        rightBack.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double max;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial   = gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower  = -(-axial - lateral + yaw);
            double rightFrontPower = -axial - lateral - yaw;
            double leftBackPower   = -axial + lateral + yaw;
            double rightBackPower  = -axial + lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
//            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
//            max = Math.max(max, Math.abs(leftBackPower));
//            max = Math.max(max, Math.abs(rightBackPower));
//
//            if (max > 1.0) {
//                leftFrontPower  /= max;
//                rightFrontPower /= max;
//                leftBackPower   /= max;
//                rightBackPower  /= max;
//            }

            // This is test code:
            //
            // Uncomment the following code to test your motor directions.
            // Each button should make the corresponding motor run FORWARD.
            //   1) First get all the motors to take to correct positions on the robot
            //      by adjusting your Robot Configuration if necessary.
            //   2) Then make sure they run in the correct direction by modifying the
            //      the setDirection() calls above.
            // Once the correct motors move in the correct direction re-comment this code.


//            leftFrontPower  = gamepad1.x ? 1.0 : 0.0;  // X gamepad
//            leftBackPower   = gamepad1.a ? 1.0 : 0.0;  // A gamepad
//            rightFrontPower = gamepad1.y ? 1.0 : 0.0;  // Y gamepad
//            rightBackPower  = gamepad1.b ? 1.0 : 0.0;  // B gamepad


            // Send calculated power to wheels
            leftFrontDriveMotor.setPower(leftFrontPower);
            rightFrontDriveMotor.setPower(rightFrontPower);
            leftBackDriveMotor.setPower(leftBackPower);
            rightBackDriveMotor.setPower(rightBackPower);
            hSlide.setPower(0.1);

//            slideUpBottom.setPower(-gamepad2.right_trigger);
//            slideUpTop.setPower(gamepad2.right_trigger);
//            slideUpBottom.setPower(gamepad2.left_trigger);
//            slideUpTop.setPower(-gamepad2.left_trigger);

            //Controller 1 Controls

            if (gamepad1.b && !bPressed) {
                bPressed = true;
            } else if (!gamepad1.b && bPressed) {
                bPressed = false;
            }

            if (bPressed) {
                bPressed = false;
                intakeMotor.setPower(0);
                intakeServo.setPosition(0.7);
            }

            //

            if (gamepad1.x && !xPressed) {
                xPressed = true;
            } else if (!gamepad1.x && xPressed) {
                xPressed = false;
            }

            if (xPressed) {
                xPressed = false;
                intakeServo.setPosition(0.7);
                intakeMotor.setPower(-0.9);
//                clawServo.setDirection(Servo.Direction.FORWARD);
//                clawServo.setPosition(0.1);
            }

            //

            if (gamepad1.a && !aPressed) {
                aPressed = true;
            } else if (!gamepad1.a && aPressed) {
                aPressed = false;
            }

            if (aPressed) {
                aPressed = false;
                intakeMotor.setPower(0.8);
                intakeServo.setPosition(0.7);
            }

            //

            if (gamepad1.y && !yPressed) {
                yPressed = true;
            } else if (!gamepad1.y && yPressed) {
                yPressed = false;
            }

            if (yPressed) {
                yPressed = false;
                intakeMotor.setPower(-.9);
                intakeServo.setPosition(0.4);
            }

//            if (gamepad1.left_bumper && !lBumpPressed1) {
//                lBumpPressed1 = true;
//            } else if (!gamepad1.left_bumper && lBumpPressed1) {
//                lBumpPressed1 = false;
//            }

            if (gamepad1.left_bumper) {
                hSlide.setDirection(DcMotorSimple.Direction.FORWARD);
                hSlide.setPower(0.75);
            }
            else{
                hSlide.setDirection(DcMotorSimple.Direction.FORWARD);
                hSlide.setPower(0.05);
            }

//            if (gamepad1.right_bumper && !rBumpPressed1) {
//                rBumpPressed1 = true;
//            } else if (!gamepad1.right_bumper && rBumpPressed1) {
//                rBumpPressed1 = false;
//            }

            if (gamepad1.right_bumper && !pickingUp) {
                hSlide.setDirection(DcMotorSimple.Direction.REVERSE);
                hSlide.setPower(0.9);
            }
            else{
                hSlide.setDirection(DcMotorSimple.Direction.FORWARD);
                hSlide.setPower(0.05);
            }

            if (gamepad1.dpad_up && !dPadUpPressed) {
                dPadUpPressed = true;
            } else if (gamepad1.dpad_up && dPadUpPressed) {
                dPadUpPressed = false;
            }

            if (dPadUpPressed) {
                dPadUpPressed = false;
                //intakeServo.setPosition(0.8);
            }

            //

            if (gamepad1.dpad_down && !dPadDownPressed) {
                dPadDownPressed = true;
            } else if (gamepad1.dpad_down && dPadDownPressed) {
                dPadDownPressed = false;
            }

            if (dPadDownPressed) {
                dPadDownPressed = false;
//                intakeServo.setPosition(0.4);
            }

            //

            if (gamepad1.dpad_left && !dPadLeftPressed) {
                dPadLeftPressed = true;
            } else if (gamepad1.dpad_left && dPadLeftPressed) {
                dPadLeftPressed = false;
            }

            if (dPadLeftPressed) {
                dPadLeftPressed = false;
                //intakeMotor.setPower(.9);
            }

            //

            if (gamepad1.dpad_right && !dPadRightPressed) {
                dPadRightPressed = true;
            } else if (gamepad1.dpad_right && dPadRightPressed) {
                dPadRightPressed = false;
            }

            if (dPadRightPressed) {
                dPadRightPressed = false;
                //intakeMotor.setPower(-.9);
            }

            //Controller 2 Controls

            if (gamepad2.x && !x2Pressed) {
                x2Pressed = true;
            } else if (!gamepad2.x && x2Pressed) {
                x2Pressed = false;
            }

            if (x2Pressed) {
                x2Pressed = false;
                clawServo.setDirection(Servo.Direction.FORWARD);
                clawServo.setPosition(0.01);
                clawServo.setPosition(0.01);
                sleep(200);
                armServoRight.setDirection(Servo.Direction.REVERSE);
                armServoRight.setPosition(0.5);
                armServoLeft.setDirection(Servo.Direction.FORWARD);
                armServoLeft.setPosition(0.5);
                wristServo.setDirection(Servo.Direction.FORWARD);
                sleep(100);
                wristServo.setPosition(0.67);
            }
            //

            if (gamepad2.b && !b2Pressed) {
                b2Pressed = true;
            } else if (!gamepad2.b && b2Pressed) {
                b2Pressed = false;
            }

            if (b2Pressed) {
                b2Pressed = false;
                clawServo.setDirection(Servo.Direction.FORWARD);
                clawServo.setPosition(0.99);

            }

            //

            if (gamepad2.y && !y2Pressed) {
                y2Pressed = true;
            } else if (!gamepad2.y && y2Pressed) {
                y2Pressed = false;
            }

            if (y2Pressed) {
                y2Pressed = false;
                intakeMotor.setPower(0);
//                encoderSlideUp(0.5, 5,MOVEMENT_DIRECTION.FORWARD);
//                slideUpTop.setPower(0.01);
//                slideUpBottom.setPower(0.01);
            }

            //

            if (gamepad2.a && !a2Pressed) {
                a2Pressed = true;
            } else if (!gamepad2.a && a2Pressed) {
                a2Pressed = false;
            }

            if (a2Pressed) {
                a2Pressed = false;
                intakeMotor.setPower(0.8);
                intakeServo.setPosition(0.4);
//                encoderSlideUp(0.5, 5,MOVEMENT_DIRECTION.REVERSE);
//                slideUpTop.setPower(0.01);
//                slideUpBottom.setPower(0.01);
            }

            //

//            if (gamepad2.left_bumper && !lBumpPressed2) {
//                lBumpPressed2 = true;
//            } else if (!gamepad2.left_bumper && lBumpPressed2) {
//                lBumpPressed2 = false;
//            }
//
//            if (lBumpPressed2) {
//                lBumpPressed2 = false;
//                slideUpTop.setPower(-0.3);
//                slideUpBottom.setPower(-0.3);
//                //encoderSlideForward(0.5, 5, MOVEMENT_DIRECTION.REVERSE);
//            }

            if (gamepad2.left_bumper) {
                slideUpTop.setPower(1);
                slideUpBottom.setPower(1);
            }
            else {
                slideUpTop.setPower(0);
                slideUpBottom.setPower(0);
            }

//            if (gamepad2.right_bumper && !rBumpPressed2) {
//                rBumpPressed2 = true;
//            } else if (!gamepad2.right_bumper && rBumpPressed2) {
//                rBumpPressed2 = false;
//            }
//
//            if (rBumpPressed2) {
//                rBumpPressed2 = false;
//                slideUpTop.setPower(0.3);
//                slideUpBottom.setPower(0.3);
//            }

            if (gamepad2.right_bumper) {
                slideUpTop.setPower(-0.8);
                slideUpBottom.setPower(-0.8);
            }
            else{
                slideUpTop.setPower(0);
                slideUpBottom.setPower(0);
            }

            //



            //

            if (gamepad2.dpad_up && !dPadUpPressed2) {
                dPadUpPressed2 = true;
            } else if (gamepad2.dpad_up && dPadUpPressed2) {
                dPadUpPressed2 = false;
            }

            if (dPadUpPressed2) {
                dPadUpPressed2 = false;
                pickingUp = false;
                armServoRight.setDirection(Servo.Direction.FORWARD);
                armServoRight.setPosition(0.5);
                armServoLeft.setDirection(Servo.Direction.REVERSE);
                armServoLeft.setPosition(0.5);
                wristServo.setDirection(Servo.Direction.FORWARD);
                wristServo.setPosition(0.67);
                //wristServo.setDirection(Servo.Direction.FORWARD);
                //wristServo.setPosition(0.02);
            }

            //

            if (gamepad2.dpad_left && !dPadLeftPressed2) {
                dPadLeftPressed2 = true;
            } else if (gamepad2.dpad_left && dPadLeftPressed2) {
                dPadLeftPressed2 = false;
            }

            if (dPadRightPressed2) {
                dPadRightPressed2 = false;
                pickingUp = true;
                armServoRight.setDirection(Servo.Direction.REVERSE);
                armServoRight.setPosition(0.21);
                armServoLeft.setDirection(Servo.Direction.FORWARD);
                armServoLeft.setPosition(0.21);
                wristServo.setDirection(Servo.Direction.FORWARD);
                wristServo.setPosition(0.67);
            }

            //

            if (gamepad2.dpad_right && !dPadRightPressed2) {
                dPadRightPressed2 = true;
            } else if (gamepad2.dpad_right && dPadRightPressed2) {
                dPadRightPressed2 = false;
            }

            if (dPadLeftPressed2) {
                dPadLeftPressed2 = false;
                pickingUp = false;
                armServoRight.setDirection(Servo.Direction.FORWARD);
                armServoRight.setPosition(0.135);
                armServoLeft.setDirection(Servo.Direction.REVERSE);
                armServoLeft.setPosition(0.135);
                wristServo.setDirection(Servo.Direction.FORWARD);
                wristServo.setPosition(0.01);
            }

            if (gamepad2.dpad_down && !dPadDownPressed2) {
                dPadDownPressed2 = true;
            } else if (gamepad2.dpad_down && dPadDownPressed2) {
                dPadDownPressed2 = false;
            }

            if (dPadDownPressed2) {
                dPadDownPressed2 = false;

                xPosition = 34;
                yPosition = -54;
                heading = Math.toRadians(90);
                activePose = new Pose2d(xPosition, yPosition, heading);

                PinpointDrive drive = new PinpointDrive(hardwareMap, activePose);
                drive.updatePoseEstimate();

                initialMovements();

                //drive to place position
                Actions.runBlocking(
                        drive.actionBuilder(activePose)
                                //.setTangent(Math.toRadians(0))
                                //.lineToX(39)
                                .lineToYLinearHeading(-59, Math.toRadians(90))

                                .build()
                );
                pickUp();

                xPosition = 34;
                yPosition = -59;
                activePose = new Pose2d(xPosition, yPosition, heading);

                Actions.runBlocking(
                        drive.actionBuilder(activePose)
                                .setTangent(Math.toRadians(90))
                                .strafeToLinearHeading(new Vector2d(-4, -36), Math.toRadians(90))

                                .build()
                );
                placeSpecimen();
                xPosition = -4;
                yPosition = -36;
                activePose = new Pose2d(xPosition, yPosition, heading);

                Actions.runBlocking(
                        drive.actionBuilder(activePose)
                                .setTangent(Math.toRadians(90))
                                .strafeTo(new Vector2d(-14,-37))

                                .build()
                );
                releaseSpecimen();

                xPosition = -14;
                yPosition = -37;
                activePose = new Pose2d(xPosition,yPosition,heading);

                Actions.runBlocking(
                        drive.actionBuilder(activePose)
                                .setTangent(Math.toRadians(90))
                                .strafeTo(new Vector2d(34,-54))

                                .build()
                );
                resetArm();
                xPosition = 34;
                yPosition = -54;

                declareHardwareProperties();

            }



//
//            slideUp.setPower(gamepad2.right_trigger);
//            slideUp.setPower(-gamepad2.left_trigger);
//            slideUp2.setPower(gamepad2.right_trigger);
//            slideUp2.setPower(-gamepad2.left_trigger);


            // Show the elapsed game time and wheel power.
//            telemetry.addData("Status", "Run Time: " + runtime.toString());
//            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
//            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            //telemetry.update();
        }
    }
    public void initialMovements(){
        hSlide.setPower(0.1);
        armServoRight.setDirection(Servo.Direction.FORWARD);
        armServoRight.setPosition(0.13);
        armServoLeft.setDirection(Servo.Direction.REVERSE);
        armServoLeft.setPosition(0.13);
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.77);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.01);
        sleep(100);
    }
    public void pickUp(){
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.01);
        sleep(500);
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.5);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.5);
        wristServo.setPosition(0.67);
    }
//Cardinal Crash™
    public void placeSpecimen(){
        //Swing arm forward to place position, wait, open claw
        armServoRight.setDirection(Servo.Direction.REVERSE);
        armServoRight.setPosition(0.23);
        armServoLeft.setDirection(Servo.Direction.FORWARD);
        armServoLeft.setPosition(0.23);
        sleep(300);

    }

    public void releaseSpecimen(){
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(0.7);
        sleep(100);
    }

    public void resetArm(){
        //swing arm back to pick up position
        armServoRight.setDirection(Servo.Direction.FORWARD);
        armServoRight.setPosition(0.135);
        armServoLeft.setDirection(Servo.Direction.REVERSE);
        armServoLeft.setPosition(0.135);
        wristServo.setDirection(Servo.Direction.FORWARD);
        wristServo.setPosition(0.01);
    }

}
