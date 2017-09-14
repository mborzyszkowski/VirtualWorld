package controller;

import java.awt.Color;
import java.awt.Shape;

public class WorldCell {
    private int x;
    private int y;
    private Color color;
    private boolean isEmpty;
    private Shape shape;

    public WorldCell(int x, int y, Color color, boolean isEmpty, Shape shape) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isEmpty = isEmpty;
        this.shape = shape;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
    
}
