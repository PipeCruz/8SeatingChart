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

    public static void export(Student[][] students) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("seatingChart.csv"));
        int i = 0;
        for (Student[] s : students) {
            for (Student ss : s) {
                System.out.println(i++ + " " + ss.get_exportName());
                writer.write(ss.get_exportName() + ",");
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

    public void set_studentName(String name) {
        if (name.length() == 0) return;
        if (name.equals("null") || name.equals("EMPTY")) {
            _studentName = "Empty\nSeat";
            _exportName = "EMPTY";
//            _studentLabel.setGraphic();fixme bold font
        } else {//fixme I NEED TO FIX THIS TO EXPORT FILES
            int newline = name.indexOf("\n");
            int ind = name.indexOf(" ");
            _studentName = name.substring(0, ind) + "\n" + name.substring(ind + 1, ind + 2) + ".";
            if (name.contains(",")) {
                if (name.contains("\n")) {
                    _exportName = name.substring(newline, name.length() - 1);//fixme
                }
            } else {
                if (name.contains("\n")) {
                    _exportName = name.substring(newline + 1);//fixme
                } else {
                    _exportName = name;
                }
            }

            System.out.println(_exportName);
        }
        _studentLabel.setText(_studentName);
    }
}
