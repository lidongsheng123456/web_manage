import axios, { type AxiosInstance } from 'axios'
import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { AdminUser, FrontUser, ApiResponse } from '@/types'

const silentRequest: AxiosInstance = axios.create({
    baseURL: import.meta.env.VUE_APP_BASEURL,
    timeout: 30000,
})

export const useUserStore = defineStore('user', () => {
    const adminUserInfo = ref<AdminUser | null>(null)
    const frontUserInfo = ref<FrontUser | null>(null)

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

    function $reset(): void {
        adminUserInfo.value = null
        frontUserInfo.value = null
    }

    return { adminUserInfo, frontUserInfo, fetchCurrentUser, fetchCurrentFrontUserInfo, $reset }
})
