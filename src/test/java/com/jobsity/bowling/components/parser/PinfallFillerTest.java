package com.jobsity.bowling.components.parser;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.spy;

import com.jobsity.bowling.components.validation.BowlingValidation;
import com.jobsity.bowling.constants.BowlingPinfallConstants;
import com.jobsity.bowling.model.BowlingFrame;
import com.jobsity.bowling.model.Pinfall;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PinfallFillerTest {

  @Mock private BowlingValidation bowlingValidation;
  private PinfallFillerImpl pinfallFillerImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    pinfallFillerImpl = spy(new PinfallFillerImpl(bowlingValidation));
  }

  @Test
  void testGetFallScoreFail() {
    willReturn(true).given(bowlingValidation).isFail(anyString());
    Assertions.assertEquals(0, pinfallFillerImpl.getFallScore("F"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"2", "5", "8"})
  void testGetFallScore(String input) {
    willReturn(false).given(bowlingValidation).isFail(anyString());
    Assertions.assertEquals(Integer.parseInt(input), pinfallFillerImpl.getFallScore(input));
  }

  @Test
  void testAddPinfallByScoreNoPinFallsNoStrike() {
    willReturn(false).given(bowlingValidation).isStrike(anyString());
    BowlingFrame frame =
        pinfallFillerImpl.addPinfallByScore(
            BowlingFrame.builder().number(1).pinfalls(new ArrayList<>()).build(), "5");
    Assertions.assertNotNull(frame);
    Assertions.assertNotNull(frame.getPinfalls());
    Assertions.assertEquals(1, frame.getPinfalls().size());
  }

  @Test
  void testAddPinfallByScoreNoPinFallsStrike() {
    willReturn(true).given(bowlingValidation).isStrike(anyString());
    BowlingFrame frame =
        pinfallFillerImpl.addPinfallByScore(
            BowlingFrame.builder().number(1).pinfalls(new ArrayList<>()).build(), "10");
    Assertions.assertNotNull(frame);
    Assertions.assertNotNull(frame.getPinfalls());
    Assertions.assertTrue(frame.isStrike());
    Assertions.assertEquals(2, frame.getPinfalls().size());
  }

  @Test
  void testGetPinfallForSpareNoSpare() {
    willReturn(false).given(bowlingValidation).isSpare(anyInt());
    willReturn(false).given(bowlingValidation).isFail(anyString());
    BowlingFrame frame = BowlingFrame.builder().number(1).pinfalls(new ArrayList<>()).build();
    Pinfall pinfall = pinfallFillerImpl.getPinfallForSpare(frame, "5", 4);
    Assertions.assertNotNull(pinfall);
    Assertions.assertEquals(5, pinfall.getFall());
    Assertions.assertEquals("5", pinfall.getFallValue());
  }

  @Test
  void testGetPinfallForSpareWithSpare() {
    willReturn(true).given(bowlingValidation).isSpare(anyInt());
    willReturn(false).given(bowlingValidation).isFail(anyString());
    BowlingFrame frame = BowlingFrame.builder().number(1).pinfalls(new ArrayList<>()).build();
    Pinfall pinfall = pinfallFillerImpl.getPinfallForSpare(frame, "5", 5);
    Assertions.assertNotNull(pinfall);
    Assertions.assertEquals(5, pinfall.getFall());
    Assertions.assertEquals(BowlingPinfallConstants.SPARE, pinfall.getFallValue());
  }
}
