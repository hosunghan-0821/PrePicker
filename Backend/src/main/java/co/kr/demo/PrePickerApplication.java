package co.kr.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

@EnableJpaAuditing
public class PrePickerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrePickerApplication.class, args);
    }

}
