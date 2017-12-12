package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static BufferedWriter bWriter;

    static int counter = 0;



    public static void main(String[] args) {
        BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
        try {
            bufr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Thread> threadPull = new ArrayList<>();
        Path path = Paths.get("txt");
        File[] files = path.toFile().listFiles();
        try {
            bWriter = new BufferedWriter(new FileWriter("counts.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File file : files) {
            threadPull.add(new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }));
        }
        for (Thread thread : threadPull) {
            thread.start();
        }
        Thread control = new Thread(new Thread(new Runnable() {
            @Override
            public void run() {
                boolean play = true;
                while (play) {
                    play = false;
                    for (Thread thread : threadPull) {
                        if (thread.isAlive()) {
                            play = true;
                        }
                    }
                    if (!play) {
                        try {
                            String total = "All words : " + counter;
                            System.out.println(total);
                            bWriter.write(total);
                            bWriter.flush();
                            bWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }));
        control.start();
        try {
            bufr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
