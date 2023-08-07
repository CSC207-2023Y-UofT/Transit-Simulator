package main;

import interactor.employee.IEmployeeInteractor;
import interactor.stat.IStatInteractor;
import interactor.station.IStationInteractor;
import interactor.ticket.ITicketInteractor;
import interactor.train.ITrainInteractor;

/**
 * The InteractorPool class acts as a centralized repository for all interactors in the system.
 * It provides methods to access different interactors that control distinct functionalities within the system.
 */
public class InteractorPool {

    /** The station-related interactor. */
    private final IStationInteractor stationInteractor;

    /** The train-related interactor. */
    private final ITrainInteractor trainInteractor;

    /** The ticket-related interactor. */
    private final ITicketInteractor ticketInteractor;

    /** The employee-related interactor. */
    private final IEmployeeInteractor employeeInteractor;

    /** The statistics-related interactor. */
    private final IStatInteractor statInteractor;

    /**
     * Constructs a new InteractorPool with the given interactors.
     *
     * @param stationInteractor  The object controlling station-related operations.
     * @param trainInteractor    The object controlling train-related operations.
     * @param ticketInteractor   The object controlling ticket-related operations.
     * @param employeeInteractor The object controlling employee-related operations.
     * @param statInteractor     The object controlling statistical operations.
     */
    public InteractorPool(IStationInteractor stationInteractor, ITrainInteractor trainInteractor,
                          ITicketInteractor ticketInteractor, IEmployeeInteractor employeeInteractor,
                          IStatInteractor statInteractor) {
        this.stationInteractor = stationInteractor;
        this.trainInteractor = trainInteractor;
        this.ticketInteractor = ticketInteractor;
        this.employeeInteractor = employeeInteractor;
        this.statInteractor = statInteractor;
    }

    /**
     * Provides access to the StationInteractor.
     *
     * @return The StationInteractor instance.
     */
    public IStationInteractor getStationInteractor() {
        return stationInteractor;
    }

    /**
     * Provides access to the TrainInteractor.
     *
     * @return The TrainInteractor instance.
     */
    public ITrainInteractor getTrainInteractor() {
        return trainInteractor;
    }

    /**
     * Provides access to the TicketInteractor.
     *
     * @return The TicketInteractor instance.
     */
    public ITicketInteractor getTicketInteractor() {
        return ticketInteractor;
    }

    /**
     * Provides access to the EmployeeInteractor.
     *
     * @return The EmployeeInteractor instance.
     */
    public IEmployeeInteractor getEmployeeInteractor() {
        return employeeInteractor;
    }

    /**
     * Provides access to the StatInteractor.
     *
     * @return The StatInteractor instance.
     */
    public IStatInteractor getStatInteractor() {
        return statInteractor;
    }
}
