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

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(24, -65, Math.toRadians(90)))
                //drive and push 3 samples into observation (block #1)

                //drive to sample 1
                .strafeTo(new Vector2d(36, -36))
                .setTangent(Math.toRadians(90))
                .lineToY(-17)
                .setTangent(Math.toRadians(0))
                .lineToX(45)
                //push 1
                .setTangent(Math.toRadians(90))
                .lineToY(-45)
                //drive to sample 2
                .lineToY(-17)
                .setTangent(Math.toRadians(0))
                .lineToX(54)
                //push 2
                .setTangent(Math.toRadians(90))
                .lineToY(-45)
                //drive to sample 3
                .lineToY(-17)
                .setTangent(Math.toRadians(0))
                .lineToX(63)
                //push 3
                .setTangent(Math.toRadians(90))
                .lineToY(-51)

                //Pick up 1 (block #2)
                //pause to slow down/bump
                .lineToY(-56)

                //Strafe to place position (block #3)
                .strafeTo(new Vector2d(4,-37))

                //place #1
                .waitSeconds(1)

                //pick up 2 (block #4)
                .strafeTo(new Vector2d(38,-54))
                .setTangent(Math.toRadians(90))
                .lineToY(-57.5)

                //drive and place #2 (block #5)
                .strafeTo(new Vector2d(2,-37))

                //place #2
                .waitSeconds(1)

                //pick up 3 (block #6)
                .strafeTo(new Vector2d(38,-54))
                .setTangent(Math.toRadians(90))
                .lineToY(-57.5)

                //drive and place #3 (block #7)
                .strafeTo(new Vector2d(0,-37))

                //place #3
                .waitSeconds(1)

                //pick up 4 (block #8)
                .strafeTo(new Vector2d(38,-54))
                .setTangent(Math.toRadians(90))
                .lineToY(-57.5)

                //drive and place #4 (block #9)
                .strafeTo(new Vector2d(-2,-37))

                //place #4
                .waitSeconds(1)

                //pick up 4 (block #10)
                .strafeTo(new Vector2d(38,-54))
                .setTangent(Math.toRadians(90))
                .lineToY(-57.5)

                //drive and place #4 (block #11)
                .strafeTo(new Vector2d(-4,-37))

                //place #5
                .waitSeconds(1)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}