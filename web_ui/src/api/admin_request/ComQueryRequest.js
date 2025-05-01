import request from "@/utils/request";

export function addComQuery(data) {
    return request({
        url: '/admin/com-query',
        method: 'POST',
        data: data
    })
}

export function batchDeleteComQuery(data) {
    return request({
        url: '/admin/com-query/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateComQuery(data) {
    return request({
        url: '/admin/com-query',
        method: 'PUT',
        data: data
    })
}

export function queryComQuery(data) {
    return request({
        url: '/admin/com-query',
        method: 'GET',
        params: data,
    })
}

export function queryComQueryById(data) {
    return request({
        url: `/admin/com-query/${data}`,
        method: 'GET'
    })
}
