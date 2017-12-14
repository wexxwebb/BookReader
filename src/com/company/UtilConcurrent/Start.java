package com.company.UtilConcurrent;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Start {

    public static void main(String[] args) {

        FileFounder fileFounder = new FileFounder("txt", "txt");
        List<Path> files = fileFounder.getFileList();
        Lock lock = new ReentrantLock();

        for (Path path : files) {
            System.out.println(path.getFileName().toString());
        }


    }
}
