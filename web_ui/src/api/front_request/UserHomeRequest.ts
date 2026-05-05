import userRequest from "@/utils/UserRequest"
import type { ApiResponse, PageResult, Notice, NoticeQueryParams } from "@/types"

export function queryNotice(data: NoticeQueryParams): Promise<ApiResponse<PageResult<Notice>>> {
    return userRequest({
        url: '/user/home/notice',
        method: 'GET',
        params: data
    })
}
