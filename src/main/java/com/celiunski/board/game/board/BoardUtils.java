package com.celiunski.board.game.board;

import com.celiunski.board.game.Exception.BoardNodeNotFoundException;
import com.celiunski.board.game.Exception.IncorrectIDException;
import com.celiunski.board.game.utils.Utils;
import com.celiunski.board.game.utils.Vector3;

class BoardUtils {
    static String getAdjacentKAway(String id, Utils.Axis axis, Utils.Move move, int k) throws IncorrectIDException {
        return Utils.getID(getAdjacentKAway(Utils.getVector(id), axis, move, k));
    }

    static Vector3 getAdjacentKAway(Vector3 vector3, Utils.Axis axis, Utils.Move move, int k) {
        switch (axis) {
            case X:
                if(move.equals(Utils.Move.UP)) {
                    vector3.x += k;
                    vector3.z -= k; //SI: z  NO: y
                } else {
                    vector3.x -= k;
                    vector3.z += k;
                }
                break;
            case Y:
                if(move.equals(Utils.Move.UP)) {
                    vector3.y += k;
                    vector3.x -= k; //SI: x  NO: z

                } else {
                    vector3.y -= k;
                    vector3.x += k;
                }
                break;
            case Z:
                if(move.equals(Utils.Move.UP)) {
                    vector3.z += k;
                    vector3.y -= k; //SI: y  NO: x
                } else {
                    vector3.z -= k;
                    vector3.y += k;
                }
        }
        return vector3;
    }

    static boolean isAdjacentKAway(Vector3 vector3a, Vector3 vector3b, int k) {
        return equalsA_absB_equals_K(vector3a.x, vector3b.x, vector3a.z, vector3b.z, k)
                || equalsA_absB_equals_K(vector3a.y, vector3b.y, vector3a.x, vector3b.x, k)
                || equalsA_absB_equals_K(vector3a.z, vector3b.z, vector3a.y, vector3b.y, k);
    }

    static String getMiddleNode(String idA, String idB) throws IncorrectIDException, BoardNodeNotFoundException {
        return Utils.getID(getMiddleNode(Utils.getVector(idA), Utils.getVector(idB)));
    }

    static Vector3 getMiddleNode(Vector3 vector3a, Vector3 vector3b) throws BoardNodeNotFoundException {
        int k = 2, x, y, z;
        if(equalsA_absB_equals_K(vector3a.x, vector3b.x, vector3a.z, vector3b.z, k)) {
            x = vector3a.x;
            z = (vector3a.z + vector3b.z) / 2;
            y = Utils.get3rdAxis(x, z);
        } else if (equalsA_absB_equals_K(vector3a.y, vector3b.y, vector3a.x, vector3b.x, k)) {
            x = (vector3a.x + vector3b.x) / 2;
            y = vector3a.y;
            z = Utils.get3rdAxis(x, y);
        } else if (equalsA_absB_equals_K(vector3a.z, vector3b.z, vector3a.y, vector3b.y, k)) {
            y = (vector3a.y + vector3b.y )/2;
            z = vector3a.z;
            x = Utils.get3rdAxis(y, z);
        } else {
            throw new BoardNodeNotFoundException("No middle node was found");
        }
        return new Vector3(x, y, z);
    }

    private static boolean equalsA_absB_equals_K(int a, int a2, int b, int b2, int k) {
        return a == a2 && Math.abs(b - b2) == k;
    }
}
