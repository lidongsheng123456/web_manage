import router from "@/router"
import axios, { type AxiosInstance, type AxiosResponse } from 'axios'
import { ElMessage } from "element-plus"
import type { ApiResponse } from "@/types"

const userRequest: AxiosInstance = axios.create({
    baseURL: import.meta.env.VUE_APP_BASEURL,
    timeout: 30000,
})

/**
 * 通过 /api 前缀代理转发到后端，同源请求自动携带 Cookie
 */
userRequest.interceptors.request.use(config => {
    return config
})

userRequest.interceptors.response.use(
    (response: AxiosResponse<ApiResponse>) => {
        const res = response.data

        // 401 token令牌效验失败
        if (res.code === 401) {
            router.push('/UserLogin').catch(() => {
            })
            return Promise.reject(new Error(res.msg))
        } else if (res.code !== 200) {
            ElMessage.error(res.msg)
            return Promise.reject(new Error(res.msg))
        } else {
            return res as unknown as AxiosResponse
        }
    },
    (error: unknown) => {
        const err = error as { response?: { status: number } }
        const msg = err.response ? `请求失败: ${err.response.status}` : '网络异常，请检查网络连接'
        ElMessage.error(msg)
        return Promise.reject(error)
    }
)

export default userRequest
