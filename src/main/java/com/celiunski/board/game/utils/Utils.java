package com.celiunski.board.game.utils;

import java.util.List;
import java.util.Map;

import com.celiunski.board.game.Exception.IncorrectIDException;
import com.sun.tools.javac.util.Pair;

public class Utils {

    private static boolean debug = false;

    public static boolean getDebug() {
        return debug;
    }

    public enum Axis {
        X, Y, Z
    }

    public enum Move {
        UP, DOWN
    }

    public static Axis axis;

    public static Move move;

    /* -------------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------- Prints --------------------------------------------------------- */

    public static <T> void print(T t) {
        System.out.print(t);
    }
    public static <T> void println(T t) {
        System.out.println(""+t);
    }
    public static void printIDs(List<String> ids) {
        printIDs(null, ids);
    }
    public static void printIDs(String message, List<String> ids) {
        if(message != null) {
            print(message+": ");
        }
        for(String id : ids) {
            print(id + "   ");
        }
        println("");
    }
    public static void printMovesList(List<Pair<String,List<String>>> possibleMovesList) {
        print("Moves List: ");
        for(Pair<String,List<String>> possibleMoves : possibleMovesList){
            printMoves(possibleMoves);
        }
        println("");
    }
    public static void printMovesList(String message, List<Pair<String,List<String>>> movesList) {
        print(message );
        printMovesList(movesList);
    }
    public static void printMoves(Pair<String,List<String>> moves) {
        if(moves.fst.isEmpty()) {
            println(" Moves for: "+ moves.fst);
        } else {
            print("Moves for: " + moves.fst +"  [  ");
            for (String idPossibleMove : moves.snd) {
                print(idPossibleMove + " ");
            }
            //println("");
            print("] ");
        }
    }
    public static void printMovesHash(String message, Map<String,List<String>> movesList) {
        if(movesList.isEmpty()) {
            return;
        }
        print(message );
        for (String key : movesList.keySet()) {
            printMoves(new Pair<>(key, movesList.get(key)));
        }
        println("");
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------- Debugs --------------------------------------------------------- */
    public static <T> void debug(T t) {
        if (debug) {
            print(t);
        }
    }
    public static <T> void debugln(T t) {
        if (debug) {
            println(t);
        }
    }
    public static void debugIDs(List<String> ids) {
        if (debug) {
            printIDs(ids);
        }
    }
    public static void debugIDs(String message, List<String> ids) {
        if (debug) {
            printIDs(message, ids);
        }
    }
    public static void debugMovesList(List<Pair<String,List<String>>> moveList) {
        if (debug) {
            printMovesList(moveList);
        }
    }
    public static void debugMovesList(String message, List<Pair<String,List<String>>> movesList) {
        if (debug) {
            printMovesList(message, movesList);
        }
    }
    public static void debugMoves(Pair<String,List<String>> moves) {
        if (debug) {
            printMoves(moves);
        }
    }
    public static void debugMovesHash(String message, Map<String,List<String>> movesList) {
        if (debug) {
            printMovesHash(message, movesList);
        }
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static int get3rdAxis(int x, int y) {
        return -x-y;
    }

    public static String getID(int x, int y, int z) {
        return ""+x+";"+y+";"+z;
    }

    public static String getID(Vector3 vector3) {
        if (vector3 == null) {
            return "";
        }
        return ""+vector3.x+";"+vector3.y+";"+vector3.z;
    }

    public static Vector3 getVector(String id) throws IncorrectIDException {
        if (id == null) {
            throw new IncorrectIDException("ID can't be null. ");
        }
        String[] parts = id.split(";");
        if(parts.length != 3) {
            throw new IncorrectIDException("ID doesn't follow the standard id form.");
        }
        return new Vector3(parts[0], parts[1], parts[2]);
    }
}