import type { DictVo } from "@/types"

// 回显数据字典
export function selectDictLabel(datas: DictVo[], value: number | undefined): string {
    if (value === undefined) {
        return ""
    }
    const actions: string[] = []
    Object.keys(datas).some((key) => {
        if (datas[Number(key)].dictValue === value) {
            actions.push(datas[Number(key)].dictLabel)
            return true
        }
    })
    if (actions.length === 0) {
        actions.push(String(value))
    }
    return actions.join('')
}

// 回显标签类型
export function selectTagType(datas: DictVo[], value: number | undefined): string {
    if (value === undefined) {
        return "info"
    }
    const actions: string[] = []
    Object.keys(datas).some((key) => {
        if (datas[Number(key)].dictValue === value) {
            actions.push(datas[Number(key)].tagType || 'info')
            return true
        }
    })
    if (actions.length === 0) {
        actions.push('info')
    }
    return actions.join('')
}
