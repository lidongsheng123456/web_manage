<template>
  <div class="home-container">
    <!-- 头部横幅 (Hero Section) -->
    <section class="hero-section" ref='vantaRef'>
      <div class="hero-overlay"></div>
      <div class="hero-content fade-in-up">
        <div class="ai-badge">🚀 AI 驱动</div>
        <h1 class="hero-title">
          <span class="gradient-text">东神脚手架</span>
        </h1>
        <p class="hero-subtitle">AI 自动化开发，1小时完成毕业设计！</p>
        <p class="hero-description">
          基于 <span class="highlight">AI 工作流</span> 与 <span class="highlight">RBAC 权限体系</span>，
          智能化代码生成，让创意瞬间落地。效率提升 <strong>10 倍</strong>！
        </p>
        <div class="hero-buttons">
          <a href="https://gitee.com/li-dongshenger/web_manage" target="_blank" class="btn btn-primary btn-glow">
            <span>🤖 体验 AI 开发</span>
          </a>
          <a href="https://gitee.com/li-dongshenger/web_manage" target="_blank" class="btn btn-secondary glass-effect">
            <span>� 了解详情</span>
          </a>
        </div>
      </div>

      <!-- AI Neural Network Background -->
      <div class="neural-network">
        <svg viewBox="0 0 400 300" class="neural-svg">
          <defs>
            <linearGradient id="nodeGradient" x1="0%" y1="0%" x2="100%" y2="100%">
              <stop offset="0%" style="stop-color:#2563eb;stop-opacity:1" />
              <stop offset="100%" style="stop-color:#10b981;stop-opacity:1" />
            </linearGradient>
          </defs>
          <!-- Nodes -->
          <circle cx="50" cy="50" r="8" fill="url(#nodeGradient)" class="pulse-node" />
          <circle cx="150" cy="100" r="6" fill="url(#nodeGradient)" class="pulse-node" />
          <circle cx="250" cy="80" r="7" fill="url(#nodeGradient)" class="pulse-node" />
          <circle cx="350" cy="120" r="5" fill="url(#nodeGradient)" class="pulse-node" />
          <circle cx="100" cy="200" r="6" fill="url(#nodeGradient)" class="pulse-node" />
          <circle cx="300" cy="180" r="8" fill="url(#nodeGradient)" class="pulse-node" />

          <!-- Connections -->
          <path d="M50,50 L150,100" stroke="rgba(37,99,235,0.3)" stroke-width="2" class="connection" />
          <path d="M150,100 L250,80" stroke="rgba(37,99,235,0.3)" stroke-width="2" class="connection" />
          <path d="M250,80 L350,120" stroke="rgba(37,99,235,0.3)" stroke-width="2" class="connection" />
          <path d="M100,200 L300,180" stroke="rgba(37,99,235,0.3)" stroke-width="2" class="connection" />
          <path d="M150,100 L100,200" stroke="rgba(37,99,235,0.3)" stroke-width="2" class="connection" />
          <path d="M250,80 L300,180" stroke="rgba(37,99,235,0.3)" stroke-width="2" class="connection" />
        </svg>
      </div>

      <!-- 滚动提示 -->
      <div class="scroll-indicator">
        <div class="mouse">
          <div class="wheel"></div>
        </div>
        <span class="arrow"></span>
      </div>
    </section>

    <!-- 核心优势 (Stats Banner) -->
    <div class="stats-banner-wrapper container">
      <div class="stats-banner glass-card floating">
        <div class="stat-item">
          <div class="stat-icon floating-icon" style="animation-delay: 0s">⚡</div>
          <div class="stat-info">
            <h3>10x 效率提升</h3>
            <p>AI 自动化开发</p>
          </div>
        </div>
        <div class="stat-divider"></div>
        <div class="stat-item">
          <div class="stat-icon floating-icon" style="animation-delay: 0.5s">🤖</div>
          <div class="stat-info">
            <h3>零手动操作</h3>
            <p>全栈代码生成</p>
          </div>
        </div>
        <div class="stat-divider"></div>
        <div class="stat-item">
          <div class="stat-icon floating-icon" style="animation-delay: 1s">🎨</div>
          <div class="stat-info">
            <h3>液态玻璃 UI</h3>
            <p>AI 设计现代化</p>
          </div>
        </div>
        <div class="stat-divider"></div>
        <div class="stat-item">
          <div class="stat-icon floating-icon" style="animation-delay: 1.5s">🔐</div>
          <div class="stat-info">
            <h3>企业级权限</h3>
            <p>RBAC + Sa-Token</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 项目简介 & 功能 (Features - ZigZag Layout) -->
    <section class="section features-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">AI 赋能，<span class="gradient-text">全栈自动化</span></h2>
          <p class="section-desc">零手动操作，AI 智能生成完整的全栈应用，让开发变得前所未有的简单</p>
        </div>

        <!-- Feature 1 -->
        <div class="feature-row">
          <div class="feature-content">
            <div class="feature-tag">AI Backend</div>
            <h3>AI 自动代码生成</h3>
            <p>
              零手动编码，AI 智能生成完整的后端架构。包括 Domain 实体、Mapper 接口、Service 业务逻辑、Controller API 接口。
              集成 <strong>Spring Boot</strong> + <strong>MyBatis</strong> + <strong>Sa-Token</strong>，企业级权限控制一键配置。
            </p>
            <ul class="feature-list">
              <li>🤖 AI 生成三层架构代码</li>
              <li>� 自动配置 RBAC 权限</li>
              <li>📊 智能数据库映射</li>
            </ul>
          </div>
          <div class="feature-visual code-card-wrapper">
            <div class="code-card">
              <div class="code-header">
                <span class="dot red"></span>
                <span class="dot yellow"></span>
                <span class="dot green"></span>
              </div>
              <div class="code-body">
                <pre><code>@SaCheckPermission("system:user:add")
@PostMapping("/add")
public R&lt;Void&gt; add(@RequestBody User user) {
    userService.save(user);
    return R.ok();
}</code></pre>
              </div>
            </div>
          </div>
        </div>

        <!-- Feature 2 -->
        <div class="feature-row reverse">
          <div class="feature-content">
            <div class="feature-tag">AI Frontend</div>
            <h3>液态玻璃 UI 自动化</h3>
            <p>
              AI 智能生成现代化液态玻璃风格界面。基于 <strong>Vue 3</strong> + <strong>Element Plus</strong>，
              自动创建响应式布局、美观卡片、流畅动画。Redis 缓存优化，秒级数据加载。
            </p>
            <ul class="feature-list">
              <li>🎨 AI 生成液态玻璃设计</li>
              <li>📱 自动响应式布局</li>
              <li>⚡ Redis 缓存加速</li>
            </ul>
          </div>
          <div class="feature-visual ui-card-wrapper">
            <div class="ui-card glass-card">
              <div class="ui-header">Dashboard</div>
              <div class="ui-body">
                <div class="skeleton-chart"></div>
                <div class="skeleton-list">
                  <span></span><span></span><span></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 技术栈 (Tech Stack - Grid Cards) -->
    <section class="section tech-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">AI 驱动，<span class="gradient-text">技术栈革新</span></h2>
          <p class="section-desc">主流技术栈 + AI 智能化，打造企业级应用开发平台</p>
        </div>

        <div class="tech-tabs">
          <div class="tech-group">
            <h3>后端 Backend</h3>
            <div class="tech-grid">
              <div class="tech-card" v-for="(item, index) in backTechs" :key="'b' + index">
                <div class="tech-icon">{{ item.icon }}</div>
                <div class="tech-info">
                  <h4>{{ item.name }}</h4>
                  <span>{{ item.version }}</span>
                </div>
              </div>
            </div>
          </div>

          <div class="tech-group">
            <h3>前端 Frontend</h3>
            <div class="tech-grid">
              <div class="tech-card" v-for="(item, index) in frontTechs" :key="'f' + index">
                <div class="tech-icon">{{ item.icon }}</div>
                <div class="tech-info">
                  <h4>{{ item.name }}</h4>
                  <span>{{ item.version }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- AI 工作流 (AI Workflow) -->
    <section class="section ai-workflow-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">⚡ <span class="gradient-text">AI 工作流</span>，1小时完成毕业设计</h2>
          <p class="section-desc">自动化代码生成，严格按照 7 步骤执行，效率提升 10 倍！</p>
        </div>
        <div class="workflow-grid">
          <div class="workflow-step">
            <div class="step-number">01</div>
            <h3>数据库设计</h3>
            <p>AI 自动生成 SQL 表结构</p>
          </div>
          <div class="workflow-step">
            <div class="step-number">02</div>
            <h3>后端基础层</h3>
            <p>AI 自动生成 Domain/Mapper/Service</p>
          </div>
          <div class="workflow-step">
            <div class="step-number">03</div>
            <h3>后端接口层</h3>
            <p>AI 自动生成 Admin/User Controller</p>
          </div>
          <div class="workflow-step">
            <div class="step-number">04</div>
            <h3>前端后台管理</h3>
            <p>AI 自动生成管理页面</p>
          </div>
          <div class="workflow-step">
            <div class="step-number">05</div>
            <h3>前台用户页面</h3>
            <p>AI 自动生成液态玻璃风格页面</p>
          </div>
          <div class="workflow-step">
            <div class="step-number">06</div>
            <h3>路由与权限配置</h3>
            <p>AI 自动生成配置</p>
          </div>
          <div class="workflow-step">
            <div class="step-number">07</div>
            <h3>自检清单</h3>
            <p>AI 自动检查代码质量</p>
          </div>
        </div>
        <div class="workflow-promise">
          <p>🎯 <strong>承诺</strong>：1小时内必能完成一个完整的业务模块！</p>
        </div>
      </div>
    </section>

    <!-- 联系与页脚 -->
    <footer class="footer-section">
      <div class="container">
        <div class="footer-content">
          <div class="footer-left">
            <h3>东神脚手架</h3>
            <p>AI 驱动的全栈开发平台，为学生和开发者提供最先进的毕业设计解决方案。</p>
            <div class="contact-card glass-effect-sm">
              <p>👨‍💻 开发者：李东升</p>
              <p>📞 电话：19976898057</p>
            </div>
          </div>
          <div class="footer-right">
            <a href="https://pan.baidu.com/s/1zU8TWzNMtv5kNSL5H9Q5_w?pwd=ys73" target="_blank" class="download-btn">
              ⬇️ 下载运行环境 (百度网盘)
            </a>
            <p class="copyright">&copy; 2025 东神脚手架. All rights reserved.</p>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import * as THREE from 'three';
import Clouds from "vanta/src/vanta.clouds";
import { onBeforeUnmount, onMounted, ref } from "vue";

// Vanta 相关
const vantaRef = ref(null)
let vantaEffect = null

// 数据配置
const backTechs = [
  { name: 'Spring Boot', version: '3.0.6', icon: '🍃' },
  { name: 'JDK', version: '17.0.13', icon: '☕' },
  { name: 'MySQL', version: '8.0.36', icon: '🐬' },
  { name: 'Redis', version: '5.0.14', icon: '⚡' },
  { name: 'Sa-Token', version: '1.38.0', icon: '🛡️' },
  { name: 'MyBatis', version: '3.0.3', icon: '🔋' },
  { name: 'HttpClient', version: '4.5.14', icon: '📡' },
  { name: 'Apache POI', version: '5.2.3', icon: '📄' },
  { name: 'Hutool', version: '5.8.26', icon: '🛠️' },
  { name: 'Knife4j', version: '4.3.0', icon: '🔪' },
]

const frontTechs = [
  { name: 'Vue 3', version: '3.2.13', icon: '💚' },
  { name: 'Node.js', version: '18.19.0', icon: '🐢' },
  { name: 'Element Plus', version: '2.9.3', icon: '💠' },
  { name: 'Vite', version: 'Latest', icon: '⚡' },
  { name: 'Axios', version: '1.7.9', icon: '📡' },
  { name: 'Vue Router', version: '4.0.3', icon: '🛣️' },
]

// 加载vanta3D动画渲染
const loadVanta = () => {
  if (vantaRef.value) {
    vantaEffect = Clouds({
      el: vantaRef.value,
      THREE: THREE,
      mouseControls: true,
      touchControls: true,
      gyroControls: false,
      minHeight: 200.00,
      minWidth: 200.00
    })
  }
}

onMounted(() => {
  loadVanta()

  // 简单的滚动动画观察者
  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible')
      }
    })
  }, { threshold: 0.1 })

  document.querySelectorAll('.section, .stats-banner, .workflow-step').forEach(el => {
    el.classList.add('animate-on-scroll')
    observer.observe(el)
  })
})

onBeforeUnmount(() => {
  if (vantaEffect) {
    vantaEffect.destroy()
    vantaEffect = null
  }
})
</script>

<style scoped>
/* ================= CSS Variables ================= */
:root {
  --primary-color: #b7e0f0;
  --secondary-color: #5dade2;
  --accent-color: #3498db;
  --text-primary: #2c3e50;
  --text-secondary: #5d6d7e;
  --bg-light: #f8f9fa;
  --glass-bg: rgba(255, 255, 255, 0.9);
  --glass-border: rgba(183, 224, 240, 0.3);
  --shadow-sm: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  --shadow-lg: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
  --radius-lg: 16px;
  --radius-xl: 24px;
}

/* ================= Global Reset & Utils ================= */
.home-container {
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
  color: #2d3748;
  background: linear-gradient(180deg, #e8f4fd 0%, #f0f7ff 30%, #f8fafc 60%, #eef5fc 100%);
  overflow-x: hidden;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
}

.gradient-text {
  background: linear-gradient(270deg, #2563eb, #10b981, #06b6d4, #8b5cf6, #2563eb);
  background-size: 300% 300%;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  animation: gradient-flow 6s ease infinite;
}

@keyframes gradient-flow {
  0% {
    background-position: 0% 50%;
  }

  50% {
    background-position: 100% 50%;
  }

  100% {
    background-position: 0% 50%;
  }
}

.glass-card {
  background: rgba(255, 255, 255, 0.35);
  backdrop-filter: blur(24px) saturate(1.8);
  -webkit-backdrop-filter: blur(24px) saturate(1.8);
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow:
    0 8px 32px rgba(31, 38, 135, 0.1),
    inset 0 1px 0 rgba(255, 255, 255, 0.7),
    inset 0 -1px 0 rgba(255, 255, 255, 0.15);
  border-radius: 24px;
}

.section {
  padding: 100px 0;
}

.section-header {
  text-align: center;
  margin-bottom: 80px;
}

.section-title {
  font-size: 2.5rem;
  font-weight: 800;
  margin-bottom: 16px;
  color: #1a202c;
  letter-spacing: -0.02em;
}

.section-desc {
  font-size: 1.125rem;
  color: #718096;
  max-width: 600px;
  margin: 0 auto;
}

/* ================= Hero Section ================= */
.hero-section {
  height: 100vh;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  overflow: hidden;
}

.hero-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: transparent;
  z-index: 1;
  pointer-events: none;
}

.hero-content {
  position: relative;
  z-index: 2;
  max-width: 800px;
  padding: 0 20px;
}

.ai-badge {
  display: inline-block;
  padding: 8px 16px;
  background: linear-gradient(135deg, #2563eb 0%, #10b981 100%);
  color: white;
  border-radius: 20px;
  font-size: 0.9rem;
  font-weight: 600;
  margin-bottom: 16px;
  box-shadow: 0 4px 12px rgba(37, 99, 235, 0.3);
}

.neural-network {
  position: absolute;
  top: 50%;
  right: 10%;
  transform: translateY(-50%);
  z-index: 1;
  opacity: 0.6;
}

.neural-svg {
  width: 300px;
  height: 200px;
}

.pulse-node {
  animation: node-pulse 3s ease-in-out infinite;
}

@keyframes node-pulse {

  0%,
  100% {
    r: 8;
    opacity: 0.8;
  }

  50% {
    r: 12;
    opacity: 1;
  }
}

.connection {
  stroke-dasharray: 5, 5;
  animation: flow 2s linear infinite;
}

@keyframes flow {
  0% {
    stroke-dashoffset: 0;
  }

  100% {
    stroke-dashoffset: -10;
  }
}

.hero-title {
  font-size: 4.5rem;
  font-weight: 900;
  line-height: 1.1;
  margin-bottom: 24px;
  letter-spacing: -0.03em;
  text-shadow: 0 2px 8px rgba(255, 255, 255, 0.6);
}

.hero-subtitle {
  font-size: 1.75rem;
  font-weight: 600;
  color: #2d3748;
  margin-bottom: 16px;
  text-shadow: 0 1px 6px rgba(255, 255, 255, 0.7);
}

.hero-description {
  font-size: 1.15rem;
  color: #4a5568;
  margin-bottom: 40px;
  line-height: 1.7;
  text-shadow: 0 1px 4px rgba(255, 255, 255, 0.8);
}

.highlight {
  color: var(--primary-color);
  font-weight: 600;
}

/* Buttons */
.hero-buttons {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.btn {
  padding: 14px 36px;
  border-radius: 50px;
  font-weight: 700;
  font-size: 1.05rem;
  text-decoration: none;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  display: inline-flex;
  align-items: center;
}

.btn-primary {
  background: linear-gradient(135deg, #2563eb 0%, #10b981 100%);
  color: white;
  border: none;
  box-shadow: 0 4px 15px rgba(37, 99, 235, 0.4);
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(37, 99, 235, 0.5);
}

.btn-secondary {
  background: rgba(255, 255, 255, 0.8);
  color: #4a5568;
  backdrop-filter: blur(4px);
  border: 1px solid rgba(255, 255, 255, 0.6);
}

.btn-secondary:hover {
  background: white;
  color: #667eea;
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
}

/* Scroll Indicator */
.scroll-indicator {
  position: absolute;
  bottom: 40px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
  opacity: 0.7;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.mouse {
  width: 26px;
  height: 40px;
  border: 2px solid #718096;
  border-radius: 20px;
  position: relative;
}

.wheel {
  width: 4px;
  height: 8px;
  background-color: #718096;
  border-radius: 2px;
  position: absolute;
  top: 6px;
  left: 50%;
  transform: translateX(-50%);
  animation: scroll 1.5s infinite;
}

@keyframes scroll {
  0% {
    transform: translate(-50%, 0);
    opacity: 1;
  }

  100% {
    transform: translate(-50%, 15px);
    opacity: 0;
  }
}

/* ================= Stats Banner ================= */
.stats-banner-wrapper {
  margin-top: 40px;
  position: relative;
  z-index: 10;
}

.stats-banner {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 32px 40px;
  background: rgba(255, 255, 255, 0.4);
  backdrop-filter: blur(24px) saturate(1.8);
  -webkit-backdrop-filter: blur(24px) saturate(1.8);
  border: 1px solid rgba(255, 255, 255, 0.55);
  box-shadow:
    0 12px 40px rgba(31, 38, 135, 0.1),
    inset 0 1px 0 rgba(255, 255, 255, 0.7),
    inset 0 -1px 0 rgba(255, 255, 255, 0.15);
  border-radius: 24px;
}

.floating {
  animation: gentle-float 6s ease-in-out infinite;
}

.floating-icon {
  animation: floating 4s ease-in-out infinite;
  font-size: 2.5rem;
}

@keyframes gentle-float {

  0%,
  100% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(-5px);
  }
}

@keyframes floating {

  0%,
  100% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(-10px);
  }
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  font-size: 2.5rem;
}

.stat-info h3 {
  font-size: 1.1rem;
  font-weight: 700;
  color: #2d3748;
  margin: 0;
}

.stat-info p {
  font-size: 0.9rem;
  color: #718096;
  margin: 4px 0 0 0;
}

.stat-divider {
  width: 1px;
  height: 40px;
  background-color: #e2e8f0;
}

/* ================= Features Section ================= */
.feature-row {
  display: flex;
  align-items: center;
  gap: 80px;
  margin-bottom: 120px;
}

.feature-row.reverse {
  flex-direction: row-reverse;
}

.feature-content {
  flex: 1;
}

.feature-visual {
  flex: 1;
  display: flex;
  justify-content: center;
}

.feature-tag {
  display: inline-block;
  padding: 6px 18px;
  background: rgba(102, 126, 234, 0.1);
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  color: #667eea;
  border-radius: 20px;
  font-weight: 600;
  font-size: 0.85rem;
  margin-bottom: 20px;
  border: 1px solid rgba(102, 126, 234, 0.15);
}

.feature-content h3 {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 20px;
  color: #1a202c;
}

.feature-content p {
  font-size: 1.1rem;
  color: #4a5568;
  line-height: 1.8;
  margin-bottom: 30px;
}

.feature-list {
  list-style: none;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 12px;
}

.feature-list li {
  font-size: 1rem;
  color: #2d3748;
  font-weight: 500;
}

/* Code Card Visual */
.code-card {
  background: rgba(30, 30, 30, 0.85);
  backdrop-filter: blur(20px) saturate(1.4);
  -webkit-backdrop-filter: blur(20px) saturate(1.4);
  border-radius: 16px;
  padding: 20px;
  width: 100%;
  max-width: 480px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  box-shadow:
    0 20px 40px rgba(0, 0, 0, 0.25),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  transform: perspective(1000px) rotateY(-5deg) rotateX(2deg);
  transition: transform 0.3s ease;
}

.code-card:hover {
  transform: perspective(1000px) rotateY(0) rotateX(0);
}

.code-header {
  display: flex;
  gap: 8px;
  margin-bottom: 16px;
}

.dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
}

.red {
  background: #ff5f56;
}

.yellow {
  background: #ffbd2e;
}

.green {
  background: #27c93f;
}

.code-body pre {
  margin: 0;
  color: #d4d4d4;
  font-family: 'Fira Code', monospace;
  font-size: 0.85rem;
  overflow-x: auto;
}

/* UI Card Visual */
.ui-card {
  width: 100%;
  max-width: 450px;
  padding: 24px;
  background: rgba(255, 255, 255, 0.4);
  backdrop-filter: blur(20px) saturate(1.6);
  -webkit-backdrop-filter: blur(20px) saturate(1.6);
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow:
    0 8px 32px rgba(31, 38, 135, 0.1),
    inset 0 1px 0 rgba(255, 255, 255, 0.6);
  border-radius: 20px;
  transform: perspective(1000px) rotateY(5deg) rotateX(2deg);
  transition: transform 0.3s ease;
}

.ui-card:hover {
  transform: perspective(1000px) rotateY(0) rotateX(0);
}

.ui-header {
  font-weight: 700;
  margin-bottom: 16px;
  border-bottom: 1px solid #edf2f7;
  padding-bottom: 12px;
  color: #2d3748;
}

.skeleton-chart {
  height: 120px;
  background: rgba(247, 250, 252, 0.5);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
  border-radius: 12px;
  margin-bottom: 16px;
}

.skeleton-list span {
  display: block;
  height: 12px;
  background: rgba(237, 242, 247, 0.6);
  margin-bottom: 8px;
  border-radius: 6px;
}

.skeleton-list span:nth-child(1) {
  width: 80%;
}

.skeleton-list span:nth-child(2) {
  width: 60%;
}

.skeleton-list span:nth-child(3) {
  width: 40%;
}


/* ================= Tech Section ================= */
.tech-section {
  background: rgba(255, 255, 255, 0.3);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
}

.tech-tabs {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 60px;
}

.tech-group h3 {
  font-size: 1.5rem;
  margin-bottom: 30px;
  color: #2d3748;
  position: relative;
  display: inline-block;
}

.tech-group h3::after {
  content: '';
  position: absolute;
  bottom: -8px;
  left: 0;
  width: 40px;
  height: 3px;
  background: #667eea;
  border-radius: 2px;
}

.tech-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
  gap: 20px;
}

.tech-card {
  background: rgba(255, 255, 255, 0.4);
  backdrop-filter: blur(16px) saturate(1.6);
  -webkit-backdrop-filter: blur(16px) saturate(1.6);
  padding: 20px;
  border-radius: 16px;
  text-align: center;
  transition: all 0.3s ease;
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow:
    0 4px 16px rgba(31, 38, 135, 0.06),
    inset 0 1px 0 rgba(255, 255, 255, 0.6);
}

.tech-card:hover {
  background: rgba(255, 255, 255, 0.6);
  border-color: rgba(255, 255, 255, 0.7);
  transform: translateY(-5px);
  box-shadow:
    0 12px 28px rgba(31, 38, 135, 0.12),
    inset 0 1px 0 rgba(255, 255, 255, 0.8);
}

.tech-icon {
  font-size: 2.5rem;
  margin-bottom: 12px;
}

.tech-info h4 {
  margin: 0;
  font-size: 1rem;
  color: #2d3748;
}

.tech-info span {
  font-size: 0.8rem;
  color: #718096;
}


/* ================= Guide Section ================= */
.guide-box {
  background: linear-gradient(135deg, #2563eb 0%, #10b981 100%);
  border-radius: 24px;
  padding: 60px;
  color: white;
  position: relative;
  overflow: hidden;
}

.guide-box::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%23ffffff' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
}

.guide-header {
  text-align: center;
  margin-bottom: 50px;
  position: relative;
  z-index: 2;
}

.guide-header h2 {
  color: white;
  font-size: 2.5rem;
  margin-bottom: 12px;
}

.guide-header p {
  color: rgba(255, 255, 255, 0.9);
  font-size: 1.1rem;
}

.steps-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
  position: relative;
  z-index: 2;
}

.step-item {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  padding: 30px;
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  transition: transform 0.3s;
}

.step-item:hover {
  transform: translateY(-5px);
  background: rgba(255, 255, 255, 0.15);
}

.step-num {
  font-size: 3rem;
  font-weight: 900;
  opacity: 0.3;
  margin-bottom: 10px;
}

.step-item h3 {
  margin: 0 0 10px 0;
  font-size: 1.25rem;
}

.step-item p {
  margin: 0;
  opacity: 0.8;
  line-height: 1.6;
}

/* ================= AI Workflow Section ================= */
.ai-workflow-section {
  background: linear-gradient(135deg, rgba(37, 99, 235, 0.04) 0%, rgba(16, 185, 129, 0.04) 100%);
}

.workflow-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.workflow-step {
  background: rgba(255, 255, 255, 0.35);
  backdrop-filter: blur(20px) saturate(1.6);
  -webkit-backdrop-filter: blur(20px) saturate(1.6);
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow:
    0 4px 20px rgba(31, 38, 135, 0.08),
    inset 0 1px 0 rgba(255, 255, 255, 0.6),
    inset 0 -1px 0 rgba(255, 255, 255, 0.1);
  padding: 30px 20px;
  border-radius: 20px;
  text-align: center;
  transition: all 0.3s ease;
}

.workflow-step:hover {
  transform: translateY(-5px);
  background: rgba(255, 255, 255, 0.5);
  box-shadow:
    0 12px 32px rgba(31, 38, 135, 0.14),
    inset 0 1px 0 rgba(255, 255, 255, 0.8);
}

.step-number {
  font-size: 2.5rem;
  font-weight: 900;
  color: var(--primary-color);
  margin-bottom: 10px;
}

.workflow-step h3 {
  margin-bottom: 10px;
  color: var(--text-primary);
  font-size: 1.1rem;
}

.workflow-step p {
  color: var(--text-secondary);
  font-size: 0.9rem;
}

.workflow-promise {
  text-align: center;
  padding: 24px;
  background: rgba(37, 99, 235, 0.08);
  backdrop-filter: blur(16px) saturate(1.4);
  -webkit-backdrop-filter: blur(16px) saturate(1.4);
  border-radius: 20px;
  border: 1px solid rgba(37, 99, 235, 0.15);
  box-shadow:
    0 4px 16px rgba(37, 99, 235, 0.06),
    inset 0 1px 0 rgba(255, 255, 255, 0.5);
}

.workflow-promise p {
  margin: 0;
  color: var(--primary-color);
  font-weight: 600;
}

.footer-section {
  background: rgba(26, 32, 44, 0.92);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  color: white;
  padding: 80px 0 40px;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 40px;
}

.footer-left h3 {
  font-size: 1.5rem;
  margin-bottom: 16px;
  background: linear-gradient(to right, #2563eb, #10b981);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.footer-left p {
  color: #a0aec0;
  max-width: 300px;
  margin-bottom: 24px;
}

.contact-card {
  background: rgba(255, 255, 255, 0.05);
  padding: 20px;
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.contact-card p {
  margin: 5px 0;
  color: #cbd5e0;
}

.download-btn {
  display: inline-block;
  padding: 12px 24px;
  background: #2d3748;
  color: white;
  text-decoration: none;
  border-radius: 8px;
  font-weight: 600;
  border: 1px solid #4a5568;
  transition: all 0.3s;
  margin-bottom: 20px;
}

.download-btn:hover {
  background: #4a5568;
  border-color: #718096;
}

.copyright {
  color: #718096;
  font-size: 0.9rem;
}

/* ================= Animations ================= */
.fade-in-up {
  animation: fadeInUp 1s ease-out;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-on-scroll {
  opacity: 0;
  transform: translateY(30px);
  transition: opacity 0.8s ease-out, transform 0.8s ease-out;
}

.animate-on-scroll.visible {
  opacity: 1;
  transform: translateY(0);
}

/* ================= Responsive ================= */
@media (max-width: 992px) {
  .hero-title {
    font-size: 3.5rem;
  }

  .stats-banner {
    flex-wrap: wrap;
    gap: 20px;
    justify-content: flex-start;
  }

  .stat-item {
    width: 45%;
  }

  .stat-divider {
    display: none;
  }

  .feature-row {
    flex-direction: column !important;
    gap: 40px;
    text-align: center;
  }

  .tech-tabs {
    grid-template-columns: 1fr;
    gap: 40px;
  }
}

@media (max-width: 768px) {
  .hero-title {
    font-size: 2.5rem;
  }

  .hero-subtitle {
    font-size: 1.25rem;
  }

  .hero-buttons {
    flex-direction: column;
    width: 100%;
    max-width: 300px;
    margin: 0 auto;
  }

  .btn {
    width: 100%;
    justify-content: center;
  }

  .stat-item {
    width: 100%;
  }

  .guide-box {
    padding: 30px;
  }

  .footer-content {
    flex-direction: column;
  }
}
</style>
