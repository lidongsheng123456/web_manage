import userRequest from "@/utils/UserRequest"
import type { ApiResponse, Notice } from "@/types"

export function queryNotice(): Promise<ApiResponse<Notice[]>> {
    return userRequest({
        url: '/user/home/notice',
        method: 'GET',
    })
}
