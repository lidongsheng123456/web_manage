import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, PageResult, FrontUser, FrontUserQueryParams } from "@/types"

export function addFrontUser(data: Partial<FrontUser>): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/front-user',
        method: 'POST',
        data: data
    })
}

export function batchDeleteFrontUser(data: string): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/front-user/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateFrontUser(data: FrontUser): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/front-user',
        method: 'PUT',
        data: data
    })
}

export function queryFrontUser(data: FrontUserQueryParams): Promise<ApiResponse<PageResult<FrontUser>>> {
    return adminRequest({
        url: '/admin/front-user',
        method: 'GET',
        params: data,
    })
}

export function queryFrontUserById(data: number): Promise<ApiResponse<FrontUser>> {
    return adminRequest({
        url: `/admin/front-user/${data}`,
        method: 'GET'
    })
}
