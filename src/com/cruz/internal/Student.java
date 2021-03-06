package com.cruz.internal;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;

public class Student {
    //Instance fields
    private String _studentName, _exportName;
    private final Label _studentLabel;

    //getters
    public String get_studentName() {
        return _studentName;
    }

    public String get_exportName() {
        return _exportName;
    }

    public Label get_studentLabel() {
        return _studentLabel;
    }

    //constructs a student object, aligning it to be centered in the gridpane
    public Student(String name) {
        _studentLabel = new Label();
        _studentLabel.getStyleClass().add("outline");
        set_studentName(name);
        _studentLabel.setPrefSize(140, 120);
        _studentLabel.setAlignment(Pos.CENTER);
        _studentLabel.setTextAlignment(TextAlignment.CENTER);
    }

    //import list of students from the csv file
    public static void load(Student[][] students) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Importing");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("com/cruz/files/images/icon.jpg"));
        alert.setHeaderText(null);
        alert.setContentText("If no file can be found, one will be created with the current seat assignments");
        alert.showAndWait();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("seatingChart.csv"));
            for (int r = 0; r < 5; r++) {
                String cur = reader.readLine();
                for (int c = 0; c < 5; c++) {
                    String name;
                    try {
                        name = cur.substring(0, cur.indexOf(","));
                        cur = cur.substring(cur.indexOf(",") + 1);
                    } catch (IndexOutOfBoundsException e) {
                        name = cur;
                    }
                    students[r][c].set_studentName(name);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            alert.setContentText("File by name 'seatingChart.csv' not found, so it has been created");
            export(students);
            alert.showAndWait();
        }
    }
    //write the current chart to a .csv file titled seatingChart.csv
    public static void export(Student[][] students) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exporting");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("com/cruz/files/images/icon.jpg"));
        alert.setHeaderText(null);
        alert.setContentText("Please search for 'seatingChart.csv' in the current directory");
        alert.showAndWait();
        BufferedWriter writer = new BufferedWriter(new FileWriter("seatingChart.csv"));
        for (Student[] student : students) {
            for (int c = 0; c < student.length; c++) {
                String ah = c != student.length - 1 ? student[c].get_exportName() + "," : student[c].get_exportName();
                writer.write(ah);
            }
            writer.newLine();
        }
        writer.close();
    }

    //updates the name of a student in the chart
    public void set_studentName(String name) {
        if (name.length() == 0) return;

        int newline = name.indexOf("\n");
        int ind = name.indexOf(" ");

        if (name.equals("null") || name.equals("EMPTY")) {
            _studentName = "Empty\nSeat";
            _exportName = "EMPTY";
            _studentLabel.setFont(Font.font("Times New Roman", 23d));
            _studentLabel.setTextFill(Paint.valueOf("blue"));
        } else {
            _studentName = name.substring(0, ind) + "\n" + name.substring(ind + 1, ind + 2) + ".";
            if (name.contains(",")) {
                if (name.contains("\n")) {
                    _exportName = name.substring(newline, name.length() - 1);
                }
            } else {
                if (name.contains("\n")) {
                    _exportName = name.substring(newline + 1);
                } else {
                    _exportName = name;
                }
            }
            _studentLabel.setFont(Font.font("Comic Sans MS", 18d));
            _studentLabel.setTextFill(Paint.valueOf("red"));
        }
        _studentLabel.setText(_studentName);
    }
    //used for debugging
    @Override
    public String toString() {
        return "Student{\n" +
                "_studentName= " + _studentName + '\n' +
                "_exportName= " + _exportName + '\n' +
                "_studentLabel= " + _studentLabel +
                "\n}";
    }
}
