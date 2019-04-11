import {http} from "./base";

/**
 * @author Yuicon
 */

const recordBaseUrl = "https://api.saabisu.cn/record-service";

function list() {
    return http.get(recordBaseUrl + `/records`);
}

function insert(source) {
    return http.post(recordBaseUrl + `/records`, {source});
}

export const recordApi = {};

recordApi.list = list;
recordApi.insert = insert;