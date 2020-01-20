package com.cruz.controllers;

import com.cruz.main.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private Button remover;
    @FXML
    private GridPane griddy;
    @FXML
    private Button swapper;
    @FXML
    private TextArea tArea;

    ArrayList<Integer> rowsCols;
    private Student[][] students;
    private boolean swap;

    @FXML
    private void initialize() {
        for (int c = 0; c < students[0].length; c++)
            for (int r = 0; r < students.length; r++) {
                griddy.add(students[r][c].get_studentLabel(), c, r);
            }
    }

    public Controller() {
        rowsCols = new ArrayList<>(4);
        students = new Student[5][5];
        for (int r = 0; r < students.length; r++) {
            for (int c = 0; c < students[0].length; c++) {
                students[r][c] = new Student("null");
                students[r][c].get_studentLabel().setOnMouseClicked(this::select);
            }
        }
    }

    private void select(MouseEvent e) {
        Label source = (Label) e.getSource();
        Integer rowIndex = GridPane.getRowIndex(source);
        Integer colIndex = GridPane.getColumnIndex(source);
        if (swap) {
            source.setTextFill(Paint.valueOf("red"));
            rowsCols.add(rowIndex);
            rowsCols.add(colIndex);
        }
        if (rowsCols.size() == 4 && swap) {
            swap = false;
            flip();
            swapper.setDisable(false);
            update();
        }
    }

    @FXML
    private void swap() {
        swap = true;
        swapper.setDisable(true);
    }

    private void flip() {
        System.out.println("flip");
        int r1 = rowsCols.remove(0);
        int c1 = rowsCols.remove(0);
        int r2 = rowsCols.remove(0);
        int c2 = rowsCols.remove(0);
        Student temp = students[r1][c1];
        students[r1][c1] = students[r2][c2];
        students[r2][c2] = temp;

        students[r1][c1].get_studentLabel().setTextFill(Paint.valueOf("black"));
        students[r2][c2].get_studentLabel().setTextFill(Paint.valueOf("black"));
    }

    @FXML
    private void clear() {
        for (Student[] st : students) {
            for (Student s : st) {
                s.set_studentName("null");
                s.get_studentLabel().setTextFill(Paint.valueOf("black"));
            }
        }
    }

    private void update() {
        System.out.println("update");
        for (Student[] st : students) {
            for (Student s : st) {
                griddy.getChildren().remove(s.get_studentLabel());
            }
        }
        for (int c = 0; c < students[0].length; c++) {
            for (int r = 0; r < students.length; r++) {
                griddy.add(students[r][c].get_studentLabel(), c, r);
            }
        }
    }

    @FXML
    private void addStudentsFromList() {
        System.out.println("add students from list");
        String names = tArea.getText();
        tArea.setText("");
        for (int c = 0; c < students[0].length; c++) {
            for (int r = 0; r < students.length; r++) {
                if (students[r][c].get_studentName().equals("Empty\nSeat")) {
                    if (names.contains(",")) {
                        students[r][c].set_studentName(names.substring(0, names.indexOf(",")));
                        names = names.substring(names.indexOf(",") + 1);
                    } else {
                        students[r][c].set_studentName(names);
                        return;
                    }
                }
            }
        }
    }

    @FXML
    private void help() {
        System.out.println("helping");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("com/cruz/files/images/GenericChair.jpg"));
        alert.setHeaderText(null);
        alert.setContentText("To Remove Students:\nSelect \"Remove\" button and select a student name\n" +
                "To Remove All Students:\nSelect \"Clear Seating Chart\"\n" +
                "To Swap Students:\nSelect the \"Swap\" button and select the names of two students\n" +
                "To Add Students:\n--Type first and last names of students you wish to add\n  -separated by commas on different lines" +
                "(Ex.)\n\tJohn Doe,[ENTER]\n\tPatricia Scott,[ENTER]...\n" +
                "--Click the \"Add Students\" button");
        alert.showAndWait();
    }

    @FXML
    private void export() throws IOException {
        Student.export(students);
    }

    @FXML
    private void loadItUp() throws IOException {//FIXME LOADING INFORMATION TODOFIXME
        Student.load(students);
    }

    @FXML
    private void remove() {
    }
}
