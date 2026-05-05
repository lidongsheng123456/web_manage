import userRequest from "@/utils/UserRequest"
import type { ApiResponse, FrontUser, FrontUserDto } from "@/types"

//登录
export function login(data: FrontUserDto): Promise<ApiResponse<null>> {
    return userRequest({
        url: '/user/login',
        method: 'POST',
        data: data
    })
}

//注册
export function register(data: FrontUserDto): Promise<ApiResponse<null>> {
    return userRequest({
        url: '/user/register',
        method: 'POST',
        data: data
    })
}

// 获取验证码
export function captcha(): Promise<string | null> {
    return fetch('/api/user/captcha', {
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
    return userRequest({
        url: '/user/logout',
        method: 'GET'
    })
}

//修改个人信息
export function updatePerson(data: Partial<FrontUser>): Promise<ApiResponse<null>> {
    return userRequest({
        url: '/user/person',
        method: 'POST',
        data: data
    })
}

//修改个人信息
export function validateFormerPassword(data: string): Promise<ApiResponse<null>> {
    return userRequest({
        url: '/user/validate/formerPassword',
        method: 'POST',
        params: {
            formerPassword: data
        }
    })
}

export function queryCurrentFrontUserInfo(): Promise<ApiResponse<FrontUser>> {
    return userRequest({
        url: '/user/current',
        method: 'GET'
    })
}
