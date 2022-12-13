package dev.gappc.example.annotation;

import dev.gappc.example.annotation.scanner.ReflectionsScanner;
import dev.gappc.example.annotation.scanner.Scanner;
import dev.gappc.example.annotation.scanner.SystemClassLoaderScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Demo for annotation resolution.
 */
public class AnnotationDemo {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotationDemo.class);

    private static final String BASE_PACKAGE = "dev.gappc.example.annotation";

    public static void main(String[] args) {
        AnnotationDemo resolver = new AnnotationDemo();

        resolver.scanWith(new SystemClassLoaderScanner(), BASE_PACKAGE);
        resolver.scanWith(new ReflectionsScanner(), BASE_PACKAGE);
    }

    private void scanWith(Scanner scanner, String packageName) {
        LOG.info("\n------- Scan with {}", scanner);

        // Scan for classes

        printClasses(scanner, packageName);

        // Scan for annotations

        printAnnotationUsage(scanner, packageName, Component.class);
        printAnnotationUsage(scanner, packageName, Service.class);
    }

    private void printClasses(Scanner scanner, String packageName) {
        Set<Class<?>> classesForBasePackage = scanner.getClasses(packageName);
        List<Class<?>> sortedClasses = sortByName(classesForBasePackage);
        LOG.info("Found the following classes in package {} and its sub-packages ({} entries): {}", packageName, sortedClasses.size(), sortedClasses);
    }

    private void printAnnotationUsage(Scanner scanner, String packageName, Class<? extends Annotation> annotation) {
        Set<Class<?>> classesAnnotatedWithComponent = scanner.findClassesAnnotatedWith(packageName, annotation);
        List<Class<?>> sortedClasses = sortByName(classesAnnotatedWithComponent);
        LOG.info("Classes annotated with @{} ({} entries): {}", annotation.getSimpleName(), sortedClasses.size(), sortedClasses);
    }

    private List<Class<?>> sortByName(Collection<Class<?>> collection) {
        return collection.stream()
                .sorted(Comparator.comparing(Class::getName))
                .collect(Collectors.toList());
    }
}
