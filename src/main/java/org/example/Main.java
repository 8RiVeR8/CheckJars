package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String className = args[0];
        List<String> jarPaths = new ArrayList<>();

        for (int i = 1; i < args.length; i++) {
            jarPaths.add(args[i]);
        }



    }
}