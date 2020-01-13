package co.com.jobsity.bowling.components.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InputValidatorTest {

    private static final String BAD_SCORE = "X";
    private static final String PLAYER = "player";
    @Spy
    private InputValidatorImpl inputValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @NullSource
    public void lineIsValidNullTest(String[] args) {
        Assertions.assertFalse(inputValidator.lineIsValid(args));
    }

    @ParameterizedTest
    @EmptySource
    @CsvSource({ PLAYER, PLAYER + "@" + BAD_SCORE, PLAYER + "@" + BAD_SCORE + "@" + "" })
    public void lineIsValidFalseTest(String args) {
        String[] input = args.split("@");
        Assertions.assertFalse(inputValidator.lineIsValid(input));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "F" })
    public void lineIsValidTrueTest(String score) {
        String[] input = { PLAYER, score };
        Assertions.assertTrue(inputValidator.lineIsValid(input));
    }

}