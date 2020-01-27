package com.cybercat3.constellation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {
    private double x;
    private double y;
    private double velX;
    private double velY;
    private double multiplier = 1;

    private final static Data data = Data.instance();

    public Particle(GraphicsContext gc) {
        this.x = Math.random() * gc.getCanvas().getWidth();
        this.y = Math.random() * gc.getCanvas().getHeight();
        this.velX = Math.random() * data.VELOCITY * 2 - data.VELOCITY;
        this.velY = Math.random() * data.VELOCITY * 2 - data.VELOCITY;
    }

    public void update(GraphicsContext gc) {
        x += velX * multiplier;
        y += velY * multiplier;

        if (x < 0 || y < 0 || x > gc.getCanvas().getWidth() || y > gc.getCanvas().getHeight()) {
            if (x < 0) {
                velX = Math.abs(velX);
            } else if (x > gc.getCanvas().getWidth()) {
                velX = Math.abs(velX) * -1;
            }
            if (y < 0) {
                velY = Math.abs(velY);
            } else if (y > gc.getCanvas().getHeight()) {
                velY = Math.abs(velY) * -1;
            }
            multiplier += 0.01;
        } else {
            if (multiplier > 1) multiplier -= 0.01;
        }
    }

    public void drawLine(Particle other, GraphicsContext gc) {
        double dDist = dist(this.x, this.y, other.x, other.y);
        if (dDist <= data.LINE_DIST) {
            int color = (int) map(dDist, 0, data.LINE_DIST, 255, 0);
            gc.setLineWidth(4);
            gc.setStroke(Color.rgb(color, color, color));
            gc.strokeLine(this.x, this.y, other.x, other.y);
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(this.x - 2, this.y - 2, 4,4);
    }

    private static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private static double map(double value, double start1, double stop1, double start2, double stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }
}
