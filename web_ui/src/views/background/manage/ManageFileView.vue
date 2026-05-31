<template>
  <div class="file-manager-page">
    <div class="file-toolbar">
      <div class="toolbar-left">
        <el-input v-model="searchKey" placeholder="搜索文件名..." clearable prefix-icon="Search"
          style="width: 260px" @input="onSearch" />
        <el-select v-model="filterType" placeholder="文件类型" clearable style="width: 140px" @change="onSearch">
          <el-option label="图片" value="image" />
          <el-option label="文档" value="doc" />
          <el-option label="其他" value="other" />
        </el-select>
      </div>
      <div class="toolbar-right">
        <el-upload :action="uploadUrl + '/common/files/upload'" :before-upload="beforeUpload"
          :on-success="handleUploadSuccess" :show-file-list="false" multiple>
          <el-button type="primary" v-no-more-click>
            <el-icon><Upload /></el-icon>上传文件
          </el-button>
        </el-upload>
        <el-button-group>
          <el-tooltip content="卡片预览" placement="top">
            <el-button :type="viewMode === 'card' ? 'primary' : ''" @click="viewMode = 'card'">
              <el-icon><Grid /></el-icon>
            </el-button>
          </el-tooltip>
          <el-tooltip content="详细信息" placement="top">
            <el-button :type="viewMode === 'table' ? 'primary' : ''" @click="viewMode = 'table'">
              <el-icon><List /></el-icon>
            </el-button>
          </el-tooltip>
        </el-button-group>
      </div>
    </div>

    <div class="file-stats">
      <span>共 <strong>{{ filteredFiles.length }}</strong> 个文件</span>
      <span>总大小: <strong>{{ formatSize(totalSize) }}</strong></span>
    </div>

    <!-- 卡片预览模式 -->
    <div v-if="viewMode === 'card'" v-loading="loading" class="file-grid">
      <div v-for="file in filteredFiles" :key="file.name" class="file-card" @click="previewFile(file)">
        <div class="file-preview">
          <img v-if="file.isImage" :src="file.url" :alt="file.name" loading="lazy" />
          <div v-else class="file-icon-placeholder">
            <span class="file-ext-badge">{{ file.ext?.toUpperCase() || '?' }}</span>
          </div>
        </div>
        <div class="file-info">
          <el-tooltip :content="file.name" placement="top" :show-after="500">
            <span class="file-name">{{ file.name }}</span>
          </el-tooltip>
          <span class="file-meta">{{ formatSize(file.size) }}</span>
        </div>
        <div class="file-actions" @click.stop>
          <el-tooltip content="下载" placement="top">
            <el-button size="small" circle @click="downloadFile(file)">
              <el-icon><Download /></el-icon>
            </el-button>
          </el-tooltip>
          <el-popconfirm title="确定删除此文件?" @confirm="deleteFile(file)">
            <template #reference>
              <el-button size="small" circle type="danger">
                <el-icon><Delete /></el-icon>
              </el-button>
            </template>
          </el-popconfirm>
        </div>
      </div>
      <el-empty v-if="!loading && filteredFiles.length === 0" description="暂无文件" />
    </div>

    <!-- 详细信息列表模式 -->
    <el-table v-if="viewMode === 'table'" v-loading="loading" :data="filteredFiles" stripe
      style="width: 100%" @row-click="previewFile">
      <el-table-column label="预览" width="80" align="center">
        <template #default="{ row }">
          <img v-if="row.isImage" :src="row.url" class="table-thumb" />
          <span v-else class="table-ext-badge">{{ row.ext?.toUpperCase() || '?' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件名" prop="name" min-width="240" show-overflow-tooltip />
      <el-table-column label="类型" prop="ext" width="100" align="center">
        <template #default="{ row }">
          <el-tag v-if="row.isImage" type="success" size="small">图片</el-tag>
          <el-tag v-else-if="docExts.includes(row.ext)" type="warning" size="small">文档</el-tag>
          <el-tag v-else type="info" size="small">{{ row.ext || '未知' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="大小" width="120" align="center">
        <template #default="{ row }">{{ formatSize(row.size) }}</template>
      </el-table-column>
      <el-table-column label="修改时间" prop="lastModified" width="180" align="center">
        <template #default="{ row }">{{ formatTime(row.lastModified) }}</template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center" fixed="right">
        <template #default="{ row }">
          <el-button size="small" type="primary" link @click.stop="previewFile(row)">预览</el-button>
          <el-button size="small" type="" link @click.stop="downloadFile(row)">下载</el-button>
          <el-popconfirm title="确定删除此文件?" @confirm="deleteFile(row)">
            <template #reference>
              <el-button size="small" type="danger" link @click.stop>删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 预览弹窗 -->
    <el-dialog v-model="previewVisible" :title="previewingFile?.name" width="700px" top="5vh" destroy-on-close>
      <div class="preview-body">
        <img v-if="previewingFile?.isImage" :src="previewingFile?.url" class="preview-image" />
        <div v-else class="preview-non-image">
          <div class="preview-icon-big">
            <span class="file-ext-badge large">{{ previewingFile?.ext?.toUpperCase() || '?' }}</span>
          </div>
          <p>此文件类型不支持在线预览</p>
          <el-button type="primary" @click="downloadFile(previewingFile!)">下载文件</el-button>
        </div>
      </div>
      <template #footer>
        <div class="preview-footer-info">
          <span>大小: {{ formatSize(previewingFile?.size || 0) }}</span>
          <span>修改时间: {{ formatTime(previewingFile?.lastModified || '') }}</span>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import adminRequest from "@/utils/AdminRequest";
import { Delete, Download, Grid, List, Upload } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import { computed, onMounted, ref } from "vue";

const uploadUrl = import.meta.env.VUE_APP_BASEURL;
const docExts = ['pdf', 'doc', 'docx', 'xls', 'xlsx', 'txt'];

interface FileItem {
  name: string
  size: number
  url: string
  lastModified: string
  ext: string
  isImage: boolean
}

const loading = ref(false);
const files = ref<FileItem[]>([]);
const searchKey = ref('');
const filterType = ref('');
const viewMode = ref<'card' | 'table'>('card');
const previewVisible = ref(false);
const previewingFile = ref<FileItem | null>(null);

const filteredFiles = computed(() => {
  let list = files.value;
  if (searchKey.value) {
    const key = searchKey.value.toLowerCase();
    list = list.filter(f => f.name.toLowerCase().includes(key));
  }
  if (filterType.value === 'image') {
    list = list.filter(f => f.isImage);
  } else if (filterType.value === 'doc') {
    list = list.filter(f => docExts.includes(f.ext));
  } else if (filterType.value === 'other') {
    list = list.filter(f => !f.isImage && !docExts.includes(f.ext));
  }
  return list;
});

const totalSize = computed(() => filteredFiles.value.reduce((s, f) => s + f.size, 0));

const fetchFiles = () => {
  loading.value = true;
  adminRequest.get('/common/files/list').then(res => {
    files.value = res.data || [];
  }).finally(() => {
    loading.value = false;
  });
};

const formatSize = (bytes: number): string => {
  if (bytes === 0) return '0 B';
  const units = ['B', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(1024));
  return (bytes / Math.pow(1024, i)).toFixed(1) + ' ' + units[i];
};

const formatTime = (iso: string): string => {
  if (!iso) return '-';
  return iso.replace('T', ' ').substring(0, 19);
};

const onSearch = () => { /* computed 自动过滤 */ };

const previewFile = (file: FileItem) => {
  previewingFile.value = file;
  previewVisible.value = true;
};

const downloadFile = (file: FileItem) => {
  const a = document.createElement('a');
  a.href = file.url;
  a.download = file.name;
  a.target = '_blank';
  a.click();
};

const deleteFile = (file: FileItem) => {
  adminRequest.delete('/common/files/' + file.name).then(() => {
    ElMessage.success('删除成功');
    fetchFiles();
  });
};

const beforeUpload = (rawFile: File) => {
  if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('文件大小不能超过 10MB!');
    return false;
  }
  return true;
};

const handleUploadSuccess = (response: any) => {
  if (response.code !== 200) {
    ElMessage.error(response.msg);
    return;
  }
  ElMessage.success('上传成功');
  fetchFiles();
};

onMounted(() => fetchFiles());
</script>

<style lang="scss" scoped>
@use "@/assets/css/admin/file-manager";
</style>
