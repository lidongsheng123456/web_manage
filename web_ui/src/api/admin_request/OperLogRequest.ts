import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, PageResult, OperLog, OperLogQueryParams } from "@/types"

export function addOperLog(data: Partial<OperLog>): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/operLog',
        method: 'POST',
        data: data
    })
}

export function batchDeleteOperLog(data: string): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/operLog/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateOperLog(data: OperLog): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/operLog',
        method: 'PUT',
        data: data
    })
}

export function queryOperLog(data: OperLogQueryParams): Promise<ApiResponse<PageResult<OperLog>>> {
    return adminRequest({
        url: '/admin/operLog',
        method: 'GET',
        params: data
    })
}

export function queryOperLogById(data: number): Promise<ApiResponse<OperLog>> {
    return adminRequest({
        url: `/admin/operLog/${data}`,
        method: 'GET'
    })
}
