package withOutConcurrent;

public class Result {
    private String fileName;
    private int count;

    public Result(String fileName, int count) {
        this.fileName = fileName;
        this.count = count;
    }

    public String getFileName() {
        return fileName;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return Integer.toString(count) + " :: " + fileName;
    }
}
