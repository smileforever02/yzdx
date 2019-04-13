package com.yzdx.health.recovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HealthRecoveryApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HealthRecoveryApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HealthRecoveryApplication.class);
    }

}
