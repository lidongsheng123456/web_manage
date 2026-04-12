import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import {
    addPermission,
    assignPermission,
    batchDeletePermission,
    exportPermission,
    queryPermission, queryPermissionById,
    queryRoleByPermissionId,
    queryRoleNotPermissionId,
    removePermission,
    updatePermission
} from '@/api/admin_request/PermissionRequest'

describe('admin PermissionRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('addPermission 应发送 POST /admin/permission', async () => {
        const data = { permissionName: '用户管理' }
        await addPermission(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/permission',
            method: 'POST',
            data: data,
        })
    })

    it('batchDeletePermission 应发送 DELETE /admin/permission/batchDelete', async () => {
        await batchDeletePermission('1,2')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/permission/batchDelete',
            method: 'DELETE',
            params: { ids: '1,2' },
        })
    })

    it('updatePermission 应发送 PUT /admin/permission', async () => {
        const data = { id: 1, permissionName: '更新权限' }
        await updatePermission(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/permission',
            method: 'PUT',
            data: data,
        })
    })

    it('queryPermission 应发送 GET /admin/permission', async () => {
        const data = { currentPage: 1, pageSize: 10 }
        await queryPermission(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/permission',
            method: 'GET',
            params: data,
        })
    })

    it('queryPermissionById 应发送 GET /admin/permission/{id}', async () => {
        await queryPermissionById(5)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/permission/5',
            method: 'GET',
        })
    })

    it('queryRoleNotPermissionId 应发送 GET /admin/permission/NotPermissionId', async () => {
        const data = { permissionId: 5 }
        await queryRoleNotPermissionId(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/permission/NotPermissionId',
            method: 'GET',
            params: data,
        })
    })

    it('queryRoleByPermissionId 应发送 GET /admin/permission/permission', async () => {
        const data = { permissionId: 5 }
        await queryRoleByPermissionId(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/permission/permission',
            method: 'GET',
            params: data,
        })
    })

    it('exportPermission 应发送 GET /admin/permission/export', async () => {
        await exportPermission()
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/permission/export',
            method: 'GET',
            responseType: 'blob',
        })
    })

    it('assignPermission 应发送 POST /admin/permission/assign', async () => {
        const data = { roleId: [1], permissionId: 5 }
        await assignPermission(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/permission/assign',
            method: 'POST',
            data: data,
        })
    })

    it('removePermission 应发送 POST /admin/permission/remove', async () => {
        const data = { roleId: [1], permissionId: 5 }
        await removePermission(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/permission/remove',
            method: 'POST',
            data: data,
        })
    })
})
