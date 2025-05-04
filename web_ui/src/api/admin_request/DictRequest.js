import adminRequest from "@/utils/AdminRequest";

export function addDict(data) {
    return adminRequest({
        url: '/admin/dict',
        method: 'POST',
        data: data
    })
}

export function batchDeleteDict(data) {
    return adminRequest({
        url: '/admin/dict/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateDict(data) {
    return adminRequest({
        url: '/admin/dict',
        method: 'PUT',
        data: data
    })
}

export function queryDict(data) {
    return adminRequest({
        url: '/admin/dict',
        method: 'GET',
        params: data,
    })
}

export function queryDictById(data) {
    return adminRequest({
        url: `/admin/dict/${data}`,
        method: 'GET'
    })
}
