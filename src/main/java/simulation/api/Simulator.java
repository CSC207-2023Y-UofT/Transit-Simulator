package simulation.api;

import entity.model.control.TransitModel;

public interface Simulator {
    void onStart(TransitModel model);
    void tick(TransitModel model, double deltaTime);
}
