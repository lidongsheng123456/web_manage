<template>
  <div class="line-chart-box">
    <div ref="chartRef" style="height: 500px;"></div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts';
import {onMounted, ref, watch} from "vue";

const props = defineProps({
  chartData: {
    type: Array,
    required: true
  }
});

const chartRef = ref(null);
let chartInstance = null;

const setOptions = (data = []) => {
  if (chartInstance) {
    chartInstance.setOption({
      xAxis: {
        type: 'category',
        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: data,
          type: 'line',
          smooth: true
        }
      ]
    });
  }
}

watch(() => props.chartData, (newValue, oldValue) => {
  setOptions(newValue);
}, {deep: true});

onMounted(() => {
  chartInstance = echarts.init(chartRef.value);
  setOptions(props.chartData);
});
</script>

<style>
.line-chart-box {
  background-color: white;
  margin: 30px;
}

canvas {
  width: 100% !important;
}

.line-chart-box div {
  width: 100% !important;
}
</style>
