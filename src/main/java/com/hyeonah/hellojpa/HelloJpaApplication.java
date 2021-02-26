package com.hyeonah.hellojpa;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HelloJpaApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }

    @Bean
    Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }
}
