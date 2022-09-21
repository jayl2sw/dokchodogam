package com.ssafy.dockchodogam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DockChoDoGamApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockChoDoGamApplication.class, args);
    }

}
