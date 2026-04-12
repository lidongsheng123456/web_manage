<template>
  <div class="front-container">
    <div class="header-bar">
      <!-- 移动端菜单按钮 -->
      <div class="mobile-menu-btn" @click="toggleMobileMenu">
        <el-icon>
          <Menu />
        </el-icon>
      </div>

      <!-- 左侧用户信息区域 -->
      <div class="top-box-left">
        <div v-if="!userInfo.id" class="login-section">
          <a class="login-link" href="/UserLogin">亲，请登录</a>
        </div>
        <el-dropdown v-else class="user-dropdown">
          <span class="el-dropdown-link">
            <el-image :src="userInfo.imgUrl || noImage" class="user-avatar" />
            <el-icon class="el-icon--right">
              <arrow-down />
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
          <Bell />
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
      <transition :duration="{ enter: 300, leave: 150 }" mode="out-in" name="slide-fade">
        <keep-alive>
          <component :is="Component" :key="$route.path" />
        </keep-alive>
      </transition>
    </router-view>
  </div>
</template>

<script setup>
import { queryNotice } from "@/api/front_request/UserHomeRequest";
import { logout } from "@/api/front_request/WebRequest";
import noImageUrl from '@/assets/img/no_image.png';
import router from "@/router";
import { useUserStore } from "@/store/modules/user";
import { ArrowDown, Bell, Menu } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox, ElNotification } from "element-plus";
import { onMounted, onUnmounted, ref } from "vue";

// 无头像图片
const noImage = noImageUrl
const userStore = useUserStore()
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
let noticeIntervalId = null

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
      noticeIntervalId = setInterval(() => {
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
  userStore.fetchCurrentFrontUserInfo().then(() => {
    userInfo.value = userStore.frontUserInfo
  })
}

// 钩子函数
onMounted(() => {
  loadNotice()
  getUserInfo()
  // 添加点击外部区域关闭菜单的事件监听
  document.addEventListener('click', handleClickOutside)
  ElNotification.info({
    title: '东神脚手架',
    message: 'AI 全流程自动化开发平台，一条指令全栈交付。',
    duration: 5000,
    offset: 100,
  })
});

// 组件卸载时移除事件监听
onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
  if (noticeIntervalId) {
    clearInterval(noticeIntervalId)
    noticeIntervalId = null
  }
})
</script>

<style lang="scss">
@use "@/assets/css/front/layout-global";
</style>

<style lang="scss" scoped>
@use "@/assets/css/front/layout";
</style>
