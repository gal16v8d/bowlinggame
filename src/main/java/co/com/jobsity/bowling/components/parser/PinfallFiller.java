package co.com.jobsity.bowling.components.parser;

import co.com.jobsity.bowling.model.BowlingFrame;

public interface PinfallFiller {

    int getFallScore(String score);

    BowlingFrame addPinfallByScore(BowlingFrame frame, String score);

    BowlingFrame addPinfallByScoreOnLastRound(BowlingFrame frame, String score);

}
