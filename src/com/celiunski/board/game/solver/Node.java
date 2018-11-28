package com.celiunski.board.game.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node {
    private boolean full;
    private Map<EdgeDirection,Edge> edges;
    private int id;

    public Node(int id) {
        this.id = id;
        setFull(true);
        edges = new HashMap<>();
    }

    public void addEdge(Edge edge, EdgeDirection direction) {
        edges.put(direction, edge);
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public ArrayList<Node> getAdjacentNodes() {
        ArrayList<Node> nodes = new ArrayList();
        //edges.forEach((k, v) -> nodes.add(v.other(this)));
        return nodes;
    }

    public int getId() {
        return id;
    }
}