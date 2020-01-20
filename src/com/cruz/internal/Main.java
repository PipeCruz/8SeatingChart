package com.cruz.internal;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * @author Felipe Cruz
     * Homework #8: Seating Chart
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/cruz/files/fxml/Introduction.fxml"));
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new Image("com/cruz/files/images/icon.jpg"));
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            try {
                stop();
            } catch (Exception ignored) {
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {launch(args);}
}
