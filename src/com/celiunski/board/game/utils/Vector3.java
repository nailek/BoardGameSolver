package com.celiunski.board.game.utils;

public class Vector3 {
    int x, y, z;

    public Vector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(String x, String y, String z) {
        this.x = Integer.valueOf(x);
        this.y = Integer.valueOf(y);
        this.z = Integer.valueOf(z);
    }

}
