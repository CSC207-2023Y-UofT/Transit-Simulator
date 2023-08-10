package entity.model.train;

/**
 * Enum representing the possible statuses of a train.
 * <br>
 * <p>
 * IN_SERVICE:            Running properly.
 * SCHEDULED_MAINTENANCE: still running, will stop for maintenance at the next station. (not automatically under maintenance)
 * UNDER_MAINTENANCE:     stopped for maintenance at a station and staff assigned to maintain.
 * OUT_OF_SERVICE:        Not running.
 * <br>
 * <p>
 * Precondition: Trains UNDER_MAINTENANCE cannot be IN_SERVICE.
 * Trains that are running can pass Offline trains whether at tracks or at stations. IRL justification: most
 * tracks have spare tracks that others can pass.
 */
public enum TrainStatus {
    IN_SERVICE,
    SCHEDULED_MAINTENANCE,
    UNDER_MAINTENANCE,
    OUT_OF_SERVICE
}
