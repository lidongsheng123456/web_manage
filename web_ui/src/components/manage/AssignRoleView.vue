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
        <el-button :icon="Search" type="primary" @click="handleQuery">搜索</el-button>
        <el-button :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
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
    </el-table>
    <br>
    <div style="display: flex;justify-content: space-between">
      <div class="demo-pagination-block">
        <el-pagination
            :current-page="queryParams.currentPage" :page-size="queryParams.pageSize"
            :page-sizes="[10, 20, 30, 40]"
            :total="total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange">
        </el-pagination>
      </div>
      <div class="dialog-footer">
        <el-button @click="handleAssignReset">取消</el-button>
        <el-button type="primary" @click="handleSubmitAssignForm">
          确认
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {queryUserByRoleId, queryUserNotRoleId} from "@/api/admin_request/RoleRequest";
import noImage from "@/assets/img/no_image.png";
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import {Refresh, Search} from "@element-plus/icons-vue";
// 定义事件
const emit = defineEmits(['handleAssignReset', 'submitAssignForm'])
// 表格数据
const tableData = ref([])
// 复选框选中ids
const ids = ref([]);
// 数据总数
const total = ref(0);
// 表格加载层
const loading = ref(true);
// 决定是分配还是移除
const isAssign = ref(null)
// 移除当前角色的id
const id = ref(null)
// 查询参数
let queryParams = ref({
  currentPage: 1,
  pageSize: 10,
  username: null,
  phone: null,
  email: null,
});
// 提交分配角色或者移除角色
const handleSubmitAssignForm = () => {
  emit('submitAssignForm', ids.value, isAssign.value);
}
// 获取列表数据
const getList = (params1, params2) => {
  if (params1) {
    isAssign.value = params1
    id.value = params2
  }

  if (isAssign.value === 'assign') {
    loading.value = true;
    queryUserNotRoleId({...queryParams.value, id: id.value}).then(res => {
      tableData.value = res.data.list;
      total.value = res.data.total;
      loading.value = false;
    }).catch(error => {
      console.log(error)
    });
  } else {
    queryUserByRoleId({...queryParams.value, id: id.value}).then(res => {
      tableData.value = res.data.list;
      total.value = res.data.total;
      loading.value = false;
    }).catch(error => {
      console.log(error)
    });
  }
}
// 控制表格复选框
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.id);
};
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
// 控制重置选中id和对话框
const handleAssignReset = () => {
  emit('handleAssignReset');
}
// 控制查询
const handleQuery = () => {
  queryParams.value.currentPage = 1;
  getList();
}
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
// 暴露方法
defineExpose({
  getList
});
</script>

<style scoped>
.demo-pagination-block {
  float: right;
}
</style>
