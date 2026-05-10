import type { ApiResponse } from "@/types"
import adminRequest from "@/utils/AdminRequest"

export interface MessageItem {
    id: number
    title: string
    content: string
    msgType: string
    senderId: number
    receiverId: number
    isRead: number
    createTime: string
    readTime: string | null
}

export function queryMessageList(): Promise<ApiResponse<MessageItem[]>> {
    return adminRequest({
        url: '/admin/message/list',
        method: 'GET'
    })
}

export function queryUnreadCount(): Promise<ApiResponse<number>> {
    return adminRequest({
        url: '/admin/message/unread-count',
        method: 'GET'
    })
}

export function markMessageRead(id: number): Promise<ApiResponse<null>> {
    return adminRequest({
        url: `/admin/message/read/${id}`,
        method: 'PUT'
    })
}

export function markAllMessageRead(): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/message/read-all',
        method: 'PUT'
    })
}

export function deleteMessage(ids: number[]): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/message/batchDelete',
        method: 'DELETE',
        params: { ids: ids.join(',') }
    })
}
