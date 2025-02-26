import request from "@/utils/request";

export function queryData() {
    return request({
        url: '/admin/data',
        method: 'GET'
    })
}
