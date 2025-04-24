<template>
  <div>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="70px">
      <el-form-item label="角色代码" prop="roleCode">
        <el-input
            v-model="queryParams.roleCode"
            placeholder="请输入角色代码"
            style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="角色名称" prop="roleName">
        <el-input
            v-model="queryParams.roleName"
            placeholder="请输入角色名称"
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
      <el-table-column align="center" label="角色代码" prop="roleCode" show-overflow-tooltip/>
      <el-table-column align="center" label="角色名称" prop="roleName" show-overflow-tooltip/>
      <el-table-column align="center" label="角色描述" prop="description" show-overflow-tooltip/>
    </el-table>
    <br>
    <div style="display: flex;justify-content: space-between">
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
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import {Refresh, Search} from "@element-plus/icons-vue";
import {queryRoleByPermissionId, queryRoleNotPermissionId} from "@/api/admin_request/PermissionRequest";
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
  roleCode: null,
  roleName: null
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
    queryRoleNotPermissionId({...queryParams.value, id: id.value}).then(res => {
      tableData.value = res.data.list;
      total.value = res.data.total;
      loading.value = false;
    }).catch(error => {
      console.log(error)
    });
  } else {
    queryRoleByPermissionId({...queryParams.value, id: id.value}).then(res => {
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
