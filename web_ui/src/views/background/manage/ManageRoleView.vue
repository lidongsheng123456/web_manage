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
        <el-button v-no-more-click :icon="Search" type="primary" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button v-no-more-click v-permission="'admin:role:add'" :icon="Plus" plain type="primary" @click="handleAdd">
        新增
      </el-button>
      <el-button v-no-more-click v-permission="'admin:role:update'" :disabled="single" :icon="EditPen" plain
                 type="success"
                 @click="handleUpdate">修改
      </el-button>
      <el-button v-no-more-click v-permission="'admin:role:delete'" :disabled="multiple" :icon="Delete" plain
                 type="danger"
                 @click="handleDelete">删除
      </el-button>
      <el-button v-no-more-click v-permission="'admin:role:export'" :icon="Bottom" plain type="warning"
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
      <el-table-column align="center" label="角色代码" prop="roleCode" show-overflow-tooltip/>
      <el-table-column align="center" label="角色名称" prop="roleName" show-overflow-tooltip/>
      <el-table-column align="center" label="角色描述" prop="description" show-overflow-tooltip/>
      <el-table-column align="center" label="创建时间" prop="createTime" show-overflow-tooltip/>
      <el-table-column align="center" label="更新时间" prop="updateTime" show-overflow-tooltip/>
      <el-table-column align="center" fixed="right" label="操作" width="300">
        <template #default="scope">
          <el-button v-no-more-click v-permission="'admin:role:update'" :icon="EditPen" link size="small" type="primary"
                     @click="handleUpdate(scope.row)">
            修改
          </el-button>
          <el-button v-no-more-click v-permission="'admin:role:delete'" :icon="Delete" link size="small" type="primary"
                     @click="handleDelete(scope.row)">
            删除
          </el-button>
          <el-button v-no-more-click v-permission="'admin:role:assign'" :icon="CircleCheck" link size="small"
                     type="primary"
                     @click="handleAssign(scope.row)">
            分配角色
          </el-button>
          <el-button v-no-more-click v-permission="'admin:role:remove'" :icon="Delete" link size="small" type="primary"
                     @click="handleRemove(scope.row)">
            移除角色
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>
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

    <el-dialog
        v-model="dialogOverflowVisible"
        :title="title"
        draggable
        overflow
        width="500"
    >
      <el-form ref="formRef" :inline="true" :model="form" :rules="rules" align="center" label-width="80px">
        <el-form-item label="角色代码" prop="roleCode">
          <el-input
              v-model="form.roleCode"
              placeholder="请输入角色代码"
              style="width: 350px"
          />
        </el-form-item>
        <el-form-item label="角色名称" prop="roleName">
          <el-input
              v-model="form.roleName"
              placeholder="请输入角色名称"
              style="width: 350px"
          />
        </el-form-item>
        <el-form-item label="角色描述" prop="description">
          <el-input
              v-model="form.description"
              placeholder="请输入角色描述"
              style="width: 350px"
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

    <el-dialog
        v-model="assignDialogOverflowVisible"
        :title="assignTitle"
        draggable
        overflow
        width="850"
    >
      <AssignRoleView ref="assignRoleViewRef" @handleAssignReset="handleAssignReset"
                      @submitAssignForm="handleSubmitAssignForm"/>
    </el-dialog>
  </div>
</template>

<script setup>
import {nextTick, onMounted, ref} from 'vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

import {
  addRole,
  assignRole,
  batchDeleteRole,
  queryRole,
  queryRoleById,
  removeRole,
  updateRole
} from "@/api/admin_request/RoleRequest";
import {Bottom, CircleCheck, Delete, EditPen, Plus, Refresh, Search} from "@element-plus/icons-vue";
import AssignRoleView from "@/components/manage/AssignRoleView.vue";

// 表格数据
const tableData = ref([])
// 复选框选中ids
const ids = ref([]);
// 角色选中id
const assignId = ref(null);
// 数据总数
const total = ref(0);
// 对话框标题
const title = ref("");
// 分配角色对话框标题
const assignTitle = ref("");
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
// 分配角色对话框显示
const assignDialogOverflowVisible = ref(false)
// 引用子组件实例
const assignRoleViewRef = ref(null);
// 上传的ip和端口号
const uploadUrl = import.meta.env.VUE_APP_BASEURL
// 表单
const form = ref({
  roleCode: null,
  roleName: null,
  description: null
});
// 查询参数
let queryParams = ref({
  currentPage: 1,
  pageSize: 10,
  roleCode: null,
  roleName: null,
});
// 验证规则
const rules = {
  roleCode: [
    {required: true, message: "角色代码不能为空", trigger: "blur"}
  ],
  roleName: [
    {required: true, message: "角色名称不能为空", trigger: "blur"}
  ],
  description: [
    {required: true, message: "角色描述不能为空", trigger: "blur"}
  ],
};
// 提交表单
const submitForm = () => {
  formRef.value.validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateRole(form.value).then(res => {
          ElMessage.success("修改成功");
          dialogOverflowVisible.value = false;
          getList();
        }).catch(error => {
          console.log(error)
        });
      } else {
        addRole(form.value).then(res => {
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
// 提交分配角色表单
const handleSubmitAssignForm = (ids, isAssign) => {
  if (isAssign === 'assign') {
    assignRole({userId: ids, roleId: assignId.value}).then(res => {
      ElMessage.success('分配角色成功');
      assignDialogOverflowVisible.value = false;
      getList()
    }).catch(error => {
      console.log(error)
    });
  } else {
    removeRole({userId: ids, roleId: assignId.value}).then(res => {
      ElMessage.success('移除角色成功');
      assignDialogOverflowVisible.value = false;
      getList()
    }).catch(error => {
      console.log(error)
    });
  }
}
// 控制删除
const handleDelete = (row) => {
  const id = row.id || ids.value;

  const idsToDelete = Array.isArray(id) ? id : [id];
  ElMessageBox.confirm(`是否确认删除角色编号为"${idsToDelete.join(', ')}"的数据项？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    batchDeleteRole(idsToDelete.join(', ')).then(res => {
      ElMessage.success("删除成功");
      getList();
    }).catch(error => {
      console.log(error)
    });
  }).catch(error => {
  });
};
// 控制更新
const handleUpdate = (row) => {
  resetFrom();
  const id = row.id || ids.value;
  queryRoleById(id).then(res => {
    Object.assign(form.value, res.data);
    dialogOverflowVisible.value = true;
    title.value = "修改角色";
  }).catch(error => {
    console.log(error)
  });
};
// 分配权限
const handleAssign = (row) => {
  handleAssignReset()
  assignDialogOverflowVisible.value = true
  assignTitle.value = '分配权限'
  assignId.value = row.id
  // nextTick 确保在 DOM 更新后调用 getList 方法,避免在子组件还未完全挂载时调用方法。
  nextTick(() => {
    if (assignRoleViewRef.value) {
      assignRoleViewRef.value.getList('assign', row.id);
    }
  });
}
// 移除权限
const handleRemove = (row) => {
  handleAssignReset()
  assignDialogOverflowVisible.value = true
  assignTitle.value = '移除角色'
  assignId.value = row.id
  // nextTick 确保在 DOM 更新后调用 getList 方法,避免在子组件还未完全挂载时调用方法。
  nextTick(() => {
    if (assignRoleViewRef.value) {
      assignRoleViewRef.value.getList('remove', row.id);
    }
  });
}
// 获取数据列表
const getList = () => {
  loading.value = true;
  queryRole(queryParams.value).then(res => {
    tableData.value = res.data.list;
    total.value = res.data.total;
    loading.value = false;
  }).catch(error => {
    console.log(error)
  });
};
// 控制导出
const handleExport = () => {
  window.location.href = uploadUrl + '/admin/role/export'
};
// 控制添加
const handleAdd = () => {
  resetFrom();
  dialogOverflowVisible.value = true;
  title.value = "添加角色";
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
// 控制重置分配角色表单
const handleAssignReset = () => {
  assignDialogOverflowVisible.value = false;
  assignId.value = null
}
// 控制重置表单
const handleReset = () => {
  resetFrom()
  dialogOverflowVisible.value = false;
}
// 重置表单
const resetFrom = () => {
  form.value = {
    roleCode: null,
    roleName: null,
    description: null
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
    roleCode: null,
    roleName: null,
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
</style>
