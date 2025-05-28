import adminRequest from "@/utils/AdminRequest";

export function addFrontUser(data) {
    return adminRequest({
        url: '/admin/front-user',
        method: 'POST',
        data: data
    })
}

export function batchDeleteFrontUser(data) {
    return adminRequest({
        url: '/admin/front-user/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateFrontUser(data) {
    return adminRequest({
        url: '/admin/front-user',
        method: 'PUT',
        data: data
    })
}

export function queryFrontUser(data) {
    return adminRequest({
        url: '/admin/front-user',
        method: 'GET',
        params: data,
    })
}

export function queryFrontUserById(data) {
    return adminRequest({
        url: `/admin/front-user/${data}`,
        method: 'GET'
    })
}
