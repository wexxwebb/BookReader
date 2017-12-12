package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookReader implements Runnable {

    int counter = 0;


    static synchronized void checkCount() {
        counter++;
        if (counter % 5 == 0) {
            try {
                bWriter.write(counter + " - Thread : " + Thread.currentThread().getName() + "\n");
                bWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
            Pattern pattern = Pattern.compile("страдание");
            for (String s : lines) {
                Matcher matcher = pattern.matcher(s);
                while (matcher.find()) {
                    checkCount();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
