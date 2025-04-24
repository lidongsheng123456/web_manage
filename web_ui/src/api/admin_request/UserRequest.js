import request from "@/utils/request";

export function addUser(data) {
    return request({
        url: '/admin/user',
        method: 'POST',
        data: data
    })
}

export function deleteUser(data) {
    return request({
        url: `/admin/user/${data}`,
        method: 'DELETE'
    })
}

export function batchDeleteUser(data) {
    return request({
        url: '/admin/user/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateUser(data) {
    return request({
        url: '/admin/user',
        method: 'PUT',
        data: data
    })
}

export function queryUser(data) {
    return request({
        url: '/admin/user',
        method: 'GET',
        params: data
    })
}

export function queryUserById(data) {
    return request({
        url: `/admin/user/${data}`,
        method: 'GET'
    })
}

export function queryCurrentUser() {
    return request({
        url: '/admin/user/current',
        method: 'GET'
    })
}
