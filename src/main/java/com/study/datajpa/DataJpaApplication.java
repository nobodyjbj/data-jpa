package com.study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }
    
    @Bean
    public AuditorAware<String> auditorProvider() {
        // 실제로는 SecurityContext의 유저정보를 꺼내서 넣어주면 된다.
        return () -> Optional.of(UUID.randomUUID().toString());
    }

}
