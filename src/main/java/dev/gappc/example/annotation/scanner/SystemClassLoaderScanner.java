package dev.gappc.example.annotation.scanner;

import dev.gappc.example.annotation.scanner.exception.ClassResolutionException;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This {@link Scanner} implementation uses the system {@link ClassLoader} to scan for classes and annotations.
 */
public class SystemClassLoaderScanner implements Scanner {

    @Override
    public Set<Class<?>> getClasses(String packageName) {
        // Replace dots in package names with slashes to get resource names that can be loaded
        String resourceName = packageName.replaceAll("[.]", "/");

        try {
            // Get an enumeration of all resources known by the system class loader for the given resource name
            Enumeration<URL> resources = ClassLoader
                    .getSystemClassLoader()
                    .getResources(resourceName);

            // Find all classes in package and sub-packages
            Set<Class<?>> classes = new HashSet<>();

            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                File file = new File(url.getFile());
                Set<Class<?>> classesForPackage = findClasses(file, packageName);
                classes.addAll(classesForPackage);
            }

            return classes;
        } catch (IOException | ClassNotFoundException e) {
            throw new ClassResolutionException(e);
        }
    }

    @Override
    public Set<Class<?>> findClassesAnnotatedWith(String packageName, Class<? extends Annotation> annotation) {
        Set<Class<?>> classes = getClasses(packageName);

        return classes.stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    private Set<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        if (!directory.exists()) {
            return Set.of();
        }

        File[] files = directory.listFiles();

        if (files == null) {
            return Set.of();
        }

        Set<Class<?>> classes = new HashSet<>();
        for (File file : files) {
            if (file.isDirectory()) {
                // If file is a directory, then recursively find all classes in that directory
                Set<Class<?>> classesForDirectory = findClasses(file, packageName + "." + file.getName());
                classes.addAll(classesForDirectory);
            } else if (file.getName().endsWith(".class")) {
                Class<?> clazz = getClass(file.getName(), packageName);
                classes.add(clazz);
            }
        }

        return classes;
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            // Remove ".class" suffix from className, e.g. "MyCustomClass.class" becomes "MyCustomClass"
            String classNameWithoutSuffix = className.substring(0, className.lastIndexOf('.'));
            // Build full class name, e.g. dev.gappc.example.annotation.MyCustomClass
            String name = String.format("%s.%s", packageName, classNameWithoutSuffix);
            // Load class object
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new ClassResolutionException(e);
        }
    }
}
