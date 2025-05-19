package com.xai.srvls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for Spaced Repetition Vocabulary Learning Software (SRVLS)
 * This application provides RESTful and GraphQL APIs for flashcard-based vocabulary learning
 * with spaced repetition algorithms.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableScheduling
public class SrvlsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SrvlsApplication.class, args);
    }
}
