# 前端后台管理开发工作流 (Frontend Admin Workflow)

生成位置: `{业务前缀}_ui/src`

## API 请求文件

路径: `api/admin_request/{Entity}Request.js`，引用 `AdminRequest`。

```js
import adminRequest from "@/utils/AdminRequest";

export function queryArticlePage(params) {
  return adminRequest({ url: '/admin/article/list', method: 'get', params });
}
export function addArticle(data) {
  return adminRequest({ url: '/admin/article', method: 'post', data });
}
export function updateArticle(data) {
  return adminRequest({ url: '/admin/article', method: 'put', data });
}
export function deleteArticle(ids) {
  return adminRequest({ url: '/admin/article/batchDelete', method: 'delete', params: { ids: ids.toString() } });
}
```

## 管理页面视图

路径: `views/background/manage/Manage{Entity}View.vue`
模板参考: `ManageNoticeView.vue`

### 关键规范

### 1. 字典字段 (状态/类型)

禁止硬编码 `el-option`，必须使用字典查询:

```js
import { queryDictByType } from "@/api/com_request/ComRequest";
import { selectDictLabel, selectTagType } from "@/utils/env";

const articleStatusOption = ref([]);
onMounted(() => {
  queryDictByType('article_status').then(res => {
    articleStatusOption.value = res.data;
  });
});
```

```html
<!-- 表格回显 -->
<el-tag :type="selectTagType(articleStatusOption, scope.row.status)">
  {{ selectDictLabel(articleStatusOption, scope.row.status) }}
</el-tag>

<!-- 表单下拉 -->
<el-select v-model="form.status">
  <el-option v-for="item in articleStatusOption" :key="item.dictValue"
    :label="item.dictLabel" :value="item.dictValue"/>
</el-select>
```

### 2. 外键关联字段

禁止手动输入 ID，必须使用通用查询:

```js
import { queryComQueryByCode } from "@/api/com_request/ComRequest";
import { selectDictLabel } from "@/utils/env";

const userOption = ref([]);
const userIdFormatter = (row) => selectDictLabel(userOption.value, row.userId);
onMounted(() => {
  queryComQueryByCode('user_query').then(res => { userOption.value = res.data });
});
```

```html
<!-- 表格回显外键 -->
<el-table-column :formatter="userIdFormatter" label="用户" prop="userId"/>
<!-- 表单选择 (单选) -->
<el-select v-model="form.userId">
  <el-option v-for="item in userOption" :key="item.dictValue"
    :label="item.dictLabel" :value="item.dictValue"/>
</el-select>
<!-- 多选 (逗号分隔字符串) -->
<el-select v-model="form.userIds" multiple>
  <el-option v-for="item in userOption" :key="item.dictValue"
    :label="item.dictLabel" :value="item.dictValue"/>
</el-select>
```

### 3. 多选字段转换

后端 `String` (逗号分隔) ↔ 前端数组:

```js
// 提交: 数组 → 字符串
const data = { ...form.value };
if (Array.isArray(data.scenicIds)) { data.scenicIds = data.scenicIds.join(','); }

// 编辑回显: 字符串 → 数组
if (form.value.scenicIds && typeof form.value.scenicIds === 'string') {
  form.value.scenicIds = form.value.scenicIds.split(',').map(Number);
}
```

### 4. 图片上传字段 (img_url)

必须使用 `el-upload`，禁止 `el-input` 文本框:

```html
<el-form-item label="封面图" prop="imgUrl">
  <el-upload :action="uploadUrl + '/common/files/upload'"
      :before-upload="beforeAvatarUpload" :on-success="handleAvatarSuccess"
      :show-file-list="false" class="img-uploader">
    <img v-if="form.imgUrl" :src="form.imgUrl" alt="" class="upload-preview"/>
    <el-icon v-else class="upload-icon"><Plus/></el-icon>
  </el-upload>
</el-form-item>
```

```js
const uploadUrl = import.meta.env.VUE_APP_BASEURL;

const handleAvatarSuccess = (response) => {
  if (response.code !== 200) { ElMessage.error(response.msg); return; }
  form.value.imgUrl = response.data;
};

const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('图片必须是 JPG 或 PNG 格式!'); return false;
  }
  if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('图片大小不能超过 10MB!'); return false;
  }
  return true;
};
```

上传样式 (scoped):
```css
.img-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}
.img-uploader .el-upload:hover { border-color: var(--el-color-primary); }
.upload-preview { width: 178px; height: 178px; display: block; object-fit: cover; }
.upload-icon { font-size: 28px; color: #8c939d; width: 178px; height: 178px; text-align: center; }
```

### 5. 按钮规范

- 防抖: `v-no-more-click`
- 权限: `v-permission`
- 导出: `uploadUrl + '/admin/模块/export'`
