package dev.gappc.example.annotation.scanner;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;

/**
 * This {@link Scanner} implementation uses the Reflections library to scan for classes and annotations.
 *
 * @see <a href="https://github.com/ronmamo/reflections">Reflections library</a>
 */
public class ReflectionsScanner implements Scanner {

    @Override
    public Set<Class<?>> getClasses(String packageName) {
        Reflections reflections = getReflectionsInstance(packageName);
        return reflections.get(SubTypes.of(Object.class).asClass());
    }

    @Override
    public Set<Class<?>> findClassesAnnotatedWith(String packageName, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getTypesAnnotatedWith(annotation);
    }

    private Reflections getReflectionsInstance(String packageName) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
                .forPackage(packageName)
                .setScanners(SubTypes.filterResultsBy(t -> true));

        return new Reflections(configurationBuilder);
    }
}
