import { defineStore } from 'pinia'
import { ref, watch } from 'vue'

const STORAGE_KEY = 'admin-settings'

function loadSettings() {
    try {
        const raw = localStorage.getItem(STORAGE_KEY)
        return raw ? JSON.parse(raw) : {}
    } catch {
        return {}
    }
}

export const useSettingsStore = defineStore('settings', () => {
    const saved = loadSettings()

    // ==================== 个性化设置 ====================
    const themeColor = ref(saved.themeColor || '#409eff')
    const sidebarCollapse = ref(saved.sidebarCollapse ?? false)
    const showTagsView = ref(saved.showTagsView ?? true)
    const showTransition = ref(saved.showTransition ?? true)
    const grayMode = ref(saved.grayMode ?? false)
    const colorWeakMode = ref(saved.colorWeakMode ?? false)

    // ==================== 网站信息 ====================
    const siteName = ref(saved.siteName || '毕设脚手架')
    const siteDescription = ref(saved.siteDescription || '基于 Spring Boot 3 + Vue 3 的全栈管理系统')
    const siteFooter = ref(saved.siteFooter || '')
    const siteIcp = ref(saved.siteIcp || '')

    // ==================== 持久化 ====================
    function persist() {
        localStorage.setItem(STORAGE_KEY, JSON.stringify({
            themeColor: themeColor.value,
            sidebarCollapse: sidebarCollapse.value,
            showTagsView: showTagsView.value,
            showTransition: showTransition.value,
            grayMode: grayMode.value,
            colorWeakMode: colorWeakMode.value,
            siteName: siteName.value,
            siteDescription: siteDescription.value,
            siteFooter: siteFooter.value,
            siteIcp: siteIcp.value,
        }))
    }

    // 监听所有设置变化自动持久化
    watch([themeColor, sidebarCollapse, showTagsView, showTransition,
        grayMode, colorWeakMode, siteName, siteDescription, siteFooter, siteIcp],
        () => persist(), { deep: true }
    )

    // ==================== 应用主题到 DOM ====================
    function hexToRgb(hex) {
        hex = hex.replace('#', '')
        return {
            r: parseInt(hex.substring(0, 2), 16),
            g: parseInt(hex.substring(2, 4), 16),
            b: parseInt(hex.substring(4, 6), 16),
        }
    }

    function mixColor(color1, color2, weight) {
        const c1 = hexToRgb(color1)
        const c2 = hexToRgb(color2)
        const w = weight / 100
        const r = Math.round(c1.r * (1 - w) + c2.r * w)
        const g = Math.round(c1.g * (1 - w) + c2.g * w)
        const b = Math.round(c1.b * (1 - w) + c2.b * w)
        return `rgb(${r}, ${g}, ${b})`
    }

    function applyTheme() {
        const el = document.documentElement
        const primary = themeColor.value

        // 主题色
        el.style.setProperty('--el-color-primary', primary)

        // Element Plus 需要的 light 变量 (与白色混合)
        for (const level of [3, 5, 7, 8, 9]) {
            el.style.setProperty(`--el-color-primary-light-${level}`, mixColor(primary, '#ffffff', level * 10))
        }
        // dark 变量 (与黑色混合)
        el.style.setProperty('--el-color-primary-dark-2', mixColor(primary, '#000000', 20))

        // Element Plus 组件级别 CSS 变量
        el.style.setProperty('--el-menu-active-color', primary)
        el.style.setProperty('--el-menu-hover-text-color', primary)
        el.style.setProperty('--el-switch-on-color', primary)
        el.style.setProperty('--el-tabs-header-text-color-active', primary)

        // 灰色模式
        el.style.filter = grayMode.value ? 'grayscale(100%)' : ''

        // 色弱模式
        el.classList.toggle('color-weak', colorWeakMode.value)
    }

    function resetPersonalization() {
        themeColor.value = '#409eff'
        sidebarCollapse.value = false
        showTagsView.value = true
        showTransition.value = true
        grayMode.value = false
        colorWeakMode.value = false
    }

    function resetSiteInfo() {
        siteName.value = '毕设脚手架'
        siteDescription.value = '基于 Spring Boot 3 + Vue 3 的全栈管理系统'
        siteFooter.value = ''
        siteIcp.value = ''
    }

    return {
        themeColor, sidebarCollapse, showTagsView, showTransition,
        grayMode, colorWeakMode,
        siteName, siteDescription, siteFooter, siteIcp,
        applyTheme, resetPersonalization, resetSiteInfo, persist,
    }
})
