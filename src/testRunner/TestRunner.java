package testRunner;

import testRunner.annotations.AfterSuite;
import testRunner.annotations.BeforeSuite;
import testRunner.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunner {
    public static void start(Class<?> testClass) {
        try {
            List<Method> setUpMethods = Arrays.stream(testClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(BeforeSuite.class))
                    .collect(Collectors.toList());
            if (setUpMethods.size() > 1) {
                throw new RuntimeException("BeforeSuite annotation must be a single");
            }

            List<Method> tearDownMethods = Arrays.stream(testClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(AfterSuite.class))
                    .collect(Collectors.toList());

            Object testObject = testClass.getConstructor().newInstance();
            if (setUpMethods.size() == 1) setUpMethods.get(0).invoke(testObject);
            if (tearDownMethods.size() == 1) tearDownMethods.get(0).invoke(testObject);

            List<Method> testMethods = Arrays.stream(testClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Test.class))
                    .sorted(Comparator.comparingInt(o -> o.getAnnotation(Test.class).order()))
                    .collect(Collectors.toList());

            for (Method method : testMethods) {
                method.invoke(testObject);
            }

            if (setUpMethods.size() > 1) {
                throw new RuntimeException("AfterSuite annotation must be a single");
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("Something went wrong with execution" , e);
        }
    }
}