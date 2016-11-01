package infrastructure;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardHandler implements EventHandler<KeyEvent> {

    private volatile boolean isZoomingIn;
    private volatile boolean isZoomingOut;
    private PaintingCanvas pc;
    private PaintingFrame pf;
    private Thread t;

    public KeyboardHandler(PaintingFrame pf, PaintingCanvas pc) {
        this.pf = pf;
        this.pc = pc;

        isZoomingIn = false;
        isZoomingOut = false;

        t = new Thread(() -> {
            while (true) {
                if (isZoomingIn) {
                    Platform.runLater(() -> {
                        pc.zoomIn();
                    });
                }
                if (isZoomingOut) {
                    Platform.runLater(() -> {
                        pc.zoomOut();
                    });
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void handle(KeyEvent e) {

        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            handleKeyPressed(e);
        } else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
            handleKeyReleased(e);
        }

    }

    private void handleKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.I) {
            isZoomingIn = true;
            isZoomingOut = false;
        }
        if (e.getCode() == KeyCode.O) {
            isZoomingOut = true;
            isZoomingIn = false;
        }
    }

    private void handleKeyReleased(KeyEvent e) {
        if (e.getCode() == KeyCode.I) {
            isZoomingIn = false;
        }
        if (e.getCode() == KeyCode.O) {
            isZoomingOut = false;
        }
    }

}
