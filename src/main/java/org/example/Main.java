package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        if(args.length < 2){
            System.out.println("Arguments are insufficient");
        }else {
            String className = args[0];
            List<String> jarPaths = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
            var JarFinder = new JarDependenciesFinder(className, jarPaths);
        }
    }
}