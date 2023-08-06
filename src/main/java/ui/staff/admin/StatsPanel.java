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
    private TimeHorizon horizon = TimeHorizon.FULL_DAY;

    private final SingletonStatViewModel viewModel = new SingletonStatViewModel();

    private Timer timer = new Timer(200, e -> this.refresh());

    public UIController getController() {
        return controller;
    }

    public StatsPanel(UIController controller) {
        this.controller = controller;
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        timer.stop();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        refresh();
        timer.start();
    }

    private void refresh() {
        StatsController controller = getController().getControllerPool().getStatController();
        switch (display) {
            case REVENUE:
                controller.getRevenue(horizon.getTimeHorizonMinutes()).thenAccept(viewModel::setAggregates);
                break;
            case EXPENSES:
                controller.getExpenses(horizon.getTimeHorizonMinutes()).thenAccept(viewModel::setAggregates);
                break;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        viewModel.draw((Graphics2D) g, getWidth(), getHeight());
    }
}
