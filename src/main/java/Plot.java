
/*
 *  Note: Map is already an interface defined by Java so I renamed to Plot.
 *  This will be where the main updating system will be. The Plot contains all Tracks and Trains, and will update them
 *  all at the same time, in idk, 5-second intervals? Keeping track of time is very hard. Maybe I shoul do away with
 *  the real life information and that this as a simulation. Then everything would have to be instantiated with a
 * specific scenario (time, places, etc)  which would be tiring. Main thing is this is where updates happen.
 */


public class Plot {
    // TODO a statistics port num passengers going out of stations

    // Model json files
    // TODO add track distance data to json files.
    // TODO add station distance to json files, initialize with a default value.
    // TODO deal with placeholder distance measurements in code.
    // TODO add train initialization to json files.
    // TODO add train identification for statistical purposes.
}
