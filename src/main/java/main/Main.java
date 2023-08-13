package main;

import entity.employee.Admin;
import entity.employee.EmployeeTracker;
import entity.employee.TrainEngineer;
import entity.employee.TrainOperator;
import persistence.boundary.*;
import persistence.impl.FileEmployeeDataStore;
import app_business.interactor.EmployeeInteractor;
import app_business.interactor.StatInteractor;
import app_business.interactor.StationInteractor;
import app_business.interactor.TicketInteractor;
import app_business.interactor.TrainInteractor;
import entity.model.control.TransitModel;
import persistence.impl.JsonModelDataStore;

import main.pool.InteractorPool;
import persistence.DataStorage;
import simulation.Simulation;
import simulation.TrainSimulator;
import stats.StatDataControllerImpl;
import stats.StatTracker;
import persistence.impl.FileAggregateDataStore;
import persistence.impl.FileEntryDataStore;
import persistence.impl.JsonTicketDataStore;
import stats.timing.BasicIndexingStrategy;
import stats.timing.IndexingStrategy;
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
        ModelDataStore dataStore = new JsonModelDataStore(file);
        TransitModel model = dataStore.readModel();

        // Stat data storage
        StatEntryDataStore statDataStore = new FileEntryDataStore(new File("stat-entries"));
        StatAggregateDataStore statAggregateDataStore = new FileAggregateDataStore(new File("stat-aggregates"));
        IndexingStrategy indexStrategy = new BasicIndexingStrategy(4000);

        StatTracker stats = new StatDataControllerImpl(indexStrategy, statDataStore, statAggregateDataStore);

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

        // Default employees
        employeeTracker.saveEmployee(new Admin(123, "Matt"));
        employeeTracker.saveEmployee(new Admin(111, "Grace"));
        employeeTracker.saveEmployee(new TrainEngineer(222, "Charles"));
        employeeTracker.saveEmployee(new TrainEngineer(333, "Zoey"));
        employeeTracker.saveEmployee(new TrainOperator(444, "Jarret"));

        // Start the simulation
        Simulation simulation = new Simulation(model, pool, stats);
        simulation.addSimulator(new TrainSimulator(stats));
        simulation.start();
    }
}

