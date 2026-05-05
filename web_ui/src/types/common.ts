import type { PageQueryParams } from './global.d'

/** 字典实体 */
export interface Dict {
  id?: number
  dictType: string
  dictLabel: string
  dictValue: number
  description: string | null
  tagType: string | null
  createTime?: string
  updateTime?: string
}

/** 字典查询参数 */
export interface DictQueryParams extends PageQueryParams {
  dictType?: string | null
  dictLabel?: string | null
}

/** 字典简要信息（前端下拉等场景） */
export interface DictVo {
  dictLabel: string
  dictValue: number
  tagType: string | null
}

/** 通用查询实体 */
export interface ComQuery {
  id?: number
  name: string
  code: string
  customSql: string
  description: string | null
  createTime?: string
  updateTime?: string
}

/** 通用查询查询参数 */
export interface ComQueryQueryParams extends PageQueryParams {
  name?: string | null
  code?: string | null
}

/** 数据中心统计 VO */
export interface DataVo {
  user: DataCountTypeVo[]
  notice: DataCountTypeVo[]
  operLog: DataCountTypeVo[]
  permission: DataCountTypeVo[]
  frontUser: DataCountTypeVo[]
  role: DataCountTypeVo[]
  dictData: DataCountTypeVo[]
  comQuery: DataCountTypeVo[]
  rolePermission: DataCountTypeVo[]
  userRole: DataCountTypeVo[]
  totalUser: number
  totalNotice: number
  totalOperLog: number
  totalPermission: number
  totalFrontUser: number
  totalRole: number
  totalDictData: number
  totalComQuery: number
  totalRolePermission: number
  totalUserRole: number
}

/** 每日数量统计项 */
export interface DataCountTypeVo {
  dayOfWeek: string
  count: number
}
