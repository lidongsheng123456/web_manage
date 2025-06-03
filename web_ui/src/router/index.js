import {createRouter, createWebHistory} from 'vue-router'

const routes = [
    //后台路由
    {
        path: '/',
        redirect: '/Front',
    },
    {
        path: '/Login',
        name: 'Login',
        meta: {name: '登录'},
        component: () => import('@/views/background/LoginView.vue')
    },
    {
        path: '/Manage',
        name: 'Manage',
        component: () => import('@/views/background/ManageView.vue'),
        redirect: '/Manage/ManageDataView',
        children: [
            {
                path: 'ManageDataView',
                name: 'ManageDataView',
                meta: {name: '数据中心'},
                component: () => import('@/views/background/manage/DataCenterView.vue')
            },
            {
                path: 'ManageNoticeView',
                name: 'ManageNoticeView',
                meta: {name: '通知管理'},
                component: () => import('@/views/background/manage/ManageNoticeView.vue')
            },
            {
                path: 'ManageUserView',
                name: 'ManageUserView',
                meta: {name: '用户管理'},
                component: () => import('@/views/background/manage/ManageUserView.vue')
            },
            {
                path: 'ManageLogView',
                name: 'ManageLogView',
                meta: {name: '日志管理'},
                component: () => import('@/views/background/manage/ManageOperLogView.vue')
            },
            {
                path: 'ManageDictView',
                name: 'ManageDictView',
                meta: {name: '字典管理'},
                component: () => import('@/views/background/manage/ManageDictView.vue')
            },
            {
                path: 'ManagePermissionView',
                name: 'ManagePermissionView',
                meta: {name: '访问权限管理'},
                component: () => import('@/views/background/manage/ManagePermissionView.vue')
            },
            {
                path: 'ManageRoleView',
                name: 'ManageRoleView',
                meta: {name: '角色管理'},
                component: () => import('@/views/background/manage/ManageRoleView.vue')
            },
            {
                path: 'DocsView',
                name: 'DocsView',
                meta: {name: '接口文档'},
                component: () => import('@/views/background/manage/DocsView.vue')
            },
            {
                path: 'ManageComQueryView',
                name: 'ManageComQueryView',
                meta: {name: '通用查询'},
                component: () => import('@/views/background/manage/ManageComQueryView.vue')
            },
            {
                path: 'ManageFrontUserView',
                name: 'ManageFrontUserView',
                meta: {name: '前台用户管理'},
                component: () => import('@/views/background/manage/ManageFrontUserView.vue')
            },
            {
                path: 'ManagePersonView',
                name: 'ManagePersonView',
                meta: {name: '个人中心'},
                component: () => import('@/views/background/manage/ManagePersonView.vue'),
                children: [
                    {
                        path: 'ManagePersonBaseInfoView',
                        name: 'ManagePersonBaseInfoView',
                        meta: {name: '个人中心基本资料'},
                        component: () => import('@/views/background/manage/person/ManagePersonBaseInfoView.vue'),
                    },
                    {
                        path: 'ManagePersonPasswordView',
                        name: 'ManagePersonPasswordView',
                        meta: {name: '个人中心修改密码'},
                        component: () => import('@/views/background/manage/person/ManagePersonPasswordView.vue'),
                    }
                ]
            }
        ]
    },
    // 前台路由
    {
        path: '/UserLogin',
        name: 'UserLogin',
        meta: {name: '登录'},
        component: () => import('@/views/front/UserLoginView.vue')
    },
    {
        path: '/UserRegister',
        name: 'UserRegister',
        meta: {name: '注册'},
        component: () => import('@/views/front/UserRegisterView.vue')
    },
    {
        path: '/Front',
        name: 'Front',
        component: () => import('@/views/front/FrontView.vue'),
        redirect: '/Front/HomeView',
        children: [
            {
                path: 'HomeView',
                name: 'HomeView',
                meta: {name: '首页'},
                component: () => import('@/views/front/home/HomeView.vue')
            },
            {
                path: 'PersonView',
                name: 'PersonView',
                meta: {name: '个人中心'},
                component: () => import('@/views/front/home/PersonView.vue')
            },
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
