import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockAdminRequest } = vi.hoisted(() => ({
    mockAdminRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/AdminRequest', () => ({ default: mockAdminRequest }))

import { login, logout, queryCurrentUser, updatePerson, validateFormerPassword } from '@/api/admin_request/WebRequest'

describe('admin WebRequest.js API', () => {
    beforeEach(() => {
        mockAdminRequest.mockClear()
    })

    it('login 应发送 POST /admin/login', async () => {
        const data = { username: 'admin', password: '123456', code: 'abcd' }
        await login(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/login',
            method: 'POST',
            data: data,
        })
    })

    it('logout 应发送 GET /admin/logout', async () => {
        await logout()
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/logout',
            method: 'GET',
        })
    })

    it('updatePerson 应发送 POST /admin/person', async () => {
        const data = { name: 'test' }
        await updatePerson(data)
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/person',
            method: 'POST',
            data: data,
        })
    })

    it('validateFormerPassword 应发送 POST /admin/validate/formerPassword', async () => {
        await validateFormerPassword('oldpwd')
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/validate/formerPassword',
            method: 'POST',
            params: { formerPassword: 'oldpwd' },
        })
    })

    it('queryCurrentUser 应发送 GET /admin/current', async () => {
        await queryCurrentUser()
        expect(mockAdminRequest).toHaveBeenCalledWith({
            url: '/admin/current',
            method: 'GET',
        })
    })
})
