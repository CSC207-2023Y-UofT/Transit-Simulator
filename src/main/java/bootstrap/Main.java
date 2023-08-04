package bootstrap;

import interactor.station.StationInteractor;
import interactor.train.TrainInteractor;
import model.persistence.JsonModelDataStore;

import model.control.*;
import ui.UIController;
import ui.WelcomePage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * The bootstrap.Main program that sets up all required classes and executes the program.
 * <p>
 * What this should do: TODO
 * <p>
 * To-do list:
 * TODO the program
 */
public class Main {


    public static void main(String[] args) throws IOException {

        InputStream str = Main.class.getResourceAsStream("Model 1.json");
        File file = new File("model-1.json");
        try {
            assert str != null;
            byte[] bytes = str.readAllBytes();
            Files.write(file.toPath(), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create the model
        JsonModelDataStore dataStore = new JsonModelDataStore(file);
        TransitModel model = dataStore.readModel();

        // Create the presenter
        InteractorPool pool = new InteractorPool(
                new StationInteractor(model),
                new TrainInteractor(model)
        );

        // Create the ui controller
        UIController controller = new UIController(pool);
        controller.open(new WelcomePage(controller));
    }
}

