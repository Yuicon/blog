package com.yuicon.blogserver.config;

import com.yuicon.blogserver.handler.ArticleHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Yuicon
 */
@Configuration
@SuppressWarnings("NullableProblems")
public class RouteConfig {

    private final ArticleHandler articleHandler;

    @Autowired
    public RouteConfig(ArticleHandler articleHandler) {
        this.articleHandler = articleHandler;
    }

    /**
     * 注册自定义RouterFunction
     */
    @Bean
    public RouterFunction<ServerResponse> restaurantRouter() {
        return route(GET("/article").and(accept(APPLICATION_JSON)), articleHandler::findAll)
                .andRoute(POST("/article").and(accept(APPLICATION_JSON)), articleHandler::insert)
                .andRoute(GET("/article/{id}").and(accept(APPLICATION_JSON)), articleHandler::findById)
                .andRoute(OPTIONS("/**").and(accept(APPLICATION_JSON)), request -> ServerResponse.ok().build());
    }

}
