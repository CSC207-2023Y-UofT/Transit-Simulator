package persistence.impl;

import org.junit.jupiter.api.Test;
import persistence.impl.file.JsonModelDataStore;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonModelDataStoreTest {

    @Test
    void readModel() {
        JsonModelDataStore data = new JsonModelDataStore(new File("boobs.non-existent"));
        assertThrows(IOException.class, data::readModel);
    }
}