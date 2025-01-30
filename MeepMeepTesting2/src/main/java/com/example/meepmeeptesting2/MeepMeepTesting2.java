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
                .setConstraints(50, 60, Math.toRadians(180), Math.toRadians(180), 13.669840915663318)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-48, 0, Math.toRadians(270)))
                //.setTangent(Math.toRadians(90))
                .lineToX(-36)
                .waitSeconds(1)
                .lineToX(-45)
                .waitSeconds(1)
                .splineToConstantHeading(new Vector2d(0,-30), Math.toRadians(90))
                //.splineTo(new Vector2d(0,-30), Math.toRadians(0))
                .waitSeconds(1)
                .setTangent(Math.toRadians(90))
                .lineToY(-35)
                //.strafeTo(new Vector2d(-36, -2))
                //.lineToY(-36)
                .waitSeconds(5)

                //.lineToYLinearHeading(-24, Math.toRadians(270))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}