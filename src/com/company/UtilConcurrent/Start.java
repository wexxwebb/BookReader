package com.company.UtilConcurrent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Start {

    public static void main(String[] args) {

        AtomicInteger counter = new AtomicInteger(0);
        FileFounder fileFounder = new FileFounder("txt", "txt");
        List<Path> files = fileFounder.getFileList();
        Lock lock = new ReentrantLock();

        try (BufferedWriter bos = new BufferedWriter(new FileWriter("counts.txt"));
                BufferedWriter countsWritre = new BufferedWriter(new FileWriter("howMany.txt"))) {
            ExecutorService execService = Executors.newFixedThreadPool(files.size());
            List<Future<Result>> futures = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                Future<Result> future = execService.submit(new FileReader(lock, files.get(i), "(С|с)традани(е|я|й|и)", counter, bos));
                futures.add(future);
            }
            while (true) {
                List<Future<Result>> tempFutures = new ArrayList<>();
                tempFutures.addAll(0, futures);

                for (Future<Result> future : tempFutures) {
                    if (future.isDone()) {
                        try {
                            //System.out.println(future.get().getFileName() + " >>> " + future.get().getCount());
                            countsWritre.write(future.get().getFileName() + " >>> " + future.get().getCount() + "\n");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        futures.remove(future);
                    }
                }
                if (futures.size() == 0) {
                    execService.shutdown();
                    countsWritre.flush();
                    String total = "Всего страданий: " + counter.get();
                    bos.write(total);
                    bos.flush();
                    System.out.println(total);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
