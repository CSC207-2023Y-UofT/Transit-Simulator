package ui.staff.admin;

import controller.stats.SingletonStatViewModel;
import controller.stats.StatsController;
import ui.UIController;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

    private final UIController controller;

    public enum StatDisplay {
        REVENUE, EXPENSES
    }

    public enum TimeHorizon {
        QUARTER_DAY(360),
        HALF_DAY(720),
        FULL_DAY(1440);

        private final long timeHorizonMinutes;

        TimeHorizon(long timeHorizonMinutes) {
            this.timeHorizonMinutes = timeHorizonMinutes;
        }

        public long getTimeHorizonMinutes() {
            return timeHorizonMinutes;
        }
    }

    private StatDisplay display = StatDisplay.REVENUE;
    private TimeHorizon horizon = TimeHorizon.QUARTER_DAY;

    private final SingletonStatViewModel viewModel = new SingletonStatViewModel();

    public StatsPanel(UIController controller) {
        this.controller = controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
        StatsController stats = this.controller.getControllerPool().getStatController();

        if (display.equals(StatDisplay.REVENUE)) {
            viewModel.setAggregates(stats.getRevenue(horizon.getTimeHorizonMinutes()));
        } else {
            viewModel.setAggregates(stats.getExpenses(horizon.getTimeHorizonMinutes()));
        }

        viewModel.draw((Graphics2D) g, getWidth(), getHeight());
    }
}
