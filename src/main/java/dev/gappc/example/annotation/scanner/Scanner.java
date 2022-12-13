package dev.gappc.example.annotation.scanner;

import dev.gappc.example.annotation.scanner.exception.ClassResolutionException;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * This interface defines methods to scan for Java classes and annotations.
 */
public interface Scanner {

    /**
     * Load all classes from the package defined by <code>packageName</code> and from its sub-packages.
     *
     * @param packageName Load all classes from this package and its sub-packages.
     * @return A {@link Set} of classes found in the given package and sub-packages.
     * @throws ClassResolutionException If something went wrong during class resolution.
     */
    Set<Class<?>> getClasses(String packageName);

    /**
     * Find all classes in the given package and its sub-packages that have the given <code>annotation</code>.
     *
     * @param packageName Find all classes in the package and sub-packages with this name.
     * @param annotation This is the annotation that a class must use in order to be part of the result set.
     * @return A {@link Set} of classes with the given annotation.
     */
    Set<Class<?>> findClassesAnnotatedWith(String packageName, Class<? extends Annotation> annotation);
}
