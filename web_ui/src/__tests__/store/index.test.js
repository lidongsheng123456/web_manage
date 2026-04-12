import { describe, it, expect } from 'vitest'
import pinia from '@/store'

describe('store/index.js', () => {
    it('应导出 pinia 实例', () => {
        expect(pinia).toBeDefined()
    })

    it('pinia 应有 install 方法', () => {
        expect(typeof pinia.install).toBe('function')
    })
})
