package com.celiunski.board.game;

import com.celiunski.board.game.board.Board;
import com.celiunski.board.game.solver.Solver;

public class Main {

    public static void main(String args[]) {
        //To get vertex using index at O(1) time
        //private List<V> verticesLookup;
        Board board = new Board();
        Solver solver = new Solver();
        solver.trySolve(board);
    }
}