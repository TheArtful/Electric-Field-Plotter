package infrastructure;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class PaintingFrame extends Pane {

    private PaintingCanvas pc;
    private Frame frame;
    private KeyboardHandler kh;

    public PaintingFrame(double width, double height) {
        setPrefSize(width, height);
        pc = new PaintingCanvas(width, height);
        getChildren().add(pc);
        frame = pc.getFrame();
        widthProperty().addListener((p, o, n) -> {
            pc.updateSize(n.doubleValue(), getHeight());
        });
        heightProperty().addListener((p, o, n) -> {
            pc.updateSize(getWidth(), n.doubleValue());
        });

    }

    public void addPaintling(Paintling paintling) {
        pc.addPaintling(paintling);
    }

    public Frame getFrame() {
        return frame;
    }

    public void setKeyboard(Region pane) {
        kh = new KeyboardHandler(this, pc);
        pane.addEventHandler(KeyEvent.KEY_PRESSED, kh);
        pane.addEventHandler(KeyEvent.KEY_RELEASED, kh);
    }

    public void update() {
        pc.update();
    }

}
