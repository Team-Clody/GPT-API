package org.sopt.gptapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GptApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GptApiApplication.class, args);
    }

}
