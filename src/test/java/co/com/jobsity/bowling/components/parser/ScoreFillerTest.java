package co.com.jobsity.bowling.components.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.jobsity.bowling.model.BowlingFrame;
import co.com.jobsity.bowling.model.Pinfall;
import co.com.jobsity.bowling.model.PlayerData;

@ExtendWith(MockitoExtension.class)
public class ScoreFillerTest {

    @Spy
    private ScoreFillerImpl scoreFiller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void fillFrameScoreTest() {
        PlayerData data = initPlayerData();
        PlayerData result = scoreFiller.fillFrameScore(data);
        Assertions.assertNotNull(result);
        Integer[] scores = result.getFrames().stream().map(BowlingFrame::getScore).toArray(Integer[]::new);
        Assertions.assertArrayEquals(new Integer[] { 20, 39, 48, 66, 74, 84, 90, 120, 148, 167 }, scores);
    }

    private static PlayerData initPlayerData() {
        List<BowlingFrame> frames = new ArrayList<>();
        frames.add(initFrame(1, true, false, false, new Pinfall[] { initPinfall("", null), initPinfall("X", 10) }));
        frames.add(initFrame(2, false, true, false, new Pinfall[] { initPinfall("7", 7), initPinfall("/", 3) }));
        frames.add(initFrame(3, false, false, false, new Pinfall[] { initPinfall("9", 9), initPinfall("0", 0) }));
        frames.add(initFrame(4, true, false, false, new Pinfall[] { initPinfall("", null), initPinfall("X", 10) }));
        frames.add(initFrame(5, false, false, false, new Pinfall[] { initPinfall("0", 0), initPinfall("8", 8) }));
        frames.add(initFrame(6, false, true, false, new Pinfall[] { initPinfall("8", 8), initPinfall("/", 2) }));
        frames.add(initFrame(7, false, false, false, new Pinfall[] { initPinfall("F", 0), initPinfall("6", 6) }));
        frames.add(initFrame(8, true, false, false, new Pinfall[] { initPinfall("", null), initPinfall("X", 10) }));
        frames.add(initFrame(9, true, false, false, new Pinfall[] { initPinfall("", null), initPinfall("X", 10) }));
        frames.add(initFrame(10, false, false, true,
                new Pinfall[] { initPinfall("X", 10), initPinfall("8", 8), initPinfall("1", 1) }));
        return PlayerData.builder().name("Jeff").frames(frames).build();
    }

    private static Pinfall initPinfall(String fallValue, Integer fall) {
        return Pinfall.builder().fall(fall).fallValue(fallValue).build();
    }

    private static BowlingFrame initFrame(int num, boolean isStrike, boolean isSpare, boolean isBonus,
            Pinfall[] pinfalls) {
        return BowlingFrame.builder().bonus(isBonus).number(num).spare(isSpare).strike(isStrike)
                .pinfalls(Stream.of(pinfalls).collect(Collectors.toList())).build();
    }

    @Test
    public void getNextValidPinFallsTest() {
        BowlingFrame frame = BowlingFrame.builder().pinfalls(List.of(arrangePinfallWithFallValue(null),
                arrangePinfallWithFallValue(""), arrangePinfallWithFallValue(" "), arrangePinfallWithFallValue("X")))
                .build();
        List<Pinfall> validPinfall = scoreFiller.getNextValidPinFalls(frame);
        Assertions.assertNotNull(validPinfall);
        Assertions.assertFalse(validPinfall.isEmpty());
        Assertions.assertEquals(1, validPinfall.size());
    }

    private static Pinfall arrangePinfallWithFallValue(String fallValue) {
        return Pinfall.builder().fallValue(fallValue).build();
    }

    @Test
    public void sumFramePinfallsTest() {
        BowlingFrame frame = BowlingFrame.builder()
                .pinfalls(List.of(arrangePinfall(null), arrangePinfall(10), arrangePinfall(8))).build();
        Assertions.assertEquals(18, scoreFiller.sumFramePinfalls(frame));
    }

    private static Pinfall arrangePinfall(Integer fall) {
        return Pinfall.builder().fall(fall).build();
    }
}
