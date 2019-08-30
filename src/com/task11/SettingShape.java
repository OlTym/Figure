package com.task11;
import javafx.scene.paint.Color;

public class SettingShape {
    private double startX;
    private double startY;
    private double weight;
    private double height;
    private boolean select;
    private boolean group;
    private Color color;

    private Pattern patern;

    public SettingShape(double startX, double startY, double weight, double height, Color color) {
        this.startX = startX;
        this.startY = startY;

        this.weight = weight;
        this.height = height;
        this.color = color;
        select = false;

    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    void setPatern(Pattern patern) {
        this.patern = patern;
    }

    public boolean isSelect() {
        return select;
    }

    public Pattern getPatern() {
        return patern;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setSelect(boolean select) {
        this.select = select;

    }

    public boolean isGroup() {
        return group;

    }

    public void setGroup(boolean group) {
        this.group = group;

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SettingShape{");
        sb.append("startX='").append(startX).append('\'');
        sb.append(", startY='").append(startY).append('\'');
        sb.append(", weight='").append(weight).append('\'');
        sb.append(", height='").append(height).append('\'');
        sb.append(", select='").append(select).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append(", patern='").append(patern).append('\'');
        sb.append('}');

        return sb.toString();

    }


}
