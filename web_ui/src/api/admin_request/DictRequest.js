import request from "@/utils/request";

export function addDict(data) {
    return request({
        url: '/admin/dict',
        method: 'POST',
        data: data
    })
}

export function batchDeleteDict(data) {
    return request({
        url: '/admin/dict/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateDict(data) {
    return request({
        url: '/admin/dict',
        method: 'PUT',
        data: data
    })
}

export function queryDict(data) {
    return request({
        url: '/admin/dict',
        method: 'GET',
        params: data,
    })
}

export function queryDictById(data) {
    return request({
        url: `/admin/dict/${data}`,
        method: 'GET'
    })
}
