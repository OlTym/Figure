package com.task11;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Figure {

    private final GraphicsContext gc;
    private double apex_x1;
    private double apex_y1;
    private double apex_x2;
    private double apex_y2;
    private double apex_x3;
    private double apex_y3;

    public Triangle(GraphicsContext gc) {
        super(gc);

        this.gc = gc;

        calcApex();

    }

    public Triangle(GraphicsContext gc, double startX, double startY, double weight, double height, boolean select, boolean group, Color color) {
        super(gc, startX, startY, weight, height, select, group, color);
        this.gc = gc;
        calcApex();
    }

    private void calcApex() {
        apex_x1 = super.getStartX() + super.getWeight() / 2;
        apex_y1 = super.getStartY();
        apex_x2 = super.getStartX();
        apex_y2 = super.getStartY() + super.getHeight();
        apex_x3 = super.getStartX() + super.getWeight();
        apex_y3 = super.getStartY() + super.getHeight();

    }

    @Override
    public void draw() {
        super.draw();
        calcApex();
        if (super.isSelect() || isGroup()) {
            gc.strokePolygon(new double[]{apex_x1, apex_x2, apex_x3}, new double[]{apex_y1, apex_y2, apex_y3}, 3);
        }
        gc.fillPolygon(new double[]{apex_x1, apex_x2, apex_x3}, new double[]{apex_y1, apex_y2, apex_y3}, 3);
    }
}
