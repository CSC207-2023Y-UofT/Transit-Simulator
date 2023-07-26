package stats;

import java.util.List;
import java.util.Random;



public class MaintenanceAggregator implements StatAggregator {

    private int repairCosts = 0;
    private Random rand = new Random();

    @Override
    public void aggregate(List<StatEntry> stats) {

        for (StatEntry stat : stats) {

            if (!(stat instanceof TrainUsageEvent)) continue;

            TrainUsageEvent event = (TrainUsageEvent) stat;
            int uses = TrainUsageAggregator.getTotal();

            // assign repair cost based on number of uses
            if (uses >= 50 && uses < 100) {
                // minor repair
                repairCosts += 1000 + rand.nextInt(9000); // random cost between 1000 and 10000
            } else if (uses >= 100 && uses < 400) {
                // major repair
                repairCosts += 10000 + rand.nextInt(90000); // random cost between 10000 and 100000
            } else if (uses >= 500) {
                // replacement
                repairCosts += 1000000 + rand.nextInt(2000000); // random cost between 1M and 3M
            }

        }

    }

    public int getRepairCosts() {
        return repairCosts;
    }

}
