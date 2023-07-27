package model.train;

import model.node.Node;

public class TrainPosition {
    private Node node;
    private int lineNumber;
    private double positionOnNode;

    public TrainPosition(Node node, int lineNumber, double positionOnNode) {
        this.node = node;
        this.lineNumber = lineNumber;
        this.positionOnNode = positionOnNode;
    }

    public Node getNode() {
        return node;
    }

    public double getPositionOnNode() {
        return positionOnNode;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setPositionOnNode(double positionOnNode) {
        this.positionOnNode = positionOnNode;
    }

}
