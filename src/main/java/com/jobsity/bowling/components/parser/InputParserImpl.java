package com.jobsity.bowling.components.parser;

import com.jobsity.bowling.components.validation.BowlingValidation;
import com.jobsity.bowling.constants.BowlingOutputConstants;
import com.jobsity.bowling.constants.BowlingPinfallConstants;
import com.jobsity.bowling.model.BowlingFrame;
import com.jobsity.bowling.model.PlayerData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class InputParserImpl implements InputParser {

  private final InputValidator inputValidator;
  private final PinfallFiller pinfallFiller;
  private final BowlingValidation bowlingValidation;
  private final ScoreFiller scoreFiller;

  @Override
  public Map<String, PlayerData> processInput(List<String> lines) {
    Map<String, PlayerData> play = new HashMap<>();
    lines.stream().forEach((String line) -> {
      String[] data = parseLine(line);
      log.debug("{} is valid {}", line, inputValidator.lineIsValid(data));
      addRollToPlayer(play, data);
    });
    play.entrySet().parallelStream().forEach(entry -> scoreFiller.fillFrameScore(entry.getValue()));
    return play;
  }

  @Override
  public String[] parseLine(String line) {
    return line.split("\\s+");
  }

  @Override
  public String showOutPut(Map<String, PlayerData> play) {
    StringBuilder playerStats = new StringBuilder();
    StringBuilder pinfallLine = new StringBuilder();
    StringBuilder scoreLine = new StringBuilder();
    play.entrySet().forEach(entry -> {
      log.debug("Showing stats for {}", entry.getKey());
      playerStats.append(entry.getKey()).append(System.lineSeparator());
      pinfallLine.append(BowlingOutputConstants.PINFALLS).append(BowlingOutputConstants.SEPARATOR);
      scoreLine.append(BowlingOutputConstants.SCORE).append(BowlingOutputConstants.SEPARATOR);
      PlayerData data = entry.getValue();
      data.getFrames().stream().forEach((BowlingFrame frame) -> {
        frame.getPinfalls().stream().forEach(pinfall -> {
          pinfallLine.append(pinfall.getFallValue()).append(BowlingOutputConstants.SEPARATOR);
          log.debug(
              "Showing stats for {}" + " and frame {}," + " pinfall '{}'",
              entry.getKey(),
              frame.getNumber(),
              pinfall.getFallValue());
        });
        scoreLine.append(frame.getScore())
            .append(BowlingOutputConstants.SEPARATOR)
            .append(BowlingOutputConstants.SEPARATOR);
        log.debug(
            "Showing score for {} on frame {} is {}",
            entry.getKey(),
            frame.getNumber(),
            frame.getScore());
      });
      playerStats.append(pinfallLine.toString()).append(System.lineSeparator());
      playerStats.append(scoreLine.toString()).append(System.lineSeparator());
      pinfallLine.setLength(0);
      scoreLine.setLength(0);
    });
    StringBuilder output = new StringBuilder();
    output.append(System.lineSeparator())
        .append(buildFrameLine())
        .append(System.lineSeparator())
        .append(playerStats.toString());
    return output.toString();
  }

  protected String buildFrameLine() {
    StringBuilder frameLine = new StringBuilder(BowlingOutputConstants.FRAME);
    frameLine.append(BowlingOutputConstants.SEPARATOR);
    for (int i = 1; i <= BowlingPinfallConstants.MAX_PIN; i++) {
      frameLine.append(i)
          .append(BowlingOutputConstants.SEPARATOR)
          .append(BowlingOutputConstants.SEPARATOR);
    }
    return frameLine.toString();
  }

  BowlingFrame getLastFrame(PlayerData data) {
    return data.getFrames().get(data.getFrames().size() - 1);
  }

  public void addRollToPlayer(Map<String, PlayerData> play, String[] playerData) {
    String player = playerData[0];
    String currentRollValue = playerData[1];
    initPlayerIfNotExists(play, player);
    PlayerData data = play.get(player);
    checkAndInitNewFrame(data);
    addPinfallByScore(data, currentRollValue);
  }

  void initPlayerIfNotExists(Map<String, PlayerData> play, String player) {
    if (play.get(player) == null) {
      List<BowlingFrame> frames = new ArrayList<>();
      frames.add(generateNewBowlingFrame(null));
      PlayerData data = PlayerData.builder().name(player).frames(frames).build();
      play.put(player, data);
    }
  }

  void checkAndInitNewFrame(PlayerData data) {
    BowlingFrame lastFrame = getLastFrame(data);
    if (bowlingValidation.isFrameFinished(lastFrame)) {
      data.getFrames().add(generateNewBowlingFrame(lastFrame));
    }
  }

  BowlingFrame generateNewBowlingFrame(BowlingFrame previous) {
    int prevNumber = Optional.ofNullable(previous).map(BowlingFrame::getNumber).orElse(0);
    BowlingFrame newFrame =
        BowlingFrame.builder().number(prevNumber + 1).pinfalls(new ArrayList<>()).build();
    if (newFrame.getNumber() == BowlingPinfallConstants.MAX_FRAME) {
      newFrame.setBonus(true);
    }
    return newFrame;
  }

  void addPinfallByScore(PlayerData data, String currentRollValue) {
    BowlingFrame lastFrame = getLastFrame(data);
    if (lastFrame.getNumber() < BowlingPinfallConstants.MAX_FRAME) {
      pinfallFiller.addPinfallByScore(lastFrame, currentRollValue);
    } else if (lastFrame.getNumber() == BowlingPinfallConstants.MAX_FRAME) {
      pinfallFiller.addPinfallByScoreOnLastRound(lastFrame, currentRollValue);
    } else {
      bowlingValidation.checkGameFrames(lastFrame.getNumber());
    }
  }
}
