import type { AdminUser, ApiResponse, PageResult, UserQueryParams } from "@/types"
import adminRequest from "@/utils/AdminRequest"

export function addUser(data: Partial<AdminUser>): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/user',
        method: 'POST',
        data: data
    })
}

export function batchDeleteUser(data: string): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/user/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateUser(data: AdminUser): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/user',
        method: 'PUT',
        data: data
    })
}

export function queryUser(data: UserQueryParams): Promise<ApiResponse<PageResult<AdminUser>>> {
    return adminRequest({
        url: '/admin/user',
        method: 'GET',
        params: data
    })
}

export function queryUserById(data: number): Promise<ApiResponse<AdminUser>> {
    return adminRequest({
        url: `/admin/user/${data}`,
        method: 'GET'
    })
}

export function importUser(file: File): Promise<ApiResponse<string>> {
    const formData = new FormData()
    formData.append('file', file)
    return adminRequest({
        url: '/admin/user/import',
        method: 'POST',
        data: formData,
        headers: { 'Content-Type': 'multipart/form-data' }
    })
}
