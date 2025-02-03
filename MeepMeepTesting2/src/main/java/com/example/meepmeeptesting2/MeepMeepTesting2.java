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

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-1, -65, Math.toRadians(90)))
                .lineToY(-32)
                .waitSeconds(1)
                .lineToY(-34)
                .splineToConstantHeading(new Vector2d(36,-30), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(36,-12), Math.toRadians(90))
                .setTangent(Math.toRadians(0))
                .lineToXLinearHeading(42, Math.toRadians(0))
                .setTangent(Math.toRadians(90))
                .lineToY(-50)
                .lineToY(-12)
                .setTangent(Math.toRadians(0))
                .lineToX(52)
                .setTangent(Math.toRadians(90))
                .lineToY(-50)
                .lineToYLinearHeading(-45, Math.toRadians(90))
                .strafeTo(new Vector2d(38, -65))
                .waitSeconds(1)
                .strafeTo(new Vector2d(0, -32))
                .waitSeconds(1)
                .strafeTo(new Vector2d(38, -65))
                .waitSeconds(1)
                .strafeTo(new Vector2d(-1, -32))
                .waitSeconds(1)
                .strafeTo(new Vector2d(38, -65))
                .waitSeconds(1)
                .strafeTo(new Vector2d(1, -32))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}