package ui;

import javax.swing.*;

public class MainScreen extends JFrame {
    private final PageController pageController;
    private JPanel contentPane;

    public MainScreen(PageController pageController) {
        super("Transit Simulator");

        this.pageController = pageController;

        contentPane = new WelcomePage(pageController);
        setContentPane(contentPane);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
