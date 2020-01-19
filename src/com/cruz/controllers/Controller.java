package com.cruz.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.ArrayList;


public class Controller {
    @FXML
    private GridPane griddy;

    private Student[][] students;
    public Controller(){
        System.out.println("controller");
        students = new Student[5][5];
        for(int r = 0; r < students.length; r++){
            for(int c = 0; c < students[r].length; c++){
                students[r][c] = new Student("null");
                students[r][c].get_studentLabel().setOnMouseClicked(this::select);
            }
        }
    }

    @FXML
    private void initialize(){
        System.out.println("initialize");
        for(int c = 0; c < 5; c++)
            for(int r = 0; r < 5; r++){
                griddy.add(students[r][c].get_studentLabel(),c,r);
                System.out.println(students[r][c]);
            }
    }

    //Method to check
    ArrayList<Integer> rowsCols = new ArrayList<>();
    
    private void select(MouseEvent e) {
        Label source = (Label) e.getSource();
        Integer rowIndex = GridPane.getRowIndex(source);
        Integer colIndex = GridPane.getColumnIndex(source);
        students[rowIndex][colIndex] = null;
        source.setTextFill(Paint.valueOf("blue"));
        for(int c = 0; c < 5; c++)
            for(int r = 0; r < 5; r++){
                System.out.println(students[r][c]);
            }
    }

    @FXML
    private void clear(){
        for (Student[] st : students) {
            for (Student s : st) {
                s.set_studentName("null");
                s.get_studentLabel().setTextFill(Paint.valueOf("black"));
            }
        }
    }

    @FXML
    private void export(MouseEvent e) throws IOException {//fixme boolean b
        Student.export(students);
        Button b = (Button) e.getSource();
        b.setDisable(true);
    }
}
