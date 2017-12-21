package withOutConcurrent;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

public class FileFounder implements ResourcesFounder {

    private Path path;
    private String extension;
    private List<String> fileList;

    private class SFV extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
            if (file.getFileName().toString().endsWith(extension)) {
                fileList.add(file.toAbsolutePath().toString());
            }
            return CONTINUE;
        }
    }

    FileFounder(String pathName, String extension) {
        this.extension = extension;
        path = Paths.get(pathName);
        fileList = new ArrayList<>();
    }

    public List<String> getResourcesList() {
        try {
            Files.walkFileTree(path, new SFV());
        } catch (IOException e) {
            System.out.println("Can't build path tree!");
        }
        return fileList;
    }
}
