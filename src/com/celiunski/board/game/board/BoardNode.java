package com.celiunski.board.game.board;

import com.celiunski.board.game.utils.Utils;
import com.celiunski.board.game.utils.Vector3;

public class BoardNode {
    private int x;
    private int y;
    private int z;
    private String id;
    private boolean filled;
    private Vector3 vector3;

    BoardNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = Utils.get3rdAxis(x, y);
        id = Utils.createId(x, y, z);
        filled = x != 0 || y != 0 || z != 0; // Only (0,0,0) is empty.
        vector3 = new Vector3(x, y, z);
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

    public Vector3 getPos() {
        return vector3;
    }

    boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}
