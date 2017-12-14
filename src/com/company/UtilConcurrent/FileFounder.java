package com.company.UtilConcurrent;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

public class FileFounder {

    Path path;
    String extension;
    List<Path> fileList;

    private class SFV extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.getFileName().toString().endsWith(extension)) {
                fileList.add(file);
            }
            return CONTINUE;
        }
    }

    public FileFounder(String pathName, String extension) {
        this.extension = extension;
        path = Paths.get(pathName);
        fileList = new ArrayList<>();
    }

    public List<Path> getFileList() {
        try {
            Files.walkFileTree(path, new SFV());
        } catch (IOException e) {
            System.out.println("Can't build path tree!");
        }
        return fileList;
    }
}
