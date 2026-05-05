import { useUserStore } from "@/store/modules/user"
import { watch, type Directive, type DirectiveBinding } from "vue"

const permissionDirective: Directive<HTMLElement, string> = {
    mounted(el: HTMLElement, binding: DirectiveBinding<string>) {
        const userStore = useUserStore()

        const checkPermission = (): void => {
            const user = userStore.adminUserInfo
            const codes: string[] = user?.permissions
                ? user.permissions.map(item => item.permission_code)
                : []
            const permission = binding.value
            el.style.display = codes.includes(permission) ? '' : 'none'
        }

        // 初始检查
        checkPermission()

        // 监听 userInfo 变化自动更新
        watch(() => userStore.adminUserInfo, checkPermission, { deep: true })
    }
}

export default permissionDirective
