<template>
  <div class="home-container">
    <!-- 头部横幅 -->
    <section class="hero-section">
      <div class="hero-content">
        <h1 class="hero-title">东神脚手架</h1>
        <p class="hero-subtitle">为毕业设计快速开发打造的脚手架系统</p>
        <p class="hero-description">支持前后端二次开发，基于 RBAC 权限模型与 Sa-Token 实现精细化权限控制</p>
        <div class="hero-buttons">
          <button class="btn btn-primary">
            <a style="color: #667eea;text-decoration: none;" href="https://gitee.com/li-dongshenger/web_manage" target="_blank">开始使用</a>
          </button>
          <button class="btn btn-primary">
            <a style="color: #667eea;text-decoration: none;" href="https://gitee.com/li-dongshenger/web_manage" target="_blank">查看文档</a>
          </button>
        </div>
      </div>
    </section>

    <!-- 项目简介 -->
    <section class="intro-section">
      <div class="container">
        <h2 class="section-title">📌 项目简介</h2>
        <div class="intro-grid">
          <div class="intro-card">
            <h3>项目定位</h3>
            <p>一个为毕业设计快速开发打造的脚手架系统，支持前后端二次开发</p>
          </div>
          <div class="intro-card">
            <h3>灵感来源</h3>
            <p>借鉴若依框架（RuoYi），结合 RBAC 权限模型与 Sa-Token 实现权限控制</p>
          </div>
          <div class="intro-card">
            <h3>作者信息</h3>
            <p><strong>开发者：</strong>李东升</p>
            <p><strong>开发时间：</strong>2025/1/15</p>
            <p><strong>状态：</strong>持续优化中</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 技术栈 -->
    <section class="tech-section">
      <div class="container">
        <h2 class="section-title">🔧 技术栈</h2>
        <div class="tech-grid">
          <div class="tech-category">
            <h3>后端技术栈</h3>
            <div class="tech-table">
              <div class="tech-row">
                <span class="tech-name">JDK</span>
                <span class="tech-version">17.0.13</span>
              </div>
              <div class="tech-row">
                <span class="tech-name">MySQL</span>
                <span class="tech-version">8.0.36</span>
              </div>
              <div class="tech-row">
                <span class="tech-name">Spring Boot</span>
                <span class="tech-version">3.0.6</span>
              </div>
              <div class="tech-row">
                <span class="tech-name">Redis</span>
                <span class="tech-version">5.0.14.1</span>
              </div>
              <div class="tech-row">
                <span class="tech-name">Sa-Token</span>
                <span class="tech-version">1.38.0</span>
              </div>
              <div class="tech-row">
                <span class="tech-name">MyBatis</span>
                <span class="tech-version">3.0.3</span>
              </div>
            </div>
          </div>
          <div class="tech-category">
            <h3>前端技术栈</h3>
            <div class="tech-table">
              <div class="tech-row">
                <span class="tech-name">Node.js</span>
                <span class="tech-version">18.19.0</span>
              </div>
              <div class="tech-row">
                <span class="tech-name">Vue</span>
                <span class="tech-version">3.2.13</span>
              </div>
              <div class="tech-row">
                <span class="tech-name">Axios</span>
                <span class="tech-version">1.7.9</span>
              </div>
              <div class="tech-row">
                <span class="tech-name">Element Plus</span>
                <span class="tech-version">2.9.3</span>
              </div>
              <div class="tech-row">
                <span class="tech-name">Vue Router</span>
                <span class="tech-version">4.0.3</span>
              </div>
              <div class="tech-row">
                <span class="tech-name">Vuex</span>
                <span class="tech-version">4.0.0</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 功能模块 -->
    <section class="features-section">
      <div class="container">
        <h2 class="section-title">🛠️ 功能模块</h2>
        <div class="features-grid">
          <div class="feature-category">
            <h3>后台功能</h3>
            <ul class="feature-list">
              <li>通知管理：后台发送通知，前台展示</li>
              <li>用户管理：管理后台用户账户及基本信息</li>
              <li>字典管理：维护静态数据，支持颜色标签选择</li>
              <li>角色管理：多角色配置，分配给用户以控制接口和菜单访问权限</li>
              <li>权限管理：精细化控制后端接口访问权限和前端按钮级权限</li>
              <li>接口文档：集成 knife4j 提供可视化接口测试</li>
              <li>通用查询：支持动态字典映射</li>
            </ul>
          </div>
          <div class="feature-category">
            <h3>前台功能</h3>
            <ul class="feature-list">
              <li>主页通知展示</li>
              <li>用户注册登录</li>
              <li>个人信息管理</li>
              <li>消息通知查看</li>
            </ul>
          </div>
        </div>
      </div>
    </section>

    <!-- 开发指南 -->
    <section class="guide-section">
      <div class="container">
        <h2 class="section-title">🧱 二次开发指南</h2>
        <div class="guide-grid">
          <div class="guide-card">
            <h3>后端开发建议</h3>
            <ol>
              <li>使用 mybatis-plus 的代码生成器生成基础代码</li>
              <li>拷贝 AdminNoticeController 的三层架构</li>
              <li>修改对应业务逻辑和表名即可快速实现新功能</li>
            </ol>
          </div>
          <div class="guide-card">
            <h3>前端开发建议</h3>
            <ol>
              <li>请求封装：复制 NoticeRequest 并重命名</li>
              <li>页面组件：参考或复制 ManageNoticeView 组件进行修改</li>
              <li>前台功能因需求差异较大，请根据实际业务场景自行开发</li>
            </ol>
          </div>
        </div>
      </div>
    </section>

    <!-- 优势亮点 -->
    <section class="highlights-section">
      <div class="container">
        <h2 class="section-title">✅ 优势亮点</h2>
        <div class="highlights-grid">
          <div class="highlight-card">
            <div class="highlight-icon">🔐</div>
            <h3>权限控制精细</h3>
            <p>RBAC + Sa-Token 实现精细化权限控制</p>
          </div>
          <div class="highlight-card">
            <div class="highlight-icon">⚡</div>
            <h3>快速开发</h3>
            <p>提供开发模板，降低重复工作量</p>
          </div>
          <div class="highlight-card">
            <div class="highlight-icon">📦</div>
            <h3>开箱即用</h3>
            <p>集成常用工具库和中间件</p>
          </div>
          <div class="highlight-card">
            <div class="highlight-icon">📚</div>
            <h3>文档完善</h3>
            <p>接口文档完善，便于调试与对接</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 联系方式 -->
    <section class="contact-section">
      <div class="container">
        <h2 class="section-title">📞 联系我们</h2>
        <div class="contact-info">
          <p>如有疑问或需要技术支持，请联系作者：</p>
          <p class="contact-detail">李东升 - 19976898057</p>
          <div class="download-link">
            <p>运行环境下载：<a href="https://pan.baidu.com/s/1zU8TWzNMtv5kNSL5H9Q5_w?pwd=ys73" class="link" target="_blank">百度网盘链接</a></p>
          </div>
        </div>
      </div>
    </section>

    <!-- 页脚 -->
    <footer class="footer">
      <div class="container">
        <p>&copy; 2025 东神脚手架. All rights reserved.</p>
        <p>开发者：李东升 | 持续优化中</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
// 可以在这里添加交互逻辑
</script>

<style scoped>
/* 全局样式 */
.home-container {
  min-height: 100vh;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  line-height: 1.6;
  color: #333;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.section-title {
  font-size: 2.5rem;
  text-align: center;
  margin-bottom: 3rem;
  color: #2c3e50;
  font-weight: 700;
}

/* 头部横幅 */
.hero-section {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 100px 0;
  text-align: center;
}

.hero-title {
  font-size: 4rem;
  font-weight: 800;
  margin-bottom: 1rem;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
}

.hero-subtitle {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  opacity: 0.9;
}

.hero-description {
  font-size: 1.1rem;
  margin-bottom: 2rem;
  opacity: 0.8;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}

.hero-buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

.btn {
  padding: 12px 30px;
  border: none;
  border-radius: 25px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
  display: inline-block;
}

.btn-primary {
  background: #fff;
  color: #667eea;
}

.btn-primary:hover {
  background: #f8f9fa;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0,0,0,0.2);
}

.btn-secondary {
  background: transparent;
  color: #fff;
  border: 2px solid #fff;
}

.btn-secondary:hover {
  background: #fff;
  color: #667eea;
  transform: translateY(-2px);
}

/* 项目简介 */
.intro-section {
  padding: 80px 0;
  background: #f8f9fa;
}

.intro-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
}

.intro-card {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
  transition: transform 0.3s ease;
}

.intro-card:hover {
  transform: translateY(-5px);
}

.intro-card h3 {
  color: #667eea;
  margin-bottom: 1rem;
  font-size: 1.3rem;
}

/* 技术栈 */
.tech-section {
  padding: 80px 0;
}

.tech-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 3rem;
}

.tech-category h3 {
  color: #2c3e50;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  text-align: center;
}

.tech-table {
  background: white;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}

.tech-row {
  display: flex;
  justify-content: space-between;
  padding: 1rem 1.5rem;
  border-bottom: 1px solid #eee;
}

.tech-row:last-child {
  border-bottom: none;
}

.tech-row:nth-child(even) {
  background: #f8f9fa;
}

.tech-name {
  font-weight: 600;
  color: #2c3e50;
}

.tech-version {
  color: #667eea;
  font-weight: 500;
}

/* 功能模块 */
.features-section {
  padding: 80px 0;
  background: #f8f9fa;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 3rem;
}

.feature-category {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}

.feature-category h3 {
  color: #667eea;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
}

.feature-list {
  list-style: none;
  padding: 0;
}

.feature-list li {
  padding: 0.5rem 0;
  border-bottom: 1px solid #eee;
  position: relative;
  padding-left: 1.5rem;
}

.feature-list li:before {
  content: '✓';
  position: absolute;
  left: 0;
  color: #28a745;
  font-weight: bold;
}

.feature-list li:last-child {
  border-bottom: none;
}

/* 开发指南 */
.guide-section {
  padding: 80px 0;
}

.guide-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 3rem;
}

.guide-card {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
  border-left: 4px solid #667eea;
}

.guide-card h3 {
  color: #2c3e50;
  margin-bottom: 1.5rem;
  font-size: 1.3rem;
}

.guide-card ol {
  padding-left: 1.5rem;
}

.guide-card li {
  margin-bottom: 0.8rem;
  line-height: 1.6;
}

/* 优势亮点 */
.highlights-section {
  padding: 80px 0;
  background: #f8f9fa;
}

.highlights-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
}

.highlight-card {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
  transition: transform 0.3s ease;
}

.highlight-card:hover {
  transform: translateY(-5px);
}

.highlight-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.highlight-card h3 {
  color: #2c3e50;
  margin-bottom: 1rem;
  font-size: 1.3rem;
}

.highlight-card p {
  color: #666;
  line-height: 1.6;
}

/* 联系方式 */
.contact-section {
  padding: 80px 0;
  text-align: center;
}

.contact-info {
  max-width: 600px;
  margin: 0 auto;
}

.contact-detail {
  font-size: 1.3rem;
  font-weight: 600;
  color: #667eea;
  margin: 1rem 0;
}

.download-link {
  margin-top: 2rem;
}

.link {
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
}

.link:hover {
  text-decoration: underline;
}

/* 页脚 */
.footer {
  background: #2c3e50;
  color: white;
  padding: 2rem 0;
  text-align: center;
}

.footer p {
  margin: 0.5rem 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hero-title {
    font-size: 2.5rem;
  }
  
  .section-title {
    font-size: 2rem;
  }
  
  .hero-buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .tech-grid,
  .features-grid,
  .guide-grid {
    grid-template-columns: 1fr;
  }
  
  .highlights-grid {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  }
}

@media (max-width: 480px) {
  .container {
    padding: 0 15px;
  }
  
  .hero-section {
    padding: 60px 0;
  }
  
  .hero-title {
    font-size: 2rem;
  }
  
  .section-title {
    font-size: 1.8rem;
  }
  
  .intro-card,
  .feature-category,
  .guide-card,
  .highlight-card {
    padding: 1.5rem;
  }
}
</style>
