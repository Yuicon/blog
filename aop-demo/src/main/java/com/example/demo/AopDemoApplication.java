package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.example.demo.service.TestService;

/**
 * @author Yuicon
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class AopDemoApplication {

    private final TestService testService;

    @Autowired
    public AopDemoApplication(TestService testService) {
        this.testService = testService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AopDemoApplication.class, args);
    }

}
