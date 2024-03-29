package com.lxc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.lxc.repository")
public class BookmallApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookmallApplication.class, args);
    }
}
