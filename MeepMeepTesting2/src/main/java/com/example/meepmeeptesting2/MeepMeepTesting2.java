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

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(8.75, -57, Math.toRadians(270)))
                .strafeTo(new Vector2d(7.5,-24))
                .waitSeconds(0.5)
                .splineTo(new Vector2d(48.5, -48), Math.toRadians(90))
                .lineToY(-39.5)
                .lineToYLinearHeading(-44, Math.toRadians(270))
                .lineToYLinearHeading(-43, Math.toRadians(90))
                .strafeTo(new Vector2d(42,-68))
                .waitSeconds(0.5)
                .splineTo(new Vector2d(5, -42), Math.toRadians(270))
                .lineToY(-24)
                .waitSeconds(0.5)
                .splineTo(new Vector2d(57.5, -48), Math.toRadians(90))
                .lineToY(-39.5)
                .lineToYLinearHeading(-44, Math.toRadians(270))
                .lineToYLinearHeading(-43, Math.toRadians(90))
                .strafeTo(new Vector2d(42,-68))
                .waitSeconds(0.5)
                .splineTo(new Vector2d(2, -42), Math.toRadians(270))
                .lineToY(-24)
                .waitSeconds(0.5)

                .lineToYLinearHeading(-32, Math.toRadians(90))
                .strafeTo(new Vector2d(42, -68))
                .waitSeconds(0.5)
                .splineTo(new Vector2d(0, -42), Math.toRadians(270))
                .lineToY(-24)
                .waitSeconds(0.5)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}