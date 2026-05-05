import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, DictVo } from "@/types"

/**
 * 查询动态字典
 */
export function queryComQueryByCode(code: string): Promise<ApiResponse<Record<string, unknown>[]>> {
    return adminRequest({
        url: `/common/query-com-query/${code}`,
        method: 'GET'
    })
}

/**
 * 查询静态字典
 */
export function queryDictByType(dictType: string): Promise<ApiResponse<DictVo[]>> {
    return adminRequest({
        url: `/common/query-dict/${dictType}`,
        method: 'GET'
    })
}
