package controller;

import java.util.LinkedList;
import java.util.List;

import infrastructure.PaintingFrame;
import infrastructure.Paintling;
import infrastructure.Point;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import view.ChargeView;

public class Charge extends Paintling {

    private ChargeView cv;
    private Point point;
    private double charge;
    private double oldMouseX, oldMouseY;
    private PaintingFrame pf;
    private boolean isChanged = false;
    private boolean dragBegan = false;

    private List<Line> lines;

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public void addLine(Line line) {
        if (lines == null) {
            lines = new LinkedList<Line>();
        }
        lines.add(line);
    }

    public List<Line> getLines() {
        return lines;
    }

    public Charge(double charge, double x, double y, PaintingFrame pf) {
        lines = new LinkedList<Line>();
        this.charge = charge;
        point = new Point(x, y);
        cv = new ChargeView();
        cv.setSign(charge > 0);
        this.pf = pf;

        cv.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            handleMousePressed(e);
        });

        cv.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e) -> {
            handleMouseDragged(e);
        });

        cv.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
            handleMouseReleased(e);
        });

    }

    private void handleMouseReleased(MouseEvent e) {
        dragBegan = false;
    }

    private void handleMouseDragged(MouseEvent e) {
        double dX = e.getSceneX() - oldMouseX;
        double dY = e.getSceneY() - oldMouseY;
        oldMouseX = e.getSceneX();
        oldMouseY = e.getSceneY();
        point.setX(point.getX() + dX / getFrame().getScale());
        point.setY(point.getY() - dY / getFrame().getScale());
        isChanged = true;
        pf.update();
    }

    private void handleMousePressed(MouseEvent e) {
        if (!dragBegan) {
            dragBegan = true;
            oldMouseX = e.getSceneX();
            oldMouseY = e.getSceneY();
        }
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }

    public Charge() {
        this(0, 0, 0, null);
    }

    public double getX() {
        return point.getX();
    }

    public void setX(double x) {
        point.setX(x);
        ;
    }

    public double getY() {
        return point.getY();
    }

    public void setY(double y) {
        point.setY(y);
    }

    public void setLocation(double x, double y) {
        point.setX(x);
        point.setY(y);
    }

    @Override
    public void update() {
        cv.setLayoutX(getFrame().computePixelX(point.getX()));
        cv.setLayoutY(getFrame().computePixelY(point.getY()));
    }

    public Node getView() {
        return cv;
    }

    public double getCharge() {
        return charge;
    }

    public Point getLocation() {
        return point;
    }

}
