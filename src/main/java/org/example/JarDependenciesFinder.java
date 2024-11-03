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

    void classGraphChecker() {
        var classGraph = new ClassGraph().overrideClasspath(jars).enableClassInfo().enableInterClassDependencies();
        try (ScanResult scanResult = classGraph.scan()) {
            ClassInfo classInfo = getClassInfo(scanResult);
            checkForDependencies(scanResult, classInfo);
        }
    }

    ClassInfo getClassInfo(ScanResult scanResult) {
        ClassInfo mainClassInfo = scanResult.getClassInfo(nameClass);
        if(mainClassInfo == null) {
            System.out.println("Class " + nameClass + " not found");
        }
        return mainClassInfo;
    }

    void checkForDependencies(ScanResult scanResult, ClassInfo classInfo) {
        ClassInfoList dependencyList = classInfo.getClassDependencies();
        if(dependencyList.isEmpty()) {
            System.out.println("false: " + nameClass + jars.stream().toString());
        } else {
            for (ClassInfo dependency : dependencyList) {
                if (!scanResult.getAllClasses().contains(dependency)) {
                    System.out.println("false: " + nameClass + jars.stream().toString());
                } else {
                    System.out.println("true: " + nameClass + jars.stream().toString());
                }
            }
        }
    }

}
