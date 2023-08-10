package main.pool;

import app_business.employee.IEmployeeInteractor;
import app_business.stat.IStatInteractor;
import app_business.station.IStationInteractor;
import app_business.ticket.ITicketInteractor;
import app_business.train.ITrainInteractor;

/**
 * The {@code InteractorPool} data class centralizes access to various interactors
 * responsible for different system operations like station management,
 * train operations, ticketing, employee administration, and statistics collection.
 *
 * This design pattern allows easy retrieval of interactors, ensuring that
 * components dependent on these services can easily obtain them.
 */
public class InteractorPool {

    /** Interactor for station-related operations. */
    private final IStationInteractor stationInteractor;

    /** Interactor for train-related operations. */
    private final ITrainInteractor trainInteractor;

    /** Interactor for ticket-related operations. */
    private final ITicketInteractor ticketInteractor;

    /** Interactor for employee-related operations. */
    private final IEmployeeInteractor employeeInteractor;

    /** Interactor for statistics-related operations. */
    private final IStatInteractor statInteractor;

    /**
     * Initializes a new instance of {@code InteractorPool} with the provided interactors.
     *
     * @param stationInteractor  Responsible for station-related operations.
     * @param trainInteractor    Responsible for train-related operations.
     * @param ticketInteractor   Responsible for ticket-related operations.
     * @param employeeInteractor Responsible for employee-related operations.
     * @param statInteractor     Responsible for statistics-related operations.
     */
    public InteractorPool(IStationInteractor stationInteractor, ITrainInteractor trainInteractor, ITicketInteractor ticketInteractor, IEmployeeInteractor employeeInteractor, IStatInteractor statInteractor) {
        this.stationInteractor = stationInteractor;
        this.trainInteractor = trainInteractor;
        this.ticketInteractor = ticketInteractor;
        this.employeeInteractor = employeeInteractor;
        this.statInteractor = statInteractor;
    }

    /**
     * Provides access to the station interactor.
     *
     * @return The {@link IStationInteractor} instance.
     */
    public IStationInteractor getStationInteractor() {
        return stationInteractor;
    }

    /**
     * Provides access to the train interactor.
     *
     * @return The {@link ITrainInteractor} instance.
     */
    public ITrainInteractor getTrainInteractor() {
        return trainInteractor;
    }

    /**
     * Provides access to the ticket interactor.
     *
     * @return The {@link ITicketInteractor} instance.
     */
    public ITicketInteractor getTicketInteractor() {
        return ticketInteractor;
    }

    /**
     * Provides access to the employee interactor.
     *
     * @return The {@link IEmployeeInteractor} instance.
     */
    public IEmployeeInteractor getEmployeeInteractor() {
        return employeeInteractor;
    }

    /**
     * Provides access to the statistics interactor.
     *
     * @return The {@link IStatInteractor} instance.
     */
    public IStatInteractor getStatInteractor() {
        return statInteractor;
    }
}
