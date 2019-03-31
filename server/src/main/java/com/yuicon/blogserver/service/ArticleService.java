package com.yuicon.blogserver.service;

import com.yuicon.blogserver.mapper.ArticleMapper;
import com.yuicon.blogserver.model.Article;
import model.vo.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Yuicon
 */
@Service
public class ArticleService {

    private final ArticleMapper articleMapper;
    private final ReactiveRedisTemplate<String, Article> reactiveRedisTemplate;
    private final String ARTICLE_KEY = "ARTICLE:";
    private final String ARTICLES_KEY = "ARTICLES:";

    @Autowired
    public ArticleService(ArticleMapper articleMapper, ReactiveRedisTemplate<String, Article> reactiveRedisTemplate) {
        this.articleMapper = articleMapper;
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.parse("2017-08-13T07:09:00Z", DateTimeFormatter.ISO_INSTANT));
    }

    public Mono<Article> put(Article article) {
        try {
            articleMapper.update(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mono.just(articleMapper.findById(article.getId()));
    }

    public Mono<Article> save(Article article) {
        try {
            articleMapper.insert(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mono.just(articleMapper.findByUniqe(article));
    }

    public Mono<PageImpl> findByPage(int page, int size) {
        List<Article> articles = articleMapper.findByPage(page * size, size);
        return Mono.just(new PageImpl<>(articles, PageRequest.of(page, size), articleMapper.count()));
    }

    public Mono<ResponseEntity> findById(int id) {
        String key = ARTICLE_KEY + id;
        return reactiveRedisTemplate.hasKey(key).flatMap(aBoolean -> {
            if (!aBoolean) {
                Article article = articleMapper.findById(id);
                article.toHtml();
                reactiveRedisTemplate.opsForValue().set(key, article).doOnNext(aBoolean1 -> {
                   if (aBoolean1) {
                       reactiveRedisTemplate.expire(key, Duration.ofDays(1));
                   }
                });
            }
            return reactiveRedisTemplate.opsForValue().get(key);
        }).map(article -> ResponseEntity.ok(JsonResponse.success(article)));
    }

}
