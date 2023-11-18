package com.hackathon.diasporadialog;

import com.hackathon.diasporadialog.security.JWTConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JWTConfigProperties.class)
@SpringBootApplication
public class DiasporaDialogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiasporaDialogApplication.class, args);
    }

}
