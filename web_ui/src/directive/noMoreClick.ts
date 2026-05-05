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
