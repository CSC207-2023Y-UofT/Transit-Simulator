package controller.stats;

import stats.aggregate.SingletonAggregate;
import ui.UIController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SingletonStatViewModel {

    /**
     * Enum for graph colour
     */
    public enum GraphColour {

        RED(new Color(160, 0, 21), new Color(160, 55, 55)),
        GREEN(new Color(0, 130, 21), new Color(55, 160, 55)),
        BLUE(Color.BLUE, new Color(100, 100, 255));

        /**
         * The primary colour of the graph, this is the colour of the border and the text
         */
        private final Color primary;

        /**
         * The secondary colour of the graph, this is the colour of the inside of the graph
         */
        private final Color secondary;

        /**
         * Constructs a new GraphColour with the given primary and secondary colours
         *
         * @param primary The primary colour of the graph
         * @param secondary The secondary colour of the graph
         */
        GraphColour(Color primary, Color secondary) {
            this.primary = primary;
            this.secondary = secondary;
        }

        /**
         * Gets the primary colour of the graph
         *
         * @return The primary colour of the graph
         */
        public Color getPrimary() {
            return primary;
        }

        /**
         * Gets the secondary colour of the graph
         *
         * @return The secondary colour of the graph
         */
        public Color getSecondary() {
            return secondary;
        }
    }

    /**
     * The data the graph will display
     */
    private List<? extends SingletonAggregate<? extends Number>> aggregates = new ArrayList<>();

    /**
     * The colour of the graph.
     */
    private GraphColour graphColour = GraphColour.BLUE;

    /**
     * Sets the colour of the graph.
     *
     * @param graphColour The colour of the graph.
     */
    public void setGraphColour(GraphColour graphColour) {
        this.graphColour = graphColour;
    }

    /**
     * Sets the data to be displayed in the graph.
     *
     * @param aggregates The data to be displayed in the graph.
     */
    public void setAggregates(List<? extends SingletonAggregate<? extends Number>> aggregates) {
        this.aggregates = aggregates;
    }

    /**
     * Draws the graph.
     *
     * @param controller The UIController to draw the graph on.
     * @param display The display to draw the graph on.
     * @param g The graphics to draw the graph with.
     * @param width The width of the graph.
     * @param height The height of the graph.
     */
    public void draw(UIController controller, String display, Graphics2D g, int width, int height) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Calculate a vertical scale by taking the max of all the stats
        double max = 0;
        for (SingletonAggregate<? extends Number> aggregate : aggregates) {
            max = Math.max(max, aggregate.getValue().doubleValue());
        }

        Stroke oldStroke = g.getStroke();
        if (max != 0) {

            Polygon polygon = new Polygon();
            polygon.addPoint(0, height);

            int minY = height;
            for (int i = 0; i < aggregates.size(); i++) {
                SingletonAggregate<? extends Number> aggregate = aggregates.get(i);
                int x = (int) (i * width / (double) aggregates.size());
                int y = (int) (height - aggregate.getValue().doubleValue() * (height * 3 / 5.0) / max);
                y = Math.max(4, y);
                polygon.addPoint(x, y);

                minY = Math.min(minY, y);
            }

            polygon.addPoint(width, height);

            g.setPaint(new LinearGradientPaint(0, minY, 0, height, new float[]{0, 1}, new Color[]{
                    setAlpha(graphColour.getSecondary(), 255), setAlpha(graphColour.getSecondary(), 50)
            }));

            g.fillPolygon(polygon);

            g.setColor(graphColour.getPrimary());
            g.setStroke(new BasicStroke(2.25F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawPolygon(polygon);
        }

        g.setColor(graphColour.getPrimary());

        g.setStroke(new BasicStroke(1.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int middle = width / 2;
        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(display);
        int textY = height / 6;
        g.drawString(display, middle - textWidth / 2, textY);

        // Calculate the total
        double total = 0;
        for (SingletonAggregate<? extends Number> aggregate : aggregates) {
            total += aggregate.getValue().doubleValue();
        }

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        metrics = g.getFontMetrics();
        String totalString = String.format("$%,.2f", total);
        int numberTextWidth = metrics.stringWidth(totalString);
        textY = height / 6 + 30;
        g.setColor(Color.BLACK);
        g.drawString(totalString, middle - numberTextWidth / 2, textY);

        g.setStroke(new BasicStroke(3.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(Color.DARK_GRAY);

        g.setStroke(oldStroke);
    }

    /**
     * Set the alpha value of a color
     *
     * @param color The color to set the alpha of
     * @param alpha The alpha value to set
     * @return The new color with the alpha value set
     */
    private Color setAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
