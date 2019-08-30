package com.task11;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveFigure {
    private StringBuilder sb = new StringBuilder();
    private Gson gson;
    private final GraphicsContext gc;
    private List<Shape> list = new ArrayList<>();


    public SaveFigure(GraphicsContext gc) {
        this.gc = gc;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }


    public List<Shape> loadFigure() {

        Shape newShape = null;
        readFromFile();
        String json = sb.toString();
        Storage restoredShape = gson.fromJson(json, Storage.class);
        List<SettingShape> newList;

        newList = restoredShape.getSettingShapeList();

        for (SettingShape shape : newList) {

            if (shape.getPatern().getCode().equals("Triangle")) {
                newShape = new Triangle(gc, shape.getStartX(), shape.getStartY(), shape.getWeight(), shape.getHeight(), shape.isSelect(), shape.isGroup(), shape.getColor());
            } else if (shape.getPatern().getCode().equals("Rect")) {
                newShape = new Rect(gc, shape.getStartX(), shape.getStartY(), shape.getWeight(), shape.getHeight(), shape.isSelect(), shape.isGroup(), shape.getColor());
                newShape.draw();
            } else if (shape.getPatern().getCode().equals("Circle")) {
                newShape = new Circle(gc, shape.getStartX(), shape.getStartY(), shape.getWeight(), shape.getHeight(), shape.isSelect(), shape.isGroup(), shape.getColor());
            }

            if (newShape != null) {
                list.add(newShape);
            }
        }

        return list;
    }

    public void saveFigure() {

        String output = gson.toJson(Storage.getInstance());
        try {
            saveToFile(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveToFile(String output) throws Exception {

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("figure.txt")))) {
            writer.write(output);
        }
    }


    private void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("figure.txt"))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
