import {createApp} from 'vue';
import App from './App.vue';
import router from './router';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import store from "@/store";
import permissionDirective from "@/directive/permission";
import noMoreClick from "@/directive/noMoreClick";

const app = createApp(App);
// 注册自定义指令
app.directive('permission', permissionDirective);
app.directive('noMoreClick', noMoreClick)

app.use(router);
app.use(store);
app.use(ElementPlus);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}

app.mount('#app');
