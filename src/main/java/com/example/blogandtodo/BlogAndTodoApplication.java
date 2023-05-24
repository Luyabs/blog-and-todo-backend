package com.example.blogandtodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BlogAndTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAndTodoApplication.class, args);
    }

}
