<template>
  <div class="home-container">
    <!-- Hero Section -->
    <section class="hero-section" ref='vantaRef'>
      <div class="hero-overlay"></div>
      <div class="hero-content fade-in-up">
        <div class="ai-badge">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
            stroke-linecap="round" stroke-linejoin="round" style="vertical-align: middle; margin-right: 6px;">
            <polygon points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"></polygon>
          </svg>
          AI-Powered Full-Stack Automation
        </div>
        <h1 class="hero-title">
          <span class="gradient-text">东神脚手架</span>
        </h1>
        <p class="hero-subtitle">一条指令，全栈交付</p>
        <p class="hero-description">
          从<strong>数据库建表</strong>到<strong>后端三层架构</strong>，从<strong>后台管理页面</strong>到<strong>前台液态玻璃 UI</strong>，
          再到<strong>权限配置</strong>与<strong>代码质检</strong> —— 7 步全流程 AI 自动完成，零手动编码。
        </p>
        <div class="hero-buttons">
          <a href="https://gitee.com/li-dongshenger/web_manage" target="_blank" class="btn btn-primary btn-glow">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
              stroke-linecap="round" stroke-linejoin="round" style="margin-right: 8px;">
              <path
                d="M15 22v-4a4.8 4.8 0 0 0-1-3.5c3 0 6-2 6-5.5.08-1.25-.27-2.48-1-3.5.28-1.15.28-2.35 0-3.5 0 0-1 0-3 1.5-2.64-.5-5.36-.5-8 0C6 2 5 2 5 2c-.3 1.15-.3 2.35 0 3.5A5.403 5.403 0 0 0 4 9c0 3.5 3 5.5 6 5.5-.39.49-.68 1.05-.85 1.65-.17.6-.22 1.23-.15 1.85v4">
              </path>
              <path d="M9 18c-4.51 2-5-2-7-2"></path>
            </svg>
            Gitee 仓库
          </a>
          <a href="https://pan.baidu.com/s/1zU8TWzNMtv5kNSL5H9Q5_w?pwd=ys73" target="_blank"
            class="btn btn-secondary glass-effect">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
              stroke-linecap="round" stroke-linejoin="round" style="margin-right: 8px;">
              <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
              <polyline points="7 10 12 15 17 10"></polyline>
              <line x1="12" y1="15" x2="12" y2="3"></line>
            </svg>
            运行环境下载
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
          <circle cx="50" cy="50" r="8" fill="url(#nodeGradient)" class="pulse-node" />
          <circle cx="150" cy="100" r="6" fill="url(#nodeGradient)" class="pulse-node" />
          <circle cx="250" cy="80" r="7" fill="url(#nodeGradient)" class="pulse-node" />
          <circle cx="350" cy="120" r="5" fill="url(#nodeGradient)" class="pulse-node" />
          <circle cx="100" cy="200" r="6" fill="url(#nodeGradient)" class="pulse-node" />
          <circle cx="300" cy="180" r="8" fill="url(#nodeGradient)" class="pulse-node" />
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
      </div>
    </section>

    <!-- 核心数据指标 -->
    <div class="stats-banner-wrapper container">
      <div class="stats-banner glass-card floating">
        <div class="stat-item" v-for="(s, i) in stats" :key="i">
          <div class="stat-value">{{ s.value }}</div>
          <div class="stat-label">{{ s.label }}</div>
        </div>
      </div>
    </div>

    <!-- 全流程自动化 Pipeline -->
    <section class="section pipeline-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title"><span class="gradient-text">全流程自动化</span> Pipeline</h2>
          <p class="section-desc">一条 AI 指令驱动 7 步闭环，从需求到交付全链路自动完成</p>
        </div>

        <div class="pipeline-track">
          <div class="pipeline-line"></div>
          <div class="pipeline-step" v-for="(step, idx) in pipelineSteps" :key="idx">
            <div class="pipeline-node">
              <div class="pipeline-icon" v-html="step.icon"></div>
            </div>
            <div class="pipeline-card glass-card">
              <div class="pipeline-num">STEP {{ String(idx + 1).padStart(2, '0') }}</div>
              <h3>{{ step.title }}</h3>
              <p>{{ step.desc }}</p>
              <div class="pipeline-output">
                <span v-for="(tag, ti) in step.outputs" :key="ti" class="output-tag">{{ tag }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 架构亮点 -->
    <section class="section arch-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">为什么<span class="gradient-text">强大</span></h2>
          <p class="section-desc">企业级架构设计 + AI 智能编排，不只是代码生成器</p>
        </div>

        <div class="arch-grid">
          <div class="arch-card glass-card" v-for="(f, i) in archFeatures" :key="i">
            <div class="arch-icon" v-html="f.icon"></div>
            <h3>{{ f.title }}</h3>
            <p>{{ f.desc }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 代码生成示例 -->
    <section class="section codegen-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">AI 生成<span class="gradient-text">真实代码</span></h2>
          <p class="section-desc">不是模板复制粘贴，而是根据业务需求智能推导的完整代码</p>
        </div>

        <div class="codegen-grid">
          <!-- 后端代码示例 -->
          <div class="code-card">
            <div class="code-header">
              <span class="dot red"></span>
              <span class="dot yellow"></span>
              <span class="dot green"></span>
              <span class="code-title">AdminController.java</span>
            </div>
            <div class="code-body">
              <pre><code><span class="code-comment">// AI 自动生成 - 后台管理接口 + RBAC 权限注解</span>
<span class="code-annotation">@SaCheckPermission</span>("admin:scenic:list")
<span class="code-annotation">@GetMapping</span>("/list")
<span class="code-keyword">public</span> R&lt;PageResult&gt; <span class="code-func">list</span>(ScenicSpot query) {
    <span class="code-keyword">return</span> R.ok(scenicSpotService.queryPage(query));
}

<span class="code-annotation">@Log</span>(title = "景点管理", businessType = BusinessType.INSERT)
<span class="code-annotation">@SaCheckPermission</span>("admin:scenic:add")
<span class="code-annotation">@PostMapping</span>("/add")
<span class="code-keyword">public</span> R&lt;Void&gt; <span class="code-func">add</span>(<span class="code-annotation">@RequestBody</span> ScenicSpot entity) {
    scenicSpotService.save(entity);
    <span class="code-keyword">return</span> R.ok();
}</code></pre>
            </div>
          </div>

          <!-- 前端代码示例 -->
          <div class="code-card code-card-light">
            <div class="code-header">
              <span class="dot red"></span>
              <span class="dot yellow"></span>
              <span class="dot green"></span>
              <span class="code-title">ManageScenicView.vue</span>
            </div>
            <div class="code-body">
              <pre><code><span class="code-comment">&lt;!-- AI 自动生成 - Element Plus 管理页面 --&gt;</span>
<span class="code-tag">&lt;el-table</span> :data="tableData" stripe<span class="code-tag">&gt;</span>
  <span class="code-tag">&lt;el-table-column</span> prop="name" label="景点名称" <span class="code-tag">/&gt;</span>
  <span class="code-tag">&lt;el-table-column</span> prop="status" label="状态"<span class="code-tag">&gt;</span>
    <span class="code-tag">&lt;template</span> #default="scope"<span class="code-tag">&gt;</span>
      <span class="code-tag">&lt;el-tag</span> :type="selectTagType(scope.row.status,
        statusOption)"<span class="code-tag">&gt;</span>
         selectDictLabel(scope.row.status, statusOption) 
      <span class="code-tag">&lt;/el-tag&gt;</span>
    <span class="code-tag">&lt;/template&gt;</span>
  <span class="code-tag">&lt;/el-table-column&gt;</span>
<span class="code-tag">&lt;/el-table&gt;</span></code></pre>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 技术栈 -->
    <section class="section tech-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title"><span class="gradient-text">技术栈</span>全景</h2>
          <p class="section-desc">经过生产验证的主流框架组合，稳定可靠</p>
        </div>

        <div class="tech-tabs">
          <div class="tech-group">
            <h3>
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                stroke-linecap="round" stroke-linejoin="round" style="vertical-align: middle; margin-right: 8px;">
                <rect x="2" y="3" width="20" height="14" rx="2" ry="2"></rect>
                <line x1="8" y1="21" x2="16" y2="21"></line>
                <line x1="12" y1="17" x2="12" y2="21"></line>
              </svg>
              后端 Backend
            </h3>
            <div class="tech-grid">
              <div class="tech-card" v-for="(item, index) in backTechs" :key="'b' + index">
                <div class="tech-icon" v-html="item.icon"></div>
                <div class="tech-info">
                  <h4>{{ item.name }}</h4>
                  <span>{{ item.version }}</span>
                </div>
              </div>
            </div>
          </div>

          <div class="tech-group">
            <h3>
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                stroke-linecap="round" stroke-linejoin="round" style="vertical-align: middle; margin-right: 8px;">
                <polyline points="16 18 22 12 16 6"></polyline>
                <polyline points="8 6 2 12 8 18"></polyline>
              </svg>
              前端 Frontend
            </h3>
            <div class="tech-grid">
              <div class="tech-card" v-for="(item, index) in frontTechs" :key="'f' + index">
                <div class="tech-icon" v-html="item.icon"></div>
                <div class="tech-info">
                  <h4>{{ item.name }}</h4>
                  <span>{{ item.version }}</span>
                </div>
              </div>
            </div>
          </div>

          <div class="tech-group">
            <h3>
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                stroke-linecap="round" stroke-linejoin="round" style="vertical-align: middle; margin-right: 8px;">
                <path d="M12 2L2 7l10 5 10-5-10-5z"></path>
                <path d="M2 17l10 5 10-5"></path>
                <path d="M2 12l10 5 10-5"></path>
              </svg>
              AI 工具链
            </h3>
            <div class="tech-grid">
              <div class="tech-card" v-for="(item, index) in aiTechs" :key="'a' + index">
                <div class="tech-icon" v-html="item.icon"></div>
                <div class="tech-info">
                  <h4>{{ item.name }}</h4>
                  <span>{{ item.desc }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 架构图示 -->
    <section class="section diagram-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">系统<span class="gradient-text">架构总览</span></h2>
          <p class="section-desc">前后端分离 + 多模块 Maven + RBAC 权限体系</p>
        </div>

        <div class="diagram-wrapper glass-card">
          <div class="diagram-row">
            <div class="diagram-block diagram-frontend">
              <div class="diagram-label">Frontend</div>
              <div class="diagram-items">
                <span>Vue 3</span><span>Element Plus</span><span>Vite</span><span>Liquid Glass UI</span>
              </div>
            </div>
          </div>
          <div class="diagram-arrow">
            <svg width="24" height="40" viewBox="0 0 24 40">
              <path d="M12 0 L12 30 L6 24 M12 30 L18 24" stroke="#2563eb" stroke-width="2" fill="none" />
            </svg>
            <span>Axios / REST API</span>
          </div>
          <div class="diagram-row">
            <div class="diagram-block diagram-controller">
              <div class="diagram-label">Controller Layer</div>
              <div class="diagram-items">
                <span>admin/*</span><span>user/*</span><span>common/*</span>
              </div>
            </div>
          </div>
          <div class="diagram-arrow">
            <svg width="24" height="40" viewBox="0 0 24 40">
              <path d="M12 0 L12 30 L6 24 M12 30 L18 24" stroke="#10b981" stroke-width="2" fill="none" />
            </svg>
            <span>Sa-Token RBAC</span>
          </div>
          <div class="diagram-row diagram-row-multi">
            <div class="diagram-block diagram-service">
              <div class="diagram-label">Service</div>
              <div class="diagram-items"><span>业务逻辑</span></div>
            </div>
            <div class="diagram-block diagram-mapper">
              <div class="diagram-label">Mapper</div>
              <div class="diagram-items"><span>MyBatis Plus</span></div>
            </div>
            <div class="diagram-block diagram-cache">
              <div class="diagram-label">Cache</div>
              <div class="diagram-items"><span>Redis</span></div>
            </div>
          </div>
          <div class="diagram-arrow">
            <svg width="24" height="40" viewBox="0 0 24 40">
              <path d="M12 0 L12 30 L6 24 M12 30 L18 24" stroke="#8b5cf6" stroke-width="2" fill="none" />
            </svg>
            <span>JDBC</span>
          </div>
          <div class="diagram-row">
            <div class="diagram-block diagram-db">
              <div class="diagram-label">MySQL 8.0</div>
              <div class="diagram-items">
                <span>业务表</span><span>sys_permission</span><span>sys_role</span><span>sys_dict_data</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 页脚 -->
    <footer class="footer-section">
      <div class="container">
        <div class="footer-content">
          <div class="footer-left">
            <h3>东神脚手架</h3>
            <p>AI 驱动的全栈自动化开发平台</p>
          </div>
          <div class="footer-center">
            <div class="footer-links">
              <a href="https://gitee.com/li-dongshenger/web_manage" target="_blank">Gitee</a>
              <span class="footer-divider">|</span>
              <a href="https://pan.baidu.com/s/1zU8TWzNMtv5kNSL5H9Q5_w?pwd=ys73" target="_blank">环境下载</a>
            </div>
          </div>
          <div class="footer-right">
            <p class="copyright">&copy; 2025 东神脚手架 by 李东升. All rights reserved.</p>
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

// SVG icon helper
const svgIcon = (d, color = '#2563eb') =>
  `<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="${color}" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">${d}</svg>`

// 核心数据指标
const stats = [
  { value: '7 步', label: '全流程闭环' },
  { value: '10x', label: '效率提升' },
  { value: '0', label: '手动编码量' },
  { value: '100%', label: 'AI 代码覆盖' },
]

// Pipeline 步骤
const pipelineSteps = [
  {
    title: '数据库设计',
    desc: 'AI 根据业务需求自动生成完整 SQL 建表语句，含索引、外键、字典数据',
    icon: svgIcon('<ellipse cx="12" cy="5" rx="9" ry="3"></ellipse><path d="M21 12c0 1.66-4 3-9 3s-9-1.34-9-3"></path><path d="M3 5v14c0 1.66 4 3 9 3s9-1.34 9-3V5"></path>', '#8b5cf6'),
    outputs: ['CREATE TABLE', 'INSERT 字典', '索引优化']
  },
  {
    title: '后端基础层',
    desc: 'Domain 实体 + Mapper 接口 + Service 业务逻辑，三层架构一次生成',
    icon: svgIcon('<path d="M12 2L2 7l10 5 10-5-10-5z"></path><path d="M2 17l10 5 10-5"></path><path d="M2 12l10 5 10-5"></path>', '#2563eb'),
    outputs: ['@Data Entity', 'BaseMapper', 'IService']
  },
  {
    title: '后端接口层',
    desc: 'Admin + User 双 Controller 自动生成，含权限注解、操作日志、分页查询',
    icon: svgIcon('<path d="M4 11a9 9 0 0 1 9 9"></path><path d="M4 4a16 16 0 0 1 16 16"></path><circle cx="5" cy="19" r="1"></circle>', '#10b981'),
    outputs: ['@SaCheckPermission', '@Log', 'CRUD API']
  },
  {
    title: '后台管理页面',
    desc: 'Element Plus 表格 + 表单 + 字典回显 + 权限按钮，完整的管理后台',
    icon: svgIcon('<rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect><line x1="3" y1="9" x2="21" y2="9"></line><line x1="9" y1="21" x2="9" y2="9"></line>', '#f59e0b'),
    outputs: ['el-table', 'el-form', 'el-tag 字典']
  },
  {
    title: '前台用户页面',
    desc: '液态玻璃 Liquid Glass 风格，响应式布局，Vanta.js 3D 背景动画',
    icon: svgIcon('<path d="M12 3c.132 0 .263 0 .393 0a7.5 7.5 0 0 0 7.92 12.446a9 9 0 1 1 -8.313-12.454z"></path>', '#06b6d4'),
    outputs: ['Liquid Glass', '响应式', '3D 动画']
  },
  {
    title: '路由与权限配置',
    desc: 'Vue Router 路由自动注册，RBAC 菜单权限 + 按钮权限 SQL 一并生成',
    icon: svgIcon('<circle cx="12" cy="12" r="3"></circle><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06A1.65 1.65 0 0 0 4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06A1.65 1.65 0 0 0 9 4.68a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path>', '#ec4899'),
    outputs: ['router/index.js', 'sys_permission SQL']
  },
  {
    title: '代码质检',
    desc: 'AI 自动执行 7 维度代码审查：命名规范、权限完整性、字典一致性等',
    icon: svgIcon('<path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path><polyline points="22 4 12 14.01 9 11.01"></polyline>', '#10b981'),
    outputs: ['命名检查', '权限审计', '字典校验']
  },
]

// 架构亮点
const archFeatures = [
  {
    title: 'RBAC 细粒度权限',
    desc: '用户-角色-权限三级模型，Sa-Token 实现接口级 + 按钮级双重控制，后台前台独立鉴权体系',
    icon: svgIcon('<rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect><path d="M7 11V7a5 5 0 0 1 10 0v4"></path>', '#2563eb'),
  },
  {
    title: '多模块 Maven 架构',
    desc: 'admin / system / framework / common 四模块分层，职责清晰，可独立编译部署',
    icon: svgIcon('<path d="M12 2L2 7l10 5 10-5-10-5z"></path><path d="M2 17l10 5 10-5"></path><path d="M2 12l10 5 10-5"></path>', '#8b5cf6'),
  },
  {
    title: 'Redis 缓存加速',
    desc: '首页热点数据缓存、开源中国式 ID 列表 + 条目缓存策略，大幅提升查询性能',
    icon: svgIcon('<polygon points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"></polygon>', '#f59e0b'),
  },
  {
    title: '字典系统',
    desc: '状态/类型等枚举值统一管理，前端自动回显 el-tag 颜色标签，告别硬编码',
    icon: svgIcon('<path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path><path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>', '#10b981'),
  },
  {
    title: '液态玻璃 UI',
    desc: 'iOS 26 Liquid Glass 设计语言，backdrop-filter 毛玻璃 + Vanta.js 3D 云层背景',
    icon: svgIcon('<path d="M12 3c.132 0 .263 0 .393 0a7.5 7.5 0 0 0 7.92 12.446a9 9 0 1 1 -8.313-12.454z"></path>', '#06b6d4'),
  },
  {
    title: 'Knife4j 接口文档',
    desc: '集成 OpenAPI 3 可视化接口文档，支持在线调试，前后端联调零障碍',
    icon: svgIcon('<path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line>', '#ec4899'),
  },
]

// 后端技术栈
const backTechs = [
  { name: 'Spring Boot', version: '3.0.6', icon: svgIcon('<path d="M17 8l4 4-4 4"></path><path d="M3 12h18"></path>', '#6db33f') },
  { name: 'JDK', version: '17.0.13', icon: svgIcon('<path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path><path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path>', '#e76f00') },
  { name: 'MySQL', version: '8.0.36', icon: svgIcon('<ellipse cx="12" cy="5" rx="9" ry="3"></ellipse><path d="M21 12c0 1.66-4 3-9 3s-9-1.34-9-3"></path><path d="M3 5v14c0 1.66 4 3 9 3s9-1.34 9-3V5"></path>', '#4479a1') },
  { name: 'Redis', version: '5.0.14', icon: svgIcon('<polygon points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"></polygon>', '#dc382d') },
  { name: 'Sa-Token', version: '1.38.0', icon: svgIcon('<rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect><path d="M7 11V7a5 5 0 0 1 10 0v4"></path>', '#2563eb') },
  { name: 'MyBatis Plus', version: '3.0.3', icon: svgIcon('<path d="M12 2L2 7l10 5 10-5-10-5z"></path><path d="M2 17l10 5 10-5"></path><path d="M2 12l10 5 10-5"></path>', '#e8364e') },
  { name: 'Knife4j', version: '4.3.0', icon: svgIcon('<path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline>', '#1890ff') },
  { name: 'Hutool', version: '5.8.26', icon: svgIcon('<path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>', '#ff6b35') },
]

// 前端技术栈
const frontTechs = [
  { name: 'Vue 3', version: '3.2.13', icon: svgIcon('<polygon points="12 2 22 8.5 22 15.5 12 22 2 15.5 2 8.5 12 2"></polygon>', '#42b883') },
  { name: 'Element Plus', version: '2.9.3', icon: svgIcon('<rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect><line x1="3" y1="9" x2="21" y2="9"></line><line x1="9" y1="21" x2="9" y2="9"></line>', '#409eff') },
  { name: 'Vite', version: 'Latest', icon: svgIcon('<polygon points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"></polygon>', '#646cff') },
  { name: 'Vue Router', version: '4.0.3', icon: svgIcon('<circle cx="12" cy="12" r="10"></circle><line x1="2" y1="12" x2="22" y2="12"></line><path d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"></path>', '#42b883') },
  { name: 'Axios', version: '1.7.9', icon: svgIcon('<path d="M4 11a9 9 0 0 1 9 9"></path><path d="M4 4a16 16 0 0 1 16 16"></path><circle cx="5" cy="19" r="1"></circle>', '#5a29e4') },
  { name: 'Vanta.js', version: '3D 背景', icon: svgIcon('<path d="M12 3c.132 0 .263 0 .393 0a7.5 7.5 0 0 0 7.92 12.446a9 9 0 1 1 -8.313-12.454z"></path>', '#06b6d4') },
]

// AI 工具链
const aiTechs = [
  { name: 'Windsurf', desc: 'AI IDE 引擎', icon: svgIcon('<rect x="4" y="4" width="16" height="16" rx="2" ry="2"></rect><rect x="9" y="9" width="6" height="6"></rect><line x1="9" y1="1" x2="9" y2="4"></line><line x1="15" y1="1" x2="15" y2="4"></line><line x1="9" y1="20" x2="9" y2="23"></line><line x1="15" y1="20" x2="15" y2="23"></line><line x1="20" y1="9" x2="23" y2="9"></line><line x1="20" y1="14" x2="23" y2="14"></line><line x1="1" y1="9" x2="4" y2="9"></line><line x1="1" y1="14" x2="4" y2="14"></line>', '#2563eb') },
  { name: 'AgentSkill', desc: '规范强制执行', icon: svgIcon('<path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path><polyline points="22 4 12 14.01 9 11.01"></polyline>', '#10b981') },
  { name: 'MCP Server', desc: '后台数据联调', icon: svgIcon('<path d="M4 11a9 9 0 0 1 9 9"></path><path d="M4 4a16 16 0 0 1 16 16"></path><circle cx="5" cy="19" r="1"></circle>', '#8b5cf6') },
  { name: 'UI/UX Pro Max', desc: '设计系统搜索', icon: svgIcon('<circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line>', '#ec4899') },
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

  document.querySelectorAll('.section, .stats-banner, .pipeline-step, .arch-card').forEach(el => {
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
  --primary: #2563eb;
  --secondary: #10b981;
  --accent: #8b5cf6;
  --text-primary: #1a202c;
  --text-secondary: #718096;
  --bg-light: #f8fafc;
}

/* ================= Global ================= */
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
  background-clip: text;
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
  box-shadow: 0 8px 32px rgba(31, 38, 135, 0.1), inset 0 1px 0 rgba(255, 255, 255, 0.7), inset 0 -1px 0 rgba(255, 255, 255, 0.15);
  border-radius: 24px;
}

.section {
  padding: 100px 0;
}

.section-header {
  text-align: center;
  margin-bottom: 64px;
}

.section-title {
  font-size: 2.5rem;
  font-weight: 800;
  margin-bottom: 16px;
  color: var(--text-primary);
  letter-spacing: -0.02em;
}

.section-desc {
  font-size: 1.125rem;
  color: var(--text-secondary);
  max-width: 600px;
  margin: 0 auto;
}

/* ================= Hero ================= */
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
  inset: 0;
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
  display: inline-flex;
  align-items: center;
  padding: 8px 20px;
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
  cursor: pointer;
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
  color: #2563eb;
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
  animation: scroll-anim 1.5s infinite;
}

@keyframes scroll-anim {
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
  padding: 36px 40px;
}

.floating {
  animation: gentle-float 6s ease-in-out infinite;
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

.stat-item {
  text-align: center;
  padding: 0 16px;
}

.stat-value {
  font-size: 2rem;
  font-weight: 900;
  background: linear-gradient(135deg, #2563eb, #10b981);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
}

.stat-label {
  font-size: 0.9rem;
  color: var(--text-secondary);
  margin-top: 4px;
  font-weight: 500;
}

/* ================= Pipeline Section ================= */
.pipeline-section {
  background: linear-gradient(135deg, rgba(37, 99, 235, 0.03) 0%, rgba(16, 185, 129, 0.03) 100%);
}

.pipeline-track {
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.pipeline-line {
  position: absolute;
  left: 28px;
  top: 0;
  bottom: 0;
  width: 3px;
  background: linear-gradient(180deg, #2563eb, #10b981, #8b5cf6, #f59e0b, #06b6d4, #ec4899, #10b981);
  border-radius: 2px;
  opacity: 0.3;
}

.pipeline-step {
  display: flex;
  align-items: flex-start;
  gap: 24px;
  position: relative;
}

.pipeline-node {
  width: 56px;
  height: 56px;
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.6);
  backdrop-filter: blur(12px);
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow: 0 4px 16px rgba(31, 38, 135, 0.08);
  z-index: 2;
}

.pipeline-icon {
  display: flex;
  align-items: center;
  justify-content: center;
}

.pipeline-card {
  flex: 1;
  padding: 24px 28px;
  transition: all 0.3s ease;
  cursor: pointer;
}

.pipeline-card:hover {
  transform: translateX(8px);
  box-shadow: 0 12px 40px rgba(31, 38, 135, 0.14), inset 0 1px 0 rgba(255, 255, 255, 0.8);
}

.pipeline-num {
  font-size: 0.75rem;
  font-weight: 700;
  color: #2563eb;
  letter-spacing: 0.1em;
  margin-bottom: 8px;
}

.pipeline-card h3 {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 8px 0;
}

.pipeline-card p {
  font-size: 0.95rem;
  color: var(--text-secondary);
  margin: 0 0 16px 0;
  line-height: 1.6;
}

.pipeline-output {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.output-tag {
  padding: 4px 12px;
  background: rgba(37, 99, 235, 0.08);
  color: #2563eb;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;
  border: 1px solid rgba(37, 99, 235, 0.12);
}

/* ================= Architecture Section ================= */
.arch-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.arch-card {
  padding: 32px 28px;
  transition: all 0.3s ease;
  cursor: pointer;
}

.arch-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 16px 40px rgba(31, 38, 135, 0.14), inset 0 1px 0 rgba(255, 255, 255, 0.8);
}

.arch-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 48px;
  height: 48px;
  background: rgba(37, 99, 235, 0.08);
  border-radius: 14px;
  margin-bottom: 20px;
}

.arch-card h3 {
  font-size: 1.15rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 12px 0;
}

.arch-card p {
  font-size: 0.95rem;
  color: var(--text-secondary);
  line-height: 1.6;
  margin: 0;
}

/* ================= Code Generation Section ================= */
.codegen-section {
  background: linear-gradient(135deg, rgba(37, 99, 235, 0.03) 0%, rgba(139, 92, 246, 0.03) 100%);
}

.codegen-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 32px;
}

.code-card {
  background: rgba(30, 30, 30, 0.9);
  backdrop-filter: blur(20px);
  border-radius: 16px;
  overflow: hidden;
  border: 1px solid rgba(255, 255, 255, 0.08);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease;
}

.code-card:hover {
  transform: translateY(-4px);
}

.code-card-light {
  background: rgba(248, 250, 252, 0.95);
  border: 1px solid #e2e8f0;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.08);
}

.code-header {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 20px;
  background: rgba(255, 255, 255, 0.05);
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
}

.code-card-light .code-header {
  background: rgba(0, 0, 0, 0.02);
  border-bottom: 1px solid #e2e8f0;
}

.code-title {
  margin-left: 8px;
  font-size: 0.8rem;
  color: #a0aec0;
  font-weight: 500;
}

.code-card-light .code-title {
  color: #718096;
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

.code-body {
  padding: 20px;
}

.code-body pre {
  margin: 0;
  font-family: 'Fira Code', 'JetBrains Mono', monospace;
  font-size: 0.82rem;
  line-height: 1.7;
  overflow-x: auto;
}

.code-card .code-body pre {
  color: #d4d4d4;
}

.code-card-light .code-body pre {
  color: #2d3748;
}

.code-comment {
  color: #6a9955;
}

.code-annotation {
  color: #dcdcaa;
}

.code-keyword {
  color: #569cd6;
}

.code-func {
  color: #4ec9b0;
}

.code-tag {
  color: #4fc1ff;
}

.code-card-light .code-comment {
  color: #6a9955;
}

.code-card-light .code-annotation {
  color: #795e26;
}

.code-card-light .code-keyword {
  color: #0000ff;
}

.code-card-light .code-func {
  color: #267f99;
}

.code-card-light .code-tag {
  color: #800000;
}

/* ================= Tech Section ================= */
.tech-section {
  background: rgba(255, 255, 255, 0.3);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
}

.tech-tabs {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 48px;
}

.tech-group h3 {
  font-size: 1.25rem;
  margin-bottom: 24px;
  color: #2d3748;
  position: relative;
  display: inline-flex;
  align-items: center;
}

.tech-group h3::after {
  content: '';
  position: absolute;
  bottom: -8px;
  left: 0;
  width: 40px;
  height: 3px;
  background: linear-gradient(90deg, #2563eb, #10b981);
  border-radius: 2px;
}

.tech-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(130px, 1fr));
  gap: 16px;
}

.tech-card {
  background: rgba(255, 255, 255, 0.4);
  backdrop-filter: blur(16px) saturate(1.6);
  -webkit-backdrop-filter: blur(16px) saturate(1.6);
  padding: 20px 16px;
  border-radius: 16px;
  text-align: center;
  transition: all 0.3s ease;
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow: 0 4px 16px rgba(31, 38, 135, 0.06);
  cursor: pointer;
}

.tech-card:hover {
  background: rgba(255, 255, 255, 0.6);
  transform: translateY(-4px);
  box-shadow: 0 12px 28px rgba(31, 38, 135, 0.12);
}

.tech-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12px;
}

.tech-info h4 {
  margin: 0;
  font-size: 0.95rem;
  color: #2d3748;
}

.tech-info span {
  font-size: 0.78rem;
  color: #718096;
}

/* ================= Diagram Section ================= */
.diagram-section {
  background: linear-gradient(135deg, rgba(139, 92, 246, 0.03) 0%, rgba(37, 99, 235, 0.03) 100%);
}

.diagram-wrapper {
  padding: 48px 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0;
}

.diagram-row {
  display: flex;
  justify-content: center;
  width: 100%;
}

.diagram-row-multi {
  display: flex;
  gap: 20px;
  justify-content: center;
}

.diagram-block {
  padding: 20px 32px;
  border-radius: 16px;
  text-align: center;
  min-width: 200px;
}

.diagram-frontend {
  background: linear-gradient(135deg, rgba(66, 184, 131, 0.12), rgba(64, 158, 255, 0.12));
  border: 1px solid rgba(66, 184, 131, 0.2);
  width: 100%;
  max-width: 600px;
}

.diagram-controller {
  background: linear-gradient(135deg, rgba(37, 99, 235, 0.1), rgba(16, 185, 129, 0.1));
  border: 1px solid rgba(37, 99, 235, 0.15);
  width: 100%;
  max-width: 600px;
}

.diagram-service {
  background: rgba(139, 92, 246, 0.08);
  border: 1px solid rgba(139, 92, 246, 0.15);
  flex: 1;
}

.diagram-mapper {
  background: rgba(232, 54, 78, 0.08);
  border: 1px solid rgba(232, 54, 78, 0.15);
  flex: 1;
}

.diagram-cache {
  background: rgba(220, 56, 45, 0.08);
  border: 1px solid rgba(220, 56, 45, 0.15);
  flex: 1;
}

.diagram-db {
  background: linear-gradient(135deg, rgba(68, 121, 161, 0.1), rgba(139, 92, 246, 0.1));
  border: 1px solid rgba(68, 121, 161, 0.2);
  width: 100%;
  max-width: 600px;
}

.diagram-label {
  font-weight: 700;
  font-size: 1rem;
  color: var(--text-primary);
  margin-bottom: 10px;
}

.diagram-items {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  justify-content: center;
}

.diagram-items span {
  padding: 4px 12px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 8px;
  font-size: 0.8rem;
  color: var(--text-secondary);
  font-weight: 500;
}

.diagram-arrow {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 4px 0;
}

.diagram-arrow span {
  font-size: 0.75rem;
  color: var(--text-secondary);
  font-weight: 600;
  margin-top: 2px;
}

/* ================= Footer ================= */
.footer-section {
  background: rgba(26, 32, 44, 0.95);
  backdrop-filter: blur(20px);
  color: white;
  padding: 40px 0;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 20px;
}

.footer-left h3 {
  font-size: 1.2rem;
  margin: 0 0 4px 0;
  background: linear-gradient(to right, #2563eb, #10b981);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
}

.footer-left p {
  color: #a0aec0;
  margin: 0;
  font-size: 0.9rem;
}

.footer-links {
  display: flex;
  gap: 16px;
  align-items: center;
}

.footer-links a {
  color: #a0aec0;
  text-decoration: none;
  font-size: 0.9rem;
  transition: color 0.2s;
}

.footer-links a:hover {
  color: white;
}

.footer-divider {
  color: #4a5568;
}

.copyright {
  color: #718096;
  font-size: 0.85rem;
  margin: 0;
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
    padding: 28px 24px;
  }

  .stat-item {
    width: 45%;
  }

  .arch-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .codegen-grid {
    grid-template-columns: 1fr;
  }

  .tech-tabs {
    grid-template-columns: 1fr;
    gap: 40px;
  }

  .pipeline-line {
    left: 28px;
  }

  .diagram-row-multi {
    flex-direction: column;
    gap: 12px;
  }
}

@media (max-width: 768px) {
  .section {
    padding: 60px 0;
  }

  .section-header {
    margin-bottom: 40px;
  }

  .section-title {
    font-size: 1.8rem;
  }

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

  .arch-grid {
    grid-template-columns: 1fr;
  }

  .pipeline-step {
    flex-direction: column;
    align-items: stretch;
  }

  .pipeline-line {
    display: none;
  }

  .pipeline-node {
    width: 100%;
    height: auto;
    padding: 12px;
    border-radius: 16px 16px 0 0;
  }

  .footer-content {
    flex-direction: column;
    text-align: center;
  }

  .diagram-wrapper {
    padding: 24px 16px;
  }

  .diagram-block {
    min-width: auto;
    padding: 16px;
  }
}
</style>
