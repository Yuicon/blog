/**
 * @author Yuicon
 */

const blogBaseUrl = "https://blog.penglei.wang";

function getArticles(page = 0, size = 20) {
    return fetch(blogBaseUrl + `/articles?page=${page}&size=${size}`).then(response => response.json()).then(json => json);
}

function getArticleById(id) {
    return fetch(blogBaseUrl + "/articles/" + id).then(response => response.json()).then(json => json);
}

function getArticle(gitUserName, repositoryName, issueId) {
    return fetch(`https://api.github.com/repos/${gitUserName}/${repositoryName}/issues/${issueId}`).then(response => response.json()).then(json => json);
}

function addArticle(gitUserName, repositoryName, issueId) {
    return fetch(blogBaseUrl + `/articles`, {
        body: JSON.stringify({gitUserName, repositoryName, issueId}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    }).then(response => response.json()).then(json => json);
}

function putArticle(id, title, body, createdAt, updatedAt, closedAt) {
    return fetch(blogBaseUrl + `/articles`, {
        body: JSON.stringify({id, title, body, createdAt, updatedAt, closedAt}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
    }).then(response => response.json()).then(json => json);
}

export const blogApi = {};

blogApi.getArticles = getArticles;
blogApi.addArticle = addArticle;
blogApi.putArticle = putArticle;
blogApi.getArticle = getArticle;
blogApi.getArticleById = getArticleById;