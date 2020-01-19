package com.cruz.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Controller {
    boolean swap;
    @FXML
    private GridPane griddy;

    private Student[][] students;
    @FXML
    private Button swapper;

    @FXML
    private void initialize() {
        System.out.println("initialize");
        for (int c = 0; c < 5; c++)
            for (int r = 0; r < 5; r++) {
                griddy.add(students[r][c].get_studentLabel(), c, r);
            }
    }

    //Method to check
    ArrayList<Integer> rowsCols = new ArrayList<>();

    public Controller() {
        System.out.println("controller");
        students = new Student[5][5];
        for (int r = 0; r < students.length; r++) {
            for (int c = 0; c < students[r].length; c++) {
                students[r][c] = new Student("null");
                students[r][c].get_studentLabel().setOnMouseClicked(this::select);
            }
        }
        students[0][0].set_studentName("robert viera");
        students[2][2].set_studentName("fasdlfjk sd");
        students[4][3].set_studentName("george bush");
    }

    private void select(MouseEvent e) {
        Label source = (Label) e.getSource();
        Integer rowIndex = GridPane.getRowIndex(source);
        Integer colIndex = GridPane.getColumnIndex(source);
        if (swap) {
            source.setTextFill(Paint.valueOf("blue"));
            //THIS ORDER IS VERY IMPORTANT
            rowsCols.add(rowIndex);
            rowsCols.add(colIndex);
        }

        System.out.println(students[rowIndex][colIndex]);

        if (rowsCols.size() == 4 && swap) {
            swap = false;
            flip();
            swapper.setDisable(false);
            update();
        }
    }

    @FXML
    private void swap(MouseEvent mouseEvent) {
        swap = true;
        swapper.setDisable(true);
    }

    private void flip() {
        System.out.println("flip");
        int r1 = rowsCols.remove(0);
        int c1 = rowsCols.remove(0);
        int r2 = rowsCols.remove(0);
        int c2 = rowsCols.remove(0);
        System.out.println(Arrays.toString(students[0]));
        Student temp = students[r1][c1];
        students[r1][c1] = students[r2][c2];
        students[r2][c2] = temp;
        System.out.println(Arrays.toString(students[0]));
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
    private void export(MouseEvent e) throws IOException {//fixme boolean b
        Student.export(students);
        Button b = (Button) e.getSource();
        b.setDisable(true);
    }

}
