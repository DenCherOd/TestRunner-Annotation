package testRunner;

import testRunner.annotations.AfterSuite;
import testRunner.annotations.BeforeSuite;
import testRunner.annotations.Test;

public class TestClass {
    @BeforeSuite
    void setUp() {
        System.out.println("SetUp method");
    }

    @AfterSuite
    void tearDown() {
        System.out.println("Tear down method");
    }

    @Test
    void method1() {
        System.out.println("Test 1");
    }

    @Test(order = 2)
    void method2() {
        System.out.println("Test 2");
    }

    @Test(order = 3)
    void method3() {
        System.out.println("Test 3");
    }

    @Test(order = 4)
    void method4() {
        System.out.println("Test 4");
    }
}