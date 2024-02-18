package org.talenthub;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    @Getter
    @Setter
    private static String[] args;

    public static void main(String[] args) {
        Main.setArgs(args);
        SpringApplication.run(Main.class);
    }

}
