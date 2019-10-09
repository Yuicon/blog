/**
 * @author Yuicon
 */

import {http} from "./base";

const articleBaseUrl = "https://api.saabisu.cn/article-service/common";

function getArticles(page = 0, size = 20) {
    return http.get(articleBaseUrl + `/public?page=${page}&size=${size}`);
}

function getArticleById(id) {
    return http.get(articleBaseUrl + "/public/" + id);
}

function addArticle(article) {
    return http.post(articleBaseUrl, article);
}

function putArticle(article) {
    return http.put(articleBaseUrl, article);
}

export const articleApi = {};

articleApi.getArticles = getArticles;
articleApi.addArticle = addArticle;
articleApi.putArticle = putArticle;
articleApi.getArticleById = getArticleById;