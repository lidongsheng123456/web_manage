import type { ApiResponse } from "@/types"
import adminRequest from "@/utils/AdminRequest"

export interface OnlineUser {
    tokenValue: string
    loginId: number
    username: string
    name: string
    imgUrl: string
    loginTime: string
}

export function queryOnlineUsers(): Promise<ApiResponse<OnlineUser[]>> {
    return adminRequest({
        url: '/admin/online/list',
        method: 'GET',
    })
}

export function kickUser(loginId: number): Promise<ApiResponse<null>> {
    return adminRequest({
        url: `/admin/online/kick/${loginId}`,
        method: 'DELETE',
    })
}
