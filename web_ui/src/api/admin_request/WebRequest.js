import adminRequest from "@/utils/AdminRequest";

//登录
export function login(data) {
    return adminRequest({
        url: '/admin/login',
        method: 'POST',
        data: data
    })
}

//注册
export function register(data) {
    return adminRequest({
        url: '/admin/register',
        method: 'POST',
        data: data
    })
}

// 获取验证码
export function captcha() {
    return fetch(process.env.VUE_APP_BASEURL + '/admin/captcha', {
        method: 'get',
        credentials: 'include' // 确保发送Cookie
    }).then(response => {
        if (!response.ok) {
            throw new Error('网络响应不正常');
        }
        return response.blob(); // 将响应体转换为 Blob 对象
    }).then(blob => {
        return URL.createObjectURL(blob);
    }).catch(error => {
        console.error('获取验证码失败:', error);
        return null;
    });
}

//退出
export function logout() {
    return adminRequest({
        url: '/admin/logout',
        method: 'GET'
    })
}

//修改个人信息
export function updatePerson(data) {
    return adminRequest({
        url: '/admin/person',
        method: 'POST',
        data: data
    })
}

//修改个人信息
export function validateFormerPassword(data) {
    return adminRequest({
        url: '/admin/validate/formerPassword',
        method: 'POST',
        params: {
            formerPassword: data
        }
    })
}

export function queryCurrentUser() {
    return adminRequest({
        url: '/admin/current',
        method: 'GET'
    })
}
