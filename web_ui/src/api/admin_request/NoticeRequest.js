import adminRequest from "@/utils/AdminRequest";

export function addNotice(data) {
    return adminRequest({
        url: '/admin/notice',
        method: 'POST',
        data: data
    })
}

export function batchDeleteNotice(data) {
    return adminRequest({
        url: '/admin/notice/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateNotice(data) {
    return adminRequest({
        url: '/admin/notice',
        method: 'PUT',
        data: data
    })
}

export function queryNotice(data) {
    return adminRequest({
        url: '/admin/notice',
        method: 'GET',
        params: data,
    })
}

export function queryNoticeById(data) {
    return adminRequest({
        url: `/admin/notice/${data}`,
        method: 'GET'
    })
}
