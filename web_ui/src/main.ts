import noMoreClick from "@/directive/noMoreClick"
import permissionDirective from "@/directive/permission"
import store from "@/store"
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)
// 注册自定义指令
app.directive('permission', permissionDirective)
app.directive('noMoreClick', noMoreClick)

app.use(router)
app.use(store)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// 路由加载进度条
const progressBar = document.createElement('div')
progressBar.id = 'route-progress'
document.body.appendChild(progressBar)

let progressTimer: ReturnType<typeof setInterval> | null = null

router.beforeEach((_to, _from, next) => {
    progressBar.style.width = '0%'
    progressBar.classList.add('active')
    let width = 0
    progressTimer = setInterval(() => {
        width += (90 - width) * 0.1
        progressBar.style.width = `${width}%`
    }, 100)
    next()
})

router.afterEach(() => {
    if (progressTimer) {
        clearInterval(progressTimer)
        progressTimer = null
    }
    progressBar.style.width = '100%'
    setTimeout(() => {
        progressBar.classList.remove('active')
        progressBar.style.width = '0%'
    }, 300)
})

app.mount('#app')

// 隐藏首屏加载动画
const loader = document.getElementById('loader-wrapper')
if (loader) {
    loader.classList.add('loaded')
    loader.addEventListener('transitionend', () => loader.remove())
}

// 全局预加载所有懒加载路由组件，消除首次访问时 chunk 下载延迟导致的闪烁
setTimeout(() => {
    router.getRoutes().forEach(route => {
        const comp = route.components?.default
        if (typeof comp === 'function') {
            try { (comp as any)() } catch (_) { /* ignore */ }
        }
    })
}, 1200)
