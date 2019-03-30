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

export const baseFetch = (url, data) => {
    return fetch(url, data).then(checkStatus).then(json => json).catch(error => error.body);
};