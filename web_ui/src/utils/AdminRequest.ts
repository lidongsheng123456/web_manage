import router from "@/router"
import axios, { type AxiosInstance, type AxiosResponse } from 'axios'
import { ElMessage, ElMessageBox } from "element-plus"
import type { ApiResponse } from "@/types"

const adminRequest: AxiosInstance = axios.create({
    baseURL: import.meta.env.VUE_APP_BASEURL,
    timeout: 30000,
})

/**
 * 通过 /api 前缀代理转发到后端，同源请求自动携带 Cookie
 */
adminRequest.interceptors.request.use(config => {
    return config
})

adminRequest.interceptors.response.use(
    (response: AxiosResponse<ApiResponse>) => {
        const res = response.data

        // 401 token令牌效验失败
        if (res.code === 401) {
            ElMessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
                    confirmButtonText: '重新登录',
                    cancelButtonText: '取消',
                    type: 'warning'
                }
            ).then(() => {
                router.push('/Login').catch(() => {
                })
            }).catch(() => {
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

export default adminRequest
