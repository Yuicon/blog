/**
 * @author Yuicon
 */

import {baseFetch} from "./base";

const blogBaseUrl = "https://blog.penglei.wang";

function getArticles(page = 0, size = 20) {
    return baseFetch(blogBaseUrl + `/articles?page=${page}&size=${size}`);
}

function getArticleById(id) {
    return baseFetch(blogBaseUrl + "/articles/" + id);
}

function getArticle(gitUserName, repositoryName, issueId) {
    return baseFetch(`https://api.github.com/repos/${gitUserName}/${repositoryName}/issues/${issueId}`);
}

function addArticle(gitUserName, repositoryName, issueId) {
    return baseFetch(blogBaseUrl + `/articles`, {
        body: JSON.stringify({gitUserName, repositoryName, issueId}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    });
}

function putArticle(id, title, body, createdAt, updatedAt, closedAt) {
    return baseFetch(blogBaseUrl + `/articles`, {
        body: JSON.stringify({id, title, body, createdAt, updatedAt, closedAt}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
    });
}

export const blogApi = {};

blogApi.getArticles = getArticles;
blogApi.addArticle = addArticle;
blogApi.putArticle = putArticle;
blogApi.getArticle = getArticle;
blogApi.getArticleById = getArticleById;