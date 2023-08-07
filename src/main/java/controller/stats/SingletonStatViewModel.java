package controller.stats;

import stats.aggregate.SingletonAggregate;
import ui.UIController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SingletonStatViewModel {
    private List<? extends SingletonAggregate<? extends Number>> aggregates = new ArrayList<>();

    public enum GraphColour {
        RED(new Color(160, 0, 21), new Color(204, 85, 85)),
        GREEN(new Color(0, 130, 21), new Color(85, 204, 85)),
        BLUE(Color.BLUE, new Color(100, 100, 255));

        private final Color primary;
        private final Color secondary;

        GraphColour(Color primary, Color secondary) {
            this.primary = primary;
            this.secondary = secondary;
        }

        public Color getPrimary() {
            return primary;
        }

        public Color getSecondary() {
            return secondary;
        }
    }

    private GraphColour graphColour = GraphColour.BLUE;

    public GraphColour getGraphColour() {
        return graphColour;
    }

    public void setGraphColour(GraphColour graphColour) {
        this.graphColour = graphColour;
    }

    public void setAggregates(List<? extends SingletonAggregate<? extends Number>> aggregates) {
        this.aggregates = aggregates;
    }

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
            for (int i = 0; i < aggregates.size(); i++) {
                SingletonAggregate<? extends Number> aggregate = aggregates.get(i);
                int x = (int) (i * width / (double) aggregates.size());
                int y = (int) (height - aggregate.getValue().doubleValue() * (height * 3 / 4.0) / max);
                y = Math.max(4, y);
                polygon.addPoint(x, y);
            }

            polygon.addPoint(width, height);
            g.setPaint(new LinearGradientPaint(0, (int) (height - max), 0, height, new float[]{0, 1}, new Color[]{
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
        textWidth = metrics.stringWidth(totalString);
        textY = height / 6 + 30;
        g.setColor(Color.BLACK);
        g.drawString(totalString, middle - textWidth / 2, textY);

        g.setStroke(oldStroke);
    }

    private Color setAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
