package withOutConcurrent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Start {

    public static void main(String[] args) {
        String resFileName = "data.txt";
        ResourcesFounder founder = new FileFounder("txt/small", "txt");
        List<String> fileList = founder.getResourcesList();
        List<Thread> threadList = new ArrayList<>();
        List<Result> results = new ArrayList<>();
        BufferedWriter bWriter = null;
        for (int i = 1; i <= 3; i++) {
            try {
                bWriter = new BufferedWriter(new FileWriter(resFileName));
            } catch (IOException e) {
                System.out.println("Can't open file " + resFileName);
                if (i == 3) {
                    System.out.println("Can't open file " + resFileName + ". Exit with error...");
                    return;
                }

            }
        }
        WriterCounter writerCounter = new WriterCounter(bWriter);

        for (String filename : fileList) {
            Thread thread = new Thread(new FileReader(filename, 5, "(С|с)традани(е|я|й|и)", results, writerCounter));
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
        for (int i = 3; i <= 3; i++) {
            try {
                bWriter.close();
            } catch (IOException e) {
                System.out.println("Error. Can't close file " + resFileName + ". Retry " + i);
                if (i == 3) {
                    System.out.println("Error. Can't close file " + resFileName + "...");
                }
            }
        }
        for (Result result : results) {
            System.out.println(result.getFileName() + " :: " + result.getCount());
        }
        System.out.println("Всего страданий " + writerCounter.getCount());
    }
}
