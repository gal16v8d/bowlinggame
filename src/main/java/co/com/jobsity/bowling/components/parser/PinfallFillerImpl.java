package co.com.jobsity.bowling.components.parser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.jobsity.bowling.components.validation.BowlingValidation;
import co.com.jobsity.bowling.constants.BowlingPinfallConstants;
import co.com.jobsity.bowling.model.BowlingFrame;
import co.com.jobsity.bowling.model.Pinfall;

@Component
public class PinfallFillerImpl implements PinfallFiller {

    private final BowlingValidation bowlingValidation;

    @Autowired
    public PinfallFillerImpl(BowlingValidation bowlingValidation) {
        this.bowlingValidation = bowlingValidation;
    }

    @Override
    public int getFallScore(String score) {
        return bowlingValidation.isFail(score) ? BowlingPinfallConstants.getFail().get(score) : Integer.parseInt(score);
    }

    @Override
    public BowlingFrame addPinfallByScore(BowlingFrame frame, String score) {
        List<Pinfall> falls = frame.getPinfalls();
        if (falls.isEmpty()) {
            if (bowlingValidation.isStrike(score)) {
                falls.add(Pinfall.builder().fallValue("").build());
                falls.add(Pinfall.builder().fallValue(BowlingPinfallConstants.getStrike().get(score))
                        .fall(getFallScore(score)).build());
                frame.setStrike(true);
            } else {
                falls.add(Pinfall.builder().fallValue(score).fall(getFallScore(score)).build());
            }
        } else {
            int lastRollScore = falls.stream().findFirst().map(Pinfall::getFall).orElse(0);
            frame.getPinfalls().add(getPinfallForSpare(frame, score, lastRollScore));
            bowlingValidation.checkIfValidFrame(lastRollScore + getFallScore(score));
        }
        frame.setPinfalls(falls);
        return frame;
    }

    @Override
    public BowlingFrame addPinfallByScoreOnLastRound(BowlingFrame frame, String score) {
        List<Pinfall> falls = Optional.ofNullable(frame).map(BowlingFrame::getPinfalls)
                .orElseGet(Collections::emptyList);
        if (bowlingValidation.isStrike(score)) {
            falls.add(Pinfall.builder().fallValue(BowlingPinfallConstants.getStrike().get(score))
                    .fall(getFallScore(score)).build());
        } else {
            addPinfallOnLastRoundWhenNotStrike(frame, score);
        }
        frame.setSpare(false);
        frame.setStrike(false);
        return frame;
    }

    Pinfall getPinfallForSpare(BowlingFrame frame, String score, int lastScore) {
        int newScore = getFallScore(score);
        int sum = lastScore + newScore;
        Pinfall pinfall;
        if (bowlingValidation.isSpare(sum)) {
            frame.setSpare(true);
            pinfall = Pinfall.builder().fall(newScore).fallValue(BowlingPinfallConstants.SPARE).build();
        } else {
            pinfall = Pinfall.builder().fall(newScore).fallValue(score).build();
        }
        return pinfall;
    }

    void addPinfallOnLastRoundWhenNotStrike(BowlingFrame frame, String score) {
        if (frame.getPinfalls().isEmpty()) {
            frame.getPinfalls().add(Pinfall.builder().fallValue(score).fall(getFallScore(score)).build());
        } else if (frame.getPinfalls().size() == 1) {
            int lastRollScore = frame.getPinfalls().stream().findFirst().map(Pinfall::getFall).orElse(0);
            frame.getPinfalls().add(getPinfallForSpare(frame, score, lastRollScore));
        } else {
            addPinfallOnLastFrameRollThree(frame, score);
        }
    }

    void addPinfallOnLastFrameRollThree(BowlingFrame frame, String score) {
        int firstAttempt = Optional.ofNullable(frame.getPinfalls().get(0)).map(Pinfall::getFall).orElse(0);
        int secondAttempt = Optional.ofNullable(frame.getPinfalls().get(1)).map(Pinfall::getFall).orElse(0);
        int thirdAttempt = getFallScore(score);
        bowlingValidation.checkIfValidFrameOnLastFrame(firstAttempt + secondAttempt, thirdAttempt);
        frame.getPinfalls().add(getPinfallForSpare(frame, score, secondAttempt));
    }

}
