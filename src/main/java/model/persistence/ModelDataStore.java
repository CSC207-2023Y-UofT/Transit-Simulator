package model.persistence;

import model.control.TransitModel;

import java.io.IOException;

public interface ModelDataStore {
    TransitModel readModel() throws IOException;
    void writeModel(TransitModel model) throws IOException;
}
