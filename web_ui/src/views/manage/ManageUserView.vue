<template>
  <div>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="70px">
      <el-form-item label="用户名" prop="username">
        <el-input
            v-model="queryParams.username"
            placeholder="请输用户名"
            style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
            v-model="queryParams.phone"
            placeholder="请输入手机号"
            style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input
            v-model="queryParams.email"
            placeholder="请输入邮箱"
            style="width: 200px"
        />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click :icon="Search" type="primary" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button v-no-more-click v-permission="'admin:user:add'" :icon="Plus" plain type="primary" @click="handleAdd">
        新增
      </el-button>
      <el-button v-no-more-click v-permission="'admin:user:update'" :disabled="single" :icon="EditPen" plain
                 type="success"
                 @click="handleUpdate">修改
      </el-button>
      <el-button v-no-more-click v-permission="'admin:user:delete'" :disabled="multiple" :icon="Delete" plain
                 type="danger"
                 @click="handleDelete">删除
      </el-button>
      <el-button v-no-more-click v-permission="'admin:user:export'" :icon="Bottom" plain type="warning"
                 @click="handleExport">导出
      </el-button>
    </div>
    <br>
    <el-table v-loading="loading"
              :data="tableData"
              :default-sort="{ prop: 'id', order: 'descending' }"
              :header-cell-style="{'background-color': '#f8f8f9'}"
              style="width: 100%"
              @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="序号" prop="id" sortable width="100"/>
      <el-table-column align="center" label="头像" prop="imgUrl">
        <template #default="scope">
          <el-avatar :size="50" :src="scope.row.imgUrl || noImage"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户名" prop="username" show-overflow-tooltip/>
      <el-table-column align="center" label="姓名" prop="name" show-overflow-tooltip/>
      <el-table-column align="center" label="手机号" prop="phone" show-overflow-tooltip/>
      <el-table-column align="center" label="邮箱" prop="email" show-overflow-tooltip/>
      <el-table-column align="center" label="创建时间" prop="createTime" show-overflow-tooltip/>
      <el-table-column align="center" label="更新时间" prop="updateTime" show-overflow-tooltip/>
      <el-table-column align="center" label="角色" prop="roles">
        <template #default="scope">
          <el-select
              v-model="scope.row.roles[0].role_code"
              placeholder="查看角色"
              size="small"
              style="width: 70px"
          >
            <el-option
                v-for="item in scope.row.roles"
                :key="item.role_code"
                :label="item.role_code"
                :value="item.role_code"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" label="权限" prop="permissions">
        <template #default="scope">
          <el-select
              v-model="scope.row.permissions[0].permission_code"
              placeholder="查看权限"
              size="small"
              style="width: 70px"
          >
            <el-option
                v-for="item in scope.row.permissions"
                :key="item.permission_code"
                :label="item.permission_code"
                :value="item.permission_code"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right" label="操作">
        <template #default="scope">
          <el-button v-no-more-click v-permission="'admin:user:update'" :icon="EditPen" link size="small" type="primary"
                     @click="handleUpdate(scope.row)">
            修改
          </el-button>
          <el-button v-no-more-click v-permission="'admin:user:delete'" :icon="Delete" link size="small" type="primary"
                     @click="handleDelete(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>
    <div class="demo-pagination-block">
      <el-config-provider :locale="zhCn">
        <el-pagination
            :current-page="queryParams.currentPage" :page-size="queryParams.pageSize"
            :page-sizes="[10, 20, 30, 40]"
            :total="total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange">
        </el-pagination>
      </el-config-provider>
    </div>

    <el-dialog
        v-model="dialogOverflowVisible"
        :title="title"
        draggable
        overflow
        width="500"
    >
      <el-form ref="formRef" :inline="true" :model="form" :rules="rules" align="center" label-width="80px">
        <el-form-item label="头像" style="display: flex;align-items: center;justify-content: space-around">
          <el-upload
              :action="uploadUrl + '/common/files/upload'"
              :before-upload="beforeAvatarUpload"
              :on-success="handleAvatarSuccess"
              :show-file-list="false"
              class="avatar-uploader"
              style="margin: 0 auto"
          >
            <img v-if="form.imgUrl" :src="form.imgUrl" alt="" class="avatar"/>
            <el-icon v-else class="avatar-uploader-icon">
              <Plus/>
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              style="width: 350px;height: 40px"
          />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input
              v-model="form.name"
              placeholder="请输入姓名"
              style="width: 350px;height: 40px"
          />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input
              v-model="form.phone"
              placeholder="请输入手机号"
              style="width: 350px;height: 40px"
          />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input
              v-model="form.email"
              placeholder="请输入邮箱"
              style="width: 350px;height: 40px"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button v-no-more-click @click="handleReset">取消</el-button>
          <el-button v-no-more-click type="primary" @click="submitForm">
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import {Bottom, Delete, EditPen, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {addUser, batchDeleteUser, queryUser, queryUserById, updateUser} from "@/api/request/UserRequest";

// 表格数据
const tableData = ref([])
// 复选框选中ids
const ids = ref([]);
// 数据总数
const total = ref(0);
// 对话框标题
const title = ref("");
// 查询表单元素
const queryFormRef = ref(null);
// 表单元素
const formRef = ref(null)
// 非单个禁用
const single = ref(true)
// 非多个禁用
const multiple = ref(true)
// 表格加载层
const loading = ref(true);
// 对话框显示
const dialogOverflowVisible = ref(false)
// 无头像时展示
const noImage = require('@/assets/img/no_image.png')
// 上传的ip和端口号
const uploadUrl = process.env.VUE_APP_BASEURL
// 表单
const form = ref({
  username: null,
  name: null,
  phone: null,
  email: null,
  imgUrl: null
});
// 查询参数
let queryParams = ref({
  currentPage: 1,
  pageSize: 10,
  username: null,
  phone: null,
  email: null,
});
// 自定义手机号验证函数
const validatePhone = (rule, value, callback) => {
  const phoneRegex = /^1[3-9]\d{9}$/;
  if (!value) {
    callback(new Error('手机号不能为空'));
  } else if (!phoneRegex.test(value)) {
    callback(new Error('请输入有效的手机号'));
  } else {
    callback();
  }
};
// 自定义邮箱验证函数
const validateEmail = (rule, value, callback) => {
  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
  if (!value) {
    callback(new Error('邮箱不能为空'));
  } else if (!emailRegex.test(value)) {
    callback(new Error('请输入有效的邮箱地址'));
  } else {
    callback();
  }
};
// 验证规则
const rules = {
  username: [
    {required: true, message: "用户名不能为空", trigger: "blur"}
  ],
  name: [
    {required: true, message: "姓名不能为空", trigger: "blur"}
  ],
  phone: [
    {required: true, message: '手机号不能为空', trigger: 'blur'},
    {validator: validatePhone, trigger: 'blur'}
  ],
  email: [
    {required: true, message: '邮箱不能为空', trigger: 'blur'},
    {validator: validateEmail, trigger: 'blur'}
  ]
};
// 提交表单
const submitForm = () => {
  formRef.value.validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateUser(form.value).then(res => {
          if (res.code !== 200) {
            ElMessage.error(res.msg);
            return;
          }
          ElMessage.success("修改成功");
          dialogOverflowVisible.value = false;
          getList();
        }).catch(error => {
          console.log(error)
        });
      } else {
        addUser(form.value).then(res => {
          if (res.code !== 200) {
            ElMessage.error(res.msg);
            return;
          }
          ElMessage.success("新增成功");
          dialogOverflowVisible.value = false;
          getList();
        }).catch(error => {
          console.log(error)
        });
      }
    }
  });
};
// 控制删除
const handleDelete = (row) => {
  const id = row.id || ids.value;

  const idsToDelete = Array.isArray(id) ? id : [id];
  ElMessageBox.confirm(`是否确认删除用户编号为"${idsToDelete.join(', ')}"的数据项？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    return batchDeleteUser(idsToDelete.join(', ')).catch(error => {
      console.log(error)
    });
  }).then(res => {
    if (res.code !== 200) {
      ElMessage.error(res.msg);
      return;
    }
    ElMessage.success("删除成功");
    getList();
  }).catch(error => {
    console.log(error);
  });
};
// 控制更新
const handleUpdate = (row) => {
  resetFrom();
  const id = row.id || ids.value;
  queryUserById(id).then(res => {
    form.value = res.data
    dialogOverflowVisible.value = true;
    title.value = "修改用户";
  }).catch(error => {
    console.log(error)
  });
};
// 获取数据列表
const getList = () => {
  loading.value = true;
  queryUser(queryParams.value).then(res => {
    if (res.code !== 200) {
      ElMessage.error(res.msg);
      return;
    }
    tableData.value = res.data.list;
    total.value = res.data.total;
    loading.value = false;
  }).catch(error => {
    console.log(error)
  });
};
// 控制导出
const handleExport = () => {
  window.location.href = uploadUrl + '/admin/user/export'
};
// 控制添加
const handleAdd = () => {
  resetFrom();
  dialogOverflowVisible.value = true;
  title.value = "添加用户";
};
// 控制表格复选框
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length !== 1
  multiple.value = !selection.length
};
// 控制查询
const handleQuery = () => {
  queryParams.value.currentPage = 1;
  getList();
}
// 控制当前表格大小
const handleSizeChange = (val) => {
  queryParams.value.pageSize = val
  getList()
}
// 控制当前页
const handleCurrentChange = (val) => {
  queryParams.value.currentPage = val
  getList()
}
// 控制上传成功
const handleAvatarSuccess = (response, uploadFile) => {
  if (response.code !== 200) {
    ElMessage.error(response.msg)
    return
  }
  form.value.imgUrl = response.data;
};
// 上传之前验证文件
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('头像图片必须是 JPG 或 PNG 格式!');
    return false;
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('头像图片大小不能超过 10MB!');
    return false;
  }
  return true;
};
// 控制重置表单
const handleReset = () => {
  resetFrom()
  dialogOverflowVisible.value = false;
}
// 重置表单
const resetFrom = () => {
  form.value = {
    username: null,
    name: null,
    phone: null,
    email: null,
    imgUrl: null
  }
  // 重置表单验证状态
  if (formRef.value) {
    formRef.value.resetFields();
  }
};
// 重置查询表单
const resetQuery = () => {
  queryParams.value = {
    currentPage: 1,
    pageSize: 10,
    username: null,
    phone: null,
    email: null,
  }
  handleQuery();
};
// 页面挂载时获取数据
onMounted(() => {
  getList();
});
</script>
<style scoped>
.demo-pagination-block {
  float: right;
}

.demo-pagination-block + .demo-pagination-block {
  margin-top: 10px;
}

.demo-pagination-block .demonstration {
  margin-bottom: 16px;
}

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
  border-radius: 50%;
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
