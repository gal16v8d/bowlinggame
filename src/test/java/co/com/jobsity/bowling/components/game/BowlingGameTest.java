package co.com.jobsity.bowling.components.game;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.jobsity.bowling.components.parser.FileProcessor;
import co.com.jobsity.bowling.components.parser.InputParser;

@ExtendWith(MockitoExtension.class)
public class BowlingGameTest {

    @Spy
    @InjectMocks
    private BowlingGameImpl bowlingGame;
    @Mock
    private FileProcessor fileProcessor;
    @Mock
    private InputParser inputParser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void executeGameTest() {
        BDDMockito.doNothing().when(bowlingGame).getScannerMenu();
        bowlingGame.executeGame();
        BDDMockito.verify(bowlingGame).getScannerMenu();
    }

    @Test
    public void beginGameTest() {
        BDDMockito.willReturn(new ArrayList<>()).given(fileProcessor).getFileLines(Mockito.anyString());
        BDDMockito.willReturn(new HashMap<>()).given(inputParser).processInput(Mockito.anyList());
        BDDMockito.willReturn("").given(inputParser).showOutPut(Mockito.anyMap());
        bowlingGame.beginGame("test");
        BDDMockito.verify(fileProcessor).getFileLines(Mockito.anyString());
        BDDMockito.verify(inputParser).processInput(Mockito.anyList());
        BDDMockito.verify(inputParser).showOutPut(Mockito.anyMap());
    }
}
