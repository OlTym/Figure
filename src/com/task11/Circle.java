package com.task11;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Figure {
    private final GraphicsContext gc;

    public Circle(GraphicsContext gc, double startX, double startY, double weight, double height, boolean select, boolean group, Color color) {
        super(gc, startX, startY, weight, height, select, group, color);
        this.gc = gc;
    }

    public Circle(GraphicsContext gc) {
        super(gc);
        this.gc = gc;

    }

    @Override
    public void draw() {

        super.draw();
        if (super.isSelect() || isGroup()) {
            gc.strokeOval(super.getStartX(), super.getStartY(), super.getHeight(), super.getHeight());
        }

        gc.fillOval(super.getStartX(), super.getStartY(), super.getHeight(), super.getHeight());

    }

}
