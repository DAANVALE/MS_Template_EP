package com.proyect.mstemplateep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.proyect")
public class MsTemplateEpApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsTemplateEpApplication.class, args);
    }

}
