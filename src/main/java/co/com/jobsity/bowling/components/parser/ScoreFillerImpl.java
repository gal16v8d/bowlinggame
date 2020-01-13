package co.com.jobsity.bowling.components.parser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import co.com.jobsity.bowling.constants.BowlingPinfallConstants;
import co.com.jobsity.bowling.model.BowlingFrame;
import co.com.jobsity.bowling.model.Pinfall;
import co.com.jobsity.bowling.model.PlayerData;

@Component
public class ScoreFillerImpl implements ScoreFiller {

    @Override
    public PlayerData fillFrameScore(PlayerData data) {
        List<BowlingFrame> frames = Optional.ofNullable(data).map(PlayerData::getFrames)
                .orElseGet(Collections::emptyList);
        int previousScore = 0;
        int frameScore = 0;
        int framesSize = frames.size();
        for (int i = 0; i < framesSize; i++) {
            frameScore = calculateScore(frames, frames.get(i));
            frames.get(i).setScore(frameScore + previousScore);
            previousScore = frames.get(i).getScore();
        }
        data.setFrames(frames);
        return data;
    }

    @Override
    public int calculateScore(List<BowlingFrame> frames, BowlingFrame currentFrame) {
        int result = 0;
        if (currentFrame.isSpare()) {
            result = BowlingPinfallConstants.MAX_PIN + getNextScore(currentFrame, frames, 1);
        } else if (currentFrame.isStrike()) {
            result = BowlingPinfallConstants.MAX_PIN + getNextScore(currentFrame, frames, 2);
        } else {
            result = sumFramePinfalls(currentFrame);
        }
        return result;
    }

    public int getNextScore(BowlingFrame currentFrame, List<BowlingFrame> frames, int bonusPins) {
        int nextScore = 0;
        if (currentFrame.isBonus()) {
            nextScore += sumFramePinfalls(currentFrame);
        } else {
            List<Pinfall> currentFall = getNextValidPinFalls(frames.get(currentFrame.getNumber()));
            if (currentFall.size() < bonusPins) {
                currentFall.addAll(getNextValidPinFalls(frames.get(currentFrame.getNumber() + 1)));
            }
            for (int i = 0; i < bonusPins; i++) {
                nextScore += Optional.ofNullable(currentFall.get(i).getFall()).orElse(0);
            }
        }
        return nextScore;
    }

    public List<Pinfall> getNextValidPinFalls(BowlingFrame frame) {
        return frame.getPinfalls().stream().filter(fall -> StringUtils.isNotBlank(fall.getFallValue()))
                .collect(Collectors.toList());
    }

    public int sumFramePinfalls(BowlingFrame currentFrame) {
        return currentFrame.getPinfalls().stream().mapToInt(fall -> Optional.ofNullable(fall.getFall()).orElse(0))
                .sum();
    }

}
