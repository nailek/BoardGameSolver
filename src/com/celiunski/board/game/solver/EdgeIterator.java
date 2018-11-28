package com.celiunski.board.game.solver;

import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

public class EdgeIterator {
    private int k;
    public int i, j;

    public int round;
    public boolean axial;

    EdgeDirection dir;
    private Queue<EdgeDirection> queueDir;
    private Queue<EdgeDirection> tmpQueueDir;

    public EdgeIterator(int i, int j, int round, boolean axial) {
        k = 0;
        this.i = i;
        this.j = j;
        this.round = round;
        this.axial = axial;

        queueDir = new LinkedTransferQueue<>();
        if(round == 2) {
            if(axial) setAxialQueueRound2();
            else setCircleQueueRound2();
        }
    }
    public void nextIterator(){
        if(round == 2) {
            if (axial) {
                nextAxialIterator();
            } else nextCircleIterator();
        }
    }
    private void nextAxialIterator(){
        if (k == 0 || k == 1) {j++; nextDir(); k++;}
        else {i++; k=0; nextQueue();}
    }
    private void nextCircleIterator(){
        i++; j++; nextDir();
    }
    private void nextDir() {
        dir = tmpQueueDir.poll();
    }
    private void nextQueue() {
        queueDir.offer(queueDir.poll()); //Puts the first one to the last position
        tmpQueueDir = new LinkedTransferQueue<>(queueDir);
        dir = tmpQueueDir.poll();
    }
    private void setAxialQueueRound2() {
        queueDir.add(dirDUD());
        queueDir.add(dirHor());
        queueDir.add(dirDDU());
        tmpQueueDir = new LinkedTransferQueue<>(queueDir);
        dir = tmpQueueDir.poll();
    }
    private void setCircleQueueRound2() {
        for (int i = 0; i < 2; i++) {
            queueDir.add(dirDDU());
            queueDir.add(dirDUD());
            queueDir.add(dirDUD());
            queueDir.add(dirHor());
            queueDir.add(dirHor());
            queueDir.add(dirDDU());
        }
        tmpQueueDir = new LinkedTransferQueue<>(queueDir);
        dir = tmpQueueDir.poll();
    }

    private EdgeDirection dirHor() {
        return EdgeDirection.HORIZONTAL;
    }
    private EdgeDirection dirDDU() {
        return EdgeDirection.DIAGONALDOWNUP;
    }
    private EdgeDirection dirDUD() {
        return EdgeDirection.DIAGONALUPDOWN;
    }

}
