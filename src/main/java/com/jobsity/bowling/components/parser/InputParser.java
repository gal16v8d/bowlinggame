package com.jobsity.bowling.components.parser;

import com.jobsity.bowling.model.PlayerData;
import java.util.List;
import java.util.Map;

public interface InputParser {

  Map<String, PlayerData> processInput(List<String> inputLines);

  String[] parseLine(String line);

  String showOutPut(Map<String, PlayerData> data);
}
