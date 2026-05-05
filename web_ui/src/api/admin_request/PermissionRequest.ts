import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, PageResult, Permission, PermissionQueryParams, PermissionRoleQueryParams, Role, AssignPermissionDTO } from "@/types"
import type { AxiosResponse } from "axios"

export function addPermission(data: Partial<Permission>): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/permission',
        method: 'POST',
        data: data
    })
}

export function batchDeletePermission(data: string): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/permission/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updatePermission(data: Permission): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/permission',
        method: 'PUT',
        data: data
    })
}

export function queryPermission(data: PermissionQueryParams): Promise<ApiResponse<PageResult<Permission>>> {
    return adminRequest({
        url: '/admin/permission',
        method: 'GET',
        params: data
    })
}

export function queryPermissionById(data: number): Promise<ApiResponse<Permission>> {
    return adminRequest({
        url: `/admin/permission/${data}`,
        method: 'GET'
    })
}

export function queryRoleNotPermissionId(data: PermissionRoleQueryParams): Promise<ApiResponse<PageResult<Role>>> {
    return adminRequest({
        url: '/admin/permission/NotPermissionId',
        method: 'GET',
        params: data
    })
}

export function queryRoleByPermissionId(data: PermissionRoleQueryParams): Promise<ApiResponse<PageResult<Role>>> {
    return adminRequest({
        url: '/admin/permission/permission',
        method: 'GET',
        params: data
    })
}

export function exportPermission(): Promise<AxiosResponse<Blob>> {
    return adminRequest({
        url: '/admin/permission/export',
        method: 'GET',
        responseType: 'blob'
    })
}

export function assignPermission(data: AssignPermissionDTO): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/permission/assign',
        method: 'POST',
        data: data
    })
}

export function removePermission(data: AssignPermissionDTO): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/permission/remove',
        method: 'POST',
        data: data
    })
}
