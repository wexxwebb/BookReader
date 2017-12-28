package withOutConcurrent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader implements Runnable {

    private int period;
    private String regExp;
    private int localCount = 0;
    private Path path;
    private List<Result> results;
    private WriterCounter writerCounter;

    public FileReader(String fileName, int period, String regExp, List<Result> results, WriterCounter writerCounter) {
        this.path = Paths.get(fileName);
        this.period = period;
        this.regExp = regExp;
        this.results = results;
        this.writerCounter = writerCounter;
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String allFile;
        int retry = 0;
        while (true) {
            try {
                allFile = new String(Files.readAllBytes(path));
                break;
            } catch (IOException e) {
                retry++;
                if (retry == 3) {
                    System.out.println("Can't read file " + path.getFileName() + ". Exit.");
                    return;
                } else {
                    System.out.println("Can't read file " + path.getFileName() + ". Retry " + retry);
                }
            }
        }
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(allFile);
        while (matcher.find()) {
            synchronized (writerCounter) {
                localCount++;
                int allCount = writerCounter.incAndGetCount();
                for (int i = 1; i <= 3; i++) {
                    //TODO вынести запись в файл в отдельный поток
                    if (allCount % period == 0) {
                        try {
                            writerCounter.getbWriter().write("+ " + period + " страданий. Всего страданий: " + allCount + "\n");
                            writerCounter.getbWriter().flush();
                            break;
                        } catch (IOException e) {
                            sleep(200);
                            System.out.println("Can't read file " + path.getFileName() + ". Retry " + i);
                            if (i == 3) {
                                System.out.println("Can't read file " + path.getFileName() + ". Thread stopped.");
                                return;
                            }
                        }
                    }
                }
            }
        }
        synchronized (results) {
            results.add(new Result(path.getFileName().toString(), localCount));
        }
    }
}
