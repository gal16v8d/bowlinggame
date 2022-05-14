package com.jobsity.bowling.constants;

import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BowlingPinfallConstants {

  protected static final Map<String, String> STRIKE = Map.of("10", "X");
  protected static final Map<String, Integer> FAIL = Map.of("F", 0);
  public static final String SPARE = "/";
  public static final int MAX_FRAME = 10;
  public static final int MAX_PIN = 10;

  public static Map<String, String> getStrike() {
    return STRIKE;
  }

  public static Map<String, Integer> getFail() {
    return FAIL;
  }
}
