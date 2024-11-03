package org.example;

import java.util.ArrayList;
import java.util.List;

public class JarDependenciesFinder {
    final private String nameClass;
    final private List<String> jars;

    public JarDependenciesFinder(String nameClass, List<String> jarPaths) {
        this.nameClass = nameClass;
        this.jars = new ArrayList<>();
    }
}
