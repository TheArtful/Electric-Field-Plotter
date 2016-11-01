package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ChargeView extends Group {

    public static final double RADIUS = 20;
    private Circle circle;
    private static final Color NEGATIVE_COLOR = Color.web("#003366");
    private static final Color POSITIVE_COLOR = Color.web("#FF0000");

    public ChargeView() {
        circle = new Circle(RADIUS);
        circle.setStroke(Color.WHITE);
        getChildren().add(circle);
    }

    public void setSign(boolean isPositive) {
        double l = 2 * RADIUS * 0.6;
        Line line = new Line(-l / 2, 0, l / 2, 0);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(3);
        getChildren().add(line);
        if (isPositive) {
            Line line2 = new Line(0, -l / 2, 0, l / 2);
            line2.setStroke(Color.WHITE);
            line2.setStrokeWidth(3);

            getChildren().add(line2);

            circle.setFill(POSITIVE_COLOR);
        } else {
            circle.setFill(NEGATIVE_COLOR);
        }

    }

}
