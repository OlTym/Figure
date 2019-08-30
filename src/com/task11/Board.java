package com.task11;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    private final GraphicsContext gc;
    private List<Shape> list = new ArrayList<>();
    private List<Integer> index = new ArrayList<>();
    private int selectedIndex;
    private boolean selectedGroup;
    private Group group = new Group();
    private  SaveFigure saveFigure;

    public Board(GraphicsContext gc) {
        this.gc = gc;
        selectedIndex = -1;
        saveFigure=new SaveFigure(gc);
    }

    private void drawAll() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (Shape shape : list) {
            shape.draw();
        }
    }

    private void selectNext() {

        if (!list.isEmpty()) {
            selectedIndex++;
            if (selectedIndex == list.size()) {
                selectedIndex = 0;
            }
        }
    }

    private void selectPrevios() {

        if (!list.isEmpty()) {

            if (selectedIndex == -1 || selectedIndex == 0) {
                selectedIndex = list.size() - 1;
            } else {
                selectedIndex--;
            }

        }
    }

    private void group() {
        Collections.sort(index);
        for (int i = index.size() - 1; i >= 0; i--) {
            list.remove((int) index.get(i));
        }
        list.add(group);
        index.clear();
        selectedGroup = false;
    }

    private void delete() {

        if (!list.isEmpty()) {
            list.remove(selectedIndex);
            Storage.getInstance().delete();
            selectedIndex = -1;
        }
    }

    private void deleteGroup() {

        if (!list.isEmpty()) {
            list.remove(group);
            group.delete();
            Storage.getInstance().deleteGroup();
        }

    }


    public void keyHandler(KeyEvent event) {

        switch (event.getCode()) {

            case R:
                Shape rect = new Rect(gc);
                list.add(rect);
                break;

            case T:
                Shape triangle = new Triangle(gc);
                list.add(triangle);
                break;

            case C:
                Shape cicle = new Circle(gc);
                list.add(cicle);
                break;

            case PAGE_DOWN:
                selectPrevios();
                if (!list.isEmpty()) {
                    for (Shape figure : list) {
                        figure.select(false);
                    }
                    list.get(selectedIndex).select(true);
                }
                break;

            case PAGE_UP:
                selectNext();
                if (!list.isEmpty()) {
                    for (Shape figure : list) {
                        figure.select(false);
                    }
                    list.get(selectedIndex).select(true);
                }
                break;

            case LEFT:
                if (event.isShiftDown()) {
                    group.move(Direct.LEFT);
                } else {
                    if (selectedIndex != -1) {
                        list.get(selectedIndex).move(Direct.LEFT);
                    }

                }
                break;

            case RIGHT:

                if (event.isShiftDown()) {
                    group.move(Direct.RIGHT);
                } else {
                    if (!list.isEmpty() && selectedIndex != -1) {
                        list.get(selectedIndex).move(Direct.RIGHT);
                    }
                }

                break;

            case DOWN:
                if (event.isShiftDown()) {
                    group.move(Direct.DOWN);
                } else {
                    if (selectedIndex != -1) {
                        list.get(selectedIndex).move(Direct.DOWN);
                    }
                }
                break;

            case UP:
                if (event.isShiftDown()) {
                    group.move(Direct.UP);
                } else {
                    if (selectedIndex != -1) {
                        list.get(selectedIndex).move(Direct.UP);
                    }
                }
                break;

            case EQUALS:
                if (event.isShiftDown()) {
                    group.increase();
                } else {
                    if (selectedIndex != -1) {
                        list.get(selectedIndex).increase();
                    }
                }
                break;

            case MINUS:
                if (event.isShiftDown()) {
                    group.decrease();
                } else {
                    if (selectedIndex != -1) {
                        list.get(selectedIndex).decrease();
                    }
                }
                break;

            case DELETE:
                if (event.isShiftDown()) {
                    deleteGroup();
                } else {
                    delete();
                }
                break;

            case G:
                if (selectedGroup) {
                    group();
                }
                break;

            case Q:
                cloneFigure();
                break;

            case S:
                saveFigure.saveFigure();
                break;
            case L:
            list=saveFigure.loadFigure();
                break;
        }

        drawAll();
    }

    private void cloneFigure() {

        Shape shape = list.get(selectedIndex).clone();
        list.add(shape);
    }

    public void mouseHandler(MouseEvent event) {
        Point2D point = new Point2D(event.getX(), event.getY());

        if (list.isEmpty())
            throw new ArrayIndexOutOfBoundsException("There is not any figure");

        for (Shape figure : list) {
            if (figure.isSelect(point)) {
                selectedIndex = list.indexOf(figure);
                index.add(selectedIndex);
            }
        }
        if (event.isShiftDown()) {

            group.add(list.get(selectedIndex));

            group.group();
            selectedGroup = true;
        } else {

            list.get(selectedIndex).select(true);
        }
        drawAll();

    }
}
