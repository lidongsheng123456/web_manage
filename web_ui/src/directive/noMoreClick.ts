/**
 * 防重复点击指令 v-no-more-click
 * 点击后在指定时间内禁用按钮，防止表单重复提交
 * 用法: v-no-more-click 或 v-no-more-click="3000" (自定义延迟毫秒)
 */
import type { Directive, DirectiveBinding } from 'vue'

const noMoreClick: Directive<HTMLElement, number> = {
    mounted(el: HTMLElement, binding: DirectiveBinding<number>) {
        const delay = binding.value || 2000
        el.addEventListener('click', () => {
            el.classList.add('is-disabled')
            el.setAttribute('disabled', 'disabled')
            setTimeout(() => {
                el.removeAttribute('disabled')
                el.classList.remove('is-disabled')
            }, delay)
        })
    }
}

export default noMoreClick
