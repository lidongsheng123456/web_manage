/**
 * 后台管理 - 数据中心统计接口
 */
import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, DataVo } from "@/types"

export function queryData(): Promise<ApiResponse<DataVo>> {
    return adminRequest({
        url: '/admin/data',
        method: 'GET'
    })
}
