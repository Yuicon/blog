import {http} from "./base";

/**
 * @author Yuicon
 */

const recordBaseUrl = "https://api.saabisu.cn/record-service";

function list() {
    return http.get(recordBaseUrl + `/records`);
}

function items(rid) {
    return http.get(recordBaseUrl + `/items?recordId=${rid}`);
}

function insert(source) {
    return http.post(recordBaseUrl + `/records`, {source});
}

function insertItem(recordId, label, value, sequence, kind) {
    return http.post(recordBaseUrl + `/items`, {recordId, label, value, sequence, kind});
}

function updateItem(id, label, value, sequence, kind, state) {
    return http.put(recordBaseUrl + `/items`, {id, label, value, sequence, kind, state});
}

export const recordApi = {};

recordApi.list = list;
recordApi.insert = insert;
recordApi.items = items;
recordApi.insertItem = insertItem;
recordApi.updateItem = updateItem;