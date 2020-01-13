package co.com.jobsity.bowling.components.parser;

import java.util.List;
import java.util.Map;

import co.com.jobsity.bowling.model.PlayerData;

public interface InputParser {

    Map<String, PlayerData> processInput(List<String> inputLines);

    String[] parseLine(String line);

    void showOutPut(Map<String, PlayerData> data);

}
