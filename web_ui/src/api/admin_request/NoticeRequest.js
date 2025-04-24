import request from "@/utils/request";

export function addNotice(data) {
    return request({
        url: '/admin/notice',
        method: 'POST',
        data: data
    })
}

export function deleteNotice(data) {
    return request({
        url: `/admin/notice/${data}`,
        method: 'DELETE'
    })
}

export function batchDeleteNotice(data) {
    return request({
        url: '/admin/notice/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateNotice(data) {
    return request({
        url: '/admin/notice',
        method: 'PUT',
        data: data
    })
}

export function queryNotice(data) {
    return request({
        url: '/admin/notice',
        method: 'GET',
        params: data,
    })
}

export function queryNoticeById(data) {
    return request({
        url: `/admin/notice/${data}`,
        method: 'GET'
    })
}
