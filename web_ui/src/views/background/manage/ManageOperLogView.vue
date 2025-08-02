<template>
  <div>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="70px">
      <el-form-item label="日志标题" prop="title">
        <el-input
            v-model="queryParams.title"
            placeholder="请输入日志标题"
            style="width: 200px"
        />
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click :icon="Search" type="primary" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button v-no-more-click v-permission="'admin:operLog:delete'" :disabled="multiple" :icon="Delete" plain
                 type="danger"
                 @click="handleDelete">删除
      </el-button>
      <el-button v-no-more-click v-permission="'admin:operLog:export'" :icon="Bottom" plain type="warning"
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
      <el-table-column align="center" label="模块标题" prop="title" show-overflow-tooltip/>
      <el-table-column align="center" label="业务类型" prop="businessType" show-overflow-tooltip>
        <template #default="scope">
          <span v-if="scope.row.businessType === 'insert'"
                style="background-color: #ecf5ff;padding: 5px;border-radius: 20%">{{ scope.row.businessType }}</span>
          <span v-else-if="scope.row.businessType === 'delete'"
                style="background-color: #fef0f0;padding: 5px;border-radius: 20%">{{ scope.row.businessType }}</span>
          <span v-else-if="scope.row.businessType === 'update'"
                style="background-color: #eff9eb;padding: 5px;border-radius: 20%">{{ scope.row.businessType }}</span>
          <span v-else
                style="background-color: #fcf6ec;padding: 5px;border-radius: 20%">{{ scope.row.businessType }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="方法名称" prop="method" show-overflow-tooltip/>
      <el-table-column align="center" label="请求方式" prop="requestMethod" show-overflow-tooltip>
        <template #default="scope">
          <span v-if="scope.row.requestMethod === 'POST'"
                style="background-color: #ecf5ff;padding: 5px;border-radius: 20%">{{ scope.row.requestMethod }}</span>
          <span v-else-if="scope.row.requestMethod === 'DELETE'"
                style="background-color: #fef0f0;padding: 5px;border-radius: 20%">{{ scope.row.requestMethod }}</span>
          <span v-else-if="scope.row.requestMethod === 'PUT'"
                style="background-color: #eff9eb;padding: 5px;border-radius: 20%">{{ scope.row.requestMethod }}</span>
          <span v-else
                style="background-color: #fcf6ec;padding: 5px;border-radius: 20%">{{ scope.row.requestMethod }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作人员" prop="operName" show-overflow-tooltip/>
      <el-table-column align="center" label="请求URL" prop="operUrl" show-overflow-tooltip/>
      <el-table-column align="center" label="主机地址" prop="operIp" show-overflow-tooltip/>
      <el-table-column align="center" label="请求参数" prop="operParam" show-overflow-tooltip/>
      <el-table-column align="center" label="返回参数" prop="jsonResult" show-overflow-tooltip/>
      <el-table-column align="center" label="操作状态" prop="status" show-overflow-tooltip>
        <template #default="scope">
          <span v-if="scope.row.status === 0"
                style="background-color: #eff9eb;padding: 5px;border-radius: 20%">正常</span>
          <span v-else-if="scope.row.status === 1"
                style="background-color: #fef0f0;padding: 5px;border-radius: 20%">失败</span>
          <span v-else
                style="background-color: #fcf6ec;padding: 5px;border-radius: 20%">异常</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="错误消息" prop="errorMsg" show-overflow-tooltip/>
      <el-table-column align="center" label="操作时间" prop="operTime" show-overflow-tooltip/>
      <el-table-column align="center" label="消耗时间(毫秒)" prop="costTime" show-overflow-tooltip/>
      <el-table-column align="center" fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button v-no-more-click v-permission="'admin:operLog:query'" :icon="View" link size="small" type="primary"
                     @click="handleDetail(scope.row)">
            详细
          </el-button>
          <el-button v-no-more-click v-permission="'admin:operLog:delete'" :icon="Delete" link size="small"
                     type="primary"
                     @click="handleDelete(scope.row)">
            删除
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
        draggable
        overflow
        width="50%"
    >
      <el-descriptions :column="1" :title="title" border label-width="100" style="width: 100%">
        <el-descriptions-item label="模块标题">
          {{ from.title }}
        </el-descriptions-item>
        <el-descriptions-item label="业务类型">
          {{ from.businessType }}
        </el-descriptions-item>
        <el-descriptions-item label="方法名称">
          {{ from.method }}
        </el-descriptions-item>
        <el-descriptions-item label="请求方式">
          {{ from.requestMethod }}
        </el-descriptions-item>
        <el-descriptions-item label="操作人员">
          {{ from.operName }}
        </el-descriptions-item>
        <el-descriptions-item label="请求URL">
          {{ from.operUrl }}
        </el-descriptions-item>
        <el-descriptions-item label="主机地址">
          {{ from.operIp }}
        </el-descriptions-item>
        <el-descriptions-item label="请求参数">
          {{ from.operParam }}
        </el-descriptions-item>
        <el-descriptions-item label="返回参数">
          <pre>
            {{ from.jsonResult }}
          </pre>
        </el-descriptions-item>
        <el-descriptions-item label="操作状态">
          {{ from.status }}
        </el-descriptions-item>
        <el-descriptions-item label="错误消息">
          {{ from.errorMsg }}
        </el-descriptions-item>
        <el-descriptions-item label="操作时间">
          {{ from.operTime }}
        </el-descriptions-item>
        <el-descriptions-item label="消耗时间（毫秒）">
          {{ from.costTime }}
        </el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <div class="dialog-footer">
          <el-button v-no-more-click @click="handleReset">取消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

import {batchDeleteOperLog, queryOperLog, queryOperLogById} from "@/api/admin_request/OperLogRequest";
import {Bottom, Delete, Refresh, Search, View} from "@element-plus/icons-vue";

// 表格数据
const tableData = ref([])
// 复选框选中ids
const ids = ref([]);
// 数据总数
const total = ref(0);
// 对话框显示
const dialogOverflowVisible = ref(false)
// 对话框标题
const title = ref('')
// 查询表单元素
const queryFormRef = ref(null);
// 非多个禁用
const multiple = ref(true)
// 表格加载层
const loading = ref(true);
// 详细表单
const from = ref({})
// 上传的ip和端口号
const uploadUrl = import.meta.env.VUE_APP_BASEURL
// 查询参数
let queryParams = ref({
  currentPage: 1,
  pageSize: 10,
  title: null,
});
// 控制详细
const handleDetail = (row) => {
  queryOperLogById(row.id).then(res => {

    from.value = res.data

    if (res.data.jsonResult) {
      const parsedJson = JSON.parse(res.data.jsonResult);
      from.value.jsonResult = JSON.stringify(parsedJson, null, 2);
    }

    dialogOverflowVisible.value = true;
    title.value = "查看详细日志";
  }).catch(error => {
    console.log(error)
  });
}
// 控制删除
const handleDelete = (row) => {
  const id = row.id || ids.value;

  const idsToDelete = Array.isArray(id) ? id : [id];
  ElMessageBox.confirm(`是否确认删除日志编号为"${idsToDelete.join(', ')}"的数据项？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    batchDeleteOperLog(idsToDelete.join(', ')).then(res => {
      ElMessage.success("删除成功");
      getList();
    }).catch(error => {
      console.log(error)
    });
  }).catch(error => {
  });
};
// 获取数据列表
const getList = () => {
  loading.value = true;
  queryOperLog(queryParams.value).then(res => {
    tableData.value = res.data.list;
    total.value = res.data.total;
    loading.value = false;
  }).catch(error => {
    console.log(error)
  });
};
// 控制导出
const handleExport = () => {
  window.location.href = uploadUrl + '/admin/operLog/export'
};
// 控制表格复选框
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.id);
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
// 控制对框框取消
const handleReset = () => {
  from.value = {}
  dialogOverflowVisible.value = false;
}
// 重置查询表单
const resetQuery = () => {
  queryParams.value = {
    currentPage: 1,
    pageSize: 10,
    title: null,
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
