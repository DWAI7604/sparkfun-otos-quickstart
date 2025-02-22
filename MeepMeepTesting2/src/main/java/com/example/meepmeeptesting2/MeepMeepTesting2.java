package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Actions;
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
                .setConstraints(50, 60, Math.toRadians(90), Math.toRadians(180), 13.669840915663318)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(2, -65, Math.toRadians(90)))
                //drive and push 3 samples into observation (block #1)
                        .lineToY(-34)

                        .strafeToLinearHeading(new Vector2d(10, -40), Math.toRadians(20))
                        .setTangent(Math.toRadians(20))
                        .lineToX(32)

//                        .setTangent(Math.toRadians(270))
//                        .splineToConstantHeading(new Vector2d(16, -40), Math.toRadians(270))
//                        .setTangent(90)
//                        .splineToConstantHeading(new Vector2d(32, -40), Math.toRadians(90))
//                        //.setTangent(0)
//                        .turn(Math.toRadians(-45))
//                        .setTangent(Math.toRadians(0))
//                        .lineToXLinearHeading(34, Math.toRadians(30))
//                        .setTangent(Math.toRadians(90))
//                        .splineTo(new Vector2d(30, -36), Math.toRadians(30))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}