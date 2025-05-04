import adminRequest from "@/utils/AdminRequest";

export function addComQuery(data) {
    return adminRequest({
        url: '/admin/com-query',
        method: 'POST',
        data: data
    })
}

export function batchDeleteComQuery(data) {
    return adminRequest({
        url: '/admin/com-query/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateComQuery(data) {
    return adminRequest({
        url: '/admin/com-query',
        method: 'PUT',
        data: data
    })
}

export function queryComQuery(data) {
    return adminRequest({
        url: '/admin/com-query',
        method: 'GET',
        params: data,
    })
}

export function queryComQueryById(data) {
    return adminRequest({
        url: `/admin/com-query/${data}`,
        method: 'GET'
    })
}
