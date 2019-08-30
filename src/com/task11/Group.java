package com.task11;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Group implements Shape {

    private List<Shape> list = new ArrayList<>();

    public void add(Shape shape) {

        list.add(shape);

    }


    @Override
    public void increase() {

        for (Shape figure : list) {
            figure.increase();
        }

    }

    @Override
    public void decrease() {

        for (Shape figure : list) {
            figure.decrease();
        }

    }

    @Override
    public void move(Direct direction) {

        for (Shape figure : list) {
            figure.move(direction);
        }

    }

    @Override
    public void draw() {
        for (Shape figure : list) {
            figure.draw();
        }

    }

    @Override
    public void select(boolean select) {
    }

    @Override
    public boolean isSelect(Point2D point) {
        return false;
    }


    @Override
    public void group() {
        for (Shape figure : list) {
            figure.group();

        }

    }

    @Override
    public Shape clone() {
        return null;
    }

    public void delete() {
        list.clear();

    }

}
