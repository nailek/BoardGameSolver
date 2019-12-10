package com.celiunski.board.game.solver;

import java.util.List;

public class NodeMoves {

    private String id;

    private List<String> moves;
    private List<String> triedMoves;

    public NodeMoves(String id, List<String> moves) {
        this.id = id;
        this.moves = moves;
    }

    public List<String> getMoves() {
        return moves;
    }

    public String getId() {
        return id;
    }

    public void addMove(String move){
        moves.add(move);
    }

    public void removeMove(String move){
        moves.remove(move);
        triedMoves.add(move);
    }

    public boolean isEmpty() {
        return id.isEmpty();
    }
}
