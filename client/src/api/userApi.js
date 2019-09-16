import {http} from "./base";

/**
 * @author Yuicon
 */

const userBaseUrl = "https://api.saabisu.cn/user-service";

function login(email, password) {
    return http.post(userBaseUrl + `/public/login`, {email, password});
}

function register(email, username, password, phone) {
    return http.post(userBaseUrl + `/public/register`, {email, username, password, phone});
}

function activate(email, code) {
    return http.post(userBaseUrl + `/activate`, {email, code});
}

function activateVerify() {
    return http.post(userBaseUrl + `/activate/verify`);
}

export const userApi = {};

userApi.login = login;
userApi.register = register;
userApi.activate = activate;
userApi.activateVerify = activateVerify;