import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import noMoreClick from '@/directive/noMoreClick'

describe('noMoreClick 指令', () => {
    let el

    beforeEach(() => {
        vi.useFakeTimers()
        el = document.createElement('button')
    })

    afterEach(() => {
        vi.useRealTimers()
    })

    it('点击后应禁用按钮', () => {
        noMoreClick.mounted(el, { value: 2000 })
        el.click()

        expect(el.disabled).toBe(true)
        expect(el.classList.contains('is-disabled')).toBe(true)
    })

    it('超时后应恢复按钮', () => {
        noMoreClick.mounted(el, { value: 2000 })
        el.click()

        vi.advanceTimersByTime(2000)

        expect(el.disabled).toBe(false)
        expect(el.classList.contains('is-disabled')).toBe(false)
    })

    it('应使用默认延迟 2000ms', () => {
        noMoreClick.mounted(el, {})
        el.click()

        expect(el.disabled).toBe(true)

        vi.advanceTimersByTime(1999)
        expect(el.disabled).toBe(true)

        vi.advanceTimersByTime(1)
        expect(el.disabled).toBe(false)
    })

    it('应支持自定义延迟', () => {
        noMoreClick.mounted(el, { value: 500 })
        el.click()

        expect(el.disabled).toBe(true)

        vi.advanceTimersByTime(500)
        expect(el.disabled).toBe(false)
    })
})
