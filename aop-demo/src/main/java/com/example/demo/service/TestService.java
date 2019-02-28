package com.example.demo.service;

import org.springframework.stereotype.Service;

/**
 * @author Yuicon
 */
@Service
public class TestService implements Test {
    @Override
    public void test() {
        System.out.println(1);
    }

    @Override
    public void test2() {
        System.out.println(2);
    }
}
