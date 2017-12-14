package com.company.UtilConcurrent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<Path> files = new FileFounder("txt", "txt").getFileList();

        try {
            List<String> list = Files.readAllLines(files.get(100));
            for (String string : list) {
                System.out.println(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
