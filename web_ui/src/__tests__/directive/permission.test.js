import { describe, it, expect, vi, beforeEach } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'

// Mock the user store
vi.mock('@/store/modules/user', () => ({
    useUserStore: vi.fn(() => ({
        userInfo: {
            permissions: [
                { permission_code: 'admin:user:add' },
                { permission_code: 'admin:user:delete' },
            ]
        },
    })),
}))

// Mock vue watch
vi.mock('vue', async () => {
    const actual = await vi.importActual('vue')
    return {
        ...actual,
        watch: vi.fn(),
    }
})

import permissionDirective from '@/directive/permission'

describe('permission 指令', () => {
    beforeEach(() => {
        setActivePinia(createPinia())
    })

    it('有权限时应显示元素', () => {
        const el = document.createElement('button')
        permissionDirective.mounted(el, { value: 'admin:user:add' })
        expect(el.style.display).toBe('')
    })

    it('无权限时应隐藏元素', () => {
        const el = document.createElement('button')
        permissionDirective.mounted(el, { value: 'admin:notice:add' })
        expect(el.style.display).toBe('none')
    })
})
