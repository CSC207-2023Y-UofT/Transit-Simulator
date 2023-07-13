
/*
 *
 *
 *
 *
 */


import org.jetbrains.annotations.NotNull;  // This was automatically suggested by IntelliJ, idk what it does

public class Station extends Node {
    private int boarding = 0;  // passengers waiting for the next train

    public String name;


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
