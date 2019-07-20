package com.celiunski.board.game.board;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.celiunski.board.game.Exception.ExceptionAxisNotFound;
import com.celiunski.board.game.utils.Utils;
import com.celiunski.board.game.utils.Vector3;

public class Board {

    private static Logger LOG = Logger.getGlobal();

    private LinkedHashMap<String, BoardNode> board;
    private List<Pair<BoardNode,BoardNode>> blockedConnections;
    final private int DIAMETER = 3;

    public Board() {
        board = new LinkedHashMap<>();
        blockedConnections = new ArrayList<>();
        initBoardNodes();
        initBoardRules();
    }

    private void initBoardNodes() {
        for (int y = -3; y <= 3; y++) {
            for (int x = -3; x <= 3; x++) {
                newBoardNode(x,y);
            }
        }
    }

    public static int get3rdAxis(int x, int y) {
        return BoardNode.get3rdAxis(x, y);
    }

    public static String createId(int x, int y, int z) {
        return BoardNode.createId(x, y, z);
    }

    public static Vector3 getVectorFromId(String id) {
        String[] parts = id.split(";");
        if(parts.length != 3) {
            //TODO: LOG and throw error!!
            Utils.println("Id not correct");
            return null;
        }
        return new Vector3(parts[0], parts[1], parts[2]);
    }

    public boolean isNodeFilled(int x, int y, int z){
        return getNode(x, y, z).isFilled();
    }

    private BoardNode getNode(int x, int y, int z) {
        return board.get(BoardNode.createId(x, y, z));
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
        int z = BoardNode.get3rdAxis(x,y);
        int min = Math.min(Math.min(x, y),z);
        int max = Math.max(Math.max(x, y),z);
        return max <= DIAMETER && min >= -DIAMETER;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public void printIt() {
        Utils.println("");
        Utils.println("Board game");
        Set<String> keys = getBoardKeys();
        int row = -3;
        for(String key : keys) {
            BoardNode node = board.get(key);
            row = adjustRowStart(row, node);
            if(node.isFilled()) {
                Utils.print("o ");
            } else {
                Utils.print("x ");
            }
        }
        Utils.println("");
    }

    private int adjustRowStart(int row, BoardNode node) {
        if(node.getYAxis() == row) {
            Utils.println("");
            if(Math.abs(row) == 3) {
                Utils.print("   ");
            } else if (Math.abs(row) == 2) {
                Utils.print("  ");
            } else if (Math.abs(row) == 1) {
            Utils.print(" ");
            }
            row++;
        }
        return row;
    }

    private void printBoardHashMap() {
        Utils.println(board);
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    private void initBoardRules() {
        try {
            for(int i = 1; i <=3; i++)
            blockedConnections.add(newBlockedConnectionForAxis(i));
        } catch (ExceptionAxisNotFound e) {
            //Should never happen TODO: Change log package import to Simple Logging Facade for Java (SLF4J)???
            LOG.warning("Trying to add a block connection of a wrong axis, Error: " + e);
        }
    }

    private Pair<BoardNode, BoardNode> newBlockedConnectionForAxis(int axis) throws ExceptionAxisNotFound {
        BoardNode node1;
        BoardNode node2;
        if(axis == 1) {
            node1 = getNode(3,1,2);
            node2 = getNode(3,2,1);
        } else if(axis == 2) {
            node1 = getNode(1,3,2);
            node2 = getNode(2,3,1);
        } else if(axis == 3) {
            node1 = getNode(2,1,3);
            node2 = getNode(1,2,3);
        }
        else {
            throw new ExceptionAxisNotFound();

        }
        return new Pair<>(node1, node2);
    }
}
