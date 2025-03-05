import axios from 'axios'
import router from "@/router";
import {ElMessage, ElMessageBox} from "element-plus";

const request = axios.create({
    baseURL: process.env.VUE_APP_BASEURL,
    timeout: 30000,
    withCredentials: true
})

request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    return config
}, error => {
    console.log(error)
    return Promise.reject(error)
})

request.interceptors.response.use(response => {
        let res = response.data;

        // 401 token令牌效验失败
        if (res.code === 401) {
            ElMessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
                    confirmButtonText: '重新登录',
                    cancelButtonText: '取消',
                    type: 'warning'
                }
            ).then(() => {
                router.push('/').catch(() => {
                });
            }).catch(error => {
            })
            return Promise.reject(new Error(res.msg))
        } else if (res.code !== 200) {
            ElMessage.error(res.msg);
            return Promise.reject(new Error(res.msg))
        } else {
            return res
        }
    },
    error => {
        console.log(error)
        return Promise.reject(error)
    }
)

export default request
