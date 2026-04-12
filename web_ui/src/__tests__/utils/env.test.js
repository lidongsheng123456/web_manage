import { describe, it, expect } from 'vitest'
import { selectDictLabel, selectTagType } from '@/utils/env'

describe('env.js 工具函数', () => {
    const datas = {
        0: { dictLabel: '男', dictValue: '1', tagType: 'success' },
        1: { dictLabel: '女', dictValue: '2', tagType: 'warning' },
    }

    describe('selectDictLabel', () => {
        it('应返回匹配的字典标签', () => {
            expect(selectDictLabel(datas, '1')).toBe('男')
        })

        it('应返回匹配的女性标签', () => {
            expect(selectDictLabel(datas, '2')).toBe('女')
        })

        it('值不匹配时应返回原值', () => {
            expect(selectDictLabel(datas, '99')).toBe('99')
        })

        it('值为 undefined 时应返回空字符串', () => {
            expect(selectDictLabel(datas, undefined)).toBe('')
        })

        it('空字典时应返回原值', () => {
            expect(selectDictLabel({}, 'test')).toBe('test')
        })
    })

    describe('selectTagType', () => {
        it('应返回匹配的标签类型', () => {
            expect(selectTagType(datas, '1')).toBe('success')
        })

        it('应返回 warning 标签类型', () => {
            expect(selectTagType(datas, '2')).toBe('warning')
        })

        it('值不匹配时应返回 info', () => {
            expect(selectTagType(datas, '99')).toBe('info')
        })

        it('值为 undefined 时应返回 info', () => {
            expect(selectTagType(datas, undefined)).toBe('info')
        })

        it('空字典时应返回 info', () => {
            expect(selectTagType({}, 'test')).toBe('info')
        })
    })
})
