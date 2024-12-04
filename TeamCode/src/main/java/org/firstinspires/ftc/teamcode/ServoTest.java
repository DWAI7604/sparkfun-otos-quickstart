package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="SlideTele", group="Linear OpMode")
public class ServoTest extends RobotLinearOpMode{
    private Servo testServo;
    private boolean bPressed;
    private boolean aPressed;
    private boolean xPressed;
    @Override
    public void runOpMode() {

        testServo = hardwareMap.get(Servo.class, "testServo");

        waitForStart();

        while(opModeIsActive()) {
            if (gamepad1.b && !bPressed) {
                bPressed = true;
            } else if (!gamepad1.b && bPressed) {
                bPressed = false;
            }

            if (bPressed) {
                bPressed = false;
                testServo.setPosition(75 / 280);
            }

            if (gamepad1.a && !aPressed) {
                aPressed = true;
            } else if (!gamepad1.a && aPressed) {
                aPressed = false;
            }

            if (aPressed) {
                aPressed = false;
                testServo.setPosition(60 / 280);
            }

            if (gamepad1.x && !xPressed) {
                xPressed = true;
            } else if (!gamepad1.x && xPressed) {
                xPressed = false;
            }

            if (xPressed) {
                xPressed = false;
                testServo.setPosition(0);
            }
        }
    }
}
