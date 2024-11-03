package org.example;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JarDependenciesFinderTest {
    @Test
    public void shouldReturnClassInfo() {
        String className = "com.jetbrains.internship2024.SomeAnotherClass";
        List<String> jars = new ArrayList<>();
        jars.add("path/ModuleA-1.0.jar");
        final var jarsDependenciesFinder = new JarDependenciesFinder(className, jars);

        var classGraph = new ClassGraph().overrideClasspath(jars).enableClassInfo().enableInterClassDependencies();
        try (ScanResult scanResult = classGraph.scan()) {
            ClassInfo classInfo = jarsDependenciesFinder.getClassInfo(scanResult);
            assertNotNull(classInfo);
            ClassInfoList dependencyList = classInfo.getClassDependencies();
            if (dependencyList.isEmpty()) {
                System.out.println("No dependencies found for " + className);
            } else {
                for (ClassInfo dependency : dependencyList) {
                    System.out.println("Dependency: " + dependency.getName());
                }
            }

            System.out.println("Classes found in scan:");
            scanResult.getAllClasses().forEach(c -> System.out.println(c.getName()));
        }
    }

    @Test
    public void shouldNotReturnClassInfo() {
        String className = "qgioerjogijqeriogjioerjb";
        List<String> jars = new ArrayList<>();
        jars.add("path/ModuleB-1.0.jar");
        final var jarsDependenciesFinder = new JarDependenciesFinder(className, jars);
        var classGraph = new ClassGraph().overrideClasspath(jars).enableClassInfo().enableInterClassDependencies();
        try (ScanResult scanResult = classGraph.scan()) {
            ClassInfo classInfo = jarsDependenciesFinder.getClassInfo(scanResult);
            assertNull(classInfo);
        }
    }
}