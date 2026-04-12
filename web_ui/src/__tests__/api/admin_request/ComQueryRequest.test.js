import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import { addComQuery, batchDeleteComQuery, queryComQuery, queryComQueryById, updateComQuery } from '@/api/admin_request/ComQueryRequest'

describe('admin ComQueryRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('addComQuery 应发送 POST /admin/com-query', async () => {
        const data = { queryName: '测试' }
        await addComQuery(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/com-query',
            method: 'POST',
            data: data,
        })
    })

    it('batchDeleteComQuery 应发送 DELETE /admin/com-query/batchDelete', async () => {
        await batchDeleteComQuery('1,2')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/com-query/batchDelete',
            method: 'DELETE',
            params: { ids: '1,2' },
        })
    })

    it('updateComQuery 应发送 PUT /admin/com-query', async () => {
        const data = { id: 1, queryName: '更新' }
        await updateComQuery(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/com-query',
            method: 'PUT',
            data: data,
        })
    })

    it('queryComQuery 应发送 GET /admin/com-query', async () => {
        const data = { currentPage: 1, pageSize: 10 }
        await queryComQuery(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/com-query',
            method: 'GET',
            params: data,
        })
    })

    it('queryComQueryById 应发送 GET /admin/com-query/{id}', async () => {
        await queryComQueryById(3)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/com-query/3',
            method: 'GET',
        })
    })
})
