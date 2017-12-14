package com.company.UtilConcurrent;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader implements Callable<Result> {

    private Lock lock;
    private Path path;
    private String searchString;
    private AtomicInteger counter;
    private BufferedWriter bos;
    private final int PERIOD = 5;

    public FileReader(Lock lock, Path path, String searchString, AtomicInteger counter, BufferedWriter bos) {
        this.lock = lock;
        this.path = path;
        this.searchString = searchString;
        this.counter = counter;
        this.bos = bos;
    }

    @Override
    public Result call() {
        int countLocal = 0;
        try {
            String allFile = new String(Files.readAllBytes(path));
            Pattern pattern = Pattern.compile(searchString);
            Matcher matcher = pattern.matcher(allFile);
            while (matcher.find()) {
                try {
                    lock.lock();
                    countLocal++;
                    if (counter.incrementAndGet() % PERIOD == 0) {
                        bos.write("Найдено еще " + PERIOD + " страданий. Всего страданий: " + counter.get() + "\n");
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(path.getFileName().toString(), countLocal);
    }
}
