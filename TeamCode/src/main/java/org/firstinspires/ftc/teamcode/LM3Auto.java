package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "LM3Auto", group = "Autonomous")
@Disabled
public class LM3Auto extends RobotLinearOpMode{
    private Servo clawServo;
    private Servo wristServo;
    private Servo armServoLeft;
    private Servo armServoRight;
    @Override
    public void runOpMode() {

        clawServo = hardwareMap.get(Servo.class, "clawServo");
        wristServo = hardwareMap.get(Servo.class, "wristServo");
        armServoLeft = hardwareMap.get(Servo.class, "armServoLeft");
        armServoRight = hardwareMap.get(Servo.class, "armServoRight");
        declareHardwareProperties();

        while (!isStarted() && !isStopRequested()) {

            declareAutoVariables();

            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);
        }

        while(opModeIsActive()){
            encoderDrive(0.5, 15, MOVEMENT_DIRECTION.REVERSE);
//            clawServo.setDirection(Servo.Direction.FORWARD);
//            clawServo.setPosition(0.55);
//            wristServo.setDirection(Servo.Direction.FORWARD);
//            wristServo.setPosition(0.01);
//            armServoRight.setDirection(Servo.Direction.REVERSE);
//            armServoRight.setPosition(0.99);
//            armServoLeft.setDirection(Servo.Direction.FORWARD);
//            armServoLeft.setPosition(0.99);
//            encoderDrive(0.4, 21, MOVEMENT_DIRECTION.FORWARD);
//            encoderDrive(0.2, 4, MOVEMENT_DIRECTION.FORWARD);
//            encoderDrive(0.2, 1, MOVEMENT_DIRECTION.REVERSE);
//            armServoRight.setDirection(Servo.Direction.REVERSE);
//            armServoRight.setPosition(0.01);
//            armServoLeft.setDirection(Servo.Direction.FORWARD);
//            armServoLeft.setPosition(0.01);
//            encoderDrive(0.4, 14, MOVEMENT_DIRECTION.REVERSE);
//            encoderDrive(0.4, 16, MOVEMENT_DIRECTION.STRAFE_RIGHT);
//            sleep(10000);
//            encoderTurn(0.8, 220, TURN_DIRECTION.TURN_RIGHT);
//            encoderDrive(0.4, 19, MOVEMENT_DIRECTION.STRAFE_RIGHT);
//            encoderDrive(0.4, 3, MOVEMENT_DIRECTION.STRAFE_LEFT);
//            //encoderSlideUpTime(0.7, 1.5, MOVEMENT_DIRECTION.REVERSE);
//            encoderDrive(0.4, 18, MOVEMENT_DIRECTION.REVERSE);
//            sleep(200);
//            //encoderSlideUp(0.7, 5, MOVEMENT_DIRECTION.FORWARD);
//            encoderDrive(0.4, 15, MOVEMENT_DIRECTION.FORWARD);
//            encoderTurn(0.8, 220, TURN_DIRECTION.TURN_RIGHT);
//            encoderDrive(0.4, 6, MOVEMENT_DIRECTION.STRAFE_LEFT);
//            //encoderSlideUpTime(0.7, 0.2, MOVEMENT_DIRECTION.REVERSE);
//            encoderDrive(0.4, 32, MOVEMENT_DIRECTION.STRAFE_RIGHT);
//            encoderDrive(0.4, 10, MOVEMENT_DIRECTION.REVERSE);
//            //encoderSlideUp(0.7, 16, MOVEMENT_DIRECTION.FORWARD);
//            encoderDrive(0.2, 8, MOVEMENT_DIRECTION.REVERSE);
//            //encoderSlideUp(0.7, 5, MOVEMENT_DIRECTION.REVERSE);
//            encoderDrive(0.4, 15, MOVEMENT_DIRECTION.FORWARD);
//            //(0.7, 15, MOVEMENT_DIRECTION.REVERSE);

            sleep(30000);

        }

    }


}
