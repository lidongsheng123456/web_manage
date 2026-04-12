import { describe, it, expect, vi, beforeEach } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'

// Mock admin WebRequest
vi.mock('@/api/admin_request/WebRequest', () => ({
    queryCurrentUser: vi.fn().mockResolvedValue({
        code: 200,
        data: { id: 1, username: 'admin' },
    }),
}))

// Mock axios
vi.mock('axios', () => {
    const mockInstance = {
        get: vi.fn().mockResolvedValue({
            data: { code: 200, data: { id: 2, username: 'frontuser' } }
        }),
        interceptors: {
            request: { use: vi.fn() },
            response: { use: vi.fn() },
        },
    }
    return {
        default: {
            create: vi.fn(() => mockInstance),
        },
    }
})

import { useUserStore } from '@/store/modules/user'

describe('store/modules/user.js', () => {
    beforeEach(() => {
        setActivePinia(createPinia())
    })

    it('初始 userInfo 应为空对象', () => {
        const store = useUserStore()
        expect(store.userInfo).toEqual({})
    })

    it('初始 frontUserInfo 应为空对象', () => {
        const store = useUserStore()
        expect(store.frontUserInfo).toEqual({})
    })

    it('fetchCurrentUser 应更新 userInfo', async () => {
        const store = useUserStore()
        await store.fetchCurrentUser()
        expect(store.userInfo).toEqual({ id: 1, username: 'admin' })
    })

    it('fetchCurrentFrontUserInfo 应更新 frontUserInfo', async () => {
        const store = useUserStore()
        await store.fetchCurrentFrontUserInfo()
        expect(store.frontUserInfo).toEqual({ id: 2, username: 'frontuser' })
    })

    it('$reset 应清空所有信息', async () => {
        const store = useUserStore()
        await store.fetchCurrentUser()
        expect(store.userInfo).not.toEqual({})

        store.$reset()
        expect(store.userInfo).toEqual({})
        expect(store.frontUserInfo).toEqual({})
    })
})
