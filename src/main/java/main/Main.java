package main;

import employee.EmployeeTracker;
import employee.persistence.EmployeeDataStore;
import employee.persistence.FileEmployeeDataStore;
import interactor.employee.EmployeeInteractor;
import interactor.stat.StatInteractor;
import interactor.station.StationInteractor;
import interactor.ticket.TicketInteractor;
import interactor.train.TrainInteractor;
import model.persistence.JsonModelDataStore;

import model.control.*;
import simulation.Simulation;
import stats.persistence.StatAggregateDataStore;
import stats.persistence.StatDataController;
import stats.persistence.StatEntryDataStore;
import stats.persistence.impl.FileAggregateDataStore;
import stats.persistence.impl.FileEntryDataStore;
import ticket.JsonTicketDataStore;
import ticket.TicketDataStore;
import ui.UIController;
import ui.WelcomePage;
import util.AsyncWriteIOProvider;
import util.DeflateCompressionProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * The main() containing Main class that sets up all required classes and starts the program.
 * <p>
 * What this should do: TODO
 * <p>
 * To-do list:
 * TODO the program
 */
public class Main {

    /**
     * The main method that executes the program.
     *
     * @param args The command line arguments.
     * @throws IOException If there is an error reading the model file.
     */
    public static void main(String[] args) throws IOException {

        File file = new File("model-2.json");
        try (InputStream str = Main.class.getClassLoader()
                .getResourceAsStream("Model 2.json")) {
            assert str != null;
            byte[] bytes = str.readAllBytes();
            Files.write(file.toPath(), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataStorage.init(
                new AsyncWriteIOProvider(),
                new DeflateCompressionProvider()
        );

        // Create the model
        JsonModelDataStore dataStore = new JsonModelDataStore(file);
        TransitModel model = dataStore.readModel();

        // Stat data storage
        StatEntryDataStore statDataStore = new FileEntryDataStore(new File("stat-entries"));
        StatAggregateDataStore statAggregateDataStore = new FileAggregateDataStore(new File("stat-aggregates"));

        StatDataController stats = new StatDataController(statDataStore, statAggregateDataStore);

        // Ticket data store
        TicketDataStore store = new JsonTicketDataStore(new File("tickets"));

        // Employee data store
        EmployeeDataStore employeeDataStore = new FileEmployeeDataStore(new File("employees"));
        EmployeeTracker employeeTracker = new EmployeeTracker(employeeDataStore);

        // Create the presenter
        InteractorPool pool = new InteractorPool(
                new StationInteractor(model),
                new TrainInteractor(model),
                new TicketInteractor(store, stats),
                new EmployeeInteractor(employeeTracker, model),
                new StatInteractor(stats)
        );

        // Create the ui controller
        UIController controller = new UIController(pool);
        controller.open(new WelcomePage(controller));

        // Start the simulation
        new Simulation(model, pool, stats).start();
    }
}

