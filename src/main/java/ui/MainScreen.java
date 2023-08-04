package ui;

import bootstrap.InteractorPool;

import javax.swing.*;

public class MainScreen extends JFrame {
    private final ScreenController screenController;
    private JPanel contentPane;

    public MainScreen(ScreenController screenController) {
        super("Transit Simulator");

        this.screenController = screenController;

        contentPane = new WelcomePage(screenController);
        setContentPane(contentPane);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
