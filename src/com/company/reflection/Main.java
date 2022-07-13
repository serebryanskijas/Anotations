package com.company.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {

        MyClass myClass = new MyClass(1,"name", 3);
        //myClass.print();

        try {
           /* Field field = myClass.getClass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(myClass, "new value");
            String name = (String) field.get(myClass);

            System.out.println(name);*/
            Field field = myClass.getClass().getDeclaredField("value");
            field.setAccessible(true);
            field.set(myClass, 1);
            int value = field.getInt(myClass);

            System.out.println(value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //Изменить уровень доступа метода и изменить его

        Method method = null;
        try {
            method = myClass.getClass().getDeclaredMethod("print");
            method.setAccessible(true);
            method.invoke(myClass);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //Создать экземпляр класса во время выполнения, если класс не известен
        MyClass myClass1 = null;

        try {
            Class clazz = Class.forName(MyClass.class.getName());
            myClass1 = (MyClass) clazz.getDeclaredConstructor().newInstance();

        } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }


        //Создать экземпляр класса во время выполнения с помощью конструктора с параметрами

        MyClass myClass2 = null;

        try {
            Class clazz = Class.forName(MyClass.class.getName());
            Class[] params = {int.class, String.class, int.class};
            myClass2 = (MyClass) clazz.getDeclaredConstructor(params).newInstance(1,"new val", 3);

        } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(myClass2);

        //Получит все конструкторы и их параметры
        Class clazz = null;
        try {
            clazz=Class.forName(MyClass.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor: constructors) {
            System.out.println(constructor.getName()+", parameters:");
            Class[] params = constructor.getParameterTypes();
            for (Class param:params)
                System.out.print(param.getName()+",");
            System.out.println();

        }
    }
}
