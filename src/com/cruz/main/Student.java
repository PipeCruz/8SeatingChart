package com.cruz.main;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Student {
    private String _studentName, _exportName;
    private Label _studentLabel;

    public String get_studentName() {
        return _studentName;
    }

    public String get_exportName() {
        return _exportName;
    }

    public Student(String name) {
        _studentLabel = new Label();
        _studentLabel.setPrefSize(140, 120);
        _studentLabel.setAlignment(Pos.CENTER);
        _studentLabel.setTextAlignment(TextAlignment.CENTER);
        _studentLabel.setFont(new Font("Comic Sans", 13));
        set_studentName(name);
    }

    public Label get_studentLabel() {
        return _studentLabel;
    }

    public void set_studentName(String name) {
        if (name.equals("null")) {
            _studentName = "Empty\nSeat";
            _exportName = "EMPTY";
//            _studentLabel.setGraphic();fixme bold font
        } else {
            int ind = name.indexOf(" ");
            _studentName = name.substring(0, ind) + "\n" + name.substring(ind + 1, ind + 2) + ".";
            _exportName = name;
        }
        _studentLabel.setText(_studentName);
    }

    @Override
    public String toString() {
        return "Student{\n" +
                "_studentName= " + _studentName + '\n' +
                "_exportName= " + _exportName + '\n' +
                "_studentLabel= " + _studentLabel +
                "\n}";
    }

    public static void export(Student[][] students) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("seatingChart.csv"));
        for(Student[] s : students){
            for (Student ss : s) {
                writer.write(ss.get_exportName() + ",");
            }
            writer.newLine();
        }
        writer.close();
    }
}
