package com.company.book;

import java.lang.annotation.*;
import java.lang.reflect.Field;

public class BookDemo {
    public static void main(String[] args) {
        Book book = new Book("title", "author", -152);
        BookProcessor.process(book);
        NonNullProcessor.process(book);
        IntPositiveProcessor.process(book);
    }
}

@BookAnnotation("long")
class Book{
    @NonNull
    public String title;
    @NonNull
    public String author;
    @IntPositive
    public int pages;

    public Book(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public void printShort(){
        System.out.println("title: "+title);
    }

    public void printLong(){
        System.out.println("title: "+title+", author: "+author);
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface BookAnnotation{
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NonNull{}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface IntPositive{}

class BookProcessor{
    public static void process(Book book){
        Class clazz = Book.class;
        //Annotation annotation = clazz.getAnnotation(BookAnnotation.class);
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation:annotations){
            if (annotation instanceof BookAnnotation){
                if (((BookAnnotation) annotation).value().equals("short"))
                    book.printShort();
                if (((BookAnnotation) annotation).value().equals("long"))
                    book.printLong();
            }
        }
    }
}

class NonNullProcessor{
    public static void process(Book book){
        Field[] fields = book.getClass().getDeclaredFields();
        for (Field field:fields){
            Annotation annotation = field.getAnnotation(NonNull.class);
            try {
                if (annotation!=null && field.get(book)==null){
                    System.err.println("field "+field.getName()+" cannot be null");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

class IntPositiveProcessor{
    public static void process(Book book){
        Field[] fields = book.getClass().getDeclaredFields();
        for (Field field:fields){
            Annotation annotation = field.getAnnotation(IntPositive.class);
            try {
                if (annotation!=null && field.getInt(book)<=0){
                    System.err.println("field "+field.getName()+" must be positive");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}