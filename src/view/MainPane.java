package view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import controller.Charge;
import controller.FieldLines;
import infrastructure.Frame;
import infrastructure.Grid;
import infrastructure.PaintingFrame;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class MainPane extends Pane {

    private PaintingFrame pf;
    private FieldLines fl;

    List<Line> lines;

    public MainPane() {
        setPrefSize(1000, 700);

        pf = new PaintingFrame(1000, 700);
        Grid grid = new Grid(pf.getFrame());

        pf.setKeyboard(this);

        widthProperty().addListener((p, o, n) -> {
            pf.setPrefSize(n.doubleValue(), getHeight());
        });
        heightProperty().addListener((p, o, n) -> {
            pf.setPrefSize(getWidth(), n.doubleValue());
        });

        fl = new FieldLines();

        getChildren().add(pf);
        pf.addPaintling(fl);
        // pf.addPaintling(grid);
        Charge charge = new Charge(10, 0, 0, pf);
        Charge charge2 = new Charge(-10, 0, 4.5, pf);
        Charge charge3 = new Charge(10, 0, -4.5, pf);
        Charge charge4 = new Charge(10, 0, 9, pf);

        addCharge(charge2);
        addCharge(charge3);
        addCharge(charge);
        addCharge(charge4);

    }

    private void addCharge(Charge charge) {
        pf.addPaintling(charge);
        fl.addCharge(charge);
        pf.getChildren().add(charge.getView());
        
      
    }

}

