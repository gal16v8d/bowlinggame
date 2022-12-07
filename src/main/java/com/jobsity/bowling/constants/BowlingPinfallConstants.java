package com.jobsity.bowling.constants;

import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class BowlingPinfallConstants {

  public static final Map<String, String> STRIKE = Map.of("10", "X");
  public static final Map<String, Integer> FAIL = Map.of("F", 0);
  public static final String SPARE = "/";
  public static final int MAX_FRAME = 10;
  public static final int MAX_PIN = 10;

}
