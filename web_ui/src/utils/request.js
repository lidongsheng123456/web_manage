import axios from 'axios'
import router from "@/router";

//使用axios的构造函数初始化baseURL（根路径）timeout（超时时间）
const request = axios.create({
    baseURL: process.env.VUE_APP_BASEURL,//引用.env.development文件
    timeout: 30000,
    withCredentials: true // 确保发送Cookie,因为验证码的正确信息和token在浏览器Cookie中
})

//使用请求拦截器
//统一加token
request.interceptors.request.use(config => {
    //设置请求体中的数据是json格式发送，并指定字符编码为utf-8避免乱码
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    return config
}, error => {
    console.log(error)
    //Promise 是 JavaScript 中处理异步操作的一种机制，代表了一个异步操作的最终完成（或失败）及其结果值。
    return Promise.reject(error)
})

//使用响应拦截器
//统一返回结果
request.interceptors.response.use(response => {
        let res = response.data;

        // 401 token令牌效验失败
        if (res.code === 401) {
            router.push('/').catch(() => {
            });
        }

        return res
    },
    error => {
        console.log(error)
        //Promise 是 JavaScript 中处理异步操作的一种机制，代表了一个异步操作的最终完成（或失败）及其结果值。
        return Promise.reject(error)
    }
)

export default request
