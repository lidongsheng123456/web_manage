import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockUserRequest } = vi.hoisted(() => ({
    mockUserRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/UserRequest', () => ({ default: mockUserRequest }))

import { submitComment } from '@/api/front_request/UserOperationRequest'

describe('front UserOperationRequest.js API', () => {
    beforeEach(() => {
        mockUserRequest.mockClear()
    })

    it('submitComment 应发送 POST /user/oper/submit/comment', async () => {
        const data = { content: '评论内容' }
        await submitComment(data)
        expect(mockUserRequest).toHaveBeenCalledWith({
            url: '/user/oper/submit/comment',
            method: 'POST',
            data: data,
        })
    })
})
