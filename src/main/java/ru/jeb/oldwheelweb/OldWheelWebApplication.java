package ru.jeb.oldwheelweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OldWheelWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldWheelWebApplication.class, args);
    }

}
