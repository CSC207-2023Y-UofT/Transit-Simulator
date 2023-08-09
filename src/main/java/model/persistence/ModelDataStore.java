package model.persistence;

import model.control.TransitModel;

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

    /**
     * Writes the model to the data store.  TODO: we don't need this since we do not support changing the model in
     * editor anyways; there wouldn't be any difference to write
     *
     * @param model The TransitModel object representing the model.
     * @throws IOException If an I/O error occurs
     */
    void writeModel(TransitModel model) throws IOException;
}
