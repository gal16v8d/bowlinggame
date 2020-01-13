package co.com.jobsity.bowling.components.parser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class InputValidatorImpl implements InputValidator {

    private final Map<String, Integer> validScoreInputs;

    public InputValidatorImpl() {
        this.validScoreInputs = validScoreInputs();
    }

    @Override
    public boolean lineIsValid(String[] separatedValues) {
        return separatedValues != null && separatedValues.length == 2
                && getValidScoreInputs().containsKey(separatedValues[1]);
    }

    private Map<String, Integer> validScoreInputs() {
        Map<String, Integer> validScoreInputs = new HashMap<>();
        validScoreInputs.put("F", 0);
        for (int i = 0; i <= 10; i++) {
            validScoreInputs.put(String.valueOf(i), i);
        }
        return validScoreInputs;
    }

}
