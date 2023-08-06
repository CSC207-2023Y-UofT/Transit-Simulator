package controller.stats;

import stats.aggregate.SingletonAggregate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SingletonStatViewModel {
    private List<? extends SingletonAggregate<? extends Number>> aggregates = new ArrayList<>();

    public void setAggregates(List<? extends SingletonAggregate<? extends Number>> aggregates) {
        this.aggregates = aggregates;
    }

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

            if (aggregate.getValue().doubleValue() != 0.0) {
                System.out.println("Entry #" + i + ": " + aggregate.getValue().doubleValue());
            }
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
