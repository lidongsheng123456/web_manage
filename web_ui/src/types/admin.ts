import type { PageQueryParams } from './global.d'

/** 后台用户实体（对应 UserVo） */
export interface AdminUser {
  id?: number
  username: string
  name: string
  phone: string
  email: string
  imgUrl: string | null
  tenantId?: number
  createTime?: string
  updateTime?: string
  permissions?: UserPermission[]
  roles?: UserRole[]
}

/** 用户关联的权限信息 */
export interface UserPermission {
  permission_code: string
  permission_name: string
  [key: string]: unknown
}

/** 用户关联的角色信息 */
export interface UserRole {
  role_code: string
  role_name: string
  [key: string]: unknown
}

/** 用户登录/注册 DTO */
export interface UserDto {
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

/** 用户查询参数 */
export interface UserQueryParams extends PageQueryParams {
  username?: string | null
  phone?: string | null
  email?: string | null
}

/** 角色实体 */
export interface Role {
  id?: number
  roleCode: string
  roleName: string
  description: string
  createTime?: string
  updateTime?: string
}

/** 角色查询参数 */
export interface RoleQueryParams extends PageQueryParams {
  roleCode?: string | null
  roleName?: string | null
}

/** 角色分配用户查询参数 */
export interface RoleUserQueryParams extends PageQueryParams {
  roleId?: number
  id?: number
  username?: string | null
  phone?: string | null
  email?: string | null
}

/** 分配角色 DTO */
export interface AssignRoleDTO {
  roleId: number
  userId: number[]
}

/** 权限实体 */
export interface Permission {
  id?: number
  permissionCode: string
  permissionName: string
  description: string
  createTime?: string
  updateTime?: string
}

/** 权限查询参数 */
export interface PermissionQueryParams extends PageQueryParams {
  permissionCode?: string | null
  permissionName?: string | null
}

/** 权限分配角色查询参数 */
export interface PermissionRoleQueryParams extends PageQueryParams {
  permissionId?: number
  id?: number
  roleCode?: string | null
  roleName?: string | null
}

/** 分配权限 DTO */
export interface AssignPermissionDTO {
  permissionId: number
  roleId: number[]
}

/** 通知实体（对应 NoticeVo） */
export interface Notice {
  id?: number
  noticeTitle: string
  noticeContent: string
  createTime?: string
  updateTime?: string
  createUserId?: number
  username?: string
}

/** 通知查询参数 */
export interface NoticeQueryParams extends PageQueryParams {
  noticeTitle?: string | null
}

/** 租户实体 */
export interface Tenant {
  id?: number
  tenantName: string
  contactName?: string
  contactPhone?: string
  status?: number
  remark?: string
  createTime?: string
  updateTime?: string
}

/** 租户查询参数 */
export interface TenantQueryParams extends PageQueryParams {
  tenantName?: string | null
  status?: number | null
}

/** 操作日志实体 */
export interface OperLog {
  id?: number
  title: string
  businessType: string
  method: string
  requestMethod: string
  operName: string
  operIp?: string
  operLocation?: string
  browser?: string
  os?: string
  operUrl: string
  operParam: string
  jsonResult: string
  status: number
  errorMsg: string
  operTime?: string
  costTime: number
}

/** 操作日志查询参数 */
export interface OperLogQueryParams extends PageQueryParams {
  title?: string | null
  businessType?: string | null
  operName?: string | null
}
