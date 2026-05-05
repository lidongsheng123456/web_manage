import adminRequest from "@/utils/AdminRequest"
import type { ApiResponse, AdminUser, UserDto } from "@/types"

//登录
export function login(data: UserDto): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/login',
        method: 'POST',
        data: data
    })
}

// 获取验证码
export function captcha(): Promise<string | null> {
    return fetch('/api/admin/captcha', {
        method: 'get',
    }).then(response => {
        if (!response.ok) {
            throw new Error('网络响应不正常')
        }
        return response.blob()
    }).then(blob => {
        return URL.createObjectURL(blob)
    }).catch(error => {
        console.error('获取验证码失败:', error)
        return null
    })
}

//退出
export function logout(): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/logout',
        method: 'GET'
    })
}

//修改个人信息
export function updatePerson(data: Partial<AdminUser>): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/person',
        method: 'POST',
        data: data
    })
}

//修改个人信息
export function validateFormerPassword(data: string): Promise<ApiResponse<null>> {
    return adminRequest({
        url: '/admin/validate/formerPassword',
        method: 'POST',
        params: {
            formerPassword: data
        }
    })
}

// 获取当前登录用户信息
export function queryCurrentUser(): Promise<ApiResponse<AdminUser>> {
    return adminRequest({
        url: '/admin/current',
        method: 'GET'
    })
}
