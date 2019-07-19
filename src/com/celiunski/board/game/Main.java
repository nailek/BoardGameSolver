package com.celiunski.board.game;

import com.celiunski.board.game.old.solver.Tauler;
import com.celiunski.board.game.solver.Board;

public class Main {

    public static void main(String args[]) {
        //To get vertex using index at O(1) time
        //private List<V> verticesLookup;
        Board board = new Board();
        board.printIt();
    }
}