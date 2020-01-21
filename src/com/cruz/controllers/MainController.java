package com.cruz.controllers;

import com.cruz.internal.Student;
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

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainController {

    @FXML
    private Button remover;
    @FXML
    private GridPane griddy;
    @FXML
    private Button swapper;
    @FXML
    private TextArea tArea;

    private ArrayList<Integer> rowsCols;
    private Student[][] students;
    private boolean swap, removeActive;


    public MainController() {
        rowsCols = new ArrayList<>(4);
        students = new Student[5][5];
        for (int r = 0; r < students.length; r++) {
            for (int c = 0; c < students[0].length; c++) {
                students[r][c] = new Student("null");
                students[r][c].get_studentLabel().setOnMouseClicked(this::select);
            }
        }
    }

    @FXML
    private void initialize() {
        tArea.setOpacity(3 / 4d);//fixme
        tArea.setPromptText("Student One,\rStudent Two,\rStudent Three,\rStudent ...");
        for (int c = 0; c < students[0].length; c++) {
            for (int r = 0; r < students.length; r++) {
                griddy.add(students[r][c].get_studentLabel(), c, r);
            }
        }
    }

    private void select(MouseEvent e) {
        Label source = (Label) e.getSource();
        Integer rowIndex = GridPane.getRowIndex(source);
        Integer colIndex = GridPane.getColumnIndex(source);
        if (removeActive) {
            students[rowIndex][colIndex].set_studentName("null");
            return;
        }
        if (swap) {
            source.setTextFill(Paint.valueOf("darkgreen"));
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
    private void remove() {
        removeActive = !removeActive;
        if (removeActive) {
            remover.setText("Remover ON");
            remover.setTextFill(Paint.valueOf("red"));
        } else {
            remover.setText("Remover OFF");
            remover.setTextFill(Paint.valueOf("blue"));
        }
    }

    @FXML
    private void swap() {
        swap = true;
        swapper.setDisable(true);
    }

    private void flip() {
        int r1 = rowsCols.remove(0);
        int c1 = rowsCols.remove(0);
        int r2 = rowsCols.remove(0);
        int c2 = rowsCols.remove(0);
        Student temp = students[r1][c1];
        students[r1][c1] = students[r2][c2];
        students[r2][c2] = temp;

        students[r1][c1].get_studentLabel().setTextFill(
                students[r1][c1].get_exportName().equals("EMPTY")
                        ?
                        Paint.valueOf("darkblue")
                        :
                        Paint.valueOf("darkred"));
        students[r2][c2].get_studentLabel().setTextFill(
                students[r2][c2].get_exportName().equals("EMPTY")
                        ?
                        Paint.valueOf("darkblue")
                        :
                        Paint.valueOf("darkred"));
    }

    @FXML
    private void clear() {
        for (Student[] st : students) {
            for (Student s : st) {
                s.set_studentName("null");
                s.get_studentLabel().setTextFill(Paint.valueOf("darkblue"));
            }
        }
    }

    private void update() {
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
        String names = tArea.getText();
        if (!names.contains(" ")) return;
        tArea.setText("");
        for (int c = 0; c < students[0].length; c++) {
            for (Student[] student : students) {
                if (student[c].get_studentName().equals("Empty\nSeat")) {
                    if (names.contains(",")) {
                        student[c].set_studentName(names.substring(0, names.indexOf(",")));
                        names = names.substring(names.indexOf(",") + 1);
                    } else {
                        student[c].set_studentName(names);
                        return;
                    }
                }
            }
        }
    }
    @FXML
    private void help() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("com/cruz/files/images/icon.jpg"));
        alert.setHeaderText(null);
        alert.setContentText("To Remove Students:\nSelect \"Remove\" button and select a student name\n" +
                "To Remove All Students:\nSelect \"Clear Seating Chart\"\n" +
                "To Swap Students:\nSelect the \"Swap\" button and select the names of two students\n" +
                "To Add Students:\n--Type first and last names of students you wish to add\n  -separated by commas on different lines" +
                "(Ex.)\n\tJohn Doe,[ENTER]\n\tPatricia Scott,[ENTER]...\n" +
                "--Click the \"Add Students\" button\n" +
                "Exporting will export the chart in a file by the name of\n--'seatingChart.csv'--\n" +
                "Importing will attempt to import the file stated above");
        alert.showAndWait();
    }

    @FXML
    private void export() throws IOException {
        Student.export(students);
    }

    @FXML
    private void loadItUp() throws IOException {
        Student.load(students);
    }

    @FXML
    private void gotoGithub() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/PipeCruz/SeatingChart"));
            } catch (IOException | URISyntaxException ex) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/PipeCruz/"));
                } catch (Exception ignored) {
                }
            }
        }
    }
}
