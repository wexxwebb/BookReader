package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        Path path = Paths.get("txt");
        File[] files = path.toFile().listFiles();
        int count = 0;
        for (File file : files) {
            int inFile = 0;
            try {
                List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
                Pattern pattern = Pattern.compile("страдание");
                for (String s : lines) {
                    Matcher matcher = pattern.matcher(s);
                    while (matcher.find()) {
                        inFile++;
                        count++;
                    }
                }
                System.out.println(inFile + " - " + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(count);
    }
}
