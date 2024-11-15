package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.example.meepmeeptesting2.Autos;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


import static com.example.meepmeeptesting2.Settings.*;
import com.example.meepmeeptesting2.Autos.*;

public class BlueAuto {

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        // a modified version of the basic spiral strategy.
        RoadRunnerBotEntity bot1 = getDefaultBot(meepMeep);
        Autos.RemoveSpecimenBottleneckStrategy(bot1);

        // the "normal" spiral strategy.
        RoadRunnerBotEntity bot2 = getDefaultBot(meepMeep);
        Autos.SpiralStrategy(bot2);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)

                // I believe the first bot would be faster in practice because it removes many bottlenecks.
                .addEntity(bot1) // 27s
                .addEntity(bot2) // 24s
                .start();
    }
}


