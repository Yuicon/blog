import {message} from "antd";

/**
 * @author Yuicon
 */

function checkStatus(response) {
    if (response.ok) {
        return response.json()
    } else {
        const error = new Error(response.statusText);
        error.body = response.json();
        throw error;
    }
}


const baseFetch = async (url, data) => {
    let body = await fetch(url, data).then(checkStatus).then(json => json).catch(error => error.body);
    if (!body) {
        body = {success: false, message: "未知错误", data: null};
    }
    return body;
};

const get = (url) => {
    return baseFetch(url);
};

const post = (url, body) => {
    return baseFetch(url, {
        body: JSON.stringify(body), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        headers: {
            'content-type': 'application/json',
            'token': localStorage.getItem("accessToken"),
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    });
};

export const http = {get, post};
