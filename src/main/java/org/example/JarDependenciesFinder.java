package org.example;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JarDependenciesFinder {
    final private String nameClass;
    final private List<String> jars;

    public JarDependenciesFinder(String nameClass, List<String> jarPaths) {
        this.nameClass = nameClass;
        this.jars = jarPaths;
    }

    void classGraphChecker() {
        var classGraph = new ClassGraph().overrideClasspath(jars).enableClassInfo().enableInterClassDependencies();
        try (ScanResult scanResult = classGraph.scan()) {
            ClassInfo classInfo = getClassInfo(scanResult);
            if (classInfo != null)
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
        boolean allDependenciesPresent = true;
        if(dependencyList.isEmpty()) {
            printResult("false");
            return;
        }
        for (ClassInfo dependency : dependencyList) {
            if (!scanResult.getAllClasses().contains(dependency)) {
                allDependenciesPresent = false;
                break;
            }
        }
        printResult(allDependenciesPresent ? "true" : "false");
    }

    void printResult(String result){
        System.out.print(result + ": " + nameClass + " ");
        for (String jar : jars) {
            Path path = Paths.get(jar);
            System.out.print(path.getFileName().toString() + " ");
        }
    }

}
