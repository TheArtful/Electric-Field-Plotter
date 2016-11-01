package infrastructure;

import javafx.scene.canvas.GraphicsContext;

public abstract class Paintling {

    private Frame frame;
    protected GraphicsContext gc;

    public Frame getFrame(){
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public void repaint() {
        update();
    }

    public abstract void update();

    public void setGC(GraphicsContext gc) {
        this.gc = gc;
    }

}
