package entity.model.control.builder;

import entity.model.control.TransitModel;
import util.Preconditions;

import java.util.List;

public class TransitLineBuilder {
    private final TransitModelBuilder builder;
    private final TransitModel model;

    public TransitLineBuilder(TransitModelBuilder builder, TransitModel model) {
        this.builder = builder;
        this.model = model;
    }

    public TransitLineBuilder station(List<String> names) {
        names.forEach(s -> {
            Preconditions.checkArgument(model.getNode(s));
        });
    }
}
