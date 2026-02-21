package org.course;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.course.entity.*;

@Configuration
@PropertySource("classpath:application.properties")
public class HibernateConfiguration {

    @Value(value = "${hibernate.connection.url}")
    private String connectionURL;

    @Value("${hibernate.connection.username}")
    private String connectionUsername;

    @Value("${hibernate.connection.password}")
    private String connectionPassword;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Bean
    public SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration
                .addPackage("org.course")
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Profile.class)
                .addAnnotatedClass(Order.class)
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", connectionURL)
                .setProperty("hibernate.connection.username", connectionUsername)
                .setProperty("hibernate.connection.password", connectionPassword)
                .setProperty("hibernate.show_sql", showSql)
                .setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        return configuration.buildSessionFactory();
    }
}
