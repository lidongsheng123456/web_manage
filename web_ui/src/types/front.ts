import type { PageQueryParams } from './global.d'

/** 前台用户实体（对应 FrontUserVo） */
export interface FrontUser {
  id?: number
  username: string
  name: string
  phone: string | null
  email: string | null
  imgUrl: string | null
  createTime?: string
  updateTime?: string
}

/** 前台用户登录/注册 DTO */
export interface FrontUserDto {
  id?: number
  username: string
  password: string
  name?: string
  phone?: string
  email?: string
  imgUrl?: string | null
  code: string
  createTime?: string
  updateTime?: string
}

/** 前台用户查询参数 */
export interface FrontUserQueryParams extends PageQueryParams {
  username?: string | null
  phone?: string | null
  email?: string | null
}

/** 评论提交参数 */
export interface CommentData {
  content: string
  [key: string]: unknown
}
