package com.yuicon.blogserver.mapper;

import com.yuicon.blogserver.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yuicon
 */
@Mapper
@Repository
public interface ArticleMapper {

    /**
     * 新建文章
     *
     * @param article 文章实体
     * @return 新建数量
     */
    @Insert("INSERT INTO tb_article " +
            "(gitUserName, repositoryName, issueId) " +
            "VALUES (#{article.gitUserName}, #{article.repositoryName}, #{article.issueId})")
    int insert(@Param("article") Article article);


    /**
     * 修改文章
     *
     * @param article 文章实体
     * @return 修改数量
     */
    @Update("UPDATE tb_article" +
            "SET title=#{article.title}, createdAt=#{article.createdAt}, updatedAt=#{article.updatedAt}, closedAt=#{article.closedAt}, body=#{article.body}" +
            "WHERE id = #{article.id}")
    int update(@Param("article") Article article);

    /**
     * 根据id查询文章
     *
     * @param id 文章id
     * @return 文章
     */
    @Select("SELECT * FROM tb_article WHERE id = #{id}")
    Article findById(@Param("id") int id);

    /**
     * 分页查询文章
     *
     * @param offset 偏移量
     * @param size   一页的数量
     * @return 文章列表
     */
    @Select("SELECT * FROM tb_article LIMIT #{offset}, #{size}")
    List<Article> findByPage(@Param("offset") int offset, @Param("size") int size);

    /**
     * 查询文章总数
     *
     * @return 文章总数
     */
    @Select("SELECT count(1) FROM tb_article")
    int count();


}
