package co.com.jobsity.bowling.components.parser;

import java.util.List;

import co.com.jobsity.bowling.model.BowlingFrame;
import co.com.jobsity.bowling.model.PlayerData;

public interface ScoreFiller {

    PlayerData fillFrameScore(PlayerData data);

    int calculateScore(List<BowlingFrame> frames, BowlingFrame currentFrame);

}
