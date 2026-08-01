package org.course;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConsoleApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext =
                new AnnotationConfigApplicationContext("org.course");
    }
}
