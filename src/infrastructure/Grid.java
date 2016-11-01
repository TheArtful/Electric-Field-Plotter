package infrastructure;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Grid extends Paintling {

    private static final double DASH_LENGTH = 20;
    private static final Color GRID_COLOR = Color.rgb(50, 50, 50);
    private static  Color AXIS_COLOR = Color.WHITE;
    private static final double MIN_LENGTH = 60;
    private static final double MAX_LENGTH = 100;
    private Frame frame;
    private double currentUnit;

    public Grid(Frame frame) {
        super();
        this.frame = frame;
        currentUnit = 1;
        
        AXIS_COLOR = Color.WHITE;
       
    }

    private void paintXGrid() {

        double y;
        if (frame.getyOffset() < 10) {
            y = 10;
        } else if (frame.getyOffset() > frame.getHeight() - 10) {
            y = frame.getHeight() - 10;
        } else
            y = getFrame().computePixelY(0);
        // Change painting color to axis color
        gc.setStroke(AXIS_COLOR);
        gc.setFill(AXIS_COLOR);
        // draw the axis line
        gc.strokeLine(0, y, frame.getWidth(), y);
        // draw the rest of the grid
        int startingPoint = (int) (frame.getMinX() / currentUnit);
        int endingPoint = (int) (frame.getMaxX() / currentUnit);
        if (startingPoint > 0)
            startingPoint++;
        if (endingPoint < 0)
            endingPoint--;

        for (int i = startingPoint; i <= endingPoint; i++) {
            if (i == 0)
                continue;
            paintYGridLine(i * currentUnit, i * currentUnit, y);
        }
    }

    private void paintYGridLine(double number, double x, double y) {
        // Draw grid line
        x = getFrame().computePixelX(x);
        gc.setStroke(GRID_COLOR);
        gc.setFill(GRID_COLOR);
        gc.strokeLine(x, 0, x, frame.getHeight());
        // Draw axis dash
        gc.setStroke(AXIS_COLOR);
        gc.setFill(AXIS_COLOR);
        gc.strokeLine(x, y - DASH_LENGTH / 2, x, y + DASH_LENGTH / 2);
        number = (int) (number * 10000) / 10000.0;
        Text t = new Text((int) number == number ? String.valueOf((int) number) : String.valueOf(number));
        gc.fillText(t.getText(), x - t.getLayoutBounds().getWidth() / 2, y + 26, t.getLayoutBounds().getWidth());
    }

    private void paintXGridLine(double number, double x, double y) {
        y = getFrame().computePixelY(y);
        // Draw grid line
        gc.setStroke(GRID_COLOR);
        gc.setFill(GRID_COLOR);
        gc.strokeLine(0, y, frame.getWidth(), y);
        // Draw axis dash
        gc.setStroke(AXIS_COLOR);
        gc.setFill(AXIS_COLOR);
        gc.strokeLine(x - DASH_LENGTH / 2, y, x + DASH_LENGTH / 2, y);
        number = (int) (number * 10000) / 10000.0;
        Text t = new Text((int) number == number ? String.valueOf((int) number) : String.valueOf(number));
        gc.fillText(t.getText(), x + 15, y + t.getLayoutBounds().getHeight() / 4);
    }

    private void paintYGrid() {
        gc.setStroke(AXIS_COLOR);
        gc.setFill(AXIS_COLOR);
        // draw the axis line
        double x;
        if (-frame.getxOffset() < 10)
            x = 10;
        else if (-frame.getxOffset() > frame.getWidth() - 10)
            x = frame.getWidth() - 10;
        else
            x = getFrame().computePixelX(0);
        gc.strokeLine(x, 0, x, frame.getHeight());
        // draw the rest of the grid
        int startingPoint = (int) (frame.getMinY() / (currentUnit));
        int endingPoint = (int) (frame.getMaxY() / (currentUnit));
        if (startingPoint > 0)
            startingPoint++;
        if (endingPoint < 0)
            endingPoint--;
        for (int i = startingPoint; i <= endingPoint; i++) {
            if (i == 0)
                continue;
            paintXGridLine(i * currentUnit, x, i * currentUnit);
        }
    }

    private void calculateUnit() {
        boolean a = false, b = false;
        if (currentUnit * frame.getScale() < MIN_LENGTH) {
            a = true;
        } else if (currentUnit * frame.getScale() > MAX_LENGTH) {
            b = true;
        }
        if (!a && !b)
            return;
        currentUnit = 1;
        double factorA, factorB;
        if (frame.getScale() < MIN_LENGTH) {
            factorA = 5;
            factorB = 2;
            currentUnit = 1;
            while (currentUnit * factorA * frame.getScale() < MAX_LENGTH)
                currentUnit *= factorA;
            while (currentUnit * frame.getScale() * factorB < MAX_LENGTH)
                currentUnit *= factorB;
        } else {
            factorA = 0.2;
            factorB = 0.5;
            while (currentUnit * frame.getScale() * factorA > MIN_LENGTH)
                currentUnit *= factorA;
            while (currentUnit * frame.getScale() * factorB > MIN_LENGTH)
                currentUnit *= factorB;
        }
    }
    @Override
    public void update() {
        calculateUnit();
        paintXGrid();
        paintYGrid();
    }

}
