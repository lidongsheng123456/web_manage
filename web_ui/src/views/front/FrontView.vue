<template>
  <div class="front-container">
    <div class="header-bar">
      <!-- 移动端菜单按钮 -->
      <div class="mobile-menu-btn" @click="toggleMobileMenu">
        <el-icon><Menu /></el-icon>
      </div>

      <!-- 左侧用户信息区域 -->
      <div class="top-box-left">
        <div v-if="!userInfo.id" class="login-section">
          <a class="login-link" href="/UserLogin">亲，请登录</a>
        </div>
        <el-dropdown v-else class="user-dropdown">
          <span class="el-dropdown-link">
            <el-image :src="userInfo.imgUrl || noImage" class="user-avatar"/>
            <el-icon class="el-icon--right">
              <arrow-down/>
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>
                <router-link to="/Front/PersonView">个人中心</router-link>
              </el-dropdown-item>
              <el-dropdown-item>
                <router-link to="#" @click="logoutLogin">退出登录</router-link>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <div class="register-section">
          <a class="register-link" href="/UserRegister">免费注册</a>
        </div>
      </div>

      <!-- 中间通知区域 -->
      <div class="front-notice">
        <el-icon class="notice-icon">
          <Bell/>
        </el-icon>
        <el-tooltip class="item" effect="dark" :content="top" placement="top">
          <span class="notice-text">{{ noticeTitle }}：{{ top }}</span>
        </el-tooltip>
      </div>

      <!-- 右侧管理链接 -->
      <div class="top-box-right">
        <div class="admin-link">
          <router-link target="_blank" to="/Login">后台管理</router-link>
        </div>
      </div>
    </div>

    <!-- 移动端下拉菜单 -->
    <div class="mobile-menu" :class="{ 'mobile-menu-open': mobileMenuOpen }" v-show="mobileMenuOpen">
      <div class="mobile-menu-item" v-if="!userInfo.id">
        <a href="/UserLogin" @click="closeMobileMenu">登录</a>
      </div>
      <div class="mobile-menu-item" v-if="!userInfo.id">
        <a href="/UserRegister" @click="closeMobileMenu">注册</a>
      </div>
      <div class="mobile-menu-item" v-if="userInfo.id">
        <router-link to="/Front/PersonView" @click="closeMobileMenu">个人中心</router-link>
      </div>
      <div class="mobile-menu-item" v-if="userInfo.id">
        <a @click="handleLogoutFromMobile">退出登录</a>
      </div>
      <div class="mobile-menu-item">
        <router-link target="_blank" to="/Login" @click="closeMobileMenu">后台管理</router-link>
      </div>
    </div>

    <!-- 遮罩层 -->
    <div class="mobile-menu-overlay" v-show="mobileMenuOpen" @click="closeMobileMenu"></div>

    <router-view v-slot="{ Component }">
      <keep-alive>
        <transition :duration="{ enter: 500, leave: 200 }" mode="out-in" name="slide-fade">
          <component :is="Component"/>
        </transition>
      </keep-alive>
    </router-view>
  </div>
</template>

<script setup>
import {onMounted, ref, onUnmounted} from "vue";
import store from "@/store";
import {queryNotice} from "@/api/front_request/UserHomeRequest";
import {ArrowDown, Bell, Menu} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import router from "@/router";
import {logout} from "@/api/front_request/WebRequest";
import noImageUrl from '@/assets/img/no_image.png'

// 无头像图片
const noImage = noImageUrl
// 登录用户信息
let userInfo = ref({});
// 通知列表
let notice = ref([])
// 通知标题
let noticeTitle = ref(null)
//通知内容
let top = ref(null)
// 移动端菜单状态
let mobileMenuOpen = ref(false)

// 切换移动端菜单
const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value
}

// 关闭移动端菜单
const closeMobileMenu = () => {
  mobileMenuOpen.value = false
}

// 处理移动端退出登录
const handleLogoutFromMobile = () => {
  closeMobileMenu()
  logoutLogin()
}

// 点击外部区域关闭菜单
const handleClickOutside = (event) => {
  const mobileMenuBtn = document.querySelector('.mobile-menu-btn')
  const mobileMenu = document.querySelector('.mobile-menu')

  if (mobileMenuOpen.value &&
      !mobileMenuBtn?.contains(event.target) &&
      !mobileMenu?.contains(event.target)) {
    closeMobileMenu()
  }
}

// 加载通知
const loadNotice = () => {
  queryNotice().then(res => {
    notice.value = res.data
    let i = 0
    if (notice.value.length) {
      top.value = notice.value[0].noticeContent
      noticeTitle.value = notice.value[0].noticeTitle
      setInterval(() => {
        top.value = notice.value[i].noticeContent
        noticeTitle.value = notice.value[i].noticeTitle
        i++
        if (i === notice.value.length) {
          i = 0
        }
      }, 2500)
    }
  }).catch(error => {
    console.log(error)
  });
}

// 退出登录
const logoutLogin = () => {
  ElMessageBox.confirm('确认退出?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    logout().then(res => {
      ElMessage.success('退出成功');
      router.push('/Front')
      setTimeout(() => {
        window.location.reload(true);
      }, 500)
    }).catch(error => {
      console.log(error)
    });
  }).catch(error => {
  });
};

// 获取当前登录用户信息
const getUserInfo = () => {
  store.dispatch('user/queryCurrentFrontUserInfo').then(() => {
    userInfo.value = store.getters["user/frontUserInfo"]
  })
}

// 钩子函数
onMounted(() => {
  loadNotice()
  getUserInfo()
  // 添加点击外部区域关闭菜单的事件监听
  document.addEventListener('click', handleClickOutside)
  ElNotification.info({
    title: '提示',
    message: '当前页面为前台页面需要自定义，但后台模板是完整的。',
    duration: 0,
    offset: 100,
  })
});

// 组件卸载时移除事件监听
onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style>
.safe-area {
  width: 1200px;
  margin: 0 auto;
}

/* 响应式安全区域 */
@media (max-width: 1200px) {
  .safe-area {
    width: 100%;
    padding: 0 20px;
  }
}

@media (max-width: 768px) {
  .safe-area {
    padding: 0 15px;
  }
}
</style>

<style scoped>
/* 头部容器 */
.header-bar {
  display: flex;
  padding: 10px 20px;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  background-color: #b7e0f0;
  width: 100%;
  box-sizing: border-box;
  position: relative;
}

/* 移动端菜单按钮 */
.mobile-menu-btn {
  display: none;
  cursor: pointer;
  padding: 8px;
  border-radius: 4px;
  transition: background-color 0.3s;
  z-index: 1001;
}

.mobile-menu-btn:hover {
  background-color: rgba(0, 0, 0, 0.1);
}

/* 左侧用户区域 */
.top-box-left {
  display: flex;
  align-items: center;
  gap: 10px;
}

.login-section, .register-section {
  transition: all 0.5s;
  cursor: pointer;
}

.login-section:hover, .register-section:hover {
  color: #ff5000;
}

.login-link, .register-link {
  color: #ff5000;
  text-decoration: none;
  font-weight: 500;
}

.user-dropdown .el-dropdown-link {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 5px;
}

/* 通知区域 */
.front-notice {
  display: flex;
  align-items: center;
  flex: 1;
  max-width: 400px;
  margin: 0 20px;
  min-width: 0;
}

.notice-icon {
  flex-shrink: 0;
  color: #ff5000;
}

.notice-text {
  margin-left: 10px;
  font-size: 14px;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex: 1;
}

/* 右侧管理区域 */
.top-box-right {
  display: flex;
  align-items: center;
}

.admin-link {
  transition: all 0.5s;
  cursor: pointer;
}

.admin-link:hover {
  color: #ff5000;
}

/* 移动端菜单 */
.mobile-menu {
  display: none;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  position: fixed;
  top: 70px;
  right: 15px;
  width: 200px;
  z-index: 1000;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  transform: translateY(-10px);
  opacity: 0;
  transition: all 0.3s ease;
  pointer-events: none;
}

.mobile-menu-open {
  transform: translateY(0);
  opacity: 1;
  pointer-events: auto;
}

.mobile-menu-item {
  padding: 12px 16px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.mobile-menu-item:hover {
  background-color: #f5f5f5;
}

.mobile-menu-item:last-child {
  border-bottom: none;
  border-radius: 0 0 8px 8px;
}

.mobile-menu-item:first-child {
  border-radius: 8px 8px 0 0;
}

.mobile-menu-item a {
  color: #333;
  text-decoration: none;
  display: block;
  width: 100%;
  font-size: 14px;
}

/* 遮罩层 */
.mobile-menu-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.3);
  z-index: 999;
  display: none;
}

/* 通用链接样式 */
a {
  color: black;
  text-decoration: none;
}

.el-dropdown .el-dropdown-link:focus-visible {
  outline: none !important;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .header-bar {
    padding: 10px 15px;
  }

  .mobile-menu-btn {
    display: block;
  }

  .top-box-left {
    display: none;
  }

  .front-notice {
    margin: 0 10px;
    max-width: none;
    flex: 1;
  }

  .notice-text {
    font-size: 12px;
  }

  .top-box-right {
    display: none;
  }

  .mobile-menu {
    display: block;
  }

  .mobile-menu-overlay {
    display: block;
  }
}

@media (max-width: 480px) {
  .header-bar {
    padding: 8px 10px;
  }

  .front-notice {
    margin: 0 5px;
  }

  .notice-text {
    font-size: 11px;
  }

  .user-avatar {
    width: 35px;
    height: 35px;
  }

  .mobile-menu {
    right: 10px;
    width: 180px;
  }
}

/* 平板设备适配 */
@media (min-width: 769px) and (max-width: 1024px) {
  .header-bar {
    padding: 10px 15px;
  }

  .front-notice {
    max-width: 300px;
  }

  .notice-text {
    font-size: 13px;
  }
}

/* 大屏幕优化 */
@media (min-width: 1400px) {
  .header-bar {
    padding: 12px 30px;
  }

  .front-notice {
    max-width: 500px;
  }
}
</style>
