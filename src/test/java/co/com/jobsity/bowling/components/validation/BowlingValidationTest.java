package co.com.jobsity.bowling.components.validation;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.jobsity.bowling.components.validation.BowlingValidationImpl;
import co.com.jobsity.bowling.exception.BadBowlingInputException;
import co.com.jobsity.bowling.model.BowlingFrame;
import co.com.jobsity.bowling.model.Pinfall;

@ExtendWith(MockitoExtension.class)
public class BowlingValidationTest {

    @Spy
    private BowlingValidationImpl bowlingValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("When frame is not valid then frame is not finished")
    @ParameterizedTest
    @MethodSource("frameNotValidInput")
    public void isFrameFinishedWhenFrameIsNotValidTest(BowlingFrame frame) {
        Assertions.assertFalse(bowlingValidation.isFrameFinished(frame));
    }

    private static Stream<Arguments> frameNotValidInput() {
        return Stream.of(null, Arguments.of(BowlingFrame.builder().build()));
    }

    @DisplayName("When bonus frame not contains 3 elements then frame is not finished")
    @ParameterizedTest
    @MethodSource("bonusFrameInput")
    public void isFrameFinishedWhenBonusFrameNotCompleteTest(BowlingFrame frame) {
        Assertions.assertFalse(bowlingValidation.isFrameFinished(frame));
    }

    private static Stream<Arguments> bonusFrameInput() {
        return Stream.of(Arguments.of(BowlingFrame.builder().bonus(true).pinfalls(List.of(arrangePinfall())).build()),
                Arguments.of(BowlingFrame.builder().bonus(true).pinfalls(List.of(arrangePinfall(), arrangePinfall()))
                        .build()));
    }

    private static Pinfall arrangePinfall() {
        return Pinfall.builder().build();
    }

    @DisplayName("When normal frame not contains 2 elements then frame is not finished")
    @Test
    public void isFrameFinishedWhenNormalFrameNotCompleteTest() {
        BowlingFrame frame = BowlingFrame.builder().pinfalls(List.of(arrangePinfall())).build();
        Assertions.assertFalse(bowlingValidation.isFrameFinished(frame));
    }

    @DisplayName("When normal frame contains 2 elements then frame is finished")
    @Test
    public void isFrameFinishedWhenNormalFrameCompleteTest() {
        BowlingFrame frame = BowlingFrame.builder().pinfalls(List.of(arrangePinfall(), arrangePinfall())).build();
        Assertions.assertTrue(bowlingValidation.isFrameFinished(frame));
    }

    @DisplayName("When bonus frame contains 3 elements then frame is finished")
    @Test
    public void isFrameFinishedWhenBonusFrameNotCompleteTest() {
        BowlingFrame frame = BowlingFrame.builder().bonus(true)
                .pinfalls(List.of(arrangePinfall(), arrangePinfall(), arrangePinfall())).build();
        Assertions.assertTrue(bowlingValidation.isFrameFinished(frame));
    }

    @DisplayName("When score is not 'F' then is not a Fail")
    @ParameterizedTest(name = "{index} => When input is '{0}' then is not a fail")
    @ValueSource(strings = { "1", "2", "3", "X" })
    public void isFailReturnFalseTest(String input) {
        Assertions.assertFalse(bowlingValidation.isFail(input));
    }

    @DisplayName("When score is 'F' then is a Fail")
    @Test
    public void isFailReturnTrueTest() {
        Assertions.assertTrue(bowlingValidation.isFail("F"));
    }

    @DisplayName("When score is not '10' then is not a Strike")
    @ParameterizedTest(name = "{index} => When input is '{0}' then is not a strike")
    @ValueSource(strings = { "1", "2", "3", "X", "F", "/" })
    public void isStrikeReturnFalseTest(String input) {
        Assertions.assertFalse(bowlingValidation.isStrike(input));
    }

    @DisplayName("When score is '10' then is a Strike")
    @Test
    public void isStrikeReturnTrueTest() {
        Assertions.assertTrue(bowlingValidation.isStrike("10"));
    }

    @DisplayName("When score is not '10' then is not a Strike")
    @ParameterizedTest(name = "{index} => When input is '{0}' then is not a spare")
    @ValueSource(ints = { 1, 2, 3, 11, 0 })
    public void isSpareReturnFalseTest(Integer input) {
        Assertions.assertFalse(bowlingValidation.isSpare(input));
    }

    @DisplayName("When pin-falls are 10 pins then is a Spare")
    @Test
    public void isSpareReturnTrueTest() {
        Assertions.assertTrue(bowlingValidation.isSpare(10));
    }

    @DisplayName("If sum of all fall pin is below to 10 and bonus is up zero then throw exc")
    @ParameterizedTest(name = "{index} => When input is '{0}' and bonus is '{1}' then exception is throw")
    @CsvSource({ "9,2", "5,1" })
    public void checkIfValidOnLastFrameLaunchExcTest(int sumofAllFallPin, int bonusPin) {
        BadBowlingInputException bbie = Assertions.assertThrows(BadBowlingInputException.class,
                () -> bowlingValidation.checkIfValidFrameOnLastFrame(sumofAllFallPin, bonusPin));
        Assertions.assertEquals(BowlingValidationImpl.VALID_FRAME_LAST_FRAME_MSG, bbie.getMessage());
    }

    @DisplayName("If sum of all fall pin is up to 10 we can have bonus pin, then no throw exc")
    @ParameterizedTest(name = "{index} => When input is '{0}' and bonus is '{1}'then no exception is throw")
    @CsvSource({ "10,2", "11,1", "13,0" })
    public void checkIfValidOnLastFrameNotLaunchExcTest(int sumofAllFallPin, int bonusPin) {
        Assertions.assertDoesNotThrow(() -> bowlingValidation.checkIfValidFrameOnLastFrame(sumofAllFallPin, bonusPin));
    }

    @DisplayName("If sum of all fall pin is up to 10 then throw exc")
    @ParameterizedTest(name = "{index} => When input is '{0}' then exception is throw")
    @ValueSource(ints = { 11, 100 })
    public void checkIfValidFrameLaunchExcTest(int sumofAllFallPin) {
        BadBowlingInputException bbie = Assertions.assertThrows(BadBowlingInputException.class,
                () -> bowlingValidation.checkIfValidFrame(sumofAllFallPin));
        Assertions.assertEquals(BowlingValidationImpl.VALID_FRAME_MSG, bbie.getMessage());
    }

    @DisplayName("If sum of all fall pin is not up to 10 then no throw exc")
    @ParameterizedTest(name = "{index} => When input is '{0}' then no exception is throw")
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 })
    public void checkIfValidFrameNotLaunchExcTest(int sumofAllFallPin) {
        Assertions.assertDoesNotThrow(() -> bowlingValidation.checkIfValidFrame(sumofAllFallPin));
    }

    @DisplayName("When frame number is less than 10 will not throw exc")
    @ParameterizedTest(name = "{index} => When input is '{0}' then no exception is throw")
    @ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 })
    public void checkGameFramesNotThrowsExcTest(int frameNumber) {
        Assertions.assertDoesNotThrow(() -> bowlingValidation.checkGameFrames(frameNumber));
    }

    @DisplayName("When frame number is more than 10 will throw exc")
    @ParameterizedTest(name = "{index} => When input is '{0}' then exception is throw")
    @ValueSource(ints = { 11, 100, 1000 })
    public void checkGameFrameThrowsExcTest(int frameNumber) {
        BadBowlingInputException bbie = Assertions.assertThrows(BadBowlingInputException.class,
                () -> bowlingValidation.checkGameFrames(frameNumber));
        Assertions.assertEquals(BowlingValidationImpl.GAME_FRAMES_MSG, bbie.getMessage());
    }

}
