package model.persistence;

import model.control.TransitModel;

public interface ModelDataStore {
    TransitModel readModel();
    void writeModel(TransitModel model);
}
