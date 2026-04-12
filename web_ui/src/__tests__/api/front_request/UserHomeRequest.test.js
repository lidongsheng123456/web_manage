import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockUserRequest } = vi.hoisted(() => ({
    mockUserRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/UserRequest', () => ({ default: mockUserRequest }))

import { queryNotice } from '@/api/front_request/UserHomeRequest'

describe('front UserHomeRequest.js API', () => {
    beforeEach(() => {
        mockUserRequest.mockClear()
    })

    it('queryNotice 应发送 GET /user/home/notice', async () => {
        const data = { currentPage: 1, pageSize: 10 }
        await queryNotice(data)
        expect(mockUserRequest).toHaveBeenCalledWith({
            url: '/user/home/notice',
            method: 'GET',
            params: data,
        })
    })
})
