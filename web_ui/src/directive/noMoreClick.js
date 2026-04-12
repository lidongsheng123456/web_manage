export default {
    mounted(el, binding) {
        const delay = binding.value || 2000;
        el.addEventListener('click', () => {
            el.classList.add('is-disabled')
            el.disabled = true
            setTimeout(() => {
                el.disabled = false
                el.classList.remove('is-disabled')
            }, delay)
        })
    }
};
