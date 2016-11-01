package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import infrastructure.Frame;
import infrastructure.Paintling;
import infrastructure.Point;
import javafx.scene.paint.Color;
import view.ChargeView;

public class FieldLines extends Paintling {

    private List<Charge> charges;
    private List<Line> fieldLines;
    private Color fieldColor = Color.rgb(70, 130, 180);
    private double dL;
    private static final double ARROW_LENGTH = 40;
    private static final int N = 10;
    private double scaleTemp;

    public FieldLines() {
        charges = new LinkedList<Charge>();
        fieldLines = new LinkedList<Line>();
        // fieldColor = Color.rgb(50, 50, 50);
    }

    public void addCharge(Charge charge) {
        charges.add(charge);
        if (getFrame() == null)
            return;
        dL = 10 / getFrame().getScale();
        // add new lines
        double alpha = (charge.getCharge() > 0) ? 0 : Math.PI / N;
        for (int i = 0; i < N; i++) {
            Line line = new Line(charge.getCharge() > 0);
            line.addPoint(
                    charge.getX() + (ChargeView.RADIUS / getFrame().getScale()) * Math.cos(alpha + 2 * i * Math.PI / N),
                    charge.getY()
                            + (ChargeView.RADIUS / getFrame().getScale()) * Math.sin(alpha + 2 * i * Math.PI / N));
            fieldLines.add(line);
            charge.addLine(line);
        }
    }

    private Point computeEField(Point point) {
        Point eField = new Point(0, 0);
        for (Charge charge : charges) {
            double eX = charge.getCharge() * (point.getX() - charge.getX())
                    / Math.pow(point.calculateDistance(charge.getLocation()), 3);
            double eY = charge.getCharge() * (point.getY() - charge.getY())
                    / Math.pow(point.calculateDistance(charge.getLocation()), 3);
            eField.setX(eField.getX() + eX);
            eField.setY(eField.getY() + eY);
        }
        return eField;

    }

    private boolean isInFrame(Point point) {
        return (point.getX() > getFrame().getMinX() && point.getX() < getFrame().getMaxX()
                && point.getY() > getFrame().getMinY() && point.getY() < getFrame().getMaxY());
    }

    public void updateLines() {
        for (Line line : fieldLines) {
            Point point = line.getLastPoint();
            Point tmp = new Point(0, 0);
            int x = 0;
            while (isInFrame(point) && isAwayFromCharges(point) && !line.isSealed()) {
                Point eField = computeEField(point);
                double m = eField.getMagnitude();
                eField.setX(eField.getX() / m);
                eField.setY(eField.getY() / m);
                if (!line.isPositive()) {
                    eField.setX(-eField.getX());
                    eField.setY(-eField.getY());
                }
                point = new Point(point.getX() + dL * eField.getX(), point.getY() + dL * eField.getY());
                line.addPoint(point);
                if (x >= 1000) {
                    if (tmp.getX() + eField.getX() < 0.000001 && eField.getY() + tmp.getY() < 0.000001) {
                        line.setSealed(true);
                        System.out.println("STOP");
                        x = 0;
                        break;
                    }
                }
                tmp = eField;

                x++;
            }
        }
    }

    private boolean isAwayFromCharges(Point point) {
        for (Charge charge : charges) {
            if (point.calculateDistance(charge.getLocation()) < 5 / getFrame().getScale())
                return false;
        }
        return true;
    }

    @Override
    public void update() {
        for (Charge charge : charges) {
            if (charge.isChanged()) {
                charge.setChanged(false);
                clearAll();
                redo();
                scaleTemp = getFrame().getScale();
            }
        }
        if (getFrame().getScale() / scaleTemp > 1.5 || getFrame().getScale() / scaleTemp < 1 / 1.5) {
            clearAll();
            redo();
            scaleTemp = getFrame().getScale();
        }

        dL = 10 / getFrame().getScale();
        updateLines();

        gc.setStroke(fieldColor);
        gc.setLineWidth(0.7);
        for (Line line : fieldLines) {
            for (int i = 0; i < line.getSize() - 2; i++) {
                if (!isInFrame(line.getPoint(i))) {
                    i++;
                    continue;
                }
                double x1 = getFrame().computePixelX(line.getPoint(i).getX());
                double x2 = getFrame().computePixelX(line.getPoint(i + 1).getX());
                double y1 = getFrame().computePixelY(line.getPoint(i).getY());
                double y2 = getFrame().computePixelY(line.getPoint(i + 1).getY());
                if (i % 41 == 10) {
                    if (line.isPositive())
                        drawArrow(new Point(x1, y1), new Point(x2, y2));
                    else
                        drawArrow(new Point(x2, y2), new Point(x1, y1));
                }
                gc.strokeLine(x1, y1, x2, y2);
            }
        }
    }

    private void redo() {
        dL = 10 / getFrame().getScale();
        for (Charge charge : charges) {
            // add new lines
            charge.getLines().clear();
            double alpha = (charge.getCharge() > 0) ? 0 : Math.PI / N;
            for (int i = 0; i < N; i++) {
                Line line = new Line(charge.getCharge() > 0);
                line.addPoint(
                        charge.getX()
                                + (ChargeView.RADIUS / getFrame().getScale()) * Math.cos(alpha + 2 * i * Math.PI / N),
                        charge.getY()
                                + (ChargeView.RADIUS / getFrame().getScale()) * Math.sin(alpha + 2 * i * Math.PI / N));
                fieldLines.add(line);
                charge.addLine(line);
            }
        }
    }

    private void clearAll() {
        for (Line line : fieldLines) {
            line.clear();
        }
        for (Charge charge : charges) {
            charge.getLines().clear();
        }
        fieldLines.clear();
    }

    private void drawArrow(Point a, Point b) {
        Point q = b.subtract(a).normal().unit().multiply(ARROW_LENGTH * 0.1);
        gc.strokeLine(b.getX(), b.getY(), a.getX() + q.getX(), a.getY() + q.getY());
        gc.strokeLine(b.getX(), b.getY(), a.getX() - q.getX(), a.getY() - q.getY());

    }

    @Override
    public void setFrame(Frame frame) {
        super.setFrame(frame);
        scaleTemp = frame.getScale();
    }

    private void drawEqui(Point point) {
        dL = 10 / getFrame().getScale();
        Line line = new Line(true);
        Point point2 = point;
        do {
            Point u = computeEField(point).normal().unit();
            point2 = new Point(point2.getX() + u.getX() * dL, point2.getY() + u.getY() * dL);

        } while (true);

    }

}
