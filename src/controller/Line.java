package controller;

import java.util.LinkedList;
import java.util.List;

import infrastructure.Point;

public class Line {

    private List<Point> points;
    private boolean isPositive;
    private boolean sealed;

    public Line(boolean isPoisitve) {
        points = new LinkedList<Point>();
        this.isPositive = isPoisitve;
    }

    public boolean isSealed() {
        return sealed;
    }

    public void setSealed(boolean sealed) {
        this.sealed = sealed;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void addPoint(double x, double y) {
        addPoint(new Point(x, y));
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public Point getLastPoint() {
        return points.get(points.size() - 1);
    }

    public void clear() {
        points.clear();
        sealed = false;
    }

    public void clearExceptStart() {
        Point point = points.get(0);
        points.clear();
        points.add(point);
        sealed = false;

    }

    public int getSize() {
        return points.size();
    }

}
