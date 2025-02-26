import {createRouter, createWebHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        redirect: '/Login',
    },
    {
        path: '/Login',
        name: 'Login',
        meta: {name: '登录'},
        component: () => import('@/views/LoginView.vue')
    },
    {
        path: '/Register',
        name: 'Register',
        meta: {name: '注册'},
        component: () => import('@/views/RegisterView.vue')
    },
    {
        //后台路由
        path: '/Manage',
        name: 'Manage',
        component: () => import('@/views/ManageView.vue'),
        redirect: '/Manage/ManageDataView',
        children: [
            {
                path: 'ManageDataView',
                name: 'ManageDataView',
                meta: {name: '数据中心'},
                component: () => import('@/views/manage/DataCenterView.vue')
            },
            {
                path: 'ManageNoticeView',
                name: 'ManageNoticeView',
                meta: {name: '通知管理'},
                component: () => import('@/views/manage/ManageNoticeView.vue')
            },
            {
                path: 'ManageUserView',
                name: 'ManageUserView',
                meta: {name: '用户管理'},
                component: () => import('@/views/manage/ManageUserView.vue')
            },
            {
                path: 'ManageLogView',
                name: 'ManageLogView',
                meta: {name: '日志管理'},
                component: () => import('@/views/manage/ManageOperLogView.vue')
            },
            {
                path: 'ManagePermissionView',
                name: 'ManagePermissionView',
                meta: {name: '访问权限管理'},
                component: () => import('@/views/manage/ManagePermissionView.vue')
            },
            {
                path: 'ManageRoleView',
                name: 'ManageRoleView',
                meta: {name: '角色管理'},
                component: () => import('@/views/manage/ManageRoleView.vue')
            },
            {
                path: '/DocsView',
                name: 'DocsView',
                meta: {name: '接口文档'},
                component: () => import('@/views/tool/DocsView.vue')
            },
            {
                path: 'ManagePersonView',
                name: 'ManagePersonView',
                meta: {name: '个人中心'},
                component: () => import('@/views/person/ManagePersonView.vue'),
                children: [
                    {
                        path: 'ManagePersonBaseInfoView',
                        name: 'ManagePersonBaseInfoView',
                        meta: {name: '个人中心基本资料'},
                        component: () => import('@/views/person/ManagePersonBaseInfoView.vue'),
                    },
                    {
                        path: 'ManagePersonPasswordView',
                        name: 'ManagePersonPasswordView',
                        meta: {name: '个人中心修改密码'},
                        component: () => import('@/views/person/ManagePersonPasswordView.vue'),
                    }
                ]
            }
        ]
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        meta: {name: '无法访问'},
        component: () => import('@/views/error/404View.vue')
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 全局前置守卫
// router.beforeEach((to, from, next) => {
//     const user = localStorage.getItem('xm-usr') || {}
//     if (!user.id) {
//         Cookies.remove("satoken");
//         next('/')
//     }
//     console.log(user)
// })
export default router
