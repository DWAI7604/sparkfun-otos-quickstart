package com.example.meepmeeptesting2;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


import static com.example.meepmeeptesting2.Settings.*;

public class Autos {
    public static void RemoveSpecimenBottleneckStrategy(RoadRunnerBotEntity bot) {
        TrajectoryActionBuilder path = bot.getDrive().actionBuilder(new Pose2d(0, 72, Math.toRadians(270)))
                // move to specimen container
                /*
                The robot starts off with a specimen, so hook that one first.
                */

                .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)

                // grab first blue
                .splineToLinearHeading(BLUE_FIRST_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(OBSERVATION_POS, SPLINE_HEADING)

                /*
                Why not spline to specimen position right after? b/c then you would have to wait for the human player to clip.
                Instead, just immediately grab the next.

                DELETED .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)
                */

                // grab second blue
                .splineToLinearHeading(BLUE_SECOND_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(OBSERVATION_POS, SPLINE_HEADING)
                /*
                Same logic here.
                DELETED .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)
                */

                // grab last blue
                .splineToLinearHeading(BLUE_THIRD_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(OBSERVATION_POS, SPLINE_HEADING);
        // Now, the robot has 3 specimens in the observation zone.
        // Go back and forth from the specimen container, clipping them one at a time.
        for(int specimen = 0; specimen < 3; specimen++) {
            path = path.splineToLinearHeading(OBSERVATION_POS, SPLINE_HEADING)
                       .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING);
        }
        bot.runAction(path.build());
    }

    // simply grab, turn to specimen, then clip.
    public static void SpiralStrategy(RoadRunnerBotEntity bot) {
        bot.runAction(bot.getDrive().actionBuilder(new Pose2d(0, 72, Math.toRadians(270)))
                // move to specimen container
                .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)
                // grab first blue
                .splineToLinearHeading(BLUE_FIRST_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(OBSERVATION_POS, SPLINE_HEADING)
                .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)
                // grab second blue
                .splineToLinearHeading(BLUE_SECOND_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(OBSERVATION_POS, SPLINE_HEADING)
                .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)
                // grab last blue
                .splineToLinearHeading(BLUE_THIRD_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(OBSERVATION_POS, SPLINE_HEADING)
                .splineToLinearHeading(SPECIMEN_POS, SPLINE_HEADING)

                .build());
    }
    public static void YellowAuto(RoadRunnerBotEntity bot) {
        bot.runAction(bot.getDrive().actionBuilder(new Pose2d(0, 72, Math.toRadians(270)))
                // move to specimen container
                .splineToLinearHeading(BASKET_POS, SPLINE_HEADING)
                // grab first blue
                .splineToLinearHeading(YELLOW_FIRST_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(BASKET_POS, SPLINE_HEADING)
                // grab second blue
                .splineToLinearHeading(YELLOW_SECOND_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(BASKET_POS, SPLINE_HEADING)
                // grab last blue
                .splineToLinearHeading(YELLOW_THIRD_SPECIMEN, SPLINE_HEADING)
                .splineToLinearHeading(BASKET_POS, SPLINE_HEADING)

                .build());
    }
}