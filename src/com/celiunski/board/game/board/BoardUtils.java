package com.celiunski.board.game.board;

import com.celiunski.board.game.utils.Utils;
import com.celiunski.board.game.utils.Vector3;

public class BoardUtils {
    static String getAdjacentKAway(String id, Utils.Axis axis, Utils.Move move, int k){
        Vector3 vector3 = Utils.getVector(id);
        switch (axis) {
            case X:
                if(move.equals(Utils.Move.UP)) {
                    vector3.x += k;
                    vector3.z -= k;
                }else {
                    vector3.x -= k;
                    vector3.z += k;
                }
                break;
            case Y:
                if(move.equals(Utils.Move.UP)) {
                    vector3.y += k;
                    vector3.x -= k;

                }else {
                    vector3.y -= k;
                    vector3.x += k;
                }
                break;
            case Z:
                if(move.equals(Utils.Move.UP)) {
                    vector3.z += k;
                    vector3.y -= k;
                }else {
                    vector3.z -= k;
                    vector3.y += k;
                }
        }
        return Utils.getID(vector3);
    }

    static boolean isAdjacentKAway(Vector3 vector3a, Vector3 vector3b, int k) {
        return vector3a.x == vector3b.x && Math.abs(vector3a.z - vector3b.z) == k
                || vector3a.y == vector3b.y && Math.abs(vector3a.x - vector3b.x) == k
                || vector3a.z == vector3b.z && Math.abs(vector3a.y - vector3b.y) == k;
    }

    static String getMiddleNode(String idA, String idB) {
        return Utils.getID(getMiddleNode(Utils.getVector(idA), Utils.getVector(idB)));
    }

    static Vector3 getMiddleNode(Vector3 vector3a, Vector3 vector3b) {
        int k = 2;
        Vector3 middleNode = null;
        if(vector3a.x == vector3b.x && Math.abs(vector3a.z - vector3b.z) == k) {
            middleNode = new Vector3(vector3a.x, 0, (vector3a.z + vector3b.z) / 2);
            middleNode.y = Utils.get3rdAxis(middleNode.x, middleNode.z);
        } else if (vector3a.y == vector3b.y && Math.abs(vector3a.x - vector3b.x) == k) {
            middleNode = new Vector3((vector3a.x + vector3b.x )/2, vector3a.y,0);
            middleNode.z = Utils.get3rdAxis(middleNode.x, middleNode.y);
        } else if (vector3a.z == vector3b.z && Math.abs(vector3a.y - vector3b.y) == k) {
            middleNode = new Vector3(0, (vector3a.y + vector3b.y )/2, vector3a.z);
            middleNode.x = Utils.get3rdAxis(middleNode.y, middleNode.z);
        }
        return middleNode;
    }
}
