package co.com.jobsity.bowling.components.game;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @AfterEach
    public void tearDown() {
        Mockito.reset(bowlingGame);
    }

    @Test
    public void executeGameTest() {
        Mockito.doNothing().when(bowlingGame).getScannerMenu();
        bowlingGame.executeGame();
        Mockito.verify(bowlingGame).getScannerMenu();
    }

    @Test
    public void beginGameTest() {
        Mockito.doReturn(new ArrayList<>()).when(fileProcessor).getFileLines(Mockito.anyString());
        Mockito.doReturn(new HashMap<>()).when(inputParser).processInput(Mockito.anyList());
        Mockito.doReturn("").when(inputParser).showOutPut(Mockito.anyMap());
        bowlingGame.beginGame("test");
        Mockito.verify(fileProcessor).getFileLines(Mockito.anyString());
        Mockito.verify(inputParser).processInput(Mockito.anyList());
        Mockito.verify(inputParser).showOutPut(Mockito.anyMap());
    }
}
