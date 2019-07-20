package com.celiunski.board.game.Exception;

public class ExceptionAxisNotFound extends Exception {

    private static final String AXIS_NOT_FOUND = "Axis was not found";

    /** Constructs an exception using the default values for message and title fields. */
    public ExceptionAxisNotFound() {
        super(AXIS_NOT_FOUND);
    }

    public ExceptionAxisNotFound(String message) {
        super(message);
    }
}
