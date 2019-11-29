package com.celiunski.board.game.board;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.celiunski.board.game.Exception.IncorrectIDException;
import com.celiunski.board.game.utils.Utils;
import com.celiunski.board.game.utils.Vector3;
import com.sun.istack.internal.Nullable;
import com.sun.tools.javac.util.Pair;

public class Board {

    private static Logger LOG = Logger.getGlobal();

    private LinkedHashMap<String, BoardNode> board;
    final private int DIAMETER = 3;

    private Set<String> lastMoveNodes = new HashSet<>();

    public Board() {
        board = new LinkedHashMap<>();
        initBoardNodes();
    }

    public Board(Board board) {
        this.board = new LinkedHashMap<>();
        Set<String> keySet = board.board.keySet();
        for (String key : keySet) {
            BoardNode node = new BoardNode(board.board.get(key));
            this.board.put(key, node);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Board) {
            Board boardObj = (Board) object;
            Set<String> thisKeySet = this.board.keySet();
            Set<String> objKeySet = boardObj.board.keySet();
            if(thisKeySet.size() != objKeySet.size()) {
                return false;
            }
            for(String key : objKeySet) {
                BoardNode thisNode = this.board.get(key);
                BoardNode objJode = boardObj.board.get(key);
                if(thisNode == null) {
                    return false;
                } else if (thisNode.isFilled() != objJode.isFilled()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isNodeFilled(int x, int y, int z) {
        return getNode(x, y, z).isFilled();
    }

    private void initBoardNodes() {
        for (int y = -DIAMETER; y <= DIAMETER; y++) {
            for (int x = -DIAMETER; x <= DIAMETER; x++) {
                boolean isFilled = x != -1 || y != -2; //Empty node at a proper position.
                newBoardNode(x, y, isFilled);
            }
        }
    }

    private BoardNode getNode(int x, int y, int z) {
        return board.get(Utils.getID(x, y, z));
    }

    private BoardNode getNode(Vector3 vector3) {
        return board.get(Utils.getID(vector3.x, vector3.y, vector3.z));
    }

    private BoardNode getNode(String id) {
        return board.get(id);
    }

    private Set<String> getBoardKeys() {
        return board.keySet();
    }

    private void newBoardNode(int x, int y, boolean isFilled) {
        if (isWithinDiameter(x, y)) {
            BoardNode node = new BoardNode(x, y, isFilled);
            board.put(node.getId(), node);
        }
        //In this case, we are not logging the nodes that are not within the diameter. Not needed.
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
    /* ----------------------------------------- General Moves ------------------------------------------------------ */

    public boolean movePice(String moveFrom, String moveTo) throws IncorrectIDException {
        boolean allowedMove = isAllowedMove(moveFrom, moveTo);
        if(allowedMove) {
            String middleNode = BoardUtils.getMiddleNode(moveFrom, moveTo);
            getNode(moveFrom).setFilled(false);
            getNode(middleNode).setFilled(false);
            getNode(moveTo).setFilled(true);
            lastMoveNodes = new HashSet<>();
            lastMoveNodes.add(moveFrom);
            lastMoveNodes.add(middleNode);
            lastMoveNodes.add(moveTo);
        }
        return allowedMove;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* ----------------------------------------- Solver Mode -------------------------------------------------------- */

    public boolean isFinished() {
        return getEmptyNodes().size() == board.keySet().size()-1;// && board.get(Utils.getID(0,0,0)).isFilled();
    }

    public List<Pair<String,List<String>>> getAvailableMoves() {
        List<Pair<String,List<String>>> availableMoves = new ArrayList<>();
        List<String> emptyNodesID = getEmptyNodes();
        for (String id : emptyNodesID) {

            List<String> possiblePiecesToMove = getPossiblePiecesToMoveHere(id);
            if (!possiblePiecesToMove.isEmpty()) {
                availableMoves.add(new Pair<>(id, possiblePiecesToMove));
            }
        }
        return availableMoves;
    }

    public List<String> getEmptyNodes() {
        List<String> emptyNodes = new ArrayList<>();
        for (String key : getBoardKeys()) {
            if (!getNode(key).isFilled()){
                emptyNodes.add(key);
            }
        }
        return emptyNodes;
    }

    public List<String> getPossiblePiecesToMoveHere(String id) {
        List<String> adjacents2Away = new ArrayList<>();
        try {
            adjacents2Away.add(getAdjacent2Away(id, Utils.Axis.X, Utils.Move.UP));
            adjacents2Away.add(getAdjacent2Away(id, Utils.Axis.X, Utils.Move.DOWN));
            adjacents2Away.add(getAdjacent2Away(id, Utils.Axis.Y, Utils.Move.UP));
            adjacents2Away.add(getAdjacent2Away(id, Utils.Axis.Y, Utils.Move.DOWN));
            adjacents2Away.add(getAdjacent2Away(id, Utils.Axis.Z, Utils.Move.UP));
            adjacents2Away.add(getAdjacent2Away(id, Utils.Axis.Z, Utils.Move.DOWN));

            adjacents2Away.removeAll(Collections.singletonList(null));
            for (int i = adjacents2Away.size() - 1; i >= 0; i--) {
                if (!isAllowedMove(adjacents2Away.get(i), id)) {
                    adjacents2Away.remove(i);
                }
            }
            //TODO: Order by distance to center (closer to farther)
        } catch (IncorrectIDException e){
            //Ignoring it
        }
        return adjacents2Away;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* ----------------------------------------- Player Mode -------------------------------------------------------- */

    public List<String> getAdjacents(String id) {
        List<String> adjacents = new ArrayList<>();
        try {
            adjacents.add(getAdjacentNodeId(id, Utils.Axis.X, Utils.Move.UP));
            adjacents.add(getAdjacentNodeId(id, Utils.Axis.X, Utils.Move.DOWN));
            adjacents.add(getAdjacentNodeId(id, Utils.Axis.Y, Utils.Move.UP));
            adjacents.add(getAdjacentNodeId(id, Utils.Axis.Y, Utils.Move.DOWN));
            adjacents.add(getAdjacentNodeId(id, Utils.Axis.Z, Utils.Move.UP));
            adjacents.add(getAdjacentNodeId(id, Utils.Axis.Z, Utils.Move.DOWN));
            adjacents.removeAll(Collections.singletonList(null));
        } catch (IncorrectIDException e) {
            //Ignoring it
        }
        return adjacents;
    }

    public List<String> getEmpty2PositionsAway(String id) {
        List<String> adjacents = new ArrayList<>();
        try {
            adjacents.add(getAdjacent2Away(id, Utils.Axis.X, Utils.Move.UP));
            adjacents.add(getAdjacent2Away(id, Utils.Axis.X, Utils.Move.DOWN));
            adjacents.add(getAdjacent2Away(id, Utils.Axis.Y, Utils.Move.UP));
            adjacents.add(getAdjacent2Away(id, Utils.Axis.Y, Utils.Move.DOWN));
            adjacents.add(getAdjacent2Away(id, Utils.Axis.Z, Utils.Move.UP));
            adjacents.add(getAdjacent2Away(id, Utils.Axis.Z, Utils.Move.DOWN));
            adjacents.removeAll(Collections.singletonList(null));
            for (int i = adjacents.size() - 1; i >= 0; i--) {
                if (getNode(adjacents.get(i)).isFilled()) {
                    adjacents.remove(i);
                }
            }
        } catch (IncorrectIDException e) {
            //Ignoring it
        }
        return adjacents;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public String getAdjacentNodeId(String id, Utils.Axis axis, Utils.Move move) throws IncorrectIDException {
        String adjacentId = BoardUtils.getAdjacentKAway(id, axis, move, 1);
        if (board.get(adjacentId) == null) {
            return null;
        }
        return adjacentId;
    }
    public String getAdjacent2Away(String id, Utils.Axis axis, Utils.Move move) throws IncorrectIDException {
        String adjacentId = BoardUtils.getAdjacentKAway(id, axis, move, 2);
        if (board.get(adjacentId) == null) {
            return null;
        }
        return adjacentId;
    }

    public boolean isAdjacent(String moveFromID, String moveToID) throws IncorrectIDException {
        return BoardUtils.isAdjacentKAway(Utils.getVector(moveFromID), Utils.getVector(moveToID), 1);
    }

    private boolean isAllowedMove(String idA,  String idB) throws IncorrectIDException {
        return isAllowedMove(Utils.getVector(idA), Utils.getVector(idB));
    }
    private boolean isAllowedMove(Vector3 moveFrom, Vector3 moveTo) {
        boolean allowed = BoardUtils.isAdjacentKAway(moveFrom, moveTo, 2);
        Vector3 middleNode = BoardUtils.getMiddleNode(moveFrom, moveTo);
        if (middleNode == null) {
            //TODO: Log, error, not 2away Adjacents
            return false;
        }
        //allowed &= !isBlockedPass(moveFrom, middleNode);
        //allowed &= !isBlockedPass(moveTo, middleNode);
        allowed &= getNode(moveFrom).isFilled();
        allowed &= getNode(middleNode).isFilled();
        allowed &= !getNode(moveTo).isFilled();

        return allowed;
    }

    public boolean isBlockedPass(String idA, String idB) throws IncorrectIDException {
        return isBlockedPass(Utils.getVector(idA), Utils.getVector(idB));
    }

    private boolean isBlockedPass(Vector3 vector3a, Vector3 vector3b) {
        boolean blocked = false;

        if (vector3a.x == -3 && vector3b.x == -3 &&
                (vector3a.y == 1 && vector3b.y == 2 || vector3a.y == 2 && vector3b.y == 1)) {
            blocked = true;
        } else if (vector3a.x == +3 && vector3b.x == +3 &&
                (vector3a.y == -1 && vector3b.y == -2 || vector3a.y == 2 && vector3b.y == 1)) {
            blocked = true;
        }
        else if(vector3a.y == -3 && vector3b.y == -3 &&
                (vector3a.x == 1 && vector3b.x == 2 || vector3a.x == 2 && vector3b.x == 1)) {
            blocked = true;
        }
        else if(vector3a.y == 3 && vector3b.y == 3 &&
                (vector3a.x == -1 && vector3b.x == -2 || vector3a.x == -2 && vector3b.x == -1)) {
            blocked = true;
        }
        else if(vector3a.z == -3 && vector3b.z == -3 &&
                (vector3a.x == 1 && vector3b.x == 2 || vector3a.x == 2 && vector3b.x == 1)) {
            blocked = true;
        }
        else if(vector3a.z == 3 && vector3b.z == 3 &&
                (vector3a.x == -1 && vector3b.x == -2 || vector3a.x == -2 && vector3b.x == -1)) {
            blocked = true;
        }
        return blocked;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public String viewIt() {
        StringBuilder view = new StringBuilder("Board game ");
        Set<String> keys = getBoardKeys();
        int row = -3;
        for (String key : keys) {
            BoardNode node = board.get(key);
            if (node.getY() == row) {
                view.append("| ");
                row++;
            }
            if (node.isFilled()) {
                view.append("o ");
            } else {
                view.append("x ");
            }
        }
        view.append("|");
        return view.toString();
    }

    public void printIt(String name) {
        Utils.print(name+": ");
        printIt();
    }

    public void printIt() {
        Utils.println("Board game");
        Set<String> keys = getBoardKeys();
        int row = -DIAMETER;
        for (String key : keys) {
            BoardNode node = board.get(key);
            row = adjustRowStart(row, node, " ");
            if (node.isFilled()) {
                if(lastMoveNodes.contains(node.getId())) {
                    Utils.print("O ");
                } else {
                    Utils.print("o ");
                }
            } else {
                if(lastMoveNodes.contains(node.getId())) {
                    Utils.print("X ");
                } else {
                    Utils.print("x ");
                }
            }
        }
        Utils.println("");
    }

    public void debugIt(String name) {
        if (Utils.getDebug()) {
            printIt(name);
        }
    }

    public void debugIt() {
        if (Utils.getDebug()) {
            printIt();
        }
    }

    public void printPossibleMoves(String name, List<Pair<String, List<String>>> availableMovesList, @Nullable Map<String, List<String>> removedMovesList) {
        Utils.print(name+": ");
        printPossibleMoves(availableMovesList, removedMovesList);
    }

    public void printPossibleMoves(List<Pair<String, List<String>>> availableMovesList, @Nullable Map<String, List<String>> removedMovesList) {
        Set<String> piecesToMove = new HashSet<>();
        for(Pair<String, List<String>> availableMoves : availableMovesList) {
                piecesToMove.add(availableMoves.fst);
        }
        Set<String> removedMoves = new HashSet<>();
        if (removedMovesList != null) {
            removedMoves.addAll(removedMovesList.keySet());
        }

        Utils.println("Board game");
        Set<String> keys = getBoardKeys();
        int row = -DIAMETER;
        for (String key : keys) {
            BoardNode node = board.get(key);
            row = adjustRowStart(row, node, " ");
            if (node.isFilled()) {
                if(lastMoveNodes.contains(node.getId())) {
                    Utils.print("O ");
                } else {
                    Utils.print("o ");
                }
            } else {
                if (lastMoveNodes.contains(node.getId())) {
                    Utils.print("X ");
                } else if (piecesToMove.contains(key)) {
                    Utils.print("% ");
                } else if (removedMoves.contains(key)) {
                    Utils.print("/ ");
                } else {
                    Utils.print("x ");
                }
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
