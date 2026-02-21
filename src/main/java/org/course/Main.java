package org.course;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext =
                new AnnotationConfigApplicationContext("org.course");
        SessionFactory sessionFactory = appContext.getBean(SessionFactory.class);
    }
}
