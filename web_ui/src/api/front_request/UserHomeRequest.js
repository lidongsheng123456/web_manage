import userRequest from "@/utils/UserRequest";

export function queryNotice(data) {
    return userRequest({
        url: '/user/home/notice',
        method: 'GET',
        params: data
    })
}
