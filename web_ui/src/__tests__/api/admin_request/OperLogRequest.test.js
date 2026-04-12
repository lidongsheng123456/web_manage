import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import { addOperLog, batchDeleteOperLog, queryOperLog, queryOperLogById, updateOperLog } from '@/api/admin_request/OperLogRequest'

describe('admin OperLogRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('addOperLog 应发送 POST /admin/operLog', async () => {
        const data = { operName: '操作' }
        await addOperLog(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/operLog',
            method: 'POST',
            data: data,
        })
    })

    it('batchDeleteOperLog 应发送 DELETE /admin/operLog/batchDelete', async () => {
        await batchDeleteOperLog('1,2')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/operLog/batchDelete',
            method: 'DELETE',
            params: { ids: '1,2' },
        })
    })

    it('updateOperLog 应发送 PUT /admin/operLog', async () => {
        const data = { id: 1, operName: '更新' }
        await updateOperLog(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/operLog',
            method: 'PUT',
            data: data,
        })
    })

    it('queryOperLog 应发送 GET /admin/operLog', async () => {
        const data = { currentPage: 1, pageSize: 10 }
        await queryOperLog(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/operLog',
            method: 'GET',
            params: data,
        })
    })

    it('queryOperLogById 应发送 GET /admin/operLog/{id}', async () => {
        await queryOperLogById(3)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/operLog/3',
            method: 'GET',
        })
    })
})
