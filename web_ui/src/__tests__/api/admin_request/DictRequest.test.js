import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import { addDict, batchDeleteDict, queryDict, queryDictById, updateDict } from '@/api/admin_request/DictRequest'

describe('admin DictRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('addDict 应发送 POST /admin/dict', async () => {
        const data = { dictLabel: '男', dictValue: '1' }
        await addDict(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/dict',
            method: 'POST',
            data: data,
        })
    })

    it('batchDeleteDict 应发送 DELETE /admin/dict/batchDelete', async () => {
        await batchDeleteDict('1,2')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/dict/batchDelete',
            method: 'DELETE',
            params: { ids: '1,2' },
        })
    })

    it('updateDict 应发送 PUT /admin/dict', async () => {
        const data = { id: 1, dictLabel: '更新' }
        await updateDict(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/dict',
            method: 'PUT',
            data: data,
        })
    })

    it('queryDict 应发送 GET /admin/dict', async () => {
        const data = { currentPage: 1, pageSize: 10 }
        await queryDict(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/dict',
            method: 'GET',
            params: data,
        })
    })

    it('queryDictById 应发送 GET /admin/dict/{id}', async () => {
        await queryDictById(3)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/dict/3',
            method: 'GET',
        })
    })
})
