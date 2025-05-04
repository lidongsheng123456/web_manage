import adminRequest from "@/utils/AdminRequest";

export function addPermission(data) {
    return adminRequest({
        url: '/admin/permission',
        method: 'POST',
        data: data
    })
}

export function deletePermission(data) {
    return adminRequest({
        url: `/admin/permission/${data}`,
        method: 'DELETE'
    })
}

export function batchDeletePermission(data) {
    return adminRequest({
        url: '/admin/permission/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updatePermission(data) {
    return adminRequest({
        url: '/admin/permission',
        method: 'PUT',
        data: data
    })
}

export function queryPermission(data) {
    return adminRequest({
        url: '/admin/permission',
        method: 'GET',
        params: data
    })
}

export function queryPermissionById(data) {
    return adminRequest({
        url: `/admin/permission/${data}`,
        method: 'GET'
    })
}

export function queryRoleNotPermissionId(data) {
    return adminRequest({
        url: '/admin/permission/NotPermissionId',
        method: 'GET',
        params: data
    })
}

export function queryRoleByPermissionId(data) {
    return adminRequest({
        url: '/admin/permission/permission',
        method: 'GET',
        params: data
    })
}

export function exportPermission() {
    return adminRequest({
        url: '/admin/permission/export',
        method: 'GET',
        responseType: 'blob'
    });
}

export function assignPermission(data) {
    return adminRequest({
        url: '/admin/permission/assign',
        method: 'POST',
        data: data
    });
}

export function removePermission(data) {
    return adminRequest({
        url: '/admin/permission/remove',
        method: 'POST',
        data: data
    });
}
