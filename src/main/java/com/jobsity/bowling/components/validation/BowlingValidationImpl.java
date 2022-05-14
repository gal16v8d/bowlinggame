package com.jobsity.bowling.components.validation;

import com.jobsity.bowling.constants.BowlingPinfallConstants;
import com.jobsity.bowling.exception.BadBowlingInputException;
import com.jobsity.bowling.model.BowlingFrame;
import com.jobsity.bowling.model.Pinfall;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class BowlingValidationImpl implements BowlingValidation {

  protected static final String GAME_FRAMES_MSG =
      "Game is not valid due we only support " + BowlingPinfallConstants.MAX_FRAME + " frames.";
  protected static final String VALID_FRAME_MSG =
      "Game is not valid due we only support " + BowlingPinfallConstants.MAX_PIN + " pin games.";
  protected static final String VALID_FRAME_LAST_FRAME_MSG =
      "Third roll is only allowed when you wipe at least "
          + BowlingPinfallConstants.MAX_PIN
          + " pins";
  private static final int BONUS_FRAME_SHOTS = 3;

  @Override
  public boolean isFrameFinished(BowlingFrame frame) {
    List<Pinfall> falls =
        Optional.ofNullable(frame).map(BowlingFrame::getPinfalls).orElseGet(Collections::emptyList);
    return extractBonusFromFrame(frame) ? falls.size() == BONUS_FRAME_SHOTS : falls.size() == 2;
  }

  @Override
  public boolean isFail(String score) {
    return BowlingPinfallConstants.getFail().containsKey(score);
  }

  @Override
  public boolean isStrike(String score) {
    return BowlingPinfallConstants.getStrike().containsKey(score);
  }

  @Override
  public boolean isSpare(int sumOffallPin) {
    return sumOffallPin == BowlingPinfallConstants.MAX_PIN;
  }

  @Override
  public void checkIfValidFrame(int sumOffallPin) {
    if (sumOffallPin > BowlingPinfallConstants.MAX_PIN) {
      throw new BadBowlingInputException(VALID_FRAME_MSG);
    }
  }

  @Override
  public void checkIfValidFrameOnLastFrame(int sumOffTwoAttempts, int bonusPins) {
    if (sumOffTwoAttempts < BowlingPinfallConstants.MAX_PIN && bonusPins > 0) {
      throw new BadBowlingInputException(VALID_FRAME_LAST_FRAME_MSG);
    }
  }

  @Override
  public void checkGameFrames(int frameNumber) {
    if (frameNumber > BowlingPinfallConstants.MAX_FRAME) {
      throw new BadBowlingInputException(GAME_FRAMES_MSG);
    }
  }

  private boolean extractBonusFromFrame(BowlingFrame frame) {
    return Optional.ofNullable(frame).map(BowlingFrame::isBonus).orElse(false);
  }
}
