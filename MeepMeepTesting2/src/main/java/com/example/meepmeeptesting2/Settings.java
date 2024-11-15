package com.example.meepmeeptesting2;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Settings {
    public static final double FORWARD = 3*Math.PI/2;
    public static final double BACK = Math.PI/2;
    public static final double LEFT = Math.PI;
    public static final double RIGHT = 0;

    public static final double SPLINE_HEADING = Math.PI;

    // OFFSET IS SET TO ZERO SO ITS CLEARER WHAT THE BOT IS SUPPOSED TO DO
    public static final double OFFSET = 0;

    public static final double PICKUP_DIR = FORWARD;
    public static final double HOOK_DIR = BACK;
    public static final double DROP_DIR = FORWARD;
    public static final double BASKET_DIR = 5*Math.PI/4;

    public static final Pose2d SPECIMEN_POS = new Pose2d(0, 35+OFFSET, HOOK_DIR);
    public static final Pose2d OBSERVATION_POS = new Pose2d(-60, 50+OFFSET, DROP_DIR);
    public static final Pose2d BLUE_FIRST_SPECIMEN = new Pose2d(-52, 35+OFFSET, PICKUP_DIR);
    public static final Pose2d BLUE_SECOND_SPECIMEN = new Pose2d(-60, 35+OFFSET, PICKUP_DIR);
    public static final Pose2d BLUE_THIRD_SPECIMEN = new Pose2d(-65, 35+OFFSET, PICKUP_DIR);
    public static final Pose2d YELLOW_FIRST_SPECIMEN = new Pose2d(52, 35+OFFSET, PICKUP_DIR);
    public static final Pose2d YELLOW_SECOND_SPECIMEN = new Pose2d(60, 35+OFFSET, PICKUP_DIR);
    public static final Pose2d YELLOW_THIRD_SPECIMEN = new Pose2d(65, 35+OFFSET, PICKUP_DIR);
    public static final Pose2d BASKET_POS = new Pose2d(56, 56, BASKET_DIR);
    public static final Pose2d START = new Pose2d(0, 72+OFFSET, BACK);

    public static RoadRunnerBotEntity getDefaultBot(MeepMeep meepMeep) {
        return new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 60, Math.toRadians(180), Math.toRadians(180), 13.669840915663318)
                .build();
    }
}
