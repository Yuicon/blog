package com.yuicon.blogserver.service;

import com.yuicon.blogserver.mapper.ArticleMapper;
import com.yuicon.blogserver.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Yuicon
 */
@Service
public class ArticleService {

    private final ArticleMapper articleMapper;
    private final WebClient gitWebClient;

    @Autowired
    public ArticleService(ArticleMapper articleMapper, WebClient githubWebClient) {
        this.articleMapper = articleMapper;
        this.gitWebClient = githubWebClient;
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

    public Article findById(int id) {
        Article article = articleMapper.findById(id);
        if (article == null) {
            return new Article();
        }
        return article.toHtml();
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.parse("2017-08-13T07:09:00Z", DateTimeFormatter.ISO_INSTANT));
    }

}
