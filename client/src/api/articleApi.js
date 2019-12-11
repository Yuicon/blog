/**
 * @author Yuicon
 */

import {http} from "./base";

const articleBaseUrl = "https://api.saabisu.cn/article-service/common";

export const articleApi = {
    getArticles: (page = 0, size = 20) => {
        return http.get(articleBaseUrl + `/public?page=${page}&size=${size}`);
    },
    getArticleById: (id) => {
        return http.get(articleBaseUrl + "/public/" + id);
    },
    addArticle: (article) => {
        return http.post(articleBaseUrl, article);
    },
    putArticle: (article) => {
        return http.put(articleBaseUrl, article);
    },
    getArticlesByTag: (tid, page = 0, size = 20) => {
        return http.get(articleBaseUrl + `/tag/${tid}?page=${page}&size=${size}`);
    },
};

const commentBaseUrl = "https://api.saabisu.cn/article-service/comment";

export const commentApi = {

    findAll: (aid, current = 1, size = 100) => {
        return http.get(`${commentBaseUrl}/public?current=${current}&size=${size}&articleId=${aid}`);
    },

    save: (comment) => {
        return http.post(`${commentBaseUrl}`, comment);
    }

};

const tagBaseUrl = "https://api.saabisu.cn/article-service/tag";

export const tagApi = {

    save: (tag) => {
        return http.post(`${tagBaseUrl}`, tag);
    },

    getTagsByArticle: (aid) => {
        return http.get(tagBaseUrl + `/article/${aid}`);
    },

    findAll: (name, current = 1, size = 10) => {
        return http.get(`${tagBaseUrl}?current=${current}&size=${size}&name=${name}`);
    },

};
