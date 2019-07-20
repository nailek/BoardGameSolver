package com.celiunski.board.game.board;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Logger;

import com.celiunski.board.game.utils.Utils;
import com.celiunski.board.game.utils.Vector3;

public class Board {

    private static Logger LOG = Logger.getGlobal();

    private LinkedHashMap<String, BoardNode> board;
    final private int DIAMETER = 3;

    public Board() {
        board = new LinkedHashMap<>();
        initBoardNodes();
    }

    public boolean isNodeFilled(int x, int y, int z) {
        return getNode(x, y, z).isFilled();
    }

    private void initBoardNodes() {
        for (int y = -3; y <= 3; y++) {
            for (int x = -3; x <= 3; x++) {
                newBoardNode(x, y);
            }
        }
    }

    private BoardNode getNode(int x, int y, int z) {
        return board.get(Utils.createId(x, y, z));
    }

    private Set<String> getBoardKeys() {
        return board.keySet();
    }

    private void newBoardNode(int x, int y) {
        if (isWithinDiameter(x, y)) {
            BoardNode node = new BoardNode(x, y);
            board.put(node.getId(), node);
        }
        //We are not logging the nodes not within the diameter in this case. Not needed.
    }

    private boolean isWithinDiameter(int x, int y) {
        int z = Utils.get3rdAxis(x, y);
        int min = Math.min(Math.min(x, y), z);
        int max = Math.max(Math.max(x, y), z);
        return max <= DIAMETER && min >= -DIAMETER;
    }

    private boolean isWithinDiameter(Vector3 vector3) {
        if (!isCorrectPosition(vector3)) {
            return false;
        }
        return isWithinDiameter(vector3.x, vector3.y);
    }

    private boolean isCorrectPosition(Vector3 vector3) {
        return vector3.x == -vector3.y-vector3.z;
    }

    /* -------------------------------------------------------------------------------------------------------------- */


    public static boolean isAdjacent(String ida, String idb) throws IllegalArgumentException {
        return isAdjacent(Utils.getVectorFromId(ida), Utils.getVectorFromId(idb));
    }

    private static boolean isAdjacent(Vector3 vector3a, Vector3 vector3b) {
        return vector3a.x == vector3b.x
                && vector3a.y == vector3b.y
                && Math.abs(vector3a.z - vector3b.z) == 1
                || vector3a.y == vector3b.y
                && vector3a.z == vector3b.z
                && Math.abs(vector3a.x - vector3b.x) == 1
                || vector3a.x == vector3b.x
                && vector3a.z == vector3b.z
                && Math.abs(vector3a.y - vector3b.y) == 1;
    }

    public static boolean isAllowedMove(String ida, String idb) {
        return isAllowedMove(Utils.getVectorFromId(ida), Utils.getVectorFromId(idb));
    }

    private static boolean isAllowedMove(Vector3 vector3a, Vector3 vector3b) {
        boolean allowed = true;
        if (vector3a.x == 3 && vector3b.x == 3 &&
                (vector3a.y == 1 && vector3b.z == 2 || vector3a.z == 2 && vector3b.y == 2)) {
            allowed = false;
        }
        else if(vector3a.y == 3 && vector3b.y == 3 &&
                (vector3a.x == 1 && vector3b.z == 2 || vector3a.z == 2 && vector3b.x == 2)) {
            allowed = false;
        }
        else if(vector3a.z == 3 && vector3b.z == 3 &&
                (vector3a.x == 1 && vector3b.y == 2 || vector3a.y == 2 && vector3b.y == 2)) {
            allowed = false;
        }
        return allowed;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public void printIt() {
        Utils.println("");
        Utils.println("Board game");
        Set<String> keys = getBoardKeys();
        int row = -3;
        for (String key : keys) {
            BoardNode node = board.get(key);
            row = adjustRowStart(row, node, " ");
            if (node.isFilled()) {
                Utils.print("o ");
            } else {
                Utils.print("x ");
            }
        }
        Utils.println("");
    }

    private int adjustRowStart(int row, BoardNode node, String spacing) {
        if (node.getY() == row) {
            Utils.println("");
            if (!spacing.equals(" ")) {
                Utils.println("");
            }
            if (Math.abs(row) == 3) {
                Utils.print(spacing + spacing + spacing);
            } else if (Math.abs(row) == 2) {
                Utils.print(spacing + spacing);
            } else if (Math.abs(row) == 1) {
                Utils.print(spacing);
            }
            row++;
        }
        return row;
    }

    public void printBoardHashMap() {
        Utils.println("");
        Utils.println("Board positions");
        Set<String> keys = getBoardKeys();
        int row = -3;
        for (String key : keys) {
            BoardNode node = board.get(key);
            row = adjustRowStart(row, node, "    ");
            Utils.print(node.getId() + "  ");
        }
        Utils.println("");
    }
}
