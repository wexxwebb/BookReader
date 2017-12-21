package withOutConcurrent;

import java.io.BufferedWriter;

public class WriterCounter {

    private BufferedWriter bWriter;
    private int count = 0;

    public WriterCounter(BufferedWriter bWriter) {
        this.bWriter = bWriter;
    }

    public BufferedWriter getbWriter() {
        return bWriter;
    }

    public int incAndGetCount() {
        return ++count;
    }

    public int getCount() {
        return count;
    }
}
