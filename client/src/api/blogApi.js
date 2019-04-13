/**
 * @author Yuicon
 */

import {baseFetch, http} from "./base";

const blogBaseUrl = "https://api.saabisu.cn/article-service";

function getArticles(page = 0, size = 20) {
    return http.get(blogBaseUrl + `/public?page=${page}&size=${size}`);
}

function getArticleById(id) {
    return http.get(blogBaseUrl + "/public/" + id);
}

function getArticle(gitUserName, repositoryName, issueId) {
    return baseFetch(`https://api.github.com/repos/${gitUserName}/${repositoryName}/issues/${issueId}`);
}

function addArticle(gitUserName, repositoryName, issueId) {
    return http.post(blogBaseUrl + `/articles`, {gitUserName, repositoryName, issueId});
}

function putArticle(id, title, body, createdAt, updatedAt, closedAt) {
    return http.put(blogBaseUrl + `/articles`, {id, title, body, createdAt, updatedAt, closedAt});
}

export const blogApi = {};

blogApi.getArticles = getArticles;
blogApi.addArticle = addArticle;
blogApi.putArticle = putArticle;
blogApi.getArticle = getArticle;
blogApi.getArticleById = getArticleById;