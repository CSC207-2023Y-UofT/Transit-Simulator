package ui;

import main.ControllerPool;
import main.InteractorPool;
import javax.swing.*;
import java.awt.*;

/**
 * MainScreen is a JFrame that displays the main screen of the application.
 * It is used by the UIController to display the main screen of the application.
 *
 * @see UIController
 */
public class UIController {
    /**
     *
     * The UIController that is used to switch panels.
     */
    private final MainScreen mainScreen;

    /**
     * The content pane of the JFrame.
     */
    private final InteractorPool interactorPool;

    /**
     * The controller pool of the application.
     */
    private final ControllerPool controllerPool;

    /**
     * Constructs a new MainScreen object.
     *
     * @param interactorPool the controller used to switch panels
     */
    public UIController(InteractorPool interactorPool) {
        this.interactorPool = interactorPool;
        this.controllerPool = new ControllerPool(interactorPool);
        this.mainScreen = new MainScreen();
        mainScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainScreen.setVisible(true);
    }

    /**
     * Returns the interactor pool.
     * @return the interactor pool
     */
    public InteractorPool getInteractorPool() {
        return interactorPool;
    }

    /**
     * Returns the controller pool.
     * @return the controller pool
     */
    public ControllerPool getControllerPool() {
        return controllerPool;
    }

    /**
     * Returns the main screen.
     * @return the main screen
     */
    public MainScreen getMainScreen() {
        return mainScreen;
    }

    /**
     * Opens the given panel.
     * @param panel the panel to open
     */
    public void open(JPanel panel) {

        mainScreen.setContentPane(panel);
        mainScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainScreen.revalidate();
        mainScreen.repaint();
    }

}
