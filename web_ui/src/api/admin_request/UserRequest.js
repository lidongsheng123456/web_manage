import adminRequest from "@/utils/AdminRequest";

export function addUser(data) {
    return adminRequest({
        url: '/admin/user',
        method: 'POST',
        data: data
    })
}

export function batchDeleteUser(data) {
    return adminRequest({
        url: '/admin/user/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateUser(data) {
    return adminRequest({
        url: '/admin/user',
        method: 'PUT',
        data: data
    })
}

export function queryUser(data) {
    return adminRequest({
        url: '/admin/user',
        method: 'GET',
        params: data
    })
}

export function queryUserById(data) {
    return adminRequest({
        url: `/admin/user/${data}`,
        method: 'GET'
    })
}
