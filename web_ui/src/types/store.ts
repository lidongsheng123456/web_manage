import type { AdminUser } from './admin'
import type { FrontUser } from './front'

/** User Store 状态 */
export interface UserState {
  adminUserInfo: AdminUser | null
  frontUserInfo: FrontUser | null
}

/** Settings Store 状态 */
export interface SettingsState {
  themeColor: string
  sidebarCollapse: boolean
  showTagsView: boolean
  showTransition: boolean
  grayMode: boolean
  colorWeakMode: boolean
  siteName: string
  siteDescription: string
  siteFooter: string
  siteIcp: string
}
