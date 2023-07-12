
/*
 *
 *
 *
 *
 */


import org.jetbrains.annotations.NotNull;

public class Station extends Node {
    private int boarding = 0;  // passengers waiting for the next train




    public Station () {
        super();
    }

    public Station (@NotNull Node previousNode) {
        super(previousNode);
    }

    public Station (@NotNull Node previousNode, @NotNull Node nextNode) {
        super(previousNode, nextNode);
    }
}
