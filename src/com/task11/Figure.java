package com.task11;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public abstract class Figure implements Shape, Cloneable {

    private final GraphicsContext gc;
    private final double SCALE = 1.2;
    private final double SCREENX;
    private final double SCREENY;

    private double startX;
    private double startY;
    private double weight;
    private double height;

    private boolean select;
    private boolean group;
    private Color color;
    private SettingShape settingShape;

    Figure(GraphicsContext gc, double startX, double startY, double weight, double height, boolean select, boolean group, Color color) {
        this.gc = gc;
        this.startX = startX;
        this.startY = startY;
        this.weight = weight;
        this.height = height;
        this.select = select;
        this.group = group;

        SCREENX = gc.getCanvas().getWidth();
        SCREENY = gc.getCanvas().getHeight();

        this.color = Color.color(color.getRed(), color.getGreen(), color.getBlue());

        settingShape = new SettingShape(startX, startY, weight, height, color);

        if (this.getClass().getSimpleName().equals(Circle.class.getSimpleName()))
            settingShape.setPatern(Pattern.CIRCLE);
        else if (this.getClass().getSimpleName().equals(Triangle.class.getSimpleName()))
            settingShape.setPatern(Pattern.TRIANGLE);
        else if (this.getClass().getSimpleName().equals(Rect.class.getSimpleName()))
            settingShape.setPatern(Pattern.RECT);


        Storage.getInstance().add(settingShape);

    }


    Figure(GraphicsContext gc) {
        this.gc = gc;
        weight = 55;
        height = 45;

        SCREENX = gc.getCanvas().getWidth();
        SCREENY = gc.getCanvas().getHeight();

        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        color = Color.rgb(r, g, b);

        do {
            startX = random.nextInt((int) (SCREENX - weight));
            startY = random.nextInt((int) (SCREENY - height));
        } while (isCrossingFigure());

        settingShape = new SettingShape(startX, startY, weight, height, color);

        if (this.getClass().getSimpleName().equals(Circle.class.getSimpleName()))
            settingShape.setPatern(Pattern.CIRCLE);
        else if (this.getClass().getSimpleName().equals(Triangle.class.getSimpleName()))
            settingShape.setPatern(Pattern.TRIANGLE);
        else if (this.getClass().getSimpleName().equals(Rect.class.getSimpleName()))
            settingShape.setPatern(Pattern.RECT);


        Storage.getInstance().add(settingShape);

    }

    private boolean isCrossingFigure() {
        List<SettingShape> shapeList = Storage.getInstance().getSettingShapeList();
        if (!shapeList.isEmpty()) {

            for (SettingShape shape : shapeList) {
                if ((shape.getStartX() <= startX && startX <= shape.getStartX() + shape.getWeight()) &&
                        (shape.getStartY() <= startY && startY <= shape.getStartY() + shape.getHeight())) {
                    return true;
                }
                if ((shape.getStartX() <= startX + weight && startX + weight <= shape.getStartX() + shape.getWeight()) &&
                        (shape.getStartY() <= startY && startY <= shape.getStartY() + shape.getHeight())) {
                    return true;
                }
                if ((shape.getStartX() <= startX && startX <= shape.getStartX() + shape.getWeight()) &&
                        (shape.getStartY() <= startY + height && startY + height <= shape.getStartY() + shape.getHeight())) {
                    return true;
                }
                if ((shape.getStartX() <= startX + weight && startX + weight <= shape.getStartX() + shape.getWeight()) &&
                        (shape.getStartY() <= startY + height && startY + height <= shape.getStartY() + shape.getHeight())) {
                    return true;
                }
            }
        }
        return false;
    }

    double getStartY() {
        return startY;
    }

    double getStartX() {
        return startX;
    }

    double getHeight() {
        return height;
    }

    double getWeight() {
        return weight;
    }

    boolean isGroup() {
        return group;
    }

    public void select(boolean select) {
        this.select = select;
        settingShape.setSelect(select);
    }


    @Override
    public void draw() {
        gc.setFill(color);

        if (select || group) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(5);
        }
    }

    @Override
    public void increase() {
        if (startX < SCREENX * SCALE && startY < SCREENY - height * SCALE) {
            weight *= SCALE;
            height *= SCALE;
        }
        settingShape.setHeight(height);
        settingShape.setWeight(weight);
    }

    @Override
    public void decrease() {
        double minWeight = 35;
        double minHeight = 29;

        if (weight > minWeight) {
            weight /= SCALE;
        }
        if (height > minHeight) {
            height /= SCALE;
        }
        settingShape.setHeight(height);
        settingShape.setWeight(weight);
    }


    public boolean isSelect() {
        return select;
    }


    @Override
    public boolean isSelect(Point2D point) {

        if ((startX <= point.getX() && point.getX() <= startX + weight) && (startY <= point.getY() && point.getY() <= startY + height)) {
            this.select = true;

        } else {
            this.select = false;
        }

        settingShape.setSelect(select);

        return select;
    }

    @Override
    public void group() {
        group = true;
        settingShape.setGroup(group);
    }

    @Override
    public Shape clone() {
        Shape result = null;
        try {
            result = (Shape) super.clone();
            startX += 10;
            startY += 10;

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public void move(Direct direction) {
        double dx = 0;
        double dy = 0;

        double min;
        double max;
        final double SPEED = 5;

        int selectIndex = 0;

        List<SettingShape> listSetting = new ArrayList<>(Storage.getInstance().getSettingShapeList());
        List<SettingShape> listGroup = null;
        if (group) {
            listGroup = new ArrayList<>();

            for (SettingShape e : listSetting) {
                if (e.isGroup())
                    listGroup.add(e);

            }
        } else {

            for (int i = 0; i < listSetting.size(); i++) {

                if (listSetting.get(i).isSelect()) {
                    selectIndex = i;
                    break;
                }

            }
        }
        switch (direction) {
            case LEFT:
                if (group) {
                    listGroup.sort(Comparator.comparing(SettingShape::getStartX));
                    if (startX > 0) {
                        if (startX == listGroup.get(0).getStartX()) {
                            dx = -SPEED;
                        } else {
                            if (listGroup.get(0).getStartX() > 0) {
                                dx = -SPEED;
                            }
                        }
                    }
                } else {
                    listSetting.sort(Comparator.comparing(SettingShape::getStartX));
                    min = listSetting.get(0).getStartX();
                    max = listSetting.get(listSetting.size() - 1).getStartX();
                    if (startX == max) {
                        listSetting.remove(listSetting.size() - 1);
                    }

                    if (min < startX && startX < max) {

                        for (int i = 0; i < listSetting.size(); i++) {

                            if (listSetting.get(i).isSelect()) {
                                selectIndex = i;
                            }
                        }

                        int k = listSetting.size() - 1;
                        while (k >= selectIndex) {
                            listSetting.remove(listSetting.size() - 1);
                            k--;
                        }
                    }
                    if (startX > 0) {
                        if (startX == min) {
                            dx = -SPEED;
                        } else {
                            for (int i = listSetting.size() - 1; i >= 0; i--) {
                                if (startX > listSetting.get(i).getStartX() + listSetting.get(i).getWeight() + SPEED) {
                                    dx = -SPEED;
                                    break;
                                } else if (startX <= listSetting.get(i).getStartX() + listSetting.get(i).getWeight() + SPEED) {
                                    if (startY > listSetting.get(i).getStartY() + listSetting.get(i).getHeight() + SPEED ||
                                            listSetting.get(i).getStartY() > startY + height + SPEED) {
                                        dx = -SPEED;
                                        break;

                                    } else {
                                        break;
                                    }
                                }

                            }
                        }
                    }
                }

                break;

            case RIGHT:
                if (group) {

                    listGroup.sort(Comparator.comparing(SettingShape::getStartX));

                    if (startX + weight < SCREENX) {
                        if (startX == listGroup.get(listGroup.size() - 1).getStartX()) {
                            dx = SPEED;
                        } else {
                            if (listGroup.get(listGroup.size() - 1).getStartX() + weight < SCREENX) {
                                dx = SPEED;
                            }
                        }
                    }

                } else {
                    listSetting.sort(Comparator.comparing(SettingShape::getStartX));
                    min = listSetting.get(0).getStartX();
                    max = listSetting.get(listSetting.size() - 1).getStartX();
                    if (startX == min) {

                        listSetting.remove(0);
                    }


                    if (min < startX && startX < max) {

                        for (int i = 0; i < listSetting.size(); i++) {

                            if (listSetting.get(i).isSelect()) {
                                selectIndex = i;
                            }
                        }


                        int k = 0;
                        while (k <= selectIndex) {
                            listSetting.remove(0);
                            k++;
                        }
                    }

                    if (startX + weight < SCREENX) {
                        if (startX == max) {
                            dx = SPEED;
                        } else {

                            for (int i = 0; i < listSetting.size(); i++) {
                                if (startX + weight + SPEED < listSetting.get(i).getStartX()) {
                                    dx = SPEED;
                                    break;
                                }
                                if (startX + weight + SPEED >= listSetting.get(i).getStartX()) {
                                    if (startY > listSetting.get(i).getStartY() + listSetting.get(i).getHeight() + SPEED ||
                                            listSetting.get(i).getStartY() > startY + height + SPEED) {
                                        dx = SPEED;
                                        break;
                                    } else {
                                        break;
                                    }


                                }

                            }
                        }

                    }
                }

                break;

            case UP:
                if (group) {
                    listGroup.sort(Comparator.comparing(SettingShape::getStartY));
                    if (startY > 0) {
                        if (startY == listGroup.get(0).getStartY()) {
                            dy = -SPEED;
                        } else {
                            if (listGroup.get(0).getStartY() > 0) {
                                dy = -SPEED;
                            }
                        }
                    }
                } else {
                    listSetting.sort(Comparator.comparing(SettingShape::getStartY));
                    min = listSetting.get(0).getStartY();
                    max = listSetting.get(listSetting.size() - 1).getStartY();

                    if (startY == max) {

                        listSetting.remove(listSetting.size() - 1);
                    }


                    if (min < startY && startY < max) {

                        for (int i = 0; i < listSetting.size(); i++) {

                            if (listSetting.get(i).isSelect()) {
                                selectIndex = i;
                            }
                        }

                        int k = listSetting.size() - 1;
                        while (k >= selectIndex) {
                            listSetting.remove(listSetting.size() - 1);
                            k--;
                        }
                    }
                    if (startY > 0) {

                        if (startY == min) {
                            dy = -SPEED;
                        } else {
                            for (int i = listSetting.size() - 1; i >= 0; i--) {
                                if (startY > listSetting.get(i).getStartY() + listSetting.get(i).getHeight() + SPEED) {
                                    dy = -SPEED;
                                    break;
                                } else if (startY <= listSetting.get(i).getStartY() + listSetting.get(i).getHeight() + SPEED) {
                                    if (startX > listSetting.get(i).getStartX() + listSetting.get(i).getWeight() + SPEED ||
                                            startX + weight + SPEED < listSetting.get(i).getStartX()) {
                                        dy = -SPEED;
                                        break;
                                    } else {
                                        break;
                                    }

                                }

                            }

                        }
                    }
                }
                break;

            case DOWN:
                if (group) {
                    listGroup.sort(Comparator.comparing(SettingShape::getStartY));
                    if (startY + height < SCREENY) {
                        if (startY == listGroup.get(listGroup.size() - 1).getStartY()) {
                            dy = SPEED;
                        } else {
                            if (listGroup.get(listGroup.size() - 1).getStartY() + height < SCREENY) {
                                dy = SPEED;
                            }
                        }
                    }
                } else {

                    listSetting.sort(Comparator.comparing(SettingShape::getStartY));
                    min = listSetting.get(0).getStartY();
                    max = listSetting.get(listSetting.size() - 1).getStartY();

                    if (startY == min) {

                        listSetting.remove(0);
                    }

                    if (min < startY && startY < max) {

                        for (int i = 0; i < listSetting.size(); i++) {

                            if (listSetting.get(i).isSelect()) {
                                selectIndex = i;
                            }
                        }


                        int k = 0;
                        while (k <= selectIndex) {
                            listSetting.remove(0);
                            k++;
                        }
                    }
                    if (startY + height < SCREENY) {

                        if (startY == max) {
                            dy = SPEED;
                        } else {
                            for (int i = 0; i < listSetting.size(); i++) {
                                if (startY + height + SPEED < listSetting.get(i).getStartY()) {
                                    dy = SPEED;
                                    break;
                                }
                                if (startY + height + SPEED >= listSetting.get(i).getStartY()) {

                                    if (startX > listSetting.get(i).getStartX() + listSetting.get(i).getWeight() + SPEED ||
                                            startX + weight + SPEED < listSetting.get(i).getStartX()) {
                                        dy = SPEED;
                                        break;
                                    } else {
                                        break;
                                    }

                                }

                            }
                        }
                    }

                }

                break;
        }

        startX += dx;
        startY += dy;

        settingShape.setStartX(startX);
        settingShape.setStartY(startY);
    }

}



