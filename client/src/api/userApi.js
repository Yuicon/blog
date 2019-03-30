import {baseFetch} from "./base";

/**
 * @author Yuicon
 */

const userBaseUrl = "http://api.saabisu.cn/user-service";

function login(email, password) {
    return baseFetch(userBaseUrl + `/public/login`, {
        body: JSON.stringify({email, password}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    });
}

function register(email, username, password) {
    return baseFetch(userBaseUrl + `/public/register`, {
        body: JSON.stringify({email, username, password}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    });
}

function activate(email, code) {
    return baseFetch(userBaseUrl + `/public/activate`, {
        body: JSON.stringify({email, code}), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json'
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    });
}

export const userApi = {};

userApi.login = login;
userApi.register = register;
userApi.activate = activate;