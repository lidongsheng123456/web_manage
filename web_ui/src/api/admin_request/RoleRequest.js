import adminRequest from "@/utils/AdminRequest";

export function addRole(data) {
    return adminRequest({
        url: '/admin/role',
        method: 'POST',
        data: data
    })
}

export function deleteRole(data) {
    return adminRequest({
        url: `/admin/role/${data}`,
        method: 'DELETE'
    })
}

export function batchDeleteRole(data) {
    return adminRequest({
        url: '/admin/role/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateRole(data) {
    return adminRequest({
        url: '/admin/role',
        method: 'PUT',
        data: data
    })
}

export function queryRole(data) {
    return adminRequest({
        url: '/admin/role',
        method: 'GET',
        params: data
    })
}

export function queryRoleById(data) {
    return adminRequest({
        url: `/admin/role/${data}`,
        method: 'GET'
    })
}

export function queryUserNotRoleId(data) {
    return adminRequest({
        url: '/admin/role/notRoleId',
        method: 'GET',
        params: data
    })
}

export function queryUserByRoleId(data) {
    return adminRequest({
        url: '/admin/role/role',
        method: 'GET',
        params: data
    })
}

export function exportRole() {
    return adminRequest({
        url: '/admin/role/export',
        method: 'GET',
        responseType: 'blob'
    });
}

export function assignRole(data) {
    return adminRequest({
        url: '/admin/role/assign',
        method: 'POST',
        data: data
    });
}

export function removeRole(data) {
    return adminRequest({
        url: '/admin/role/remove',
        method: 'POST',
        data: data
    });
}
