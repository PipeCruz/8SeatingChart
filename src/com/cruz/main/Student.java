package com.cruz.main;

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
    private String _studentName, _exportName;
    private Label _studentLabel;

    public String get_studentName() {
        return _studentName;
    }

    public String get_exportName() {
        return _exportName;
    }

    public Label get_studentLabel() {
        return _studentLabel;
    }

    public void set_studentName(String name) {
        if (name.length() == 0) return;
        if (name.equals("null") || name.equals("EMPTY")) {
            _studentName = "Empty\nSeat";
            _exportName = "EMPTY";
//            _studentLabel.setGraphic();fixme bold font
        } else {
            int newline = name.indexOf("\n");
            int ind = name.indexOf(" ");
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
            System.out.println(_exportName);
        }
        _studentLabel.setText(_studentName);
    }

    public Student(String name) {
        _studentLabel = new Label();
        set_studentName(name);
        _studentLabel.setPrefSize(140, 120);
        _studentLabel.setAlignment(Pos.CENTER);
        _studentLabel.setTextAlignment(TextAlignment.CENTER);
        _studentLabel.setTextFill(Paint.valueOf("BLACK"));
        _studentLabel.setFont(new Font("Comic Sans", 13));
    }

    public static void load(Student[][] students) throws IOException {
        //todo refactor into student class?
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Importing");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("com/cruz/files/images/GenericChair.jpg"));
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
            alert.setContentText("File by name 'seatingChart.csv' not found, so it has been created");
            Student.export(students);
            alert.showAndWait();
        }
    }

    public static void export(Student[][] students) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("seatingChart.csv"));
        int e = 1;

        for (Student[] s : students) {
            for (Student student : s) {
                System.out.println(e++ + " " + student.get_exportName());
                writer.write(student.get_exportName() + ",");
            }
            writer.newLine();
        }

        writer.close();
    }

    @Override
    public String toString() {
        return "Student{\n" +
                "_studentName= " + _studentName + '\n' +
                "_exportName= " + _exportName + '\n' +
                "_studentLabel= " + _studentLabel +
                "\n}";
    }
}
