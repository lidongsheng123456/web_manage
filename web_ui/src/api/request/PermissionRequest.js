import request from "@/utils/request";

export function addPermission(data) {
    return request({
        url: '/admin/permission',
        method: 'POST',
        data: data
    })
}

export function deletePermission(data) {
    return request({
        url: `/admin/permission/${data}`,
        method: 'DELETE'
    })
}

export function batchDeletePermission(data) {
    return request({
        url: '/admin/permission/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updatePermission(data) {
    return request({
        url: '/admin/permission',
        method: 'PUT',
        data: data
    })
}

export function queryPermission(data) {
    return request({
        url: '/admin/permission',
        method: 'GET',
        params: data
    })
}

export function queryPermissionById(data) {
    return request({
        url: `/admin/permission/${data}`,
        method: 'GET'
    })
}

export function queryRoleNotPermissionId(data) {
    return request({
        url: '/admin/permission/NotPermissionId',
        method: 'GET',
        params: data
    })
}

export function queryRoleByPermissionId(data) {
    return request({
        url: '/admin/permission/permission',
        method: 'GET',
        params: data
    })
}

export function exportPermission() {
    return request({
        url: '/admin/permission/export',
        method: 'GET',
        responseType: 'blob'
    });
}

export function assignPermission(data) {
    return request({
        url: '/admin/permission/assign',
        method: 'POST',
        data: data
    });
}

export function removePermission(data) {
    return request({
        url: '/admin/permission/remove',
        method: 'POST',
        data: data
    });
}
