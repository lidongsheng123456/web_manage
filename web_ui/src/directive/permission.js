import store from "@/store";
import {watch} from "vue";

export default {
    mounted(el, binding) {
        watch(() => store.getters["user/userInfo"], (newValue, oldValue) => {
            const user = store.getters["user/userInfo"] || {}
            const filterUser = user.permissions ? user.permissions.map(item => item.permission_code) : [];
            const permission = binding.value; // 获取绑定到指令的值，即权限名称
            const hasPermission = filterUser && filterUser.includes(permission);
            // 如果用户没有权限，则删除元素
            if (!hasPermission) {
                el.parentNode.removeChild(el);
            }
        }, {deep: true});

        const user = store.getters["user/userInfo"]
        if (user.id) {
            const filterUser = user.permissions ? user.permissions.map(item => item.permission_code) : [];
            const permission = binding.value; // 获取绑定到指令的值，即权限名称
            const hasPermission = filterUser && filterUser.includes(permission);
            // 如果用户没有权限，则删除元素
            if (!hasPermission) {
                el.parentNode.removeChild(el);
            }
        }
    }
};
