<template>
  <div>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="70px">
      <el-form-item label="字典类型" prop="dictType">
        <el-input
            v-model="queryParams.dictType"
            placeholder="请输入字典类型"
            style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="字典标签" prop="dictLabel">
        <el-input
            v-model="queryParams.dictLabel"
            placeholder="请输入字典标签"
            style="width: 200px"
        />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click :icon="Search" type="primary" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button v-no-more-click v-permission="'admin:dict:add'" :icon="Plus" plain type="primary" @click="handleAdd">
        新增
      </el-button>
      <el-button v-no-more-click v-permission="'admin:dict:update'" :disabled="single" :icon="EditPen" plain
                 type="success"
                 @click="handleUpdate">修改
      </el-button>
      <el-button v-no-more-click v-permission="'admin:dict:delete'" :disabled="multiple" :icon="Delete" plain
                 type="danger"
                 @click="handleDelete">删除
      </el-button>
      <el-button v-no-more-click v-permission="'admin:dict:export'" :icon="Bottom" plain type="warning"
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
      <el-table-column align="center" label="字典类型" prop="dictType"/>
      <el-table-column align="center" label="字典标签" prop="dictLabel"/>
      <el-table-column align="center" label="字典键值" prop="dictValue"/>
      <el-table-column align="center" label="描述" prop="description"/>
      <el-table-column align="center" label="创建时间" prop="createTime" show-overflow-tooltip/>
      <el-table-column align="center" label="更新时间" prop="updateTime" show-overflow-tooltip/>
      <el-table-column align="center" fixed="right" label="操作">
        <template #default="scope">
          <el-button v-no-more-click v-permission="'admin:dict:update'" :icon="EditPen" link size="small"
                     type="primary"
                     @click="handleUpdate(scope.row)">
            修改
          </el-button>
          <el-button v-no-more-click v-permission="'admin:dict:delete'" :icon="Delete" link size="small"
                     type="primary"
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
      <el-form ref="formRef" :inline="true" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="字典类型" prop="dictType">
          <el-input
              v-model="form.dictType"
              placeholder="请输入字典类型"
              style="width: 350px;"
          />
        </el-form-item>
        <el-form-item label="字典标签" prop="dictLabel">
          <el-input
              v-model="form.dictLabel"
              placeholder="请输入字典标签"
              style="width: 350px"
          />
        </el-form-item>
        <el-form-item label="字典键值" prop="dictValue">
          <el-input-number v-model="form.dictValue" :min="0" :max="100"/>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
              v-model="form.description"
              placeholder="请输入描述"
              style="width: 350px"
              type="textarea"
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

import {
  addDict,
  batchDeleteDict,
  queryDict,
  queryDictById,
  updateDict
} from "@/api/admin_request/DictRequest";
import {Bottom, Delete, EditPen, Plus, Refresh, Search} from "@element-plus/icons-vue";

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
// 上传的ip和端口号
const uploadUrl = process.env.VUE_APP_BASEURL
// 表单
const form = ref({
  dictType: null,
  dictLabel: null,
  dictValue: null,
  description: null
});
// 查询参数
let queryParams = ref({
  currentPage: 1,
  pageSize: 10,
  dictType: null,
  dictLabel: null,
});
// 验证规则
const rules = {
  dictType: [
    {required: true, message: "字典类型不能为空", trigger: "blur"}
  ],
  dictLabel: [
    {required: true, message: "字典标签不能为空", trigger: "blur"}
  ],
  dictValue: [
    {required: true, message: "字典键值不能为空", trigger: "blur"}
  ],
};
// 提交表单
const submitForm = () => {
  formRef.value.validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateDict(form.value).then(res => {
          ElMessage.success("修改成功");
          dialogOverflowVisible.value = false;
          getList();
        }).catch(error => {
          console.log(error)
        });
      } else {
        addDict(form.value).then(res => {
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
  ElMessageBox.confirm(`是否确认删除字典编号为"${idsToDelete.join(', ')}"的数据项？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    batchDeleteDict(idsToDelete.join(', ')).then(res => {
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
  queryDictById(id).then(res => {
    form.value = res.data
    dialogOverflowVisible.value = true;
    title.value = "修改字典";
  }).catch(error => {
    console.log(error)
  });
};
// 获取数据列表
const getList = () => {
  loading.value = true;
  queryDict(queryParams.value).then(res => {
    tableData.value = res.data.list;
    total.value = res.data.total;
    loading.value = false;
  }).catch(error => {
    console.log(error)
  });
};
// 控制导出
const handleExport = () => {
  window.location.href = uploadUrl + '/admin/dict/export'
};
// 控制添加
const handleAdd = () => {
  resetFrom();
  dialogOverflowVisible.value = true;
  title.value = "添加字典";
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
// 控制重置表单
const handleReset = () => {
  resetFrom()
  dialogOverflowVisible.value = false;
}
// 重置表单
const resetFrom = () => {
  form.value = {
    dictType: null,
    dictLabel: null,
    dictValue: null,
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
    dictType: null,
    dictLabel: null,
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
