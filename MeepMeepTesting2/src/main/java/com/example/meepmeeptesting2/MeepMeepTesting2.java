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

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(8.75, -57, Math.toRadians(270)))
                .strafeTo(new Vector2d(7.5,-24))
                .lineToY(-36)
                .splineTo(new Vector2d(36,-36), Math.toRadians(90))
                .lineToY(-20)
                .strafeTo(new Vector2d(48.5, -20))
                .strafeTo(new Vector2d(48.5, -48))
                .strafeTo(new Vector2d(48.5, -20))
                .strafeTo(new Vector2d(57.5, -20))
                .strafeTo(new Vector2d(57.5, -48))
                .strafeTo(new Vector2d(57.5, -20))
                .strafeTo(new Vector2d(63.5, -20))
                .strafeTo(new Vector2d(63.5, -48))
                .strafeTo(new Vector2d(37,-45))
                .strafeTo(new Vector2d(37, -70))
                .strafeTo(new Vector2d(5, -50))
                .strafeTo(new Vector2d(5, -48))
                .lineToYLinearHeading(-24, Math.toRadians(270))
                .strafeTo(new Vector2d(37, -40))
                .strafeTo(new Vector2d(37, -45))
                .lineToYLinearHeading(-65, Math.toRadians(90))
                .lineToY(-70)

                //.lineToYLinearHeading(-24, Math.toRadians(270))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}