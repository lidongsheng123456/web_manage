<template>
  <div>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="70px">
      <el-form-item label="消息标题" prop="title">
        <el-input v-model="queryParams.title" placeholder="请输入消息标题" style="width: 200px" />
      </el-form-item>
      <el-form-item label="消息状态" prop="isRead">
        <el-select v-model="queryParams.isRead" placeholder="全部" clearable style="width: 120px">
          <el-option label="未读" :value="0" />
          <el-option label="已读" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click :icon="Search" type="primary" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button v-no-more-click :icon="Check" plain type="primary" @click="handleReadAll">全部已读</el-button>
      <el-button v-no-more-click :disabled="multiple" :icon="Delete" plain type="danger" @click="handleDelete">删除
      </el-button>
    </div>
    <br>
    <el-table v-loading="loading" :data="filteredData" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55" />
      <el-table-column align="center" label="状态" width="80">
        <template #default="scope">
          <el-tag v-if="scope.row.isRead === 0" type="danger" size="small">未读</el-tag>
          <el-tag v-else type="info" size="small">已读</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="类型" prop="msgType" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.msgType === 'system'" type="primary" size="small">系统</el-tag>
          <el-tag v-else-if="scope.row.msgType === 'notice'" type="warning" size="small">通知</el-tag>
          <el-tag v-else-if="scope.row.msgType === 'warn'" type="danger" size="small">预警</el-tag>
          <el-tag v-else size="small">{{ scope.row.msgType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="标题" prop="title" show-overflow-tooltip />
      <el-table-column align="center" label="内容" prop="content" show-overflow-tooltip />
      <el-table-column align="center" label="时间" prop="createTime" width="180" show-overflow-tooltip />
      <el-table-column align="center" fixed="right" label="操作" width="200">
        <template #default="scope">
          <el-button v-if="scope.row.isRead === 0" v-no-more-click :icon="EditPen" link size="small" type="primary"
            @click="handleRead(scope.row)">标记已读
          </el-button>
          <el-button v-no-more-click :icon="Delete" link size="small" type="primary" @click="handleDelete(scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import {
  deleteMessage,
  markAllMessageRead,
  markMessageRead,
  queryMessageList,
  type MessageItem
} from '@/api/admin_request/MessageRequest'
import { Check, Delete, EditPen, Refresh, Search } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { computed, onMounted, ref } from 'vue'

const tableData = ref<MessageItem[]>([])
const loading = ref(true)
const ids = ref<number[]>([])
const multiple = ref(true)
const queryParams = ref<{ title: string | null; isRead: number | null }>({
  title: null,
  isRead: null,
})

const filteredData = computed(() => {
  let data = tableData.value
  const kw = queryParams.value.title?.trim().toLowerCase()
  if (kw) {
    data = data.filter(m => m.title?.toLowerCase().includes(kw) || m.content?.toLowerCase().includes(kw))
  }
  if (queryParams.value.isRead !== null && queryParams.value.isRead !== undefined) {
    data = data.filter(m => m.isRead === queryParams.value.isRead)
  }
  return data
})

const getList = () => {
  loading.value = true
  queryMessageList().then(res => {
    tableData.value = res.data
    loading.value = false
  }).catch(() => { loading.value = false })
}

const handleSelectionChange = (selection: MessageItem[]) => {
  ids.value = selection.map(item => item.id)
  multiple.value = !selection.length
}

const handleRead = (row: MessageItem) => {
  markMessageRead(row.id).then(() => {
    ElMessage.success('已标记为已读')
    getList()
  })
}

const handleReadAll = () => {
  markAllMessageRead().then(() => {
    ElMessage.success('全部已读')
    getList()
  })
}

const handleDelete = (row?: Partial<MessageItem>) => {
  const idsToDelete = row?.id ? [row.id] : ids.value
  ElMessageBox.confirm(`是否确认删除选中的 ${idsToDelete.length} 条消息？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteMessage(idsToDelete).then(() => {
      ElMessage.success('删除成功')
      getList()
    })
  }).catch(() => { })
}

const handleQuery = () => { /* filteredData computed handles it */ }
const resetQuery = () => {
  queryParams.value = { title: null, isRead: null }
}

onMounted(() => { getList() })
</script>

<style lang="scss" scoped>
@use "@/assets/css/admin/common";
</style>
