package com.task11;

import javafx.geometry.Point2D;

public interface Shape extends Cloneable {

  void increase();

  void decrease();

  void move(Direct direction);

  void draw();

  void select(boolean select);

  boolean isSelect(Point2D point);

  void group();

  Shape clone();


}
