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
     * The UIController that is used to switch panels.
     */
    private final MainScreen mainScreen;
    /**
     * The content pane of the JFrame.
     */
    private final InteractorPool interactorPool;

    private final ControllerPool controllerPool;

    /**
     * Constructs a new MainScreen object.
     *
     * @param interactorPool the controller used to switch panels
     */
    public UIController(InteractorPool interactorPool) {
        this.interactorPool = interactorPool;
        this.controllerPool = new ControllerPool(interactorPool);
        this.mainScreen = new MainScreen(this);
    }

    /**
     * Returns the interactor pool.
     * @return the interactor pool
     */
    public InteractorPool getInteractorPool() {
        return interactorPool;
    }

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
        mainScreen.setPreferredSize(new Dimension(1500, 800));
        mainScreen.pack();
        mainScreen.revalidate();
        mainScreen.repaint();
    }
}
