package ui.map;

import controller.map.ArrivalsViewModel;
import controller.ticket.TicketViewModel;
import ui.UIController;
import ui.passenger.TicketPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StationPage {

    public StationPage(ArrivalsViewModel viewModel) {

        JFrame frame = new JFrame("Station Time");
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new StationPanel(viewModel);
        frame.setContentPane(panel);

        frame.revalidate();
        frame.pack();
        frame.setVisible(true);
        frame.repaint();
    }

}
