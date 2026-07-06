/**
 * 用户状态管理
 * 管理后台管理员 (adminUserInfo) 和前台用户 (frontUserInfo) 的登录信息
 */
import axios, { type AxiosInstance } from 'axios'
import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { AdminUser, FrontUser, ApiResponse } from '@/types'

// 静默请求实例（不触发全局错误提示）
const silentRequest: AxiosInstance = axios.create({
    baseURL: import.meta.env.VUE_APP_BASEURL,
    timeout: 30000,
})

export const useUserStore = defineStore('user', () => {
    const adminUserInfo = ref<AdminUser | null>(null)
    const frontUserInfo = ref<FrontUser | null>(null)

    /** 获取当前后台管理员信息 */
    async function fetchCurrentUser(): Promise<ApiResponse<AdminUser> | null> {
        try {
            const response = await silentRequest.get<ApiResponse<AdminUser>>('/admin/current')
            const res = response.data
            if (res.code === 200) {
                adminUserInfo.value = res.data
            }
            return res
        } catch {
            return null
        }
    }

    /** 获取当前前台用户信息 */
    async function fetchCurrentFrontUserInfo(): Promise<ApiResponse<FrontUser> | null> {
        try {
            const response = await silentRequest.get<ApiResponse<FrontUser>>('/user/current')
            const res = response.data
            if (res.code === 200) {
                frontUserInfo.value = res.data
            }
            return res
        } catch {
            return null
        }
    }

    /** 重置所有用户状态 */
    function $reset(): void {
        adminUserInfo.value = null
        frontUserInfo.value = null
    }

    return { adminUserInfo, frontUserInfo, fetchCurrentUser, fetchCurrentFrontUserInfo, $reset }
})
