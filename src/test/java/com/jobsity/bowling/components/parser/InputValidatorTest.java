package com.jobsity.bowling.components.parser;

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
class InputValidatorTest {

  private static final String BAD_SCORE = "X";
  private static final String PLAYER = "player";
  @Spy
  private InputValidatorImpl inputValidator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @NullSource
  void testLineIsValidNull(String[] args) {
    Assertions.assertFalse(inputValidator.lineIsValid(args));
  }

  @ParameterizedTest
  @EmptySource
  @CsvSource({PLAYER, PLAYER + "@" + BAD_SCORE, PLAYER + "@" + BAD_SCORE + "@" + ""})
  void testLineIsValidFalse(String args) {
    String[] input = args.split("@");
    Assertions.assertFalse(inputValidator.lineIsValid(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "F"})
  void testLineIsValidTrue(String score) {
    String[] input = {PLAYER, score};
    Assertions.assertTrue(inputValidator.lineIsValid(input));
  }
}
