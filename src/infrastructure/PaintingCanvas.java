package infrastructure;

import java.util.LinkedList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PaintingCanvas extends Canvas {

    private Color backgroundColor = Color.WHITE;
    private GraphicsContext gc;
    private boolean dragBegan;
    private Frame frame;
    private List<Paintling> paintlings;
    private double oldMouseX, oldMouseY;

    public PaintingCanvas(double width, double height) {
        frame = new Frame();
        gc = getGraphicsContext2D();

        paintlings = new LinkedList<Paintling>();
        //
        frame.setScale(80);
        //
        updateSize(width, height);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(15));

        frame.setxOffset(-width / 2);
        frame.setyOffset(height / 2);

        addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            handleMousePressed(e);
        });

        addEventHandler(MouseEvent.MOUSE_DRAGGED, (e) -> {
            handleMouseDragged(e);
        });

        addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
            handleMouseReleased(e);
        });

        dragBegan = false;
    }

    private void handleMouseReleased(MouseEvent e) {
        dragBegan = false;
    }

    private void handleMouseDragged(MouseEvent e) {
        double dX = e.getX() - oldMouseX;
        double dY = e.getY() - oldMouseY;
        oldMouseX = e.getX();
        oldMouseY = e.getY();
        update(frame.getxOffset() - dX, frame.getyOffset() + dY);
    }

    private void handleMousePressed(MouseEvent e) {
        if (!dragBegan) {
            dragBegan = true;
            oldMouseX = e.getX();
            oldMouseY = e.getY();
        }
    }

    public void update(double xOffset, double yOffset) {
        frame.setxOffset(xOffset);
        frame.setyOffset(yOffset);
        update();
    }

    public void update() {
        frame.updateMinAndMaxPoints();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(backgroundColor);
        gc.fillRect(0, 0, getWidth(), getHeight());
        for (Paintling paintling : paintlings) {
            paintling.repaint();
        }
    }

    public void updateSize(double width, double height) {
        setWidth(width);
        setHeight(height);
        frame.setWidth(width);
        frame.setHeight(height);
        update();
    }

    public void zoomIn() {
        setScaleAndAdjust(frame.getScale() * 1.01);
    }

    public void zoomOut() {
        setScaleAndAdjust(frame.getScale() * (1.0 - 0.01));
    }

    private void setScaleAndAdjust(double scale) {
        double deltaScale = scale - frame.getScale();
        double middleX = (frame.getMinX() + frame.getMaxX()) / 2;
        double middleY = (frame.getMinY() + frame.getMaxY()) / 2;
        frame.setxOffset(frame.getxOffset() + deltaScale * middleX);
        frame.setyOffset(frame.getyOffset() + deltaScale * middleY);
        frame.setScale(scale);
        update();
    }

    public Frame getFrame() {
        return frame;
    }

    public void addPaintling(Paintling paintling) {
        if (paintling != null) {
            paintlings.add(paintling);
            paintling.setFrame(frame);
            paintling.setGC(gc);
        }
    }
    
    
}