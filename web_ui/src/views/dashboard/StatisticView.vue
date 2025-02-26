<template>
  <div>
    <el-row>
      <el-col :span="5" @click="handleSetLineChartData('user')">
        <el-statistic :value="dataCount.user" title="总用户">
          <template #suffix>
            <el-icon style="vertical-align: -0.125em">
              <User/>
            </el-icon>
          </template>
        </el-statistic>
      </el-col>
      <el-col :span="5" @click="handleSetLineChartData('notice')">
        <el-statistic :value="dataCount.notice" title="总通知">
          <template #suffix>
            <el-icon style="vertical-align: -0.125em">
              <Bell/>
            </el-icon>
          </template>
        </el-statistic>
      </el-col>
      <el-col :span="5" @click="handleSetLineChartData('operLog')">
        <el-statistic :value="dataCount.operLog" title="总日志">
          <template #suffix>
            <el-icon style="vertical-align: -0.125em">
              <Comment/>
            </el-icon>
          </template>
        </el-statistic>
      </el-col>
      <el-col :span="5" @click="handleSetLineChartData('permission')">
        <el-statistic :value="dataCount.permission" title="总访问权限">
          <template #suffix>
            <el-icon style="vertical-align: -0.125em">
              <Unlock/>
            </el-icon>
          </template>
        </el-statistic>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import {Bell, Comment, Unlock, User} from '@element-plus/icons-vue'
import {computed, defineProps} from "vue";
// 父组件事件
const emit = defineEmits(['updateLineChartData'])
// 父组件的数据
const props = defineProps({
  chartData: {
    type: Object,
    required: true
  }
});
// 计算角色属性
const dataCount = computed(() => {
  return {
    user: props.chartData.user.reduce((accumulator, currentValue) => accumulator + currentValue, 0),
    notice: props.chartData.notice.reduce((accumulator, currentValue) => accumulator + currentValue, 0),
    operLog: props.chartData.operLog.reduce((accumulator, currentValue) => accumulator + currentValue, 0),
    permission: props.chartData.permission.reduce((accumulator, currentValue) => accumulator + currentValue, 0)
  }
});
// 控制点击
const handleSetLineChartData = (type) => {
  emit('updateLineChartData', type);
};
</script>

<style lang="scss" scoped>
.el-row {
  justify-content: space-around;
}

.el-col {
  text-align: center;
  background-color: white;
  padding: 40px;
  transition: all .5s;
  cursor: pointer;
}

.el-col:hover {
  background: #cccccc;
  color: white;
}
</style>
