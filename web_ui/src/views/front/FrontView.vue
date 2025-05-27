<template>
  <div class="front-container">
    <div style="display: flex;padding: 10px 0;justify-content: space-around;align-items: center;font-size: 14px;background-color: #eaeaea;width: 100%">
      <div class="top-box-left">
        <div v-if="!userInfo.id">
          <a style="margin-left: 5px;color: #ff5000" href="/UserLogin">亲，请登录</a>
        </div>
        <el-dropdown v-else>
          <span class="el-dropdown-link">
            <el-image :src="userInfo.imgUrl || noImage" style="width: 50px; height: 50px;border-radius: 50%"/>
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
        <div>
          <a style="margin-left: 5px" href="/UserRegister">免费注册</a>
        </div>
      </div>
      <div class="front-notice">
        <el-icon>
          <Bell/>
        </el-icon>
        <el-tooltip class="item" effect="dark" :content="top" placement="top">
          <span class="notice-text">{{ noticeTitle }}：{{ top }}</span>
        </el-tooltip>
      </div>
      <div class="top-box-right">
        <div>
          <router-link target="_blank" to="/Login">后台管理</router-link>
        </div>
      </div>
    </div>
    <br>
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
import {onMounted, ref} from "vue";
import store from "@/store";
import {queryNotice} from "@/api/front_request/UserHomeRequest";
import {ArrowDown, Bell} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";
import router from "@/router";
import {logout} from "@/api/front_request/WebRequest";

// 无头像图片
const noImage = require('@/assets/img/no_image.png')
// 登录用户信息
let userInfo = ref({});
// 通知列表
let notice = ref([])
// 通知标题
let noticeTitle = ref(null)
//通知内容
let top = ref(null)
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
  })
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
  ElNotification.info({
    title: '提示',
    message: '当前页面为前台页面需要自定义，但后台模板是完整的。',
    duration: 0,
    offset: 100,
  })
});
</script>
<style>
.safe-area {
  width: 1200px;
  margin: 0 auto
}
</style>
<style scoped>
.front-notice {
  display: flex;
  align-items: center;
  width: 100%;
  max-width: 400px;
}

.notice-text {
  margin-left: 10px;
  font-size: 14px;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.el-dropdown .el-dropdown-link:focus-visible {
  outline: none !important;
}

.top-box-left {
  display: flex;
  align-items: center
}

.top-box-left div {
  transition: all .5s;
  cursor: pointer;
}

.top-box-left div:hover {
  color: #ff5000;
}

.top-box-right {
  display: flex;
  align-items: center
}

.top-box-right div {
  margin: 0 5px;
  transition: all .5s;
  cursor: pointer;
}

.top-box-right div:hover {
  color: #ff5000;
}

a {
  color: black;
  text-decoration: none;
}
</style>
