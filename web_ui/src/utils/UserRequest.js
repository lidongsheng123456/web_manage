import axios from 'axios'
import router from "@/router";
import {ElMessage} from "element-plus";

const adminRequest = axios.create({
    baseURL: process.env.VUE_APP_BASEURL,
    timeout: 30000,
    withCredentials: true
})

/**
 * 后端使用sa-token不需要手动指定请求头token
 * 响应时会将token自动注入到Cookie
 * 请求时网络请求携带着Cookie，sa-token会自动提取Cookie中的token验证
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
