package ui.map;

import controller.ticket.TicketViewModel;
import ui.UIController;
import ui.passenger.TicketPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StationPage {

    /** The controller used to switch pages. */
    private final UIController controller;

    private final JFrame frame;

    private JPanel panel;

    public StationPage(UIController controller, List<TicketViewModel> viewModels) {
        this.controller = controller;

        frame = new JFrame("Station Time");
        frame.setPreferredSize(new Dimension(300, 600));

        new StationPanel(controller, viewModels);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
