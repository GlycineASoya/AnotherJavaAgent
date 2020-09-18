package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileUtils {
    private String pathToFile;

    public void appendToFile(String string, String pathToFile) {
        this.pathToFile = (this.pathToFile == null) ? "/Users/ilevinsk/Work/working_log" : this.pathToFile;
        this.pathToFile = (pathToFile == null) ? this.pathToFile : pathToFile;

        try {
            File f1 = new File(pathToFile);
            if (!f1.exists()) {
                f1.createNewFile();
            }
            if (f1.canWrite()) {
                String timestamp = LocalDateTime.now() + "\t";
                FileWriter fw = new FileWriter(f1, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(timestamp + string);
                bw.newLine();
                bw.flush();
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendToFile(String string) {
        this.pathToFile = (this.pathToFile == null) ? "/Users/ilevinsk/Work/working_log" : this.pathToFile;

        try {
            File f1 = new File(pathToFile);
            if (!f1.exists()) {
                f1.createNewFile();
            }
            if (f1.canWrite()) {
                String timestamp = LocalDateTime.now() + "\t";
                FileWriter fw = new FileWriter(f1, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(timestamp + string);
                bw.newLine();
                bw.flush();
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
}
