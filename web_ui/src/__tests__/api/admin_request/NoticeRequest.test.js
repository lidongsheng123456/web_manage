import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import { addNotice, batchDeleteNotice, queryNotice, queryNoticeById, updateNotice } from '@/api/admin_request/NoticeRequest'

describe('admin NoticeRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('addNotice 应发送 POST /admin/notice', async () => {
        const data = { noticeTitle: '通知' }
        await addNotice(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/notice',
            method: 'POST',
            data: data,
        })
    })

    it('batchDeleteNotice 应发送 DELETE /admin/notice/batchDelete', async () => {
        await batchDeleteNotice('1,2')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/notice/batchDelete',
            method: 'DELETE',
            params: { ids: '1,2' },
        })
    })

    it('updateNotice 应发送 PUT /admin/notice', async () => {
        const data = { id: 1, noticeTitle: '更新' }
        await updateNotice(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/notice',
            method: 'PUT',
            data: data,
        })
    })

    it('queryNotice 应发送 GET /admin/notice', async () => {
        const data = { currentPage: 1, pageSize: 10 }
        await queryNotice(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/notice',
            method: 'GET',
            params: data,
        })
    })

    it('queryNoticeById 应发送 GET /admin/notice/{id}', async () => {
        await queryNoticeById(3)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/notice/3',
            method: 'GET',
        })
    })
})
