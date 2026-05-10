<template>
  <div>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="70px">
      <el-form-item label="文件名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入文件名" style="width: 200px" />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click :icon="Search" type="primary" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div style="display:flex;align-items:center;gap:8px;">
      <el-upload :show-file-list="false" :before-upload="handleUpload" multiple>
        <el-button v-no-more-click :icon="Upload" plain type="primary">上传文件</el-button>
      </el-upload>
      <el-radio-group v-model="viewMode" style="margin-left:auto;">
        <el-radio-button value="table">
          <el-icon>
            <List />
          </el-icon>
        </el-radio-button>
        <el-radio-button value="grid">
          <el-icon>
            <Grid />
          </el-icon>
        </el-radio-button>
      </el-radio-group>
    </div>
    <br>

    <!-- 表格视图 -->
    <el-table v-if="viewMode === 'table'" v-loading="loading" :data="filteredData" style="width: 100%">
      <el-table-column align="center" label="文件名" prop="name" show-overflow-tooltip>
        <template #default="scope">
          <div style="display:flex;align-items:center;gap:8px;justify-content:center;">
            <el-icon :size="18" :color="getFileColor(scope.row.ext)">
              <component :is="getFileIcon(scope.row.ext)" />
            </el-icon>
            <span>{{ scope.row.name }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="大小" width="120">
        <template #default="scope">{{ formatSize(scope.row.size) }}</template>
      </el-table-column>
      <el-table-column align="center" label="类型" prop="ext" width="80" />
      <el-table-column align="center" label="修改时间" prop="lastModified" width="180" show-overflow-tooltip />
      <el-table-column align="center" fixed="right" label="操作" width="200">
        <template #default="scope">
          <el-button v-if="isImage(scope.row.ext)" v-no-more-click :icon="View" link size="small" type="primary"
            @click="openPreview(scope.row)">预览
          </el-button>
          <el-button v-no-more-click :icon="Bottom" link size="small" type="primary" @click="downloadFile(scope.row)">下载
          </el-button>
          <el-button v-no-more-click :icon="Delete" link size="small" type="primary" @click="handleDelete(scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 预览网格视图 -->
    <div v-else v-loading="loading" class="file-grid">
      <div v-for="(file, idx) in filteredData" :key="file.name" class="file-card"
        @click="isImage(file.ext) ? openPreview(file) : downloadFile(file)">
        <div class="file-thumb">
          <el-image v-if="isImage(file.ext)" :src="file.url" fit="cover" style="width:100%;height:100%;" />
          <el-icon v-else :size="40" :color="getFileColor(file.ext)">
            <component :is="getFileIcon(file.ext)" />
          </el-icon>
        </div>
        <div class="file-info">
          <div class="file-name" :title="file.name">{{ file.name }}</div>
          <div class="file-meta">{{ formatSize(file.size) }}</div>
        </div>
        <div class="file-actions">
          <el-button v-no-more-click link size="small" type="primary" @click.stop="downloadFile(file)">下载</el-button>
          <el-button v-no-more-click link size="small" type="danger" @click.stop="handleDelete(file)">删除</el-button>
        </div>
      </div>
      <el-empty v-if="!loading && filteredData.length === 0" description="暂无文件" />
    </div>

    <!-- 全局图片预览器：支持左右翻页浏览所有图片 -->
    <el-image-viewer v-if="previewVisible" :url-list="previewUrls" :initial-index="previewIndex"
      @close="previewVisible = false" />
  </div>
</template>

<script setup lang="ts">
import adminRequest from "@/utils/AdminRequest"
import {
  Bottom, Delete, Document, Grid, List, Picture, Refresh, Search, Upload, VideoCamera, View
} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { computed, onMounted, ref } from 'vue'

interface FileItem {
  name: string
  size: number
  lastModified: string
  url: string
  ext: string
}

const fileList = ref<FileItem[]>([])
const loading = ref(true)
const queryParams = ref({ name: '' })
const viewMode = ref<'table' | 'grid'>('grid')
const previewVisible = ref(false)
const previewIndex = ref(0)

const filteredData = computed(() => {
  const kw = queryParams.value.name?.trim().toLowerCase()
  if (!kw) return fileList.value
  return fileList.value.filter(f => f.name.toLowerCase().includes(kw))
})

const previewUrls = computed(() => {
  return filteredData.value.filter(f => isImage(f.ext)).map(f => f.url)
})

const openPreview = (file: FileItem) => {
  const images = filteredData.value.filter(f => isImage(f.ext))
  const idx = images.findIndex(f => f.name === file.name)
  previewIndex.value = idx >= 0 ? idx : 0
  previewVisible.value = true
}

const getList = () => {
  loading.value = true
  adminRequest({ url: '/common/files/list', method: 'GET' }).then((res: any) => {
    fileList.value = res.data || []
    loading.value = false
  }).catch(() => { loading.value = false })
}

const handleUpload = (file: File) => {
  const formData = new FormData()
  formData.append('file', file)
  adminRequest({
    url: '/common/files/upload',
    method: 'POST',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  }).then(() => {
    ElMessage.success('上传成功')
    getList()
  }).catch(() => {
    ElMessage.error('上传失败')
  })
  return false
}

const handleDelete = (file: FileItem) => {
  ElMessageBox.confirm(`是否确认删除文件"${file.name}"？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    adminRequest({ url: `/common/files/${encodeURIComponent(file.name)}`, method: 'DELETE' }).then(() => {
      ElMessage.success('删除成功')
      getList()
    })
  }).catch(() => { })
}

const downloadFile = (file: FileItem) => {
  const a = document.createElement('a')
  a.href = file.url
  a.download = file.name
  a.target = '_blank'
  a.click()
}

const isImage = (ext: string) => ['jpg', 'jpeg', 'png', 'gif', 'webp', 'bmp', 'svg'].includes(ext)

const getFileIcon = (ext: string) => {
  if (isImage(ext)) return Picture
  if (['mp4', 'avi', 'mov', 'wmv'].includes(ext)) return VideoCamera
  return Document
}

const getFileColor = (ext: string) => {
  if (isImage(ext)) return '#67c23a'
  if (['pdf'].includes(ext)) return '#f56c6c'
  if (['xlsx', 'xls'].includes(ext)) return '#409eff'
  if (['doc', 'docx'].includes(ext)) return '#e6a23c'
  return '#909399'
}

const formatSize = (bytes: number) => {
  if (bytes < 1024) return bytes + ' B'
  if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB'
  return (bytes / (1024 * 1024)).toFixed(1) + ' MB'
}

const handleQuery = () => { /* filteredData computed handles it */ }
const resetQuery = () => {
  queryParams.value = { name: '' }
}

onMounted(() => { getList() })
</script>

<style lang="scss" scoped>
@use "@/assets/css/admin/common";

.file-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 16px;
}

.file-card {
  border: 1px solid var(--el-border-color-light, #ebeef5);
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  transition: box-shadow 0.2s;

  &:hover {
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  }
}

.file-thumb {
  height: 120px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--el-fill-color-light, #f5f7fa);
}

.file-info {
  padding: 8px 12px;
}

.file-name {
  font-size: 13px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.file-meta {
  font-size: 12px;
  color: var(--el-text-color-secondary, #909399);
  margin-top: 4px;
}

.file-actions {
  padding: 4px 12px 8px;
  display: flex;
  gap: 8px;
}
</style>
