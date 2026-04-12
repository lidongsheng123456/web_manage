import { describe, expect, it, vi } from 'vitest'

const { mockCreate, mockRequestUse, mockResponseUse } = vi.hoisted(() => {
    const mockRequestUse = vi.fn()
    const mockResponseUse = vi.fn()
    const mockCreate = vi.fn(() => ({
        interceptors: {
            request: { use: mockRequestUse },
            response: { use: mockResponseUse },
        },
    }))
    return { mockCreate, mockRequestUse, mockResponseUse }
})

vi.mock('axios', () => ({ default: { create: mockCreate } }))
vi.mock('@/router', () => ({ default: { push: vi.fn().mockReturnValue(Promise.resolve()) } }))

describe('AdminRequest.js', () => {
    it('应使用 axios.create 创建实例', async () => {
        await import('@/utils/AdminRequest')
        expect(mockCreate).toHaveBeenCalled()
    })

    it('创建时应设置 timeout 为 30000', async () => {
        await import('@/utils/AdminRequest')
        const callArgs = mockCreate.mock.calls[0][0]
        expect(callArgs.timeout).toBe(30000)
    })

    it('应注册请求拦截器', async () => {
        await import('@/utils/AdminRequest')
        expect(mockRequestUse).toHaveBeenCalled()
    })

    it('应注册响应拦截器', async () => {
        await import('@/utils/AdminRequest')
        expect(mockResponseUse).toHaveBeenCalled()
    })
})
