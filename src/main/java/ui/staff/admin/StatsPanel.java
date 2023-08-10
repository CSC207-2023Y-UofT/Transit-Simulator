package ui.staff.admin;

import interface_adapter.stats.SingletonStatViewModel;
import interface_adapter.stats.StatsController;
import ui.UIController;

import javax.swing.*;
import java.awt.*;

/**
 * The StatsPanel class is responsible for displaying statistical information related
 * to revenue and expenses. It includes options to choose the statistical information
 * to be displayed (e.g., REVENUE or EXPENSES) and the time horizon for the data.
 * <p>
 * The statistics are periodically refreshed and displayed using the SingletonStatViewModel.
 */
public class StatsPanel extends JPanel {

    /**
     * The UI controller.
     */
    private final UIController controller;

    /**
     * Enum representing the type of statistics to be displayed.
     */
    public enum StatDisplay {
        REVENUE, EXPENSES
    }

    /**
     * Enum representing the time horizon in minutes for the statistics to be displayed.
     */
    public enum TimeHorizon {

        QUARTER_DAY(360),
        HALF_DAY(720),
        FULL_DAY(1440);

        /**
         * The time horizon in minutes.
         */
        private final long timeHorizonMinutes;

        /**
         * Constructs a TimeHorizon with the given time in minutes.
         *
         * @param timeHorizonMinutes the time horizon in minutes
         */
        TimeHorizon(long timeHorizonMinutes) {
            this.timeHorizonMinutes = timeHorizonMinutes;
        }

        /**
         * Gets the time horizon in minutes.
         *
         * @return the time horizon in minutes
         */
        public long getTimeHorizonMinutes() {
            return timeHorizonMinutes;
        }
    }

    /**
     * The type of statistics to be displayed.
     */
    private StatDisplay display = StatDisplay.REVENUE;

    /**
     * The time horizon for the statistics.
     */
    private TimeHorizon horizon = TimeHorizon.QUARTER_DAY;

    /**
     * The view model responsible for managing the statistics data.
     */
    private final SingletonStatViewModel viewModel = new SingletonStatViewModel();

    /**
     * Timer to refresh the statistics periodically.
     */
    private final Timer timer = new Timer(100, e -> this.refresh());

    /**
     * Returns the UIController for this panel.
     *
     * @return the UIController
     */
    public UIController getController() {
        return controller;
    }

    /**
     * Constructs a new StatsPanel with the given UIController.
     *
     * @param controller the UIController.
     */
    public StatsPanel(UIController controller) {
        this.controller = controller;
    }

    /**
     * Gets the view model for this panel.
     *
     * @return the view model
     */
    public SingletonStatViewModel getViewModel() {
        return viewModel;
    }

    /**
     * Gets the time horizon for the statistics.
     *
     * @return the time horizon
     */
    public void setHorizon(TimeHorizon horizon) {
        this.horizon = horizon;
    }

    /**
     * Gets the type of statistics to be displayed.
     *
     * @param display the type of statistics to be displayed
     */
    public void setDisplay(StatDisplay display) {
        this.display = display;
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

    /**
     * Refreshes the statistics based on the current display and time horizon.
     * Retrieves the appropriate data from the StatsController.
     */
    private void refresh() {
        StatsController controller = getController().getControllerPool().getStatController();

        switch (display) {
            case REVENUE:
                controller.getRevenue(horizon.getTimeHorizonMinutes()).thenAccept(viewModel::setAggregates)
                        .thenAccept(t -> repaint())
                        .exceptionally(t -> {
                            t.printStackTrace();
                            return null;
                        });
                break;
            case EXPENSES:
                controller.getExpenses(horizon.getTimeHorizonMinutes()).thenAccept(viewModel::setAggregates)
                        .thenAccept(t -> repaint())
                        .exceptionally(t -> {
                            t.printStackTrace();
                            return null;
                        });
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        viewModel.draw(controller, display.toString(), (Graphics2D) g, getWidth(), getHeight());
    }
}
