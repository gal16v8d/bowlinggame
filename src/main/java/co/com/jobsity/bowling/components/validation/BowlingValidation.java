package co.com.jobsity.bowling.components.validation;

import co.com.jobsity.bowling.model.BowlingFrame;

public interface BowlingValidation {

    boolean isFrameFinished(BowlingFrame frame);

    boolean isFail(String score);

    boolean isStrike(String score);

    boolean isSpare(int sumOffallPin);

    void checkIfValidFrame(int sumOffallPin);

    void checkIfValidFrameOnLastFrame(int sumOffTwoAttempts, int bonusPins);

    void checkGameFrames(int frameNumber);

}
