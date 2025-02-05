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
                .lineToY(-37)
                .setTangent(Math.toRadians(0))
                .lineToX(35.5)
                .setTangent(Math.toRadians(90))
                .lineToY(-18)
                .setTangent(Math.toRadians(0))
                .lineToX(47)
                .setTangent(Math.toRadians(90))
                .lineToY(-50)
                .lineToY(-18)
                .setTangent(Math.toRadians(0))
                .lineToX(56)
                .setTangent(Math.toRadians(90))
                .lineToY(-50)
                .strafeTo(new Vector2d(38, -45))
                .setTangent(Math.toRadians(90))
                .lineToY(-57)
                .strafeTo(new Vector2d(0, -32))
                .strafeTo(new Vector2d(28, -47))
                .lineToY(-57)
                .strafeTo(new Vector2d(0, -32))
                .strafeTo(new Vector2d(26, -47))
                .lineToY(-57)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}