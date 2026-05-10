import noMoreClick from "@/directive/noMoreClick"
import permissionDirective from "@/directive/permission"
import i18n from '@/i18n'
import store from "@/store"
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/dark/css-vars.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)
// 注册自定义指令
app.directive('permission', permissionDirective)
app.directive('noMoreClick', noMoreClick)

app.use(router)
app.use(store)
app.use(i18n)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.mount('#app')

// 隐藏首屏加载动画
const loader = document.getElementById('loader-wrapper')
if (loader) {
    loader.classList.add('loaded')
    loader.addEventListener('transitionend', () => loader.remove())
}
