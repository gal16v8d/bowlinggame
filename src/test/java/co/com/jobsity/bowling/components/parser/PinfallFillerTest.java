package co.com.jobsity.bowling.components.parser;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.jobsity.bowling.components.validation.BowlingValidation;
import co.com.jobsity.bowling.constants.BowlingPinfallConstants;
import co.com.jobsity.bowling.model.BowlingFrame;
import co.com.jobsity.bowling.model.Pinfall;

@ExtendWith(MockitoExtension.class)
class PinfallFillerTest {

    @Mock
    private BowlingValidation bowlingValidation;
    private PinfallFillerImpl pinfallFillerImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pinfallFillerImpl = Mockito.spy(new PinfallFillerImpl(bowlingValidation));
    }

    @Test
    void getFallScoreFailTest() {
        Mockito.doReturn(true).when(bowlingValidation).isFail(Mockito.anyString());
        Assertions.assertEquals(0, pinfallFillerImpl.getFallScore("F"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "2", "5", "8" })
    void getFallScoreTest(String input) {
        Mockito.doReturn(false).when(bowlingValidation).isFail(Mockito.anyString());
        Assertions.assertEquals(Integer.parseInt(input), pinfallFillerImpl.getFallScore(input));
    }

    @Test
    void addPinfallByScoreNoPinFallsNoStrikeTest() {
        Mockito.doReturn(false).when(bowlingValidation).isStrike(Mockito.anyString());
        BowlingFrame frame = pinfallFillerImpl
                .addPinfallByScore(BowlingFrame.builder().number(1).pinfalls(new ArrayList<>()).build(), "5");
        Assertions.assertNotNull(frame);
        Assertions.assertNotNull(frame.getPinfalls());
        Assertions.assertEquals(1, frame.getPinfalls().size());
    }

    @Test
    void addPinfallByScoreNoPinFallsStrikeTest() {
        Mockito.doReturn(true).when(bowlingValidation).isStrike(Mockito.anyString());
        BowlingFrame frame = pinfallFillerImpl
                .addPinfallByScore(BowlingFrame.builder().number(1).pinfalls(new ArrayList<>()).build(), "10");
        Assertions.assertNotNull(frame);
        Assertions.assertNotNull(frame.getPinfalls());
        Assertions.assertTrue(frame.isStrike());
        Assertions.assertEquals(2, frame.getPinfalls().size());
    }

    @Test
    void getPinfallForSpareNoSpareTest() {
        Mockito.doReturn(false).when(bowlingValidation).isSpare(Mockito.anyInt());
        Mockito.doReturn(false).when(bowlingValidation).isFail(Mockito.anyString());
        BowlingFrame frame = BowlingFrame.builder().number(1).pinfalls(new ArrayList<>()).build();
        Pinfall pinfall = pinfallFillerImpl.getPinfallForSpare(frame, "5", 4);
        Assertions.assertNotNull(pinfall);
        Assertions.assertEquals(5, pinfall.getFall());
        Assertions.assertEquals("5", pinfall.getFallValue());
    }

    @Test
    void getPinfallForSpareWithSpareTest() {
        Mockito.doReturn(true).when(bowlingValidation).isSpare(Mockito.anyInt());
        Mockito.doReturn(false).when(bowlingValidation).isFail(Mockito.anyString());
        BowlingFrame frame = BowlingFrame.builder().number(1).pinfalls(new ArrayList<>()).build();
        Pinfall pinfall = pinfallFillerImpl.getPinfallForSpare(frame, "5", 5);
        Assertions.assertNotNull(pinfall);
        Assertions.assertEquals(5, pinfall.getFall());
        Assertions.assertEquals(BowlingPinfallConstants.SPARE, pinfall.getFallValue());
    }

}
