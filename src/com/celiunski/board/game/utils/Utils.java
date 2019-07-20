package com.celiunski.board.game.utils;

public class Utils {

    public enum Axis {
        X, Y, Z
    }

    public enum Move {
        UP, DOWN
    }

    public static Axis axis;

    public static Move move;

    public static <T> void print(T t) {
        System.out.print(t);
    }
    public static <T> void println(T t) {
        System.out.println(""+t);
    }

    public static int get3rdAxis(int x, int y) {
        return -x-y;
    }

    public static String createId(int x, int y, int z) {
        return ""+x+";"+y+";"+z;
    }

    public static String createId(Vector3 vector3) {
        return ""+vector3.x+";"+vector3.y+";"+vector3.z;
    }

    public static Vector3 getVectorFromId(String id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Argument can't be null. ");
        }
        String[] parts = id.split(";");
        if(parts.length != 3) {
            throw new IllegalArgumentException("Argument doesn't follow the standard id form.");
        }
        return new Vector3(parts[0], parts[1], parts[2]);
    }
}
