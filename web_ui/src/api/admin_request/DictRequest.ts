import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, PageResult, Dict, DictQueryParams } from "@/types"

export function addDict(data: Partial<Dict>): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/dict',
        method: 'POST',
        data: data
    })
}

export function batchDeleteDict(data: string): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/dict/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateDict(data: Dict): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/dict',
        method: 'PUT',
        data: data
    })
}

export function queryDict(data: DictQueryParams): Promise<ApiResponse<PageResult<Dict>>> {
    return adminRequest({
        url: '/admin/dict',
        method: 'GET',
        params: data,
    })
}

export function queryDictById(data: number): Promise<ApiResponse<Dict>> {
    return adminRequest({
        url: `/admin/dict/${data}`,
        method: 'GET'
    })
}
