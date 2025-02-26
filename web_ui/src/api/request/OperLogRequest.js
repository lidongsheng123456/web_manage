import request from "@/utils/request";

export function addOperLog(data) {
    return request({
        url: '/admin/operLog',
        method: 'POST',
        data: data
    })
}

export function deleteOperLog(data) {
    return request({
        url: `/admin/operLog/${data}`,
        method: 'DELETE'
    })
}

export function batchDeleteOperLog(data) {
    return request({
        url: '/admin/operLog/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateOperLog(data) {
    return request({
        url: '/admin/operLog',
        method: 'PUT',
        data: data
    })
}

export function queryOperLog(data) {
    return request({
        url: '/admin/operLog',
        method: 'GET',
        params: data
    })
}

export function queryOperLogById(data) {
    return request({
        url: `/admin/operLog/${data}`,
        method: 'GET'
    })
}
