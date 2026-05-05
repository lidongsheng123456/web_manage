/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VUE_APP_BASEURL: string
  readonly VUE_APP_API_TARGET: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}

declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<Record<string, unknown>, Record<string, unknown>, unknown>
  export default component
}

declare module 'element-plus/dist/locale/zh-cn.mjs' {
  const zhCn: Record<string, unknown>
  export default zhCn
}

declare module 'vue-count-to' {
  import type { DefineComponent } from 'vue'
  const CountTo: DefineComponent<Record<string, unknown>, Record<string, unknown>, unknown>
  export default CountTo
}

declare module 'vanta/dist/vanta.net.min' {
  const VANTA_NET: unknown
  export default VANTA_NET
}
