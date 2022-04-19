package com.jobsity.bowling.exception;

public class BadBowlingInputException extends RuntimeException {

    private static final long serialVersionUID = 4034971196012046360L;

    public BadBowlingInputException(String msg) {
        super(msg);
    }
}
