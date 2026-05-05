import userRequest from "@/utils/UserRequest"
import type { ApiResponse, CommentData } from "@/types"

export function submitComment(data: CommentData): Promise<ApiResponse<null>> {
    return userRequest({
        url: '/user/oper/submit/comment',
        method: 'POST',
        data: data
    })
}
