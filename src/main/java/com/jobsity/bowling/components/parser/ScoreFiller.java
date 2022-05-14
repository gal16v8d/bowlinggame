package com.jobsity.bowling.components.parser;

import com.jobsity.bowling.model.BowlingFrame;
import com.jobsity.bowling.model.PlayerData;
import java.util.List;

public interface ScoreFiller {

  PlayerData fillFrameScore(PlayerData data);

  int calculateScore(List<BowlingFrame> frames, BowlingFrame currentFrame);
}
