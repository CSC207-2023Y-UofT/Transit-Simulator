public interface ITrack {
    /**
     *  returns the distance of the closest incoming train based on train information like location and line.
     *  Note that the Station will have to ask this information of the track, and all the track needs to know is the
     *  direction, to be able to give distance of the next incoming train. We can recursively ask this information of
     *  the previous Node and so on and so on, in the case that there is no immediate Train in the vicinity.
     */
    // The whole reason this interface exists is because of the CRC cards, im pre sure this interface could be made way better
    int nextArrivalDistance ();  // Or, depending on the unit, can be renamed to "nextArrivalTime"

}
