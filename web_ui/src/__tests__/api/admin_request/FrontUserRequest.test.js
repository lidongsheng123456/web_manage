import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import { addFrontUser, batchDeleteFrontUser, queryFrontUser, queryFrontUserById, updateFrontUser } from '@/api/admin_request/FrontUserRequest'

describe('admin FrontUserRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('addFrontUser 应发送 POST /admin/front-user', async () => {
        const data = { username: 'frontuser' }
        await addFrontUser(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/front-user',
            method: 'POST',
            data: data,
        })
    })

    it('batchDeleteFrontUser 应发送 DELETE /admin/front-user/batchDelete', async () => {
        await batchDeleteFrontUser('1,2')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/front-user/batchDelete',
            method: 'DELETE',
            params: { ids: '1,2' },
        })
    })

    it('updateFrontUser 应发送 PUT /admin/front-user', async () => {
        const data = { id: 1, username: '更新' }
        await updateFrontUser(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/front-user',
            method: 'PUT',
            data: data,
        })
    })

    it('queryFrontUser 应发送 GET /admin/front-user', async () => {
        const data = { currentPage: 1, pageSize: 10 }
        await queryFrontUser(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/front-user',
            method: 'GET',
            params: data,
        })
    })

    it('queryFrontUserById 应发送 GET /admin/front-user/{id}', async () => {
        await queryFrontUserById(5)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/front-user/5',
            method: 'GET',
        })
    })
})
