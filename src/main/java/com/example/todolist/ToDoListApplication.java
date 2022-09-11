package com.example.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@SpringBootApplication
public class ToDoListApplication implements RepositoryRestConfigurer {

    @Bean
    Validator validator() {
       return new LocalValidatorFactoryBean();
    }

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

}
