/**
 * 前后端 API 路径对齐验证测试
 *
 * 后端 Controller RequestMapping:
 *   /admin              -> AdminWebController
 *   /admin/user         -> AdminUserController
 *   /admin/role         -> AdminRoleController
 *   /admin/permission   -> AdminPermissionController
 *   /admin/operLog      -> AdminOperlogController
 *   /admin/notice       -> AdminNoticeController
 *   /admin/front-user   -> AdminFrontUserController
 *   /admin/dict         -> AdminDictController
 *   /admin/data         -> AdminDataCenterController
 *   /admin/com-query    -> AdminComQueryController
 *   /common             -> ComQueryController
 *   /common/files       -> FileController
 *   /user               -> UserWebController
 *   /user/home          -> UserHomeController
 *   /user/oper          -> UserOperationController
 */
import { describe, it, expect, vi } from 'vitest'

// 收集所有前端 API 请求的 url + method
const calls = []

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn(config => { calls.push(config); return Promise.resolve({ code: 200, data: {} }) })
}))
const { mockUserRequest } = vi.hoisted(() => ({
    mockUserRequest: vi.fn(config => { calls.push(config); return Promise.resolve({ code: 200, data: {} }) })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))
vi.mock('@/utils/UserRequest', () => ({ default: mockUserRequest }))

// ===== 后端接口路径映射 =====
const BACKEND_ENDPOINTS = {
    // AdminWebController -> /admin
    'POST /admin/login': true,
    'GET /admin/captcha': true,
    'GET /admin/logout': true,
    'POST /admin/person': true,
    'POST /admin/validate/formerPassword': true,
    'GET /admin/current': true,

    // AdminUserController -> /admin/user
    'POST /admin/user': true,
    'DELETE /admin/user/batchDelete': true,
    'PUT /admin/user': true,
    'GET /admin/user': true,
    'GET /admin/user/{id}': true,
    'GET /admin/user/export': true,

    // AdminRoleController -> /admin/role
    'POST /admin/role': true,
    'DELETE /admin/role/batchDelete': true,
    'PUT /admin/role': true,
    'GET /admin/role': true,
    'GET /admin/role/{id}': true,
    'GET /admin/role/notRoleId': true,
    'GET /admin/role/role': true,
    'GET /admin/role/export': true,
    'POST /admin/role/assign': true,
    'POST /admin/role/remove': true,

    // AdminPermissionController -> /admin/permission
    'POST /admin/permission': true,
    'DELETE /admin/permission/batchDelete': true,
    'PUT /admin/permission': true,
    'GET /admin/permission': true,
    'GET /admin/permission/{id}': true,
    'GET /admin/permission/NotPermissionId': true,
    'GET /admin/permission/permission': true,
    'GET /admin/permission/export': true,
    'POST /admin/permission/assign': true,
    'POST /admin/permission/remove': true,

    // AdminOperlogController -> /admin/operLog
    'POST /admin/operLog': true,
    'DELETE /admin/operLog/batchDelete': true,
    'PUT /admin/operLog': true,
    'GET /admin/operLog': true,
    'GET /admin/operLog/{id}': true,
    'GET /admin/operLog/export': true,

    // AdminNoticeController -> /admin/notice
    'POST /admin/notice': true,
    'DELETE /admin/notice/batchDelete': true,
    'PUT /admin/notice': true,
    'GET /admin/notice': true,
    'GET /admin/notice/{id}': true,
    'GET /admin/notice/export': true,

    // AdminFrontUserController -> /admin/front-user
    'POST /admin/front-user': true,
    'DELETE /admin/front-user/batchDelete': true,
    'PUT /admin/front-user': true,
    'GET /admin/front-user': true,
    'GET /admin/front-user/{id}': true,
    'GET /admin/front-user/export': true,

    // AdminDictController -> /admin/dict
    'POST /admin/dict': true,
    'DELETE /admin/dict/batchDelete': true,
    'PUT /admin/dict': true,
    'GET /admin/dict': true,
    'GET /admin/dict/{id}': true,
    'GET /admin/dict/export': true,

    // AdminDataCenterController -> /admin/data
    'GET /admin/data': true,

    // AdminComQueryController -> /admin/com-query
    'POST /admin/com-query': true,
    'DELETE /admin/com-query/batchDelete': true,
    'PUT /admin/com-query': true,
    'GET /admin/com-query': true,
    'GET /admin/com-query/{id}': true,
    'GET /admin/com-query/export': true,

    // ComQueryController -> /common
    'GET /common/query-dict/{dictType}': true,
    'GET /common/query-com-query/{code}': true,

    // UserWebController -> /user
    'POST /user/login': true,
    'POST /user/register': true,
    'GET /user/captcha': true,
    'GET /user/logout': true,
    'POST /user/person': true,
    'POST /user/validate/formerPassword': true,
    'GET /user/current': true,

    // UserHomeController -> /user/home
    'GET /user/home/notice': true,

    // UserOperationController -> /user/oper (currently empty)
}

/**
 * 将前端调用中的 url 规范化为后端路径模式
 * 如 /admin/user/5 -> /admin/user/{id}
 */
function normalizeUrl(url) {
    // 匹配尾部的纯数字路径段，替换为 {id}
    return url.replace(/\/\d+$/, '/{id}')
        // 匹配 /query-dict/xxx -> /query-dict/{dictType}
        .replace(/\/query-dict\/[^/]+$/, '/query-dict/{dictType}')
        // 匹配 /query-com-query/xxx -> /query-com-query/{code}
        .replace(/\/query-com-query\/[^/]+$/, '/query-com-query/{code}')
}

describe('前后端 API 路径对齐验证', () => {
    // --- Admin WebRequest ---
    it('admin login 路径对齐', async () => {
        const { login } = await import('@/api/admin_request/WebRequest')
        calls.length = 0
        await login({ username: 'a', password: 'b', code: 'c' })
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin logout 路径对齐', async () => {
        const { logout } = await import('@/api/admin_request/WebRequest')
        calls.length = 0
        await logout()
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin updatePerson 路径对齐', async () => {
        const { updatePerson } = await import('@/api/admin_request/WebRequest')
        calls.length = 0
        await updatePerson({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin validateFormerPassword 路径对齐', async () => {
        const { validateFormerPassword } = await import('@/api/admin_request/WebRequest')
        calls.length = 0
        await validateFormerPassword('pwd')
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin queryCurrentUser 路径对齐', async () => {
        const { queryCurrentUser } = await import('@/api/admin_request/WebRequest')
        calls.length = 0
        await queryCurrentUser()
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    // --- Admin UserRequest ---
    it('admin addUser 路径对齐', async () => {
        const { addUser } = await import('@/api/admin_request/UserRequest')
        calls.length = 0
        await addUser({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin batchDeleteUser 路径对齐', async () => {
        const { batchDeleteUser } = await import('@/api/admin_request/UserRequest')
        calls.length = 0
        await batchDeleteUser('1,2')
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin updateUser 路径对齐', async () => {
        const { updateUser } = await import('@/api/admin_request/UserRequest')
        calls.length = 0
        await updateUser({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin queryUser 路径对齐', async () => {
        const { queryUser } = await import('@/api/admin_request/UserRequest')
        calls.length = 0
        await queryUser({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin queryUserById 路径对齐', async () => {
        const { queryUserById } = await import('@/api/admin_request/UserRequest')
        calls.length = 0
        await queryUserById(1)
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    // --- Admin NoticeRequest ---
    it('admin addNotice 路径对齐', async () => {
        const { addNotice } = await import('@/api/admin_request/NoticeRequest')
        calls.length = 0
        await addNotice({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin queryNoticeById 路径对齐', async () => {
        const { queryNoticeById } = await import('@/api/admin_request/NoticeRequest')
        calls.length = 0
        await queryNoticeById(1)
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    // --- Admin RoleRequest ---
    it('admin assignRole 路径对齐', async () => {
        const { assignRole } = await import('@/api/admin_request/RoleRequest')
        calls.length = 0
        await assignRole({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin exportRole 路径对齐', async () => {
        const { exportRole } = await import('@/api/admin_request/RoleRequest')
        calls.length = 0
        await exportRole()
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    // --- Admin PermissionRequest ---
    it('admin assignPermission 路径对齐', async () => {
        const { assignPermission } = await import('@/api/admin_request/PermissionRequest')
        calls.length = 0
        await assignPermission({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('admin exportPermission 路径对齐', async () => {
        const { exportPermission } = await import('@/api/admin_request/PermissionRequest')
        calls.length = 0
        await exportPermission()
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    // --- Admin DataCenterRequest ---
    it('admin queryData 路径对齐', async () => {
        const { queryData } = await import('@/api/admin_request/DataCenterRequest')
        calls.length = 0
        await queryData()
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    // --- ComRequest ---
    it('common queryDictByType 路径对齐', async () => {
        const { queryDictByType } = await import('@/api/com_request/ComRequest')
        calls.length = 0
        await queryDictByType('gender')
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('common queryComQueryByCode 路径对齐', async () => {
        const { queryComQueryByCode } = await import('@/api/com_request/ComRequest')
        calls.length = 0
        await queryComQueryByCode('user_status')
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    // --- Front WebRequest ---
    it('front login 路径对齐', async () => {
        const { login } = await import('@/api/front_request/WebRequest')
        calls.length = 0
        await login({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('front register 路径对齐', async () => {
        const { register } = await import('@/api/front_request/WebRequest')
        calls.length = 0
        await register({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('front logout 路径对齐', async () => {
        const { logout } = await import('@/api/front_request/WebRequest')
        calls.length = 0
        await logout()
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('front updatePerson 路径对齐', async () => {
        const { updatePerson } = await import('@/api/front_request/WebRequest')
        calls.length = 0
        await updatePerson({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('front validateFormerPassword 路径对齐', async () => {
        const { validateFormerPassword } = await import('@/api/front_request/WebRequest')
        calls.length = 0
        await validateFormerPassword('pwd')
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    it('front queryCurrentFrontUserInfo 路径对齐', async () => {
        const { queryCurrentFrontUserInfo } = await import('@/api/front_request/WebRequest')
        calls.length = 0
        await queryCurrentFrontUserInfo()
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })

    // --- Front UserHomeRequest ---
    it('front queryNotice 路径对齐', async () => {
        const { queryNotice } = await import('@/api/front_request/UserHomeRequest')
        calls.length = 0
        await queryNotice({})
        const key = `${calls[0].method.toUpperCase()} ${normalizeUrl(calls[0].url)}`
        expect(BACKEND_ENDPOINTS[key]).toBe(true)
    })
})
