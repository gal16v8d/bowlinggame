package com.jobsity.bowling.components.game;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.spy;

import com.jobsity.bowling.components.parser.FileProcessor;
import com.jobsity.bowling.components.parser.InputParser;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BowlingGameTest {

  private BowlingGameImpl bowlingGame;
  @Mock private FileProcessor fileProcessor;
  @Mock private InputParser inputParser;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    bowlingGame = spy(new BowlingGameImpl(fileProcessor, inputParser));
  }

  @Test
  void testExecuteGame() {
    willDoNothing().given(bowlingGame).getScannerMenu();
    bowlingGame.executeGame();
    then(bowlingGame).should().getScannerMenu();
  }

  @Test
  void testBeginGame() {
    willReturn(new ArrayList<>()).given(fileProcessor).getFileLines(anyString());
    willReturn(new HashMap<>()).given(inputParser).processInput(anyList());
    willReturn("").given(inputParser).showOutPut(anyMap());
    bowlingGame.beginGame("test");
    then(fileProcessor).should().getFileLines(anyString());
    then(inputParser).should().processInput(anyList());
    then(inputParser).should().showOutPut(anyMap());
  }
}
