package com.jobsity.bowling.components.parser;

import com.jobsity.bowling.constants.BowlingPinfallConstants;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class InputValidatorImpl implements InputValidator {

  private final Map<String, Integer> validScoreInputs;

  public InputValidatorImpl() {
    this.validScoreInputs = validScoreInputs();
  }

  @Override
  public boolean lineIsValid(String[] separatedValues) {
    return separatedValues != null
        && separatedValues.length == 2
        && getValidScoreInputs().containsKey(separatedValues[1]);
  }

  private Map<String, Integer> validScoreInputs() {
    Map<String, Integer> mapScoreInputs = new HashMap<>();
    mapScoreInputs.put("F", 0);
    for (int i = 0; i <= BowlingPinfallConstants.MAX_PIN; i++) {
      mapScoreInputs.put(String.valueOf(i), i);
    }
    return mapScoreInputs;
  }
}
