package com.yuicon.blogserver.service;

import com.yuicon.blogserver.mapper.ArticleMapper;
import com.yuicon.blogserver.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Yuicon
 */
@Service
public class ArticleService {

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public PageImpl findByPage(int page, int size) {
        List<Article> articles = articleMapper.findByPage(page * size, size);
        return new PageImpl<>(articles, PageRequest.of(page, size), articleMapper.count());
    }

    public Optional<Article> findById(int id) {
        return Optional.of(articleMapper.findById(id));
    }

}
