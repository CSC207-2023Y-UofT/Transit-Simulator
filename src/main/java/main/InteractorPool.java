package main;

import interactor.employee.IEmployeeInteractor;
import interactor.stat.IStatInteractor;
import interactor.station.IStationInteractor;
import interactor.ticket.ITicketInteractor;
import interactor.train.ITrainInteractor;

/**
 * The InteractorPool dataclass represents a collection of a stationInteractor and a trainInteractor.
 */
public class InteractorPool {
    /**
     * The station interactor.
     */
    private final IStationInteractor stationInteractor;
    /**
     * The train interactor.
     */
    private final ITrainInteractor trainInteractor;

    private final ITicketInteractor ticketInteractor;
    private final IEmployeeInteractor employeeInteractor;
    private final IStatInteractor statInteractor;

    /**
     * Constructs a new InteractorPool with the given station and train interactors.
     *
     * @param stationInteractor  The StationInteractor.
     * @param trainInteractor    The TrainInteractor.
     * @param ticketInteractor   The TicketInteractor.
     * @param employeeInteractor The EmployeeInteractor.
     * @param statInteractor     The StatInteractor.
     */
    public InteractorPool(IStationInteractor stationInteractor, ITrainInteractor trainInteractor, ITicketInteractor ticketInteractor, IEmployeeInteractor employeeInteractor, IStatInteractor statInteractor) {
        this.stationInteractor = stationInteractor;
        this.trainInteractor = trainInteractor;
        this.ticketInteractor = ticketInteractor;
        this.employeeInteractor = employeeInteractor;
        this.statInteractor = statInteractor;
    }

    /**
     * Gets the station interactor.
     *
     * @return The station interactor.
     */
    public IStationInteractor getStationInteractor() {
        return stationInteractor;
    }

    /**
     * Gets the train interactor.
     *
     * @return The train interactor.
     */
    public ITrainInteractor getTrainInteractor() {
        return trainInteractor;
    }

    public ITicketInteractor getTicketInteractor() {
        return ticketInteractor;
    }

    public IEmployeeInteractor getEmployeeInteractor() {
        return employeeInteractor;
    }

    public IStatInteractor getStatInteractor() {
        return statInteractor;
    }
}
