import { useUserStore } from "@/store/modules/user";
import { watch } from "vue";

export default {
    mounted(el, binding) {
        const userStore = useUserStore();

        const checkPermission = () => {
            const user = userStore.userInfo || {};
            const codes = user.permissions ? user.permissions.map(item => item.permission_code) : [];
            const permission = binding.value;
            el.style.display = codes.includes(permission) ? '' : 'none';
        };

        // 初始检查
        checkPermission();

        // 监听 userInfo 变化自动更新
        watch(() => userStore.userInfo, checkPermission, { deep: true });
    }
};
