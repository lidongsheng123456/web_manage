<template>
  <div class="data-center">
    <div class="dc-header">
      <div class="dc-header-left">
        <h1 class="dc-title"><el-icon :size="24" color="#409eff">
            <DataAnalysis />
          </el-icon><span>数据中心</span></h1>
        <p class="dc-subtitle">SYSTEM DATA ANALYTICS CENTER</p>
      </div>
      <div class="dc-total-badge">
        <span class="dc-total-label">数据总量</span>
        <span class="dc-total-value">{{ formatNum(Math.round(animGrandTotal)) }}</span>
      </div>
    </div>
    <div class="stat-grid">
      <div v-for="(card, idx) in cardConfigs" :key="card.key" class="stat-card"
        :class="{ active: activeKey === card.key }" :style="{ '--ac': card.color, animationDelay: idx * 0.06 + 's' }"
        @click="activeKey = card.key">
        <div class="stat-top">
          <div class="stat-icon" :style="{ background: card.color + '15', color: card.color }">
            <el-icon :size="20">
              <component :is="card.icon" />
            </el-icon>
          </div>
          <svg class="stat-spark" viewBox="0 0 60 24" preserveAspectRatio="none">
            <path :d="sparkArea(card.key)" :fill="card.color" fill-opacity="0.08" />
            <path :d="sparkLine(card.key)" fill="none" :stroke="card.color" stroke-width="1.5" stroke-linecap="round"
              stroke-linejoin="round" />
          </svg>
        </div>
        <div class="stat-val">{{ formatNum(Math.round(animTotals[card.key])) }}</div>
        <div class="stat-lbl">{{ card.label }}</div>
      </div>
    </div>
    <div class="charts-grid">
      <div class="chart-card chart-wide">
        <div class="chart-hd">
          <span class="chart-title"><i class="cd" style="background:#409eff"></i>近7天数据趋势</span>
          <div class="chart-legend">
            <span v-for="lg in legends" :key="lg.key" class="lg-tag" :class="{ off: !showLine[lg.key] }"
              @click="showLine[lg.key] = !showLine[lg.key]; updateLine()">
              <i class="cd" :style="{ background: lg.color }"></i>{{ lg.label }}
            </span>
          </div>
        </div>
        <div ref="lineRef" class="chart-body chart-lg"></div>
      </div>
      <div class="chart-card">
        <div class="chart-hd"><span class="chart-title"><i class="cd" style="background:#8b5cf6"></i>数据占比分布</span></div>
        <div ref="pieRef" class="chart-body"></div>
      </div>
      <div class="chart-card chart-wide">
        <div class="chart-hd"><span class="chart-title"><i class="cd" style="background:#10b981"></i>各表数据总量对比</span>
        </div>
        <div ref="barRef" class="chart-body"></div>
      </div>
      <div class="chart-card">
        <div class="chart-hd"><span class="chart-title"><i class="cd" style="background:#f59e0b"></i>近7天分布雷达</span>
        </div>
        <div ref="radarRef" class="chart-body"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { queryData } from '@/api/admin_request/DataCenterRequest'
import { Avatar, Bell, Collection, Connection, DataAnalysis, Document, Key, Search, Unlock, User, UserFilled } from '@element-plus/icons-vue'
import { TransitionPresets, useTransition } from '@vueuse/core'
import * as echarts from 'echarts'
import { computed, nextTick, onActivated, onBeforeUnmount, onDeactivated, onMounted, reactive, ref } from 'vue'

const lineRef = ref(null), pieRef = ref(null), barRef = ref(null), radarRef = ref(null)
let lineChart, pieChart, barChart, radarChart
const activeKey = ref('user')
const dateLabels = ref(['', '', '', '', '', '', ''])

const weekly = reactive({
  user: [0, 0, 0, 0, 0, 0, 0], notice: [0, 0, 0, 0, 0, 0, 0], operLog: [0, 0, 0, 0, 0, 0, 0], permission: [0, 0, 0, 0, 0, 0, 0],
  frontUser: [0, 0, 0, 0, 0, 0, 0], role: [0, 0, 0, 0, 0, 0, 0], dictData: [0, 0, 0, 0, 0, 0, 0], comQuery: [0, 0, 0, 0, 0, 0, 0],
  rolePermission: [0, 0, 0, 0, 0, 0, 0], userRole: [0, 0, 0, 0, 0, 0, 0]
})
const tots = {
  user: ref(0), notice: ref(0), operLog: ref(0), permission: ref(0), frontUser: ref(0),
  role: ref(0), dictData: ref(0), comQuery: ref(0), rolePermission: ref(0), userRole: ref(0)
}
const TR = { duration: 2000, transition: TransitionPresets.easeOutCubic }
const animTotals = reactive({
  user: useTransition(tots.user, TR), notice: useTransition(tots.notice, TR),
  operLog: useTransition(tots.operLog, TR), permission: useTransition(tots.permission, TR),
  frontUser: useTransition(tots.frontUser, TR), role: useTransition(tots.role, TR),
  dictData: useTransition(tots.dictData, TR), comQuery: useTransition(tots.comQuery, TR),
  rolePermission: useTransition(tots.rolePermission, TR), userRole: useTransition(tots.userRole, TR)
})
const grandTotal = computed(() => Object.values(tots).reduce((s, r) => s + r.value, 0))
const animGrandTotal = useTransition(grandTotal, TR)

const cardConfigs = [
  { key: 'user', label: '后台用户', icon: User, color: '#409eff' }, { key: 'notice', label: '系统通知', icon: Bell, color: '#e6a23c' },
  { key: 'operLog', label: '操作日志', icon: Document, color: '#67c23a' }, { key: 'permission', label: '访问权限', icon: Unlock, color: '#8b5cf6' },
  { key: 'frontUser', label: '前台用户', icon: Avatar, color: '#06b6d4' }, { key: 'role', label: '系统角色', icon: UserFilled, color: '#f56c6c' },
  { key: 'dictData', label: '字典数据', icon: Collection, color: '#f97316' }, { key: 'comQuery', label: '通用查询', icon: Search, color: '#14b8a6' },
  { key: 'rolePermission', label: '角色权限', icon: Key, color: '#6366f1' }, { key: 'userRole', label: '用户角色', icon: Connection, color: '#ec4899' }
]
const showLine = reactive({ user: true, notice: true, operLog: true, permission: true, frontUser: true })
const legends = [
  { key: 'user', label: '后台用户', color: '#409eff' }, { key: 'notice', label: '通知', color: '#e6a23c' },
  { key: 'operLog', label: '日志', color: '#67c23a' }, { key: 'permission', label: '权限', color: '#8b5cf6' },
  { key: 'frontUser', label: '前台用户', color: '#06b6d4' }
]

const formatNum = n => n >= 10000 ? (n / 10000).toFixed(1) + 'w' : n >= 1000 ? (n / 1000).toFixed(1) + 'k' : String(n)
const sparkLine = k => {
  const d = weekly[k], mx = Math.max(...d, 1)
  return 'M' + d.map((v, i) => `${(i / 6) * 60},${22 - (v / mx) * 20}`).join(' L')
}
const sparkArea = k => { const p = sparkLine(k); return p ? p + ' L60,24 L0,24 Z' : '' }

const TIP = { backgroundColor: '#fff', borderColor: '#e4e7ed', textStyle: { color: '#303133', fontSize: 12 }, extraCssText: 'box-shadow:0 2px 12px rgba(0,0,0,0.1)' }
const TC = '#909399', GC = '#ebeef5'

const updateLine = () => {
  if (!lineChart) return
  const clr = { user: '#409eff', notice: '#e6a23c', operLog: '#67c23a', permission: '#8b5cf6', frontUser: '#06b6d4' }
  const series = Object.entries(clr).filter(([k]) => showLine[k]).map(([k, c]) => ({
    name: cardConfigs.find(x => x.key === k)?.label, type: 'line', smooth: true, symbol: 'circle',
    symbolSize: 6, showSymbol: false, data: weekly[k],
    lineStyle: { width: 2, color: c }, itemStyle: { color: c },
    areaStyle: { color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{ offset: 0, color: c + '30' }, { offset: 1, color: c + '05' }]) }
  }))
  lineChart.setOption({
    backgroundColor: 'transparent', tooltip: { ...TIP, trigger: 'axis' },
    grid: { top: 20, right: 20, bottom: 30, left: 50 },
    xAxis: { type: 'category', data: dateLabels.value, boundaryGap: false, axisLine: { lineStyle: { color: GC } }, axisLabel: { color: TC }, splitLine: { show: false } },
    yAxis: { type: 'value', axisLine: { show: false }, axisLabel: { color: TC }, splitLine: { lineStyle: { color: GC, type: 'dashed' } } },
    series
  }, true)
}
const updatePie = () => {
  if (!pieChart) return
  const data = cardConfigs.map(c => ({ name: c.label, value: tots[c.key].value, itemStyle: { color: c.color } })).filter(d => d.value > 0)
  pieChart.setOption({
    backgroundColor: 'transparent', tooltip: { ...TIP, trigger: 'item', formatter: '{b}: {c} ({d}%)' },
    series: [{
      type: 'pie', radius: ['40%', '70%'], center: ['50%', '52%'],
      itemStyle: { borderRadius: 4, borderColor: '#fff', borderWidth: 2 },
      label: { color: '#606266', fontSize: 11, formatter: '{b}\n{d}%' }, labelLine: { lineStyle: { color: '#c0c4cc' } },
      emphasis: { label: { show: true, fontWeight: 'bold', fontSize: 13 }, itemStyle: { shadowBlur: 10, shadowColor: 'rgba(0,0,0,0.15)' } },
      data
    }]
  }, true)
}
const updateBar = () => {
  if (!barChart) return
  const sorted = [...cardConfigs].map(c => ({ ...c, value: tots[c.key].value })).sort((a, b) => b.value - a.value)
  barChart.setOption({
    backgroundColor: 'transparent', tooltip: { ...TIP, trigger: 'axis' },
    grid: { top: 10, right: 30, bottom: 30, left: 85 },
    xAxis: { type: 'value', axisLine: { show: false }, axisLabel: { color: TC }, splitLine: { lineStyle: { color: GC, type: 'dashed' } } },
    yAxis: { type: 'category', data: sorted.map(c => c.label), axisLine: { lineStyle: { color: GC } }, axisLabel: { color: '#606266', fontSize: 12 } },
    series: [{
      type: 'bar', barWidth: 16, data: sorted.map(c => ({
        value: c.value,
        itemStyle: { color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{ offset: 0, color: c.color + '60' }, { offset: 1, color: c.color }]), borderRadius: [0, 4, 4, 0] }
      }))
    }]
  }, true)
}
const updateRadar = () => {
  if (!radarChart) return
  const keys = ['user', 'notice', 'operLog', 'permission', 'frontUser']
  const clr = { user: '#409eff', notice: '#e6a23c', operLog: '#67c23a', permission: '#8b5cf6', frontUser: '#06b6d4' }
  const allMax = Math.max(...keys.flatMap(k => weekly[k]), 5)
  radarChart.setOption({
    backgroundColor: 'transparent', tooltip: { ...TIP },
    radar: {
      indicator: dateLabels.value.map(d => ({ name: d, max: allMax + 2 })), shape: 'polygon', splitNumber: 4,
      axisName: { color: TC, fontSize: 11 }, splitLine: { lineStyle: { color: GC } },
      splitArea: { areaStyle: { color: ['#fafafa', '#f5f7fa'] } },
      axisLine: { lineStyle: { color: GC } }
    },
    series: [{
      type: 'radar', data: keys.map(k => ({
        name: cardConfigs.find(c => c.key === k)?.label, value: weekly[k],
        symbol: 'circle', symbolSize: 4, lineStyle: { width: 2, color: clr[k] }, itemStyle: { color: clr[k] }, areaStyle: { color: clr[k] + '18' }
      }))
    }]
  }, true)
}

const onResize = () => { lineChart?.resize(); pieChart?.resize(); barChart?.resize(); radarChart?.resize() }

const init = async () => {
  try {
    const { data } = await queryData()
    const wKeys = ['user', 'notice', 'operLog', 'permission', 'frontUser', 'role', 'dictData', 'comQuery', 'rolePermission', 'userRole']
    wKeys.forEach(k => { if (data[k]) weekly[k] = data[k].map(i => i.count) })
    if (data.user && data.user.length > 0) dateLabels.value = data.user.map(i => i.dayOfWeek)
    Object.keys(tots).forEach(k => { const tKey = 'total' + k.charAt(0).toUpperCase() + k.slice(1); tots[k].value = data[tKey] || 0 })
    await nextTick()
    updateLine(); updatePie(); updateBar(); updateRadar()
  } catch (e) { console.error('数据中心加载失败:', e) }
}

onMounted(() => {
  lineChart = echarts.init(lineRef.value); pieChart = echarts.init(pieRef.value)
  barChart = echarts.init(barRef.value); radarChart = echarts.init(radarRef.value)
  init()
  window.addEventListener('resize', onResize)
})
onActivated(() => {
  // keep-alive 重新激活时，容器尺寸可能已变化，需要 resize
  nextTick(() => onResize())
})
onDeactivated(() => {
  // keep-alive 停用时移除监听，避免后台无意义触发
  window.removeEventListener('resize', onResize)
})
onBeforeUnmount(() => {
  window.removeEventListener('resize', onResize)
  lineChart?.dispose(); pieChart?.dispose(); barChart?.dispose(); radarChart?.dispose()
})
</script>

<style lang="scss" scoped>
@use "@/assets/css/admin/data-center";
</style>
