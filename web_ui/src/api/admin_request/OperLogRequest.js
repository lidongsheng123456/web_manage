import adminRequest from "@/utils/AdminRequest";

export function addOperLog(data) {
    return adminRequest({
        url: '/admin/operLog',
        method: 'POST',
        data: data
    })
}

export function batchDeleteOperLog(data) {
    return adminRequest({
        url: '/admin/operLog/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateOperLog(data) {
    return adminRequest({
        url: '/admin/operLog',
        method: 'PUT',
        data: data
    })
}

export function queryOperLog(data) {
    return adminRequest({
        url: '/admin/operLog',
        method: 'GET',
        params: data
    })
}

export function queryOperLogById(data) {
    return adminRequest({
        url: `/admin/operLog/${data}`,
        method: 'GET'
    })
}
