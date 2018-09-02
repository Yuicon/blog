package com.yuicon.blogserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Yuicon
 */
@Configuration
public class GithubWebClient {

    @Bean()
    public WebClient gitWebClient() {
        return WebClient.create("https://api.github.com");
    }

}
