package io.github.forezp.bootexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run( BootExampleApplication.class, args );
    }
}
