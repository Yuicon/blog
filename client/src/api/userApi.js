/**
 * @author Yuicon
 */

const userBaseUrl = "http://blogApi.saabisu.cn/user-service";

function login(email, password) {
    return fetch(userBaseUrl + `/public/login`, {
        body: JSON.stringify({email, password}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    }).then(response => response.json()).then(json => json);
}

function register(email, username, password) {
    return fetch(userBaseUrl + `/public/register`, {
        body: JSON.stringify({email, username, password}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    }).then(response => response.json()).then(json => json);
}

function activate(email, code) {
    return fetch(userBaseUrl + `/public/activate`, {
        body: JSON.stringify({email, code}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    }).then(response => response.json()).then(json => json);
}

export const userApi = {};

userApi.login = login;
userApi.register = register;
userApi.activate = activate;