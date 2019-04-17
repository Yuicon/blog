/**
 * @author Yuicon
 */

import {TOKEN_KEY} from "../constant";

function checkStatus(response) {
    if (response.ok) {
        return response.json()
    }
    const error = new Error(response.statusText);
    if (response.status === 401) {
        error.body = {success: false, message: "未登录或登录已过期", data: null};
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem("username");
        window.location.reload();
    } else {
        try {
            error.body = response.json();
        } catch (e) {
            error.body = {success: false, message: "未知错误", data: null};
        }
    }
    throw error;
}


export const baseFetch = (url, data) => {
    return fetch(url, data).then(checkStatus).then(json => json).catch(error => {
        if (error.body) {
            return error.body;
        }
        return {success: false, message: "未知错误", data: null};
    });
};

const get = (url) => {
    return baseFetch(url, {
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json',
            'token': localStorage.getItem(TOKEN_KEY),
        },
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
    });
};

const post = (url, body) => {
    return baseFetch(url, {
        body: JSON.stringify(body), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json',
            'token': localStorage.getItem(TOKEN_KEY),
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    });
};

const put = (url, body) => {
    return baseFetch(url, {
        body: JSON.stringify(body), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json',
            'token': localStorage.getItem(TOKEN_KEY),
        },
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
    });
};

export const http = {get, post, put};
