package com.yuicon.blogserver.handler;

import com.yuicon.blogserver.mapper.ArticleMapper;
import com.yuicon.blogserver.model.Article;
import com.yuicon.blogserver.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @author Yuicon
 */
@Component
public class ArticleHandler {

    private final ArticleMapper articleMapper;
    private final ArticleService articleService;

    @Autowired
    public ArticleHandler(ArticleMapper articleMapper, ArticleService articleService) {
        this.articleMapper = articleMapper;
        this.articleService = articleService;
    }

    public Mono<ServerResponse> insert(ServerRequest request) {
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Article.class).map(articleMapper::insert), Integer.class)
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(request.bodyToMono(Article.class).map(articleMapper::update), Integer.class)
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(subscriber -> articleService
                                .findByPage((int) request.attribute("page").orElse(0),
                                        (int) request.attribute("size").orElse(20)),
                        PageImpl.class)
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(Mono.just(articleService.findById(Integer.valueOf(request.pathVariable("id"))))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(Article::toHtml), Article.class)
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

}
