package com.example.meepmeeptesting2;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class YellowAuto {
    static double FORWARD = 3*Math.PI/2;
    static double BACK = Math.PI/2;
    static double LEFT = Math.PI;
    static double RIGHT = 0;
    static double SPLINE_HEADING = 0;
    private static final double pi = Math.PI;
    private static final Pose2d SPECIMEN_POS = new Pose2d(0, 35, 3*pi/2);
    private static final Pose2d OBSERVATION_POS = new Pose2d(60, 50, BACK);
    private static final Pose2d FIRST_SPECIMEN = new Pose2d(52, 35, FORWARD);
    private static final Pose2d SECOND_SPECIMEN = new Pose2d(60, 35, FORWARD);
    private static final Pose2d THIRD_SPECIMEN = new Pose2d(70, 35, FORWARD);



    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 60, Math.toRadians(180), Math.toRadians(180), 13.669840915663318)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 72, Math.toRadians(270)))
                // move to specimen container
                .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)
                // grab first blue
                .splineToLinearHeading(FIRST_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(OBSERVATION_POS, SPLINE_HEADING)
                .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)
                // grab second blue
                .splineToLinearHeading(SECOND_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(OBSERVATION_POS, SPLINE_HEADING)
                .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)
                // grab last blue
                .splineToLinearHeading(THIRD_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(OBSERVATION_POS, SPLINE_HEADING)
                .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)


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
