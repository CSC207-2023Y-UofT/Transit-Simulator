package entity.model.control.builder;

import entity.model.control.TransitModel;
import util.Preconditions;

import java.util.HashSet;
import java.util.Set;

public class TransitModelBuilder {
    private final TransitModel model = new TransitModel();
    private final Set<Integer> createdLines = new HashSet<>();

    public TransitModelBuilder line(int lineNumber) {
        Preconditions.checkArgument(!createdLines.contains(lineNumber), "Line already created");
    }
}
