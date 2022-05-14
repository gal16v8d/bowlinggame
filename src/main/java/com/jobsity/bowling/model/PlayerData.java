package com.jobsity.bowling.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

@Generated
@Builder
@Data
public class PlayerData {

  private String name;
  private List<BowlingFrame> frames;
}
