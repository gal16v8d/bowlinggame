package com.jobsity.bowling.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

@Generated
@Data
@Builder
public class BowlingFrame {

    private int number;
    private List<Pinfall> pinfalls;
    private int score;
    private boolean bonus;
    private boolean strike;
    private boolean spare;
}
