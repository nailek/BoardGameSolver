package com.celiunski.board.game.Exception;

public class BoardNodeNotFoundException extends Exception {

    private static final String BOARD_NODE_NOT_FOUND = "BoardNode was not found";

    /** Constructs an exception using the default values for message and title fields. */
    public BoardNodeNotFoundException() {
        super(BOARD_NODE_NOT_FOUND);
    }

    public BoardNodeNotFoundException(String message) {
        super(BOARD_NODE_NOT_FOUND + message);
    }
}
