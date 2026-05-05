import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, PageResult, Role, RoleQueryParams, RoleUserQueryParams, AdminUser, AssignRoleDTO } from "@/types"
import type { AxiosResponse } from "axios"

export function addRole(data: Partial<Role>): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/role',
        method: 'POST',
        data: data
    })
}

export function batchDeleteRole(data: string): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/role/batchDelete',
        method: 'DELETE',
        params: {
            ids: data
        }
    })
}

export function updateRole(data: Role): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/role',
        method: 'PUT',
        data: data
    })
}

export function queryRole(data: RoleQueryParams): Promise<ApiResponse<PageResult<Role>>> {
    return adminRequest({
        url: '/admin/role',
        method: 'GET',
        params: data
    })
}

export function queryRoleById(data: number): Promise<ApiResponse<Role>> {
    return adminRequest({
        url: `/admin/role/${data}`,
        method: 'GET'
    })
}

export function queryUserNotRoleId(data: RoleUserQueryParams): Promise<ApiResponse<PageResult<AdminUser>>> {
    return adminRequest({
        url: '/admin/role/notRoleId',
        method: 'GET',
        params: data
    })
}

export function queryUserByRoleId(data: RoleUserQueryParams): Promise<ApiResponse<PageResult<AdminUser>>> {
    return adminRequest({
        url: '/admin/role/role',
        method: 'GET',
        params: data
    })
}

export function exportRole(): Promise<AxiosResponse<Blob>> {
    return adminRequest({
        url: '/admin/role/export',
        method: 'GET',
        responseType: 'blob'
    })
}

export function assignRole(data: AssignRoleDTO): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/role/assign',
        method: 'POST',
        data: data
    })
}

export function removeRole(data: AssignRoleDTO): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/role/remove',
        method: 'POST',
        data: data
    })
}
