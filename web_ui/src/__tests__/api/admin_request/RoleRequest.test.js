import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import {
    addRole,
    assignRole,
    batchDeleteRole,
    exportRole,
    queryRole, queryRoleById,
    queryUserByRoleId,
    queryUserNotRoleId,
    removeRole,
    updateRole
} from '@/api/admin_request/RoleRequest'

describe('admin RoleRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('addRole 应发送 POST /admin/role', async () => {
        const data = { roleName: '编辑' }
        await addRole(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/role',
            method: 'POST',
            data: data,
        })
    })

    it('batchDeleteRole 应发送 DELETE /admin/role/batchDelete', async () => {
        await batchDeleteRole('2,3')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/role/batchDelete',
            method: 'DELETE',
            params: { ids: '2,3' },
        })
    })

    it('updateRole 应发送 PUT /admin/role', async () => {
        const data = { id: 2, roleName: '更新角色' }
        await updateRole(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/role',
            method: 'PUT',
            data: data,
        })
    })

    it('queryRole 应发送 GET /admin/role', async () => {
        const data = { currentPage: 1, pageSize: 10 }
        await queryRole(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/role',
            method: 'GET',
            params: data,
        })
    })

    it('queryRoleById 应发送 GET /admin/role/{id}', async () => {
        await queryRoleById(2)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/role/2',
            method: 'GET',
        })
    })

    it('queryUserNotRoleId 应发送 GET /admin/role/notRoleId', async () => {
        const data = { roleId: 2 }
        await queryUserNotRoleId(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/role/notRoleId',
            method: 'GET',
            params: data,
        })
    })

    it('queryUserByRoleId 应发送 GET /admin/role/role', async () => {
        const data = { roleId: 2 }
        await queryUserByRoleId(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/role/role',
            method: 'GET',
            params: data,
        })
    })

    it('exportRole 应发送 GET /admin/role/export', async () => {
        await exportRole()
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/role/export',
            method: 'GET',
            responseType: 'blob',
        })
    })

    it('assignRole 应发送 POST /admin/role/assign', async () => {
        const data = { userId: [1], roleId: 2 }
        await assignRole(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/role/assign',
            method: 'POST',
            data: data,
        })
    })

    it('removeRole 应发送 POST /admin/role/remove', async () => {
        const data = { userId: [1], roleId: 2 }
        await removeRole(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/role/remove',
            method: 'POST',
            data: data,
        })
    })
})
