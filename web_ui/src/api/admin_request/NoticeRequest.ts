import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, PageResult, Notice, NoticeQueryParams } from "@/types"

export function addNotice(data: Partial<Notice>): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/notice',
        method: 'POST',
        data: data
    })
}

export function batchDeleteNotice(data: string): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/notice/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateNotice(data: Notice): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/notice',
        method: 'PUT',
        data: data
    })
}

export function queryNotice(data: NoticeQueryParams): Promise<ApiResponse<PageResult<Notice>>> {
    return adminRequest({
        url: '/admin/notice',
        method: 'GET',
        params: data,
    })
}

export function queryNoticeById(data: number): Promise<ApiResponse<Notice>> {
    return adminRequest({
        url: `/admin/notice/${data}`,
        method: 'GET'
    })
}
