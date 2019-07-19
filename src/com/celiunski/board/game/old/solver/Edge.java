package com.celiunski.board.game.old.solver;

public class Edge {
    private Node node1;
    private Node node2;
    private EdgeDirection direction;

    public Edge(Node node1, Node node2, EdgeDirection direction){
        this.node1 = node1;
        this.node2 = node2;
        this.direction = direction;
        node1.addEdge(this, direction);
        node2.addEdge(this, direction);
    }

    public Node first(){
        return node1;
    }

    public Node second(){
        return node2;
    }

    public EdgeDirection getDirection(){
        return direction;
    }

    public Node other(Node node){
        if (node1 == node) return node2;
        else if (node2 == node) return node1;
        else return null; //Todo: throw exception
    }

    public void printIt(){
        System.out.print(node1.getId()+"-"+node2.getId());
        String print = null;
        switch (direction) {
            case HORIZONTAL: {
                print = "H";
                break;
            }
            case DIAGONALDOWNUP: {
                print = "DDU";
                break;
            }
            case DIAGONALUPDOWN: {
                print = "DUD";
                break;
            }
        }
        System.out.print(print);
    }
}
