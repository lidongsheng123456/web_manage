<template>
  <div class="front-container">
    <el-alert
        title="提示"
        type="info"
        description="当前页面为前台页面需要自定义，但后台模板是完整的。"
        show-icon
        :closable="false"
        class="custom-alert"
    />
    <div class="front-notice">
      <el-icon>
        <Bell/>
      </el-icon>
      <span class="notice-text">公告：{{ top }}</span>
    </div>
    <div class="admin-link">
      <router-link target="_blank" to="/Login">后台管理</router-link>
    </div>
    <router-view/>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import store from "@/store";
import {queryNotice} from "@/api/front_request/UserHomeRequest";
import {Bell} from "@element-plus/icons-vue";

let userInfo = ref({});
let notice = ref([])
let top = ref(null)

const loadNotice = () => {
  queryNotice().then(res => {
    notice.value = res.data
    let i = 0
    if (notice.value.length) {
      top.value = notice.value[0].noticeContent
      setInterval(() => {
        top.value = notice.value[i].noticeContent
        i++
        if (i === notice.value.length) {
          i = 0
        }
      }, 2500)
    }
  })
}

const getUserInfo = () => {
  store.dispatch('user/queryCurrentFrontUserInfo').then(() => {
    userInfo.value = store.getters["user/frontUserInfo"]
  })
}

onMounted(() => {
  loadNotice()
  getUserInfo()
});
</script>

<style scoped>
.front-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.custom-alert {
  margin-bottom: 20px;
  width: 100%;
  max-width: 600px;
}

.front-notice {
  padding: 10px 20px;
  background-color: #f5f5f5;
  border-radius: 4px;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  width: 100%;
  max-width: 600px;
}

.notice-text {
  margin-left: 10px;
  font-size: 14px;
  color: #333;
}

.admin-link {
  margin-bottom: 20px;
}

.el-dropdown .el-dropdown-link:focus-visible {
  outline: none !important;
}
</style>
