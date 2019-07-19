package com.celiunski.board.game.old.solver;

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
        addThirdRound();
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

        EdgeIterator it = new EdgeIterator(1, 7, 2, true);
        for(int i = 0; i < 18; ++i) { //6 nodes, 3 edges per node.
            if(i == 17) edges.add(createEdge(it.i, 7, it.dir)); //Closing the cicle.
            else edges.add(createEdge(it.i, it.j, it.dir));
            it.nextIterator();
        }
        it = new EdgeIterator(7, 8, 2, false);
        for(int i = 0; i < 12; ++i) {
            if(i == 11) edges.add(createEdge(it.i, 7, it.dir));
            else edges.add(createEdge(it.i, it.j, it.dir));
            it.nextIterator();
        }
    }

    private void addThirdRound() {
        addNodes(18);
        EdgeIterator it = new EdgeIterator(7, 19, 3, true);
        for(int i = 0; i < 6*6; ++i) { //6 nodes, 3 edges per node.
            if(i == 6*6-1) edges.add(createEdge(it.i, 19, it.dir)); //Closing the cicle.
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
