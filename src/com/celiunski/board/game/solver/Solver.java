package com.celiunski.board.game.solver;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.celiunski.board.game.Exception.IncorrectIDException;
import com.celiunski.board.game.board.Board;
import com.celiunski.board.game.utils.Utils;

public class Solver {
    private int retries = 0;
    private Board finalBoard;
    private List<Board> deadEndBoards = new LinkedList<>();
    private List<Pair<Integer, List<Pair<String, List<String>>>>> storePossibleMoves = new LinkedList<>();
    private List<Board> halfSteps = new LinkedList<>();
    private boolean isFinished = false;
    private boolean isCutShort = false;

    private static final int testCount = 500000;

    public void trySolve(Board board) {
        isFinished = false;
        board.printIt();
        //TODO: Add stack to keep final solve moves.
        int iterations = makeMove(0, board);
        printEnd(board, iterations);
    }

    private void printEnd(Board board, int iterations) {
        Utils.println("");
        Utils.println("");
        Utils.println("");
        Utils.println("");
        Utils.println(" --- THE END ---");
        //printDeadEndBoards();
        //printUnreachedPossibleMoves();
        if(isFinished) {
            Utils.println(" --- Solved ---");
        } else if (isCutShort) {
            Utils.println(" --- Not solved: Program cut short---");
        } else {
            Utils.println(" --- Not solved ---");
        }
        board.printIt("To be solved");
        Utils.println("Num of iterations: " + iterations);
        Utils.println("Retries: " + retries);
        if(finalBoard != null) {
            finalBoard.printIt("Final Board");
            Utils.println(" --- Solved ---");
        }
    }

    private int makeMove(int iteration, Board board) {
        List<Pair<String, List<String>>> possibleMovesList;
        possibleMovesList = board.getAvailableMoves();

        Map<String, List<String>> removedOptionsList = new LinkedHashMap<>();
        if(isFinished(board)) {
            ifDeadEndStoreFinalBoard(board);
            isFinished = true;
            board.printIt("Final board at  Iteration: "+iteration);
            return iteration;
        }
        if(isCutShort(board, iteration)) {
            isCutShort = true;
            return iteration;
        }
        if(possibleMovesList.isEmpty()) {
            Utils.debugln("Move: "+iteration);
            board.debugIt("Outer: No possible Moves | ");
            ifDeadEndStoreFinalBoard(new Board(board));
        } else {
            Utils.debugMovesList(possibleMovesList);
            iteration = iteratePossibleMovesForThisNode(iteration, iteration, board, possibleMovesList, removedOptionsList);
        }
        return iteration;
    }

    private void ifDeadEndStoreFinalBoard(Board board) {
        if(board.getAvailableMoves().size() == 0) {
            deadEndBoards.add(board);
        } else {
            halfSteps.add(board);
        }
    }

    private boolean isFinished(Board board) {
        boolean isFinished = board.isFinished();
        if (isFinished) {
            finalBoard = new Board(board);
        }
        return isFinished;
    }

    private boolean isCutShort(Board board, int iteration) {
        boolean isCutShort = testCount != -1 && iteration >= testCount;
        if (!this.isCutShort && isCutShort) {
            Utils.println("Cutting Short the intent. It is taking too long...");
            board.printIt("Iteration: "+iteration);
        }
        return isCutShort;
    }

    private int iteratePossibleMovesForThisNode(int thisNodeIteration, int iteration, Board board, List<Pair<String, List<String>>> possibleMovesList, Map<String, List<String>> removedMovesList) {
        Utils.debugMovesList("|||POSSIBLE||| ", possibleMovesList);
        Utils.debugMovesHash("Removed ", removedMovesList);
        Board nextBoard = new Board(board);
        if(isFinished(board)) {
            ifDeadEndStoreFinalBoard(new Board(board));
            isFinished = true;
            board.printIt("Final board at Iteration: "+iteration);
            return iteration;
        }
        if(isCutShort(board, iteration)) {
            isCutShort = true;
            return iteration;
        }
        if(possibleMovesList.isEmpty()) {
            Utils.debugln("Move: "+iteration);
            board.debugIt("Inner: No possible Moves");
            ifDeadEndStoreFinalBoard(new Board(board));
            return iteration;
        }
        String emptyNode = possibleMovesList.get(0).getKey();
        while(!possibleMovesList.isEmpty()) {
            List<String> possibleMoves = possibleMovesList.get(0).getValue();
            String possibleMove = possibleMoves.get(0);
            iteration++;
            Utils.debugln("\nMove: "+iteration +" From: "+possibleMove +" To: "+ emptyNode);
            try {
                nextBoard.movePice(possibleMove, emptyNode);
            } catch (IncorrectIDException e) {
                //TODO: Log this
                Utils.print("There was an unexpected error: ");
                e.printStackTrace();
            }
            nextBoard.debugIt();

            iteration = makeMove(iteration, nextBoard);
            if(isFinished || isCutShort) {
                board.printIt("Iteration: " + thisNodeIteration);
                //board.printPossibleMoves("Iteration: " + thisNodeIteration, possibleMovesList, removedMovesList);
                storePossibleMoves.add(new Pair<>(iteration, possibleMovesList));
                return iteration;
            }

            Utils.debugln("Retrying from iteration: " + thisNodeIteration);
            retries++;
            Utils.debugMovesList("Old Possible: ", possibleMovesList);
            Utils.debugMovesHash("Old Removed: ", removedMovesList);
            board.debugIt("Old");

            if(removedMovesList.isEmpty()) {
                removedMovesList = new LinkedHashMap<>();
            }
            removedMovesList.computeIfAbsent(emptyNode, value -> new ArrayList<>());
            removedMovesList.get(emptyNode).add(possibleMove);

            if(possibleMoves.size() == 1) {
                possibleMovesList.remove(0);
            } else {
                possibleMoves.remove(0);
            }
            iteration = iteratePossibleMovesForThisNode(thisNodeIteration, iteration, board, possibleMovesList, removedMovesList);
        }
        return iteration;
    }

    private void printUnreachedPossibleMoves() {
        for (Pair<Integer, List<Pair<String, List<String>>>> storedPossible : storePossibleMoves) {
            Utils.printMovesList(""+storedPossible.getKey(), storedPossible.getValue());
        }
    }

    private void printDeadEndBoards() {
        for (int i = 0; i< deadEndBoards.size(); i++) {
            deadEndBoards.get(i).printIt("Board: " + i);
        }
    }
}
