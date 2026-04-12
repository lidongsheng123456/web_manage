import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import { queryData } from '@/api/admin_request/DataCenterRequest'

describe('admin DataCenterRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('queryData 应发送 GET /admin/data', async () => {
        await queryData()
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/data',
            method: 'GET',
        })
    })
})
