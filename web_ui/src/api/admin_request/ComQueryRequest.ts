import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, PageResult, ComQuery, ComQueryQueryParams } from "@/types"

export function addComQuery(data: Partial<ComQuery>): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/com-query',
        method: 'POST',
        data: data
    })
}

export function batchDeleteComQuery(data: string): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/com-query/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateComQuery(data: ComQuery): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/com-query',
        method: 'PUT',
        data: data
    })
}

export function queryComQuery(data: ComQueryQueryParams): Promise<ApiResponse<PageResult<ComQuery>>> {
    return adminRequest({
        url: '/admin/com-query',
        method: 'GET',
        params: data,
    })
}

export function queryComQueryById(data: number): Promise<ApiResponse<ComQuery>> {
    return adminRequest({
        url: `/admin/com-query/${data}`,
        method: 'GET'
    })
}
