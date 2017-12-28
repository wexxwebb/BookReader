package withOutConcurrent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Start {

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            return;
        }
    }

    private static BufferedWriter getBuffedWriter(String fileName, int retry) {
        try {
            return new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            retry++;
            if (retry > 5) {
                System.out.printf("Can't open file '%s'.\n", fileName);
                return null;
            } else {
                if (retry == 1) System.out.printf("Opening file '%s'.", fileName);
                else System.out.print(".");
                sleep(500);
                return getBuffedWriter(fileName, retry);
            }
        }
    }

    private static boolean closeBufferedWriter(BufferedWriter bufferedWriter, String fileName, int retry) {
        try {
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            retry++;
            if (retry > 5) {
                System.out.printf("Can't close file '%s'.\n", fileName);
                return false;
            } else {
                if (retry == 1) System.out.printf("Closing file '%s'.", fileName);
                else System.out.print(".");
                sleep(500);
                return closeBufferedWriter(bufferedWriter, fileName, retry);
            }
        }
    }

    public static void main(String[] args) {

        String resFileName = "data.txt";
        Founder founder = new FileFounder("txt/small", "txt");
        List<String> fileList = founder.getResourcesList();
        List<Thread> threadList = new ArrayList<>();
        List<Result> results = new ArrayList<>();
        BufferedWriter bWriter;

        if ((bWriter = getBuffedWriter(resFileName, 0)) == null) return;

        WriterCounter writerCounter = new WriterCounter(bWriter);

        for (String filename : fileList) {
            Thread thread = new Thread(
                    new FileReader(filename, 5, "(С|с)традани(е|я|й|и)", results, writerCounter)
            );
            thread.start();
            threadList.add(thread);
        }

        boolean play = true;
        while (play) {
            play = false;
            for (Thread thread : threadList) {
                if (thread.isAlive()) play = true;
            }
        }

        closeBufferedWriter(bWriter, resFileName, 0);

        results.stream().forEach(
                System.out::println
        );

        System.out.println("Всего страданий " + writerCounter.getCount());
    }
}
