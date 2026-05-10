import { createI18n } from 'vue-i18n'
import zhCn from './zh-cn'
import en from './en'

const i18n = createI18n({
  legacy: false,
  locale: localStorage.getItem('locale') || 'zh-cn',
  fallbackLocale: 'zh-cn',
  messages: {
    'zh-cn': zhCn,
    'en': en
  }
})

export default i18n
