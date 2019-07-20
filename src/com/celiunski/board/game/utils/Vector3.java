package com.celiunski.board.game.utils;

public class Vector3 {
    public int x, y, z;

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

    public Vector3(Vector3 vector3) {
        this.x = vector3.x;
        this.y = vector3.y;
        this.z = vector3.z;
    }

    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object instanceof Vector3) {
            Vector3 vector3 = (Vector3) object;
            result = (x == vector3.x && y == vector3.y && z == vector3.z);
        }
        return result;
    }
}
