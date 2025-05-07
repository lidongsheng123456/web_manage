import userRequest from "@/utils/UserRequest";

export function submitComment(data) {
    return userRequest({
        url: '/user/oper/submit/comment',
        method: 'POST',
        data: data
    })
}
