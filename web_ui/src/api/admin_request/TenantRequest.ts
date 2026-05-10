import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, PageResult, Tenant, TenantQueryParams } from "@/types"

export function addTenant(data: Partial<Tenant>): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/tenant',
        method: 'POST',
        data: data
    })
}

export function deleteTenant(ids: string): Promise<ApiResponse<null>> {
    return adminRequest({
        url: `/admin/tenant/${ids}`,
        method: 'DELETE'
    })
}

export function updateTenant(data: Tenant): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/tenant',
        method: 'PUT',
        data: data
    })
}

export function queryTenant(data: TenantQueryParams): Promise<ApiResponse<PageResult<Tenant>>> {
    return adminRequest({
        url: '/admin/tenant',
        method: 'GET',
        params: data,
    })
}

export function queryTenantById(id: number): Promise<ApiResponse<Tenant>> {
    return adminRequest({
        url: `/admin/tenant/${id}`,
        method: 'GET'
    })
}

export function queryAllTenant(): Promise<ApiResponse<Tenant[]>> {
    return adminRequest({
        url: '/admin/tenant/all',
        method: 'GET'
    })
}

export function exportTenant(): Promise<any> {
    return adminRequest({
        url: '/admin/tenant/export',
        method: 'GET',
        responseType: 'blob'
    })
}
