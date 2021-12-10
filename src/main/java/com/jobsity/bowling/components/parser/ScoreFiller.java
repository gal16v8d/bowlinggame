package com.jobsity.bowling.components.parser;

import java.util.List;
import com.jobsity.bowling.model.BowlingFrame;
import com.jobsity.bowling.model.PlayerData;

public interface ScoreFiller {

  PlayerData fillFrameScore(PlayerData data);

  int calculateScore(List<BowlingFrame> frames, BowlingFrame currentFrame);

}
