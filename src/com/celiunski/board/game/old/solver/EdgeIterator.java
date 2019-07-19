package com.celiunski.board.game.old.solver;

import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

public class EdgeIterator {
    private int k;
    private boolean vertex;
    public int i, j;

    public int round;
    public boolean axial;

    EdgeDirection dir;
    private Queue<EdgeDirection> queueDir; //TODO: Make my own Queues that add Directions easier.
    private Queue<EdgeDirection> tempQueueDir;

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
        else if(round == 3) {
            if(axial) setAxialQueueRound3();
            else setCircleQueueRound3();
        }
    }
    public void nextIterator(){
        if(round == 2) {
            if (axial) {
                nextAxialIterator2();
            } else nextCircleIterator2();
        } else if (round == 3) {
            if (axial) {
                nextAxialIterator3();
            } else nextCircleIterator3();
        }
    }
    private void nextAxialIterator2(){
        if (k == 0 || k == 1) {j++; nextDir(); k++;}
        else {i++; k=0; nextQueue();}
    }
    private void nextCircleIterator2(){
        i++; j++; nextDir();
    }
    private void nextAxialIterator3() {
        if(vertex && k == 2) {
            vertex = false;
            k = 0;
            i++;
            nextQueue();
        }
        else if (!vertex && k == 1) {
            vertex = true;
            k = 0;
            nextQueue();
        }
        else if (k == 0 || k == 1) {j++; nextDir(); k++;}
    }
    private void nextCircleIterator3(){
        //TODO: Not implemented.
    }
    private void nextDir() {
        dir = tempQueueDir.poll();
    }
    private void nextQueue() {
        queueDir.offer(queueDir.poll()); //Puts the first one to the last position
        tempQueueDir = new LinkedTransferQueue<>(queueDir);
        dir = tempQueueDir.poll();
    }
    private void setAxialQueueRound2() {
        queueDir.add(dirDUD());
        queueDir.add(dirH());
        queueDir.add(dirDDU());
        tempQueueDir = new LinkedTransferQueue<>(queueDir);
        dir = tempQueueDir.poll();
    }
    private void setCircleQueueRound2() {
        for (int i = 0; i < 2; i++) {
            queueDir.add(dirDDU());
            queueDir.add(dirDUD());
            queueDir.add(dirDUD());
            queueDir.add(dirH());
            queueDir.add(dirH());
            queueDir.add(dirDDU());
        }
        tempQueueDir = new LinkedTransferQueue<>(queueDir);
        dir = tempQueueDir.poll();
    }
    private void setAxialQueueRound3() {
        Queue<EdgeDirection> orderQueueDir = new LinkedTransferQueue<>();
        orderQueueDir.add(dirDUD());
        orderQueueDir.add(dirH());
        orderQueueDir.add(dirDDU());

        int i1 = 2; //Starts from specific point...
        boolean tempvertex = false;
        for(int i = 0; i < 5; i++) {
            tempQueueDir = new LinkedTransferQueue<>(orderQueueDir);
            queueDir.add(tempQueueDir.poll());
            queueDir.add(tempQueueDir.poll());
            if(tempvertex) {
                queueDir.add(tempQueueDir.poll());
            }
            orderQueueDir.offer(orderQueueDir.poll());
        }
        //Add the last point
        tempQueueDir = new LinkedTransferQueue<>(orderQueueDir);
        queueDir.add(tempQueueDir.poll());
        queueDir.add(tempQueueDir.poll());

        k=0;
        vertex=false; //Starts from specific point...
        tempQueueDir = new LinkedTransferQueue<>(queueDir);
        dir = tempQueueDir.poll();
    }
    private void setCircleQueueRound3() {
        for (int i = 0; i < 2; i++) {
            queueDir.add(dirDDU());
            queueDir.add(dirDUD());
            queueDir.add(dirDUD());
            queueDir.add(dirH());
            queueDir.add(dirH());
            queueDir.add(dirDDU());
        }
        tempQueueDir = new LinkedTransferQueue<>(queueDir);
        dir = tempQueueDir.poll();
    }

    private EdgeDirection dirH() {
        return EdgeDirection.HORIZONTAL;
    }
    private EdgeDirection dirDDU() {
        return EdgeDirection.DIAGONALDOWNUP;
    }
    private EdgeDirection dirDUD() {
        return EdgeDirection.DIAGONALUPDOWN;
    }

}
