<template>
  <div style="background-color: #eff1f4;padding: 20px;">
    <StatisticView :chart-data="lineChartDataS" @updateLineChartData="handleSetLineChartData"/>
    <LineChartView :chart-data="lineChartData"/>
  </div>
</template>

<script setup>
import StatisticView from "@/views/dashboard/StatisticView.vue";
import LineChartView from "@/views/dashboard/LineChartView.vue";
import {onMounted, ref} from "vue";
import {queryData} from "@/api/request/DataCenterRequest";
import {ElMessage} from "element-plus";
// 表格数据
let lineChartDataS = ref({
  user: [0, 0, 0, 0, 0, 0, 0],
  notice: [0, 0, 0, 0, 0, 0, 0],
  operLog: [0, 0, 0, 0, 0, 0, 0],
  permission: [0, 0, 0, 0, 0, 0, 0]
});
// 初始化线表数据
let lineChartData = ref(lineChartDataS.value['user']);
// 控制选择表格数据
const handleSetLineChartData = (type) => {
  lineChartData.value = lineChartDataS.value[type];
};
// 初始化
const init = () => {
  queryData().then(res => {
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    let data = res.data
    lineChartDataS.value.user = data.user.map(item => item.count)
    lineChartDataS.value.notice = data.notice.map(item => item.count)
    lineChartDataS.value.operLog = data.operLog.map(item => item.count)
    lineChartDataS.value.permission = data.permission.map(item => item.count)
  }).catch(error => {
    console.log(error)
  });
}
onMounted(() => {
  init()
})
</script>

<style scoped>
</style>
