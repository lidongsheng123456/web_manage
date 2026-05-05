/** 通用 API 响应 */
export interface ApiResponse<T = unknown> {
  code: number
  msg: string
  data: T
}

/** 通用分页响应 */
export interface PageResult<T> {
  list: T[]
  total: number
}

/** 通用分页查询参数 */
export interface PageQueryParams {
  currentPage: number
  pageSize: number
}

/** 文件上传响应 */
export interface UploadResponse {
  code: number
  msg: string
  data: string
}
