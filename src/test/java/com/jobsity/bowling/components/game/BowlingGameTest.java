package com.jobsity.bowling.components.game;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.jobsity.bowling.components.parser.FileProcessor;
import com.jobsity.bowling.components.parser.InputParser;

@ExtendWith(MockitoExtension.class)
class BowlingGameTest {

  private BowlingGameImpl bowlingGame;
  @Mock
  private FileProcessor fileProcessor;
  @Mock
  private InputParser inputParser;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    bowlingGame = Mockito.spy(new BowlingGameImpl(fileProcessor, inputParser));
  }

  @AfterEach
  void tearDown() {
    Mockito.reset(bowlingGame);
  }

  @Test
  void testExecuteGame() {
    Mockito.doNothing().when(bowlingGame).getScannerMenu();
    bowlingGame.executeGame();
    Mockito.verify(bowlingGame).getScannerMenu();
  }

  @Test
  void testBeginGame() {
    Mockito.doReturn(new ArrayList<>()).when(fileProcessor).getFileLines(Mockito.anyString());
    Mockito.doReturn(new HashMap<>()).when(inputParser).processInput(Mockito.anyList());
    Mockito.doReturn("").when(inputParser).showOutPut(Mockito.anyMap());
    bowlingGame.beginGame("test");
    Mockito.verify(fileProcessor).getFileLines(Mockito.anyString());
    Mockito.verify(inputParser).processInput(Mockito.anyList());
    Mockito.verify(inputParser).showOutPut(Mockito.anyMap());
  }
}