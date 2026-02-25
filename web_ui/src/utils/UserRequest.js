import router from "@/router";
import axios from 'axios';
import { ElMessage } from "element-plus";

const adminRequest = axios.create({
    baseURL: import.meta.env.VUE_APP_BASEURL,
    timeout: 30000,
})

/**
 * 通过 /api 前缀代理转发到后端，同源请求自动携带 Cookie
 */
adminRequest.interceptors.request.use(config => {
    return config
})

adminRequest.interceptors.response.use(response => {
        let res = response.data;

        // 401 token令牌效验失败
        if (res.code === 401) {
            router.push('/UserLogin').catch(() => {
            });
            return Promise.reject(new Error(res.msg))
        } else if (res.code !== 200) {
            ElMessage.error(res.msg);
            return Promise.reject(new Error(res.msg))
        } else {
            return res
        }
    }
)

export default adminRequest
