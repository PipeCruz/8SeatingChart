package com.cruz.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Introduction {

    public void commence(MouseEvent mouseEvent) throws IOException {
        Node node = (Node) mouseEvent.getSource();
        Scene scene = node.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        stage.setHeight(620);
        stage.setWidth(890);
        scene.setRoot(FXMLLoader.load(getClass().getResource("/com/cruz/files/fxml/Main.fxml")));
        stage.setTitle("Student Seating Simulator");
        stage.centerOnScreen();
    }
}
