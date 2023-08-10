package persistence.boundary;

import entity.model.control.TransitModel;

import java.io.IOException;

/**
 * The ModelDataStore interface is an interface for classes that store and retrieve
 */
public interface ModelDataStore {
    /**
     * Reads the model from the data store.
     *
     * @return The TransitModel object representing the model.
     * @throws IOException if there is an error reading the model
     */
    TransitModel readModel() throws IOException;

}
