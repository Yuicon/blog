package com.yuicon.blogserver.controller;

import com.yuicon.blogserver.mapper.ArticleMapper;
import com.yuicon.blogserver.model.Article;
import com.yuicon.blogserver.service.ArticleService;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Yuicon
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleMapper articleMapper;

    private final ArticleService articleService;

    public ArticleController(ArticleMapper articleMapper, ArticleService articleService) {
        this.articleMapper = articleMapper;
        this.articleService = articleService;
    }

    @PostMapping
    public Mono<Integer> insert(@RequestBody Article article) {
        return Mono.just(articleMapper.insert(article));
    }
    
//    @PutMapping
//    public Mono<Integer> update(@RequestBody Article article) {
//        return Mono.just(articleMapper.update(article));
//    }

    @GetMapping()
    public Mono<PageImpl> findAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                  @RequestParam(value = "size", defaultValue = "20", required = false) int size) {
        return articleService.findByPage(page, size);
    }

    @GetMapping("/{id}")
    public Mono<Article> findById(@PathVariable("id") int id) {
        return Mono.just(articleService.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Article::toHtml);
    }


}
