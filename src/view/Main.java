package view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainPane mainPane = new MainPane();
        Scene scene = new Scene(mainPane);
        mainPane.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("WHAAT");
        }
    }
}
