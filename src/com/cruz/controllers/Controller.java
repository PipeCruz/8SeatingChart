package com.cruz.controllers;

import com.cruz.main.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Controller {
    boolean swap;
    @FXML
    private GridPane griddy;

    private Student[][] students;
    @FXML
    private Button swapper;
    @FXML
    private TextArea tArea;


    //Method to check
    ArrayList<Integer> rowsCols = new ArrayList<>();

    public Controller() {
//        System.out.println("controller");
        students = new Student[5][5];
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                students[r][c] = new Student("null");
                students[r][c].get_studentLabel().setOnMouseClicked(this::select);
            }
        }
    }

    @FXML
    private void initialize() {//todo load from previous .csvs
//        System.out.println("initialize");
        for (int c = 0; c < 5; c++)
            for (int r = 0; r < 5; r++) {
                griddy.add(students[r][c].get_studentLabel(), c, r);
            }
    }

    private void select(MouseEvent e) {
        Label source = (Label) e.getSource();
        Integer rowIndex = GridPane.getRowIndex(source);
        Integer colIndex = GridPane.getColumnIndex(source);
        if (swap) {
            source.setTextFill(Paint.valueOf("red"));
            //THIS ORDER IS VERY IMPORTANT
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
        for (int c = 0; c < 5; c++)
            for (int r = 0; r < 5; r++) {
                griddy.add(students[r][c].get_studentLabel(), c, r);
            }
    }

    @FXML
    private void export() throws IOException {
        Student.export(students);
    }

    @FXML
    private void addStudentsFromList() {
        System.out.println("add students from list");
        String names = tArea.getText();
        tArea.setText("");
        for (int c = 0; c < 5; c++) {
            for (int r = 0; r < 5; r++) {
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
//        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        stage.getIcons().add(
//                new Image("/Pictures/icon.png"));
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
    private void loadItUp() throws IOException {//FIXME LOADING INFORMATION TODOFIXME
        //todo refactor into student class?
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Importing");
//        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        stage.getIcons().add(
//                new Image("/Pictures/icon.png"));
        alert.setHeaderText(null);
        alert.setContentText("If no file is found, one will be created with the current seat assignments");
        alert.showAndWait();
        BufferedReader reader;
        try {//fixme
            reader = new BufferedReader(new FileReader("seatingChart.csv"));
            for (int r = 0; r < 5; r++) {
                String cur = reader.readLine();
                for (int c = 0; c < 5; c++) {
                    System.out.println("r " + r + "c " + c + "cur\n" + cur);
                    String name = cur.substring(0, cur.indexOf(","));
                    System.out.println("name " + name);
                    cur = cur.substring(cur.indexOf(name) + name.length() + 1);
                    System.out.println("trimmed name " + name);
                    students[r][c].set_studentName(name);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            alert.setContentText("File by name 'seatingChart.csv' not found, so it has been created");//fixme EXCEPTION
            Student.export(students);
            alert.showAndWait();
        }
    }

}
