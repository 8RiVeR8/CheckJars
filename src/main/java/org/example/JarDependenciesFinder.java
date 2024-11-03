package org.example;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.util.ArrayList;
import java.util.List;

public class JarDependenciesFinder {
    final private String nameClass;
    final private List<String> jars;

    public JarDependenciesFinder(String nameClass, List<String> jarPaths) {
        this.nameClass = nameClass;
        this.jars = new ArrayList<>();
    }

    void checkForDependencies() {
        var classGraph = new ClassGraph().overrideClasspath(jars).enableClassInfo().enableInterClassDependencies();
        try (ScanResult scanResult = classGraph.scan()) {
            ClassInfo classInfoList = getClassInfo(scanResult);
        }
    }

    ClassInfo getClassInfo(ScanResult scanResult) {
        ClassInfo mainClassInfo = scanResult.getClassInfo(nameClass);
        if(mainClassInfo == null) {
            System.out.println("Class " + nameClass + " not found");
        }
        return mainClassInfo;
    }
}
