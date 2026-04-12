import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import { queryComQueryByCode, queryDictByType } from '@/api/com_request/ComRequest'

describe('com ComRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('queryComQueryByCode 应发送 GET /common/query-com-query/{code}', async () => {
        await queryComQueryByCode('user_status')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/common/query-com-query/user_status',
            method: 'GET',
        })
    })

    it('queryDictByType 应发送 GET /common/query-dict/{dictType}', async () => {
        await queryDictByType('gender')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/common/query-dict/gender',
            method: 'GET',
        })
    })
})
