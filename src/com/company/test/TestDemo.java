package com.company.test;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

public class TestDemo {
    public static void main(String[] args) {
        TestProcessor.process(new Test());
    }
}

class Test {
    @Order(3)
    public void run1() {
        System.out.println("run1");
    }

    @Order(2)
    public void run2() {
        System.out.println("run2");
    }

    @Order(1)
    public void run3() {
        System.out.println("run3");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Order {
    int value();
}

class TestProcessor {
    public static void process(Test test) {
        Method[] methods = test.getClass().getDeclaredMethods();
        Map<Integer, Method> map = new TreeMap<>();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(Order.class);
            map.put(((Order)annotation).value(),method);
        }
        for (Map.Entry<Integer,Method> entry:map.entrySet()){
            try {
                entry.getValue().invoke(test);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}