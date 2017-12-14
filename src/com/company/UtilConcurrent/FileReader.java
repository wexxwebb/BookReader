package com.company.UtilConcurrent;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader implements Runnable {

    private Lock lock;
    private Path path;
    private String searchString;

    public FileReader(Lock lock, Path path, String searchString) {
        this.lock = lock;
        this.path = path;
        this.searchString = searchString;
    }

    @Override
    public void run() {
        try {
            String allFile = new String(Files.readAllBytes(path));
            Pattern pattern = Pattern.compile(searchString);
            Matcher matcher = pattern.matcher(allFile);
            while (true) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
