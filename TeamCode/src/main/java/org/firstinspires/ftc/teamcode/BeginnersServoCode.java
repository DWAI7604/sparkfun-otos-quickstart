package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="BeginnersServoCode", group="BeginnerTeles")
public class BeginnersServoCode extends RobotLinearOpMode{
    //Declaration of Servos
    private Servo testServo;
    private CRServo testCRServo;

    //button press booleans
    private boolean xPressed = false;
    private boolean yPressed = false;
    private boolean aPressed = false;
    private boolean bPressed = false;

    @Override
    public void runOpMode() {
        //instantiation of servos
        testServo = hardwareMap.get(Servo.class, "testServo");
        testCRServo = hardwareMap.get(CRServo.class, "testCrServo");

        //set the directions of the servos
        testServo.setDirection(Servo.Direction.FORWARD);
        testCRServo.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive())
        {
            //turns the servo to position 0
            testServo.setPosition(0);
            sleep(1000);
            //turns the servo to position 1
            testServo.setPosition(1);
            sleep(1000);
            //turns the servo to position 0.5
            testServo.setPosition(0.5);
            sleep(1000);

            //turns the crservo on for 1 second at 0.5 power
            testCRServo.setPower(0.5);
            sleep(1000);
            //turn the crservo off for a second
            testCRServo.setPower(0);
            sleep(1000);
            //turns the crservo on for 1 second at -0.5 power (backwards)
            testCRServo.setPower(-0.5);
            sleep(1000);
            //turn the crservo off
            testCRServo.setPower(0);

            //if the x button is pressed turn the servo to position 1
            if (gamepad1.x && !xPressed) {
                xPressed = true;
            } else if (!gamepad1.x && xPressed) {
                xPressed = false;
            }

            if (xPressed) {
                xPressed = false;
                testServo.setPosition(1);
            }

            //if the y button is pressed turn the servo to position 0
            if (gamepad1.y && !yPressed) {
                yPressed = true;
            } else if (!gamepad1.y && yPressed) {
                yPressed = false;
            }

            if (yPressed) {
                yPressed = false;
                testServo.setPosition(0);
            }

            //if the a button is pressed turn the crservo on at 0.5 power
            if (gamepad1.a && !aPressed) {
                aPressed = true;
            } else if (!gamepad1.a && aPressed) {
                aPressed = false;
            }

            if (aPressed) {
                aPressed = false;
                testCRServo.setPower(0.5);
            }

            //if the b button is pressed turn the crservo off
            if (gamepad1.b && !bPressed) {
                bPressed = true;
            } else if (!gamepad1.b && bPressed) {
                bPressed = false;
            }

            if (bPressed) {
                bPressed = false;
                testCRServo.setPower(0);
            }
        }
    }
}
