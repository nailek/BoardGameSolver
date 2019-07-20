package com.celiunski.board.game.Exception;

public class IncorrectIDException extends Exception {

    private static final String BOARD_INCORRECT_ID = "Node is incorrect. ";

    /** Constructs an exception using the default values for message and title fields. */
    public IncorrectIDException() {
        super(BOARD_INCORRECT_ID);
    }

    public IncorrectIDException(String message) {
        super(BOARD_INCORRECT_ID + message);
    }
}