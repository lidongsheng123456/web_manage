import { beforeEach, describe, expect, it, vi } from 'vitest'

const { mockUserRequest } = vi.hoisted(() => ({
    mockUserRequest: vi.fn().mockResolvedValue({ code: 200, data: {} })
}))
vi.mock('@/utils/UserRequest', () => ({ default: mockUserRequest }))

import { login, logout, queryCurrentFrontUserInfo, register, updatePerson, validateFormerPassword } from '@/api/front_request/WebRequest'

describe('front WebRequest.js API', () => {
    beforeEach(() => {
        mockUserRequest.mockClear()
    })

    it('login 应发送 POST /user/login', async () => {
        const data = { username: 'user1', password: '123456', code: 'abcd' }
        await login(data)
        expect(mockUserRequest).toHaveBeenCalledWith({
            url: '/user/login',
            method: 'POST',
            data: data,
        })
    })

    it('register 应发送 POST /user/register', async () => {
        const data = { username: 'newuser', password: '123456', code: 'abcd' }
        await register(data)
        expect(mockUserRequest).toHaveBeenCalledWith({
            url: '/user/register',
            method: 'POST',
            data: data,
        })
    })

    it('logout 应发送 GET /user/logout', async () => {
        await logout()
        expect(mockUserRequest).toHaveBeenCalledWith({
            url: '/user/logout',
            method: 'GET',
        })
    })

    it('updatePerson 应发送 POST /user/person', async () => {
        const data = { name: '新名字' }
        await updatePerson(data)
        expect(mockUserRequest).toHaveBeenCalledWith({
            url: '/user/person',
            method: 'POST',
            data: data,
        })
    })

    it('validateFormerPassword 应发送 POST /user/validate/formerPassword', async () => {
        await validateFormerPassword('oldpwd')
        expect(mockUserRequest).toHaveBeenCalledWith({
            url: '/user/validate/formerPassword',
            method: 'POST',
            params: { formerPassword: 'oldpwd' },
        })
    })

    it('queryCurrentFrontUserInfo 应发送 GET /user/current', async () => {
        await queryCurrentFrontUserInfo()
        expect(mockUserRequest).toHaveBeenCalledWith({
            url: '/user/current',
            method: 'GET',
        })
    })
})
