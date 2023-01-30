package com.jwt;

import com.jwt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.Random;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class JwtauthenticationserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtauthenticationserverApplication.class, args);
    }

}
