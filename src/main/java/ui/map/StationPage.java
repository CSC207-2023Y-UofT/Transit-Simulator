package ui.map;

import controller.map.ArrivalsViewModel;
import controller.ticket.TicketViewModel;
import ui.UIController;
import ui.passenger.TicketPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StationPage extends JFrame {

    public StationPage(ArrivalsViewModel viewModel) {

        super("Station Time");

        this.setPreferredSize(new Dimension(400, 400));

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setVisible(true);

        JPanel panel = new StationPanel(viewModel);
        this.setContentPane(panel);

        this.pack();

        this.revalidate();
        this.repaint();
    }

}
