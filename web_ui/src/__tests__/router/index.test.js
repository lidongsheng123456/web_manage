import { describe, it, expect } from 'vitest'
import router from '@/router'

describe('router/index.js', () => {
    const routes = router.getRoutes()
    const routeNames = routes.map(r => r.name).filter(Boolean)

    it('应包含 Login 路由', () => {
        expect(routeNames).toContain('Login')
    })

    it('应包含 Manage 路由', () => {
        expect(routeNames).toContain('Manage')
    })

    it('应包含 UserLogin 路由', () => {
        expect(routeNames).toContain('UserLogin')
    })

    it('应包含 UserRegister 路由', () => {
        expect(routeNames).toContain('UserRegister')
    })

    it('应包含 Front 路由', () => {
        expect(routeNames).toContain('Front')
    })

    it('应包含 NotFound 路由', () => {
        expect(routeNames).toContain('NotFound')
    })

    it('应包含后台管理子路由 ManageDataView', () => {
        expect(routeNames).toContain('ManageDataView')
    })

    it('应包含后台管理子路由 ManageNoticeView', () => {
        expect(routeNames).toContain('ManageNoticeView')
    })

    it('应包含后台管理子路由 ManageUserView', () => {
        expect(routeNames).toContain('ManageUserView')
    })

    it('应包含后台管理子路由 ManageLogView', () => {
        expect(routeNames).toContain('ManageLogView')
    })

    it('应包含后台管理子路由 ManageDictView', () => {
        expect(routeNames).toContain('ManageDictView')
    })

    it('应包含后台管理子路由 ManagePermissionView', () => {
        expect(routeNames).toContain('ManagePermissionView')
    })

    it('应包含后台管理子路由 ManageRoleView', () => {
        expect(routeNames).toContain('ManageRoleView')
    })

    it('应包含后台管理子路由 ManageComQueryView', () => {
        expect(routeNames).toContain('ManageComQueryView')
    })

    it('应包含后台管理子路由 ManageFrontUserView', () => {
        expect(routeNames).toContain('ManageFrontUserView')
    })

    it('应包含前台子路由 HomeView', () => {
        expect(routeNames).toContain('HomeView')
    })

    it('应包含前台子路由 PersonView', () => {
        expect(routeNames).toContain('PersonView')
    })

    it('/ 路由应重定向到 /Front', () => {
        const rootRoute = router.getRoutes().find(r => r.path === '/')
        expect(rootRoute.redirect).toBe('/Front')
    })

    it('应使用 history 模式', () => {
        expect(router.options.history).toBeDefined()
    })
})
