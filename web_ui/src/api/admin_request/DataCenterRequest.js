import adminRequest from "@/utils/AdminRequest";

export function queryData() {
    return adminRequest({
        url: '/admin/data',
        method: 'GET'
    })
}
