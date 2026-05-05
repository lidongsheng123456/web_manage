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

app.mount('#app')

// 隐藏首屏加载动画
const loader = document.getElementById('loader-wrapper')
if (loader) {
    loader.classList.add('loaded')
    loader.addEventListener('transitionend', () => loader.remove())
}
