package com.company.builtin;

public class BuiltinDemo {
    public static void main(String[] args) {
      DeprecatedDemo.test();
    }
}

class Parent{
    public String getName(){
        return "parent";
    }
}

class Child extends Parent{
    @Override
    public String getName(){
        return "child";
    }
}

class DeprecatedDemo{
    @Deprecated(since = "4.5", forRemoval = true)
    public static void test(){
        System.out.println("test");
    }
}

@FunctionalInterface
interface print{
    void printString();
}