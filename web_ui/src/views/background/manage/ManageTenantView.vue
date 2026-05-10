<template>
  <div>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="70px">
      <el-form-item label="租户名称" prop="tenantName">
        <el-input v-model="queryParams.tenantName" placeholder="请输入租户名称" style="width: 200px" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="请选择状态" style="width: 200px">
          <el-option label="正常" :value="0" />
          <el-option label="停用" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click :icon="Search" type="primary" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button v-no-more-click v-permission="'admin:tenant:add'" :icon="Plus" plain type="primary" @click="handleAdd">
        新增
      </el-button>
      <el-button v-no-more-click v-permission="'admin:tenant:update'" :disabled="single" :icon="EditPen" plain
        type="success" @click="handleUpdate">修改
      </el-button>
      <el-button v-no-more-click v-permission="'admin:tenant:delete'" :disabled="multiple" :icon="Delete" plain
        type="danger" @click="handleDelete">删除
      </el-button>
      <el-button v-no-more-click v-permission="'admin:tenant:export'" :icon="Bottom" plain type="warning"
        @click="handleExport">导出
      </el-button>
    </div>
    <br>
    <el-table v-loading="loading" :data="tableData" :default-sort="{ prop: 'id', order: 'descending' }"
      style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55" />
      <el-table-column align="center" label="租户ID" prop="id" sortable width="100" />
      <el-table-column align="center" label="租户名称" prop="tenantName" />
      <el-table-column align="center" label="联系人" prop="contactName" />
      <el-table-column align="center" label="联系电话" prop="contactPhone" />
      <el-table-column align="center" label="状态" prop="status" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.status === 0" type="success" size="small">正常</el-tag>
          <el-tag v-else type="danger" size="small">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="备注" prop="remark" show-overflow-tooltip />
      <el-table-column align="center" label="创建时间" prop="createTime" show-overflow-tooltip />
      <el-table-column align="center" label="更新时间" prop="updateTime" show-overflow-tooltip />
      <el-table-column align="center" fixed="right" label="操作">
        <template #default="scope">
          <el-button v-no-more-click v-permission="'admin:tenant:update'" :icon="EditPen" link size="small"
            type="primary" @click="handleUpdate(scope.row)">
            修改
          </el-button>
          <el-button v-no-more-click v-permission="'admin:tenant:delete'" :icon="Delete" link size="small"
            type="primary" @click="handleDelete(scope.row)" :disabled="scope.row.id === 1">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>
    <div class="demo-pagination-block">
      <el-pagination :current-page="queryParams.currentPage" :page-size="queryParams.pageSize"
        :page-sizes="[10, 20, 30, 40]" :total="total" layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange" @current-change="handleCurrentChange">
      </el-pagination>
    </div>

    <el-dialog v-model="dialogOverflowVisible" :title="title" draggable overflow width="500">
      <el-form ref="formRef" :inline="true" :model="form" :rules="rules" align="center" label-width="80px">
        <el-form-item label="租户名称" prop="tenantName">
          <el-input v-model="form.tenantName" placeholder="请输入租户名称" style="width: 350px;height: 40px" />
        </el-form-item>
        <el-form-item label="联系人" prop="contactName">
          <el-input v-model="form.contactName" placeholder="请输入联系人" style="width: 350px;height: 40px" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" style="width: 350px;height: 40px" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态" style="width: 350px">
            <el-option label="正常" :value="0" />
            <el-option label="停用" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" style="width: 350px" type="textarea" />
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

<script setup lang="ts">
import type { Tenant, TenantQueryParams } from "@/types";
import type { FormInstance } from 'element-plus';
import { ElMessage, ElMessageBox } from 'element-plus';
import { onMounted, ref } from 'vue';

import {
  addTenant,
  deleteTenant,
  queryTenant,
  queryTenantById,
  updateTenant
} from "@/api/admin_request/TenantRequest";
import { Bottom, Delete, EditPen, Plus, Refresh, Search } from "@element-plus/icons-vue";

// 表格数据
const tableData = ref<Tenant[]>([])
// 复选框选中ids
const ids = ref<number[]>([]);
// 数据总数
const total = ref(0);
// 对话框标题
const title = ref("");
// 查询表单元素
const queryFormRef = ref<FormInstance>();
// 表单元素
const formRef = ref<FormInstance>()
// 非单个禁用
const single = ref(true)
// 非多个禁用
const multiple = ref(true)
// 表格加载层
const loading = ref(true);
// 对话框显示
const dialogOverflowVisible = ref(false)
// 表单
const form = ref<Partial<Tenant>>({
  tenantName: null as unknown as string,
  contactName: null as unknown as string,
  contactPhone: null as unknown as string,
  status: 0,
  remark: null as unknown as string
});
// 查询参数
const queryParams = ref<TenantQueryParams>({
  currentPage: 1,
  pageSize: 10,
  tenantName: null,
  status: null,
});
// 验证规则
const rules = {
  tenantName: [
    { required: true, message: "租户名称不能为空", trigger: "blur" }
  ]
};
// 提交表单
const submitForm = () => {
  formRef.value?.validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateTenant(form.value as Tenant).then(res => {
          ElMessage.success("修改成功");
          dialogOverflowVisible.value = false;
          getList();
        }).catch(error => {
          console.log(error)
        });
      } else {
        addTenant(form.value).then(res => {
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
const handleDelete = (row: Partial<Tenant>) => {
  const id = row.id || ids.value;
  const idsToDelete = Array.isArray(id) ? id : [id];
  if (idsToDelete.includes(1)) {
    ElMessage.warning("禁止删除默认租户");
    return;
  }
  ElMessageBox.confirm(`是否确认删除租户编号为"${idsToDelete.join(', ')}"的数据项？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteTenant(idsToDelete.join(',')).then(res => {
      ElMessage.success("删除成功");
      getList();
    }).catch(error => {
      console.log(error)
    });
  }).catch(() => { });
};
// 控制更新
const handleUpdate = (row: Partial<Tenant>) => {
  resetFrom();
  const tenantId = row.id || ids.value[0];
  queryTenantById(tenantId).then(res => {
    form.value = res.data
    dialogOverflowVisible.value = true;
    title.value = "修改租户";
  }).catch(error => {
    console.log(error)
  });
};
// 获取数据列表
const getList = () => {
  loading.value = true;
  queryTenant(queryParams.value).then(res => {
    tableData.value = res.data.list;
    total.value = res.data.total;
    loading.value = false;
  }).catch(error => {
    console.log(error)
  });
};
// 控制导出
const handleExport = () => {
  const uploadUrl = import.meta.env.VUE_APP_BASEURL
  window.location.href = uploadUrl + '/admin/tenant/export'
};
// 控制添加
const handleAdd = () => {
  resetFrom();
  dialogOverflowVisible.value = true;
  title.value = "添加租户";
};
// 控制表格复选框
const handleSelectionChange = (selection: Tenant[]) => {
  ids.value = selection.map(item => item.id!);
  single.value = selection.length !== 1
  multiple.value = !selection.length
};
// 控制查询
const handleQuery = () => {
  queryParams.value.currentPage = 1;
  getList();
}
// 控制当前表格大小
const handleSizeChange = (val: number) => {
  queryParams.value.pageSize = val
  getList()
}
// 控制当前页
const handleCurrentChange = (val: number) => {
  queryParams.value.currentPage = val
  getList()
}
// 控制重置表单
const handleReset = () => {
  resetFrom()
  dialogOverflowVisible.value = false;
}
// 重置表单
const resetFrom = () => {
  form.value = {
    tenantName: null as unknown as string,
    contactName: null as unknown as string,
    contactPhone: null as unknown as string,
    status: 0,
    remark: null as unknown as string
  }
  if (formRef.value) {
    formRef.value.resetFields();
  }
};
// 重置查询表单
const resetQuery = () => {
  queryParams.value = {
    currentPage: 1,
    pageSize: 10,
    tenantName: null,
    status: null,
  }
  handleQuery();
};
// 页面挂载时获取数据
onMounted(() => {
  getList();
});
</script>
<style lang="scss" scoped>
@use "@/assets/css/admin/common";
</style>
