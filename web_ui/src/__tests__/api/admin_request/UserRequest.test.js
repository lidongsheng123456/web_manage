import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import { addUser, batchDeleteUser, queryUser, queryUserById, updateUser } from '@/api/admin_request/UserRequest'

describe('admin UserRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('addUser 应发送 POST /admin/user', async () => {
        const data = { username: 'newuser' }
        await addUser(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/user',
            method: 'POST',
            data: data,
        })
    })

    it('batchDeleteUser 应发送 DELETE /admin/user/batchDelete', async () => {
        await batchDeleteUser('1,2,3')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/user/batchDelete',
            method: 'DELETE',
            params: { ids: '1,2,3' },
        })
    })

    it('updateUser 应发送 PUT /admin/user', async () => {
        const data = { id: 1, username: 'updated' }
        await updateUser(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/user',
            method: 'PUT',
            data: data,
        })
    })

    it('queryUser 应发送 GET /admin/user', async () => {
        const data = { currentPage: 1, pageSize: 10 }
        await queryUser(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/user',
            method: 'GET',
            params: data,
        })
    })

    it('queryUserById 应发送 GET /admin/user/{id}', async () => {
        await queryUserById(5)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/user/5',
            method: 'GET',
        })
    })
})
