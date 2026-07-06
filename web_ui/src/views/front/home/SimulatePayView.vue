<template>
  <div class="alipay-cashier">
    <!-- 支付成功页 -->
    <div v-if="paySuccess" class="alipay-success-page">
      <div class="ali-header ali-header-success">
        <div class="ali-header-inner">
          <div class="ali-logo">
            <svg viewBox="0 0 32 32" width="26" height="26">
              <rect width="32" height="32" rx="6" fill="#1677FF"/>
              <text x="16" y="23" font-family="Arial" font-size="18" fill="white" text-anchor="middle" font-weight="bold">支</text>
            </svg>
            <span class="ali-brand-text">支付宝<small>ALIPAY</small></span>
            <span class="ali-divider-text">|</span>
            <span class="ali-page-title">交易完成</span>
          </div>
        </div>
      </div>
      <div class="success-content">
        <div class="success-box">
          <div class="success-icon-wrap">
            <svg viewBox="0 0 64 64" width="52" height="52">
              <circle cx="32" cy="32" r="30" fill="#52C41A"/>
              <path d="M20 32L28 40L44 24" stroke="white" stroke-width="4.5" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <h2 class="success-title">付款成功</h2>
          <p class="success-sub">{{ countdown }} 秒后页面将自动关闭</p>
          <div class="success-info">
            <div class="info-row"><span class="info-label">交易金额：</span><span class="info-val info-amount">{{ totalPrice }} 元</span></div>
            <div class="info-row"><span class="info-label">收款方：</span><span class="info-val">{{ shopName }}</span></div>
            <div class="info-row"><span class="info-label">交易号：</span><span class="info-val info-mono">{{ payNo || orderIds }}</span></div>
            <div class="info-row"><span class="info-label">付款时间：</span><span class="info-val">{{ payTime }}</span></div>
          </div>
          <div class="success-actions">
            <button class="ali-btn-primary" @click="goToOrders">查看订单</button>
            <button class="ali-btn-default" @click="goHome">关闭页面</button>
          </div>
        </div>
      </div>
      <div class="ali-footer">
        <span>ICP证：模拟环境</span>
      </div>
    </div>

    <!-- 支付宝收银台页面 -->
    <div v-else class="alipay-pay-page">
      <!-- 顶栏 -->
      <div class="ali-header">
        <div class="ali-header-inner">
          <div class="ali-logo">
            <svg viewBox="0 0 32 32" width="26" height="26">
              <rect width="32" height="32" rx="6" fill="#1677FF"/>
              <text x="16" y="23" font-family="Arial" font-size="18" fill="white" text-anchor="middle" font-weight="bold">支</text>
            </svg>
            <span class="ali-brand-text">支付宝<small>ALIPAY</small></span>
          </div>
          <div class="ali-header-right">
            <span>你好，欢迎使用支付宝付款！</span>
            <a href="javascript:void(0)">常见问题</a>
          </div>
        </div>
      </div>

      <!-- 订单信息条 -->
      <div class="ali-order-bar">
        <div class="ali-order-inner">
          <div class="order-bar-left">
            <p class="order-bar-tip">正在使用即时到账交易 <span class="tip-time">交易将在15分钟后关闭，请及时付款！</span></p>
            <p class="order-bar-detail">
              <span class="order-name">{{ shopName }}订单</span>
              <span class="order-payee">付款方：{{ payNo || orderIds }}</span>
            </p>
          </div>
          <div class="order-bar-right">
            <span class="order-price">{{ totalPrice }}</span>
            <span class="order-unit">元</span>
          </div>
        </div>
      </div>

      <!-- 主体内容 -->
      <div class="ali-main">
        <div class="ali-main-inner">
          <!-- 左侧扫码 -->
          <div class="ali-qr-area">
            <h3 class="area-title qr-title">扫码支付</h3>
            <div class="qr-wrapper">
              <canvas ref="qrCanvas" width="200" height="200" class="qr-canvas"></canvas>
              <div class="qr-center-logo">
                <svg viewBox="0 0 32 32" width="28" height="28">
                  <rect width="32" height="32" rx="6" fill="#1677FF"/>
                  <text x="16" y="22" font-family="Arial" font-size="16" fill="white" text-anchor="middle" font-weight="bold">支</text>
                </svg>
              </div>
            </div>
            <p class="qr-desc">使用手机支付宝扫码完成付款</p>
            <p class="qr-links">
              <a href="javascript:void(0)">手机支付宝下载</a>
              <span>|</span>
              <a href="javascript:void(0)">如何使用?</a>
            </p>
          </div>

          <!-- 分割线 -->
          <div class="ali-divider"></div>

          <!-- 右侧登录付款 -->
          <div class="ali-login-area">
            <h3 class="area-title">登录支付宝账户付款 <a class="register-link" href="javascript:void(0)">新用户注册</a></h3>
            <div class="login-form">
              <div class="form-group">
                <label>账户名：</label>
                <div class="form-input-wrap">
                  <input type="text" class="form-input" v-model="accountName" placeholder="邮箱/手机号"/>
                  <a class="form-link" href="javascript:void(0)">忘记账户名?</a>
                </div>
              </div>
              <div class="form-group">
                <label>支付密码：</label>
                <div class="form-input-wrap">
                  <input type="password" class="form-input" v-model="payPassword" placeholder="请输入支付密码"/>
                  <a class="form-link" href="javascript:void(0)">忘记密码?</a>
                </div>
              </div>
              <p class="form-tip">请输入账户用 <a href="javascript:void(0)">支付密码</a>，不是登录密码。</p>
              <button class="ali-submit-btn" :disabled="paying" @click="handlePay">
                {{ paying ? '付款中...' : '下一步' }}
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- 底部 -->
      <div class="ali-footer">
        <span>ICP证：模拟支付环境</span>
      </div>
      <div class="ali-footer-logos">
        <span class="logo-item">VeriSign</span>
        <span class="logo-item">PCI</span>
        <span class="logo-item">VERIFIED</span>
        <span class="logo-item">MasterCard</span>
        <span class="logo-item">JCB</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * 模拟支付宝收银台页面
 * 通过 URL 参数接收订单信息，模拟扫码/登录付款流程
 * 参数: payNo(支付单号), totalPrice(金额), orderIds(订单ID), type(new/repay), shopName(商户名)
 */
import { ref, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'

const route = useRoute()
const payNo = ref(route.query.payNo as string || '')
const totalPrice = ref(route.query.totalPrice as string || '0.00')
const orderIds = ref(route.query.orderIds as string || '')
const shopName = ref(route.query.shopName as string || '东神脚手架商城')

const accountName = ref('user@sandbox.com')
const payPassword = ref('111111')
const paying = ref(false)
const paySuccess = ref(false)
const payTime = ref('')
const countdown = ref(5)
const qrCanvas = ref<HTMLCanvasElement | null>(null)
let countdownTimer: ReturnType<typeof setInterval> | null = null

// 生成模拟二维码（基于 Canvas 绘制高清晰度像素图案）
const drawQrCode = () => {
  const canvas = qrCanvas.value
  if (!canvas) return
  const ctx = canvas.getContext('2d')!
  const size = 200
  const moduleCount = 25
  const moduleSize = size / moduleCount

  ctx.fillStyle = '#ffffff'
  ctx.fillRect(0, 0, size, size)

  // 伪随机种子（基于订单号生成一致的图案）
  let seed = 0
  const seedStr = payNo.value || orderIds.value || 'default'
  for (let i = 0; i < seedStr.length; i++) seed += seedStr.charCodeAt(i)
  const pseudoRandom = () => { seed = (seed * 16807) % 2147483647; return (seed - 1) / 2147483646 }

  ctx.fillStyle = '#000000'

  // 绘制定位图案（三个角）
  const drawFinder = (x: number, y: number) => {
    ctx.fillRect(x * moduleSize, y * moduleSize, 7 * moduleSize, 7 * moduleSize)
    ctx.fillStyle = '#ffffff'
    ctx.fillRect((x + 1) * moduleSize, (y + 1) * moduleSize, 5 * moduleSize, 5 * moduleSize)
    ctx.fillStyle = '#000000'
    ctx.fillRect((x + 2) * moduleSize, (y + 2) * moduleSize, 3 * moduleSize, 3 * moduleSize)
  }
  drawFinder(0, 0)
  drawFinder(moduleCount - 7, 0)
  drawFinder(0, moduleCount - 7)

  // 绘制数据区域
  for (let row = 0; row < moduleCount; row++) {
    for (let col = 0; col < moduleCount; col++) {
      // 跳过定位图案区域和中心 Logo 区域
      if ((row < 8 && col < 8) || (row < 8 && col > moduleCount - 9) || (row > moduleCount - 9 && col < 8)) continue
      if (row >= 10 && row <= 14 && col >= 10 && col <= 14) continue

      if (pseudoRandom() > 0.55) {
        ctx.fillRect(col * moduleSize, row * moduleSize, moduleSize, moduleSize)
      }
    }
  }
}

// 模拟支付流程
const handlePay = () => {
  if (!accountName.value || !payPassword.value) {
    ElMessage.warning('请输入账户名和支付密码')
    return
  }
  paying.value = true

  setTimeout(() => {
    paySuccess.value = true
    payTime.value = new Date().toLocaleString('zh-CN')
    paying.value = false
    startCountdown()
  }, 1500)
}

// 支付成功倒计时
const startCountdown = () => {
  countdown.value = 5
  countdownTimer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(countdownTimer!)
      goHome()
    }
  }, 1000)
}

// 跳转订单页
const goToOrders = () => {
  if (countdownTimer) clearInterval(countdownTimer)
  window.close()
}

// 关闭页面
const goHome = () => {
  if (countdownTimer) clearInterval(countdownTimer)
  window.close()
}

onMounted(() => {
  drawQrCode()
})

onUnmounted(() => {
  if (countdownTimer) clearInterval(countdownTimer)
})
</script>

<style scoped>
.alipay-cashier {
  min-height: 100vh;
  background: #fff;
  font-family: "Microsoft YaHei", "PingFang SC", "Helvetica Neue", Arial, sans-serif;
}

/* ===== 头部 ===== */
.ali-header {
  background: #fff;
  border-bottom: 2px solid #1677FF;
  height: 70px;
  display: flex;
  align-items: center;
}

.ali-header-inner {
  max-width: 1000px;
  width: 100%;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.ali-logo {
  display: flex;
  align-items: center;
  gap: 8px;
}

.ali-brand-text {
  font-size: 22px;
  font-weight: 700;
  color: #1677FF;
  letter-spacing: 2px;
}

.ali-brand-text small {
  font-size: 11px;
  font-weight: 400;
  color: #999;
  margin-left: 6px;
  letter-spacing: 1px;
}

.ali-divider-text {
  color: #ddd;
  font-size: 16px;
  margin: 0 10px;
}

.ali-page-title {
  font-size: 14px;
  color: #666;
}

.ali-header-right {
  font-size: 12px;
  color: #666;
  display: flex;
  gap: 16px;
}

.ali-header-right a {
  color: #1677FF;
  text-decoration: none;
}

/* ===== 订单信息条 ===== */
.ali-order-bar {
  background: #EBF4FF;
  border-bottom: 1px solid #D4E8FF;
  padding: 12px 0;
}

.ali-order-inner {
  max-width: 1000px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.order-bar-tip {
  font-size: 12px;
  color: #666;
  margin-bottom: 4px;
}

.tip-time { color: #FA5151; }

.order-bar-detail {
  font-size: 13px;
  color: #333;
}

.order-name {
  font-weight: 500;
  margin-right: 20px;
}

.order-payee {
  color: #888;
  font-size: 12px;
}

.order-bar-right {
  display: flex;
  align-items: baseline;
  gap: 2px;
}

.order-price {
  font-size: 28px;
  font-weight: 700;
  color: #FA5151;
}

.order-unit {
  font-size: 14px;
  color: #FA5151;
}

/* ===== 主体区域 ===== */
.ali-main { padding: 40px 0 60px; }

.ali-main-inner {
  max-width: 1000px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  gap: 0;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
}

/* 左侧扫码 */
.ali-qr-area {
  flex: 1;
  padding: 30px 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.area-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 24px;
  align-self: flex-start;
}

.qr-title { color: #1677FF; }

.qr-wrapper {
  width: 200px;
  height: 200px;
  position: relative;
  border: 2px solid #e8e8e8;
  border-radius: 4px;
  padding: 4px;
  margin-bottom: 16px;
}

.qr-canvas {
  width: 100%;
  height: 100%;
  display: block;
}

.qr-center-logo {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: white;
  padding: 4px;
  border-radius: 6px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
}

.qr-desc {
  font-size: 13px;
  color: #666;
  margin-bottom: 8px;
}

.qr-links {
  font-size: 12px;
  color: #999;
}

.qr-links a {
  color: #1677FF;
  text-decoration: none;
}

.qr-links span {
  margin: 0 8px;
  color: #ddd;
}

/* 分割线 */
.ali-divider {
  width: 1px;
  background: #e8e8e8;
  margin: 20px 0;
}

/* 右侧登录 */
.ali-login-area {
  flex: 1;
  padding: 30px 40px;
}

.register-link {
  font-size: 13px;
  font-weight: 400;
  color: #1677FF;
  text-decoration: none;
  margin-left: 12px;
}

.login-form { margin-top: 8px; }

.form-group { margin-bottom: 18px; }

.form-group label {
  display: block;
  font-size: 13px;
  color: #333;
  margin-bottom: 6px;
}

.form-input-wrap { position: relative; }

.form-input {
  width: 100%;
  height: 38px;
  border: 1px solid #1677FF;
  border-radius: 2px;
  padding: 0 12px;
  font-size: 14px;
  outline: none;
  box-sizing: border-box;
}

.form-input:focus {
  border-color: #1677FF;
  box-shadow: 0 0 0 2px rgba(22, 119, 255, 0.1);
}

.form-link {
  position: absolute;
  right: 0;
  top: -20px;
  font-size: 12px;
  color: #1677FF;
  text-decoration: none;
}

.form-tip {
  font-size: 12px;
  color: #999;
  margin-bottom: 20px;
}

.form-tip a {
  color: #FA5151;
  text-decoration: none;
}

.ali-submit-btn {
  width: 100%;
  height: 42px;
  background: linear-gradient(to bottom, #1890FF, #1677FF);
  border: 1px solid #1566D9;
  border-radius: 4px;
  color: white;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.ali-submit-btn:hover:not(:disabled) {
  background: linear-gradient(to bottom, #40A0FF, #1890FF);
}

.ali-submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* ===== 底部 ===== */
.ali-footer {
  text-align: center;
  padding: 20px 0 10px;
  font-size: 12px;
  color: #bbb;
}

.ali-footer-logos {
  text-align: center;
  padding: 10px 0 30px;
  display: flex;
  justify-content: center;
  gap: 24px;
}

.logo-item {
  font-size: 11px;
  color: #bbb;
  padding: 4px 12px;
  border: 1px solid #e8e8e8;
  border-radius: 3px;
  font-weight: 600;
  font-family: "Georgia", serif;
}

/* ===== 成功页 ===== */
.ali-header-success { border-bottom-color: #52C41A; }

.success-content {
  max-width: 600px;
  margin: 60px auto;
  padding: 0 20px;
}

.success-box { text-align: center; }

.success-icon-wrap { margin-bottom: 16px; }

.success-title {
  font-size: 22px;
  color: #333;
  font-weight: 600;
  margin-bottom: 8px;
}

.success-sub {
  font-size: 13px;
  color: #999;
  margin-bottom: 32px;
}

.success-info {
  text-align: left;
  background: #f9f9f9;
  border: 1px solid #f0f0f0;
  border-radius: 4px;
  padding: 20px 28px;
  margin-bottom: 32px;
}

.info-row {
  display: flex;
  padding: 8px 0;
  font-size: 13px;
}

.info-label {
  color: #888;
  width: 90px;
  flex-shrink: 0;
}

.info-val { color: #333; }

.info-amount {
  color: #FA5151;
  font-size: 16px;
  font-weight: 700;
}

.info-mono {
  font-family: 'Consolas', 'SF Mono', monospace;
  font-size: 12px;
  word-break: break-all;
}

.success-actions {
  display: flex;
  justify-content: center;
  gap: 16px;
}

.ali-btn-primary {
  height: 36px;
  padding: 0 28px;
  background: #1677FF;
  border: 1px solid #1566D9;
  border-radius: 4px;
  color: white;
  font-size: 14px;
  cursor: pointer;
}

.ali-btn-primary:hover { background: #1890FF; }

.ali-btn-default {
  height: 36px;
  padding: 0 28px;
  background: white;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  color: #333;
  font-size: 14px;
  cursor: pointer;
}

.ali-btn-default:hover { border-color: #1677FF; color: #1677FF; }

/* 响应式 */
@media (max-width: 768px) {
  .ali-main-inner { flex-direction: column; }
  .ali-divider { width: 100%; height: 1px; margin: 0; }
  .ali-qr-area, .ali-login-area { padding: 20px; }
  .ali-header-right { display: none; }
}
</style>
