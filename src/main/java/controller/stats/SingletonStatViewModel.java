package controller.stats;

import stats.aggregate.SingletonAggregate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a view model for displaying singleton statistics as a graphical representation.
 * It is designed to render a set of singleton aggregate values using a filled polygon on a Graphics2D canvas.
 */
public class SingletonStatViewModel {

    /** A list of aggregates representing the singleton statistics. */
    private List<? extends SingletonAggregate<? extends Number>> aggregates = new ArrayList<>();


    /**
     * Sets the aggregates for the view model.
     *
     * @param aggregates A list of aggregates of type SingletonAggregate with values of type Number.
     */
    public void setAggregates(List<? extends SingletonAggregate<? extends Number>> aggregates) {
        this.aggregates = aggregates;
    }

    /**
     * Draws the graphical representation of the singleton statistics on a Graphics2D canvas.
     *
     * @param g The Graphics2D object on which the representation is to be drawn.
     * @param width The width of the drawing area.
     * @param height The height of the drawing area.
     */
    public void draw(Graphics2D g, int width, int height) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);

        // Calculate a vertical scale by taking the max of all the stats
        double max = 0;
        for (SingletonAggregate<? extends Number> aggregate : aggregates) {
            max = Math.max(max, aggregate.getValue().doubleValue());
        }

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
        Paint oldPaint = g.getPaint();
        g.setPaint(new LinearGradientPaint(0, 0, 0, height, new float[]{0, 1}, new Color[]{
                new Color(0, 131, 227, 255), new Color(0, 111, 197, 50)
        }));
        g.fillPolygon(polygon);
        g.setPaint(oldPaint);
        g.setColor(Color.BLUE);
        g.drawPolygon(polygon);
    }
}
