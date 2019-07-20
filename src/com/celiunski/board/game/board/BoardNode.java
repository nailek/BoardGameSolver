package com.celiunski.board.game.board;

import com.celiunski.board.game.utils.Vector3;

public class BoardNode {
    private int x;
    private int y;
    private int z;
    private String id;
    private boolean filled;
    Vector3 vector3;

    BoardNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = get3rdAxis(x, y);
        id = createId(x, y, z);
        filled = x != 0 || y != 0 || z != 0; // Only (0,0,0) is empty.
        vector3 = new Vector3(x, y, z);
    }

    String getId() {
        return id;
    }

    int getXAxis() {
        return x;
    }

    int getZAxis() {
        return z;
    }

    int getYAxis() {
        return y;
    }

    public Vector3 getAxis() {
        return vector3;
    }

    static int get3rdAxis(int x, int y) {
        return -x-y;
    }

    static String createId(int x, int y, int z) {
        return ""+x+";"+y+";"+z;
    }

    boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}
