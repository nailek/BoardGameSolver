package com.celiunski.board.game.board;

import com.celiunski.board.game.utils.Utils;
import com.celiunski.board.game.utils.Vector3;

class BoardNode {
    private int x;
    private int y;
    private int z;
    private String id;
    private boolean filled;
    private Vector3 vector3;

    BoardNode(int x, int y, boolean filled) {
        this.x = x;
        this.y = y;
        this.z = Utils.get3rdAxis(x, y);
        id = Utils.getID(x, y, z);
        this.filled = filled;
        vector3 = new Vector3(x, y, z);
    }

    BoardNode(BoardNode node) {
        this.x = node.x;
        this.y = node.y;
        this.z = node.z;
        id = node.id;
        filled = node.filled;
        vector3 = node.vector3;
    }

    String getId() {
        return id;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getZ() {
        return z;
    }

    Vector3 getPos() {
        return vector3;
    }

    boolean isFilled() {
        return filled;
    }

    void setFilled(boolean filled) {
        this.filled = filled;
    }
}
