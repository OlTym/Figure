package com.task11;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rect extends Figure {
    private final GraphicsContext gc;

    public Rect(GraphicsContext gc, double startX, double startY, double weight, double height, boolean select, boolean group, Color color) {
        super(gc, startX, startY, weight, height, select, group, color);
        this.gc = gc;
    }


    public Rect(GraphicsContext gc) {
        super(gc);
        this.gc = gc;
    }

    public void draw() {
        super.draw();
        if (super.isSelect() || isGroup()) {
            gc.strokeRect(super.getStartX(), super.getStartY(), super.getWeight(), super.getHeight());
        }
        gc.fillRect(super.getStartX(), super.getStartY(), super.getWeight(), super.getHeight());

    }
}
