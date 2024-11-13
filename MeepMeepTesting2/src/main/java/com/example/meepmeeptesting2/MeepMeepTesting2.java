package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting2 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 60, Math.toRadians(180), Math.toRadians(180), 13.669840915663318)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(9, -57, Math.toRadians(270)))
                //Place 1
                .strafeTo(new Vector2d(2, -26))
                .waitSeconds(1)
                .lineToY(-32)
                .splineTo(new Vector2d(48, -44), Math.toRadians(90))
                .waitSeconds(1)
                .lineToYLinearHeading(-50, Math.toRadians(270))
                .strafeTo(new Vector2d(60, -44))
                .waitSeconds(1)
                .lineToY(-40)
                .strafeTo(new Vector2d(40, -70))
                .waitSeconds(1)
                //Place2
                .strafeTo(new Vector2d(0, -25))
                .waitSeconds(1)
                .lineToY(-32)
                .splineTo(new Vector2d(58, -44), Math.toRadians(90))
                .waitSeconds(1)
                .lineToYLinearHeading(-50, Math.toRadians(270))
                .strafeTo(new Vector2d(60, -44))
                .waitSeconds(1)
                .lineToY(-40)
                .strafeTo(new Vector2d(40, -70))
                .waitSeconds(1)
                //Place 3
                .strafeTo(new Vector2d(-2, -25))



//                .lineToYLinearHeading(70, Math.toRadians(90))
//                .lineToYLinearHeading(60, Math.toRadians(270))
//                .splineTo(new Vector2d(-8, 26), Math.toRadians(270))
//                .lineToY(32)
//                .splineTo(new Vector2d(-48, 30), Math.toRadians(90))
//                .waitSeconds(2)
//                .lineToYLinearHeading(70, Math.toRadians(90))
//                .lineToYLinearHeading(60, Math.toRadians(270))
//                .splineTo(new Vector2d(-2, 26), Math.toRadians(270))
//                .turn(Math.toRadians(90))
//                .lineToX(0)
//                .turn(Math.toRadians(90))
//                .lineToY(0)
//                .turn(Math.toRadians(90))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}