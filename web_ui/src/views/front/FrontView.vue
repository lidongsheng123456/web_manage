<template>
  <div>
    <div class="front-notice">
      <el-icon>
        <Bell/>
      </el-icon>
      <span style="margin-left: 5px">公告：{{ top }}</span>
    </div>
    <router-link target="_blank" to="/Login">后台管理</router-link>
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
.el-dropdown .el-dropdown-link:focus-visible {
  outline: none !important;
}

.front-notice {
  padding: 5px 20px;
  color: black;
  font-size: 12px;
  display: flex;
  align-items: center;
}
</style>
