package com.celiunski.board.game.solver;

import java.util.*;
import java.util.concurrent.LinkedTransferQueue;


public class Tauler {
    private Map<Integer,Node> nodes;
    private Integer countNodes;
    private List<Edge> edges;

    public Tauler() {
        nodes = new HashMap<>();
        edges = new ArrayList<>();
        countNodes = 0;
        initTauler();
    }

    private void initTauler() {
        addCenterAndFirstRound();
        addSecondRound();
    }

    private void addCenterAndFirstRound() {
        //TODO: Delete hardcoded variables
        addNodes(1); //CentralNode key = 0;
        addNodes(6);
        int keyFirstNode = 0;
        Queue<EdgeDirection> queueDir = new LinkedTransferQueue<>();
        queueDir.add(EdgeDirection.DIAGONALUPDOWN);
        queueDir.add(EdgeDirection.HORIZONTAL);
        queueDir.add(EdgeDirection.DIAGONALDOWNUP);
        queueDir.add(EdgeDirection.DIAGONALUPDOWN);
        queueDir.add(EdgeDirection.HORIZONTAL);
        queueDir.add(EdgeDirection.DIAGONALDOWNUP);
        for (int i = 1; i < 7; ++i) {
            if(i%3 == 1) edges.add(createEdge(keyFirstNode,i, EdgeDirection.HORIZONTAL));
            else if(i%3 == 2) edges.add(createEdge(keyFirstNode,i, EdgeDirection.DIAGONALDOWNUP));
            else if(i%3 == 0) edges.add(createEdge(keyFirstNode,i, EdgeDirection.DIAGONALUPDOWN));
        }

        for (int i = 1; i < 7; ++i) {
            if(i == 6) edges.add(createEdge(i,1,queueDir.peek()));
            else edges.add(createEdge(i,i+1,queueDir.peek()));
            queueDir.offer(queueDir.poll());
        }
        /* Casualment...
        edges.add(createEdge(keyFirstNode,1, EdgeDirection.HORIZONTAL));
        edges.add(createEdge(keyFirstNode,2, EdgeDirection.DIAGONALDOWNUP));
        edges.add(createEdge(keyFirstNode,3, EdgeDirection.DIAGONALUPDOWN));
        edges.add(createEdge(keyFirstNode,4, EdgeDirection.HORIZONTAL));
        edges.add(createEdge(keyFirstNode,5, EdgeDirection.DIAGONALDOWNUP));
        edges.add(createEdge(keyFirstNode,6, EdgeDirection.DIAGONALUPDOWN));
        */
    }

    private void addSecondRound(){//ToDo: Separar en diferents tipus d'iteradors Axial(radial), Circular, round 1, 2 , 3,...
        addNodes(12);
        //TODO: Delete hardcoded variables
        class EdgeIterator {
            private int k;
            public int i, j;

            EdgeDirection dir;
            private int indexDir;
            private Queue<EdgeDirection> axialQueueDir;
            private Queue<EdgeDirection> circleQueueDir;
            private Queue<EdgeDirection> tmpQueueDir;
            private boolean axial;

            public EdgeIterator(int i, int j) {
                this.i = i;
                this.j = j;
                axialQueueDir = new LinkedTransferQueue<>();
                circleQueueDir = new LinkedTransferQueue<>();
                axialQueue();
                circleQueue();
                k = 0;
            }
            public void nextAxialIterator(){
                if (k == 0 || k == 1) {j++; nextDir(); k++;}
                else {i++; k=0; nextQueue();}
            }
            public void nextCircleIterator(){
                i++; j++; nextDir();
            }
            private void nextDir() {
                dir = tmpQueueDir.poll();
            }
            private void nextQueue() {
                axialQueueDir.offer(axialQueueDir.poll()); //Puts the first one to the last position
                tmpQueueDir = new LinkedTransferQueue<>(axialQueueDir);
                dir = tmpQueueDir.poll();
            }
            private void axialQueue() {
                axialQueueDir.add(EdgeDirection.DIAGONALUPDOWN);
                axialQueueDir.add(EdgeDirection.HORIZONTAL);
                axialQueueDir.add(EdgeDirection.DIAGONALDOWNUP);
                tmpQueueDir = new LinkedTransferQueue<>(axialQueueDir);
                dir = tmpQueueDir.poll();
            }
            private void circleQueue() {
                circleQueueDir.add(EdgeDirection.DIAGONALDOWNUP);
                circleQueueDir.add(EdgeDirection.DIAGONALUPDOWN);
                circleQueueDir.add(EdgeDirection.DIAGONALUPDOWN);
                circleQueueDir.add(EdgeDirection.HORIZONTAL);
                circleQueueDir.add(EdgeDirection.HORIZONTAL);
                circleQueueDir.add(EdgeDirection.DIAGONALDOWNUP);
                tmpQueueDir = new LinkedTransferQueue<>(circleQueueDir);
                circleQueueDir.addAll(tmpQueueDir);
                tmpQueueDir = new LinkedTransferQueue<>(circleQueueDir);
                dir = tmpQueueDir.poll();
            }
        }
        EdgeIterator it = new EdgeIterator(1,7);
        for(int i = 0; i < 18; ++i) { //6 nodes, 3 edges per node.
            if(i == 17) edges.add(createEdge(it.i, 7, it.dir)); //Closing the cicle.
            else edges.add(createEdge(it.i, it.j, it.dir));
            it.nextAxialIterator();
        }
        it = new EdgeIterator(7,8);
        for(int i = 0; i < 12; ++i) {
            if(i == 11) edges.add(createEdge(it.i, 7, it.dir));
            else edges.add(createEdge(it.i, it.j, it.dir));
            it.nextCircleIterator();
        }
    }

    private void addThirdRound(){ //TODO: Still to do
        addNodes(18);
        //TODO: Delete hardcoded variables
        class EdgeIterator {
            private int k;
            public int i, j;

            EdgeDirection dir;
            private int indexDir;
            private Queue<EdgeDirection> queueDir;
            private Queue<EdgeDirection> tmpQueueDir;

            public EdgeIterator(int i, int j) {
                this.i = i;
                this.j = j;
                queueDir = new LinkedTransferQueue<>();
                queueDir.add(EdgeDirection.DIAGONALUPDOWN);
                queueDir.add(EdgeDirection.HORIZONTAL);
                queueDir.add(EdgeDirection.DIAGONALDOWNUP);
                tmpQueueDir = new LinkedTransferQueue<>(queueDir);
                dir = tmpQueueDir.poll();
                k = 0;
            }
            public void nextIterator(){
                if (k == 0 || k == 1) {j++; nextDir(); k++;}
                else {i++; k=0; nextQueue();}
            }
            private void nextDir() {
                dir = tmpQueueDir.poll();
            }
            private void nextQueue() {
                queueDir.offer(queueDir.poll()); //Puts the first one to the last position
                tmpQueueDir = new LinkedTransferQueue<>(queueDir);
                dir = tmpQueueDir.poll();
            }
        }
        EdgeIterator it = new EdgeIterator(1,7);
        for(int i = 0; i < 18; ++i) { //6 nodes, 3 edges per node.
            if(i == 17) edges.add(createEdge(it.i, 7, it.dir)); //Closing the cicle.
            else edges.add(createEdge(it.i, it.j, it.dir));
            it.nextIterator();
        }
    }

    private Edge createEdge(int keyNode1, int keyNode2, EdgeDirection dir){
        return new Edge(nodes.get(keyNode1),nodes.get(keyNode2), dir);
    }

    private void addNodes(int k) {
        for (int i = 0; i < k; ++i) {
            nodes.put(countNodes, new Node(countNodes));
            ++countNodes;
        }
    }

    public void printIt(){
        System.out.print("Nodes: ");
        for (int i = 0; i < countNodes; ++i) {
            System.out.print(nodes.get(i).getId()+" ");
        }
        System.out.print("\n");

        System.out.print("Edges: ");
        for (int i = 0; i < edges.size(); ++i) {
            edges.get(i).printIt();
            if(i == 5 || i == 11 || i == 29) System.out.println(); //Separation of rounds, axial and circles
            else System.out.print(" ");
        }
    }
}
