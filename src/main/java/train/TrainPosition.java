package train;

public class TrainPosition {
    private Node node;
    private double positionOnNode;

    public TrainPosition(Node node, double positionOnNode) {
        this.node = node;
        this.positionOnNode = positionOnNode;
    }

    public Node getNode() {
        return node;
    }

    public double getPositionOnNode() {
        return positionOnNode;
    }

    public void setPositionOnNode(double positionOnNode) {
        this.positionOnNode = positionOnNode;
    }

}
