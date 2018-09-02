/**
 * @author Yuicon
 */

const baseUrl = "http://localhost:8080";

function getArticles(page = 0, size = 20) {
    return fetch(baseUrl + `/articles?page=${page}&size=${size}`).then(response => response.json()).then(json => json);
}

function getArticle(gitUserName, repositoryName, issueId) {
    return fetch(`https://api.github.com/repos/${gitUserName}/${repositoryName}/issues/${issueId}`).then(response => response.json()).then(json => json);
}

function addArticle(gitUserName, repositoryName, issueId) {
    return fetch(baseUrl + `/articles`, {
        body: JSON.stringify({gitUserName, repositoryName, issueId}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    }).then(response => response.json()).then(json => json);
}

function putArticle(id, title, body, createdAt, updatedAt, closedAt) {
    return fetch(baseUrl + `/articles`, {
        body: JSON.stringify({id, title, body, createdAt, updatedAt, closedAt}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
    }).then(response => response.json()).then(json => json);
}

export const api = {};

api.getArticles = getArticles;
api.addArticle = addArticle;
api.putArticle = putArticle;
api.getArticle = getArticle;