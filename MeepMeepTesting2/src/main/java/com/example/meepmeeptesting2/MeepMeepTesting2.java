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

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(9, -63.5, Math.toRadians(270)))
                .strafeTo(new Vector2d(4.5,-32))
                .lineToY(-36)
                .splineTo(new Vector2d(36,-36), Math.toRadians(0))
                .setTangent(Math.toRadians(90))
                .lineToYLinearHeading(-14, Math.toRadians(0))
                .setTangent(Math.toRadians(180))
                .lineToX(47.5)
                .setTangent(Math.toRadians(90))
                .lineToY(-48)
                .lineToY(-14)
                .setTangent(Math.toRadians(180))
                .lineToX(57.5)
                .setTangent(Math.toRadians(90))
                .lineToY(-48)
                .lineToY(-44)
                .strafeToLinearHeading(new Vector2d(36, -57), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .lineToY(-63)
                .lineToY(-60)
                .strafeToLinearHeading(new Vector2d(5, -45), Math.toRadians(270))
                .setTangent(Math.toRadians(90))
                .lineToY(-32)
                .lineToY(-36)
                .strafeToLinearHeading(new Vector2d(36, -57), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .lineToY(-63)
                .lineToY(-60)
                .strafeToLinearHeading(new Vector2d(3, -45), Math.toRadians(270))
                .setTangent(Math.toRadians(90))
                .lineToY(-32)
                .lineToY(-36)
                .strafeToLinearHeading(new Vector2d(36, -57), Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .lineToY(-63)
                .lineToY(-60)
                .strafeToLinearHeading(new Vector2d(1, -45), Math.toRadians(270))
                .setTangent(Math.toRadians(90))
                .lineToY(-32)



                //.lineToYLinearHeading(-24, Math.toRadians(270))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}