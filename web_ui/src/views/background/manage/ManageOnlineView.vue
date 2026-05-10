<template>
  <div>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="70px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="queryParams.username" placeholder="请输入用户名" style="width: 200px" />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click :icon="Search" type="primary" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button v-no-more-click :icon="Refresh" plain @click="getList">刷新列表</el-button>
    </div>
    <br>
    <el-table v-loading="loading" :data="filteredData" style="width: 100%">
      <el-table-column align="center" label="用户ID" prop="loginId" width="100" />
      <el-table-column align="center" label="用户名" prop="username" width="120" />
      <el-table-column align="center" label="昵称" prop="name" width="120" />
      <el-table-column align="center" label="登录时间" prop="loginTime" width="180" show-overflow-tooltip />
      <el-table-column align="center" label="Token" prop="tokenValue" show-overflow-tooltip />
      <el-table-column align="center" fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button v-no-more-click :icon="Delete" link size="small" type="primary" @click="handleKick(scope.row)">强踢
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import type { OnlineUser } from "@/api/admin_request/OnlineRequest"
import { kickUser, queryOnlineUsers } from "@/api/admin_request/OnlineRequest"
import { Delete, Refresh, Search } from "@element-plus/icons-vue"
import { ElMessage, ElMessageBox } from "element-plus"
import { computed, onMounted, ref } from "vue"

const tableData = ref<OnlineUser[]>([])
const loading = ref(true)
const queryParams = ref({ username: '' })

const filteredData = computed(() => {
  const kw = queryParams.value.username?.trim().toLowerCase()
  if (!kw) return tableData.value
  return tableData.value.filter(u => u.username?.toLowerCase().includes(kw) || u.name?.toLowerCase().includes(kw))
})

const getList = () => {
  loading.value = true
  queryOnlineUsers().then(res => {
    tableData.value = res.data
    loading.value = false
  }).catch(() => { loading.value = false })
}

const handleKick = (row: OnlineUser) => {
  ElMessageBox.confirm(`是否确认将用户"${row.username}"强制踢下线？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    kickUser(row.loginId).then(() => {
      ElMessage.success('已踢下线')
      getList()
    })
  }).catch(() => { })
}

const handleQuery = () => { /* filteredData computed handles it */ }
const resetQuery = () => {
  queryParams.value = { username: '' }
}

onMounted(() => { getList() })
</script>

<style lang="scss" scoped>
@use "@/assets/css/admin/common";
</style>
