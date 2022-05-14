package com.jobsity.bowling.components.parser;

import com.jobsity.bowling.model.BowlingFrame;

public interface PinfallFiller {

  int getFallScore(String score);

  BowlingFrame addPinfallByScore(BowlingFrame frame, String score);

  BowlingFrame addPinfallByScoreOnLastRound(BowlingFrame frame, String score);
}
