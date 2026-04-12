<template>
  <div class="settings-page">
    <el-tabs v-model="activeTab" class="settings-tabs">
      <!-- ==================== 个性化设置 ==================== -->
      <el-tab-pane label="个性化设置" name="personalization">
        <div class="settings-section">
          <h3 class="section-title">
            <el-icon><Brush /></el-icon>外观设置
          </h3>
          <div class="setting-item">
            <div class="setting-label">
              <span class="label-text">主题色</span>
              <span class="label-desc">自定义系统主题颜色</span>
            </div>
            <div class="setting-control">
              <div class="color-presets">
                <div v-for="color in presetColors" :key="color" class="color-dot"
                  :class="{ active: settingsStore.themeColor === color }"
                  :style="{ backgroundColor: color }"
                  @click="changeThemeColor(color)" />
              </div>
              <el-color-picker v-model="settingsStore.themeColor" @change="onThemeColorChange" />
            </div>
          </div>
          <el-divider />

          <h3 class="section-title">
            <el-icon><Setting /></el-icon>布局设置
          </h3>
          <div class="setting-item">
            <div class="setting-label">
              <span class="label-text">侧边栏折叠</span>
              <span class="label-desc">折叠左侧菜单栏，获得更大内容区域</span>
            </div>
            <el-switch v-model="settingsStore.sidebarCollapse" />
          </div>
          <div class="setting-item">
            <div class="setting-label">
              <span class="label-text">显示标签页</span>
              <span class="label-desc">在顶部显示已打开页面的标签</span>
            </div>
            <el-switch v-model="settingsStore.showTagsView" />
          </div>
          <div class="setting-item">
            <div class="setting-label">
              <span class="label-text">页面切换动画</span>
              <span class="label-desc">切换页面时显示过渡动画效果</span>
            </div>
            <el-switch v-model="settingsStore.showTransition" />
          </div>
          <el-divider />

          <h3 class="section-title">
            <el-icon><View /></el-icon>辅助功能
          </h3>
          <div class="setting-item">
            <div class="setting-label">
              <span class="label-text">灰色模式</span>
              <span class="label-desc">将页面整体置灰，适用于特殊场景</span>
            </div>
            <el-switch v-model="settingsStore.grayMode" @change="settingsStore.applyTheme()" />
          </div>
          <div class="setting-item">
            <div class="setting-label">
              <span class="label-text">色弱模式</span>
              <span class="label-desc">调整颜色对比度，改善色弱用户体验</span>
            </div>
            <el-switch v-model="settingsStore.colorWeakMode" @change="settingsStore.applyTheme()" />
          </div>
          <el-divider />
          <div class="setting-actions">
            <el-popconfirm title="确定要恢复所有个性化设置为默认值吗？" @confirm="resetPersonalization">
              <template #reference>
                <el-button type="warning" plain>
                  <el-icon><RefreshLeft /></el-icon>恢复默认
                </el-button>
              </template>
            </el-popconfirm>
          </div>
        </div>
      </el-tab-pane>

      <!-- ==================== 网站信息 ==================== -->
      <el-tab-pane label="网站信息" name="site">
        <div class="settings-section">
          <h3 class="section-title">
            <el-icon><Monitor /></el-icon>基本信息
          </h3>
          <el-form label-width="100px" class="site-form">
            <el-form-item label="网站名称">
              <el-input v-model="settingsStore.siteName" placeholder="请输入网站名称" maxlength="20" show-word-limit />
            </el-form-item>
            <el-form-item label="网站描述">
              <el-input v-model="settingsStore.siteDescription" type="textarea" :rows="3"
                placeholder="请输入网站描述" maxlength="100" show-word-limit />
            </el-form-item>
            <el-form-item label="页脚信息">
              <el-input v-model="settingsStore.siteFooter" placeholder="例如：Copyright © 2025 xxx" maxlength="100" />
            </el-form-item>
            <el-form-item label="备案号">
              <el-input v-model="settingsStore.siteIcp" placeholder="例如：京ICP备xxxxxxxx号" maxlength="50" />
            </el-form-item>
          </el-form>
          <el-divider />
          <div class="setting-actions">
            <el-popconfirm title="确定要恢复网站信息为默认值吗？" @confirm="resetSiteInfo">
              <template #reference>
                <el-button type="warning" plain>
                  <el-icon><RefreshLeft /></el-icon>恢复默认
                </el-button>
              </template>
            </el-popconfirm>
          </div>
        </div>
      </el-tab-pane>

      <!-- ==================== 关于系统 ==================== -->
      <el-tab-pane label="关于系统" name="about">
        <div class="settings-section about-section">
          <div class="about-header">
            <img alt="Logo" src="@/assets/img/logo.svg" class="about-logo" />
            <div class="about-title-block">
              <h2 class="about-name">{{ settingsStore.siteName }}</h2>
              <p class="about-desc">{{ settingsStore.siteDescription }}</p>
            </div>
          </div>
          <el-divider />
          <h3 class="section-title">
            <el-icon><InfoFilled /></el-icon>系统信息
          </h3>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="系统版本">v1.0.0</el-descriptions-item>
            <el-descriptions-item label="开源协议">MulanPSL-2.0</el-descriptions-item>
            <el-descriptions-item label="前端框架">Vue 3 + Vite + Element Plus</el-descriptions-item>
            <el-descriptions-item label="后端框架">Spring Boot 3 + MyBatis</el-descriptions-item>
            <el-descriptions-item label="状态管理">Pinia</el-descriptions-item>
            <el-descriptions-item label="权限认证">Sa-Token</el-descriptions-item>
            <el-descriptions-item label="数据库">MySQL 8.0</el-descriptions-item>
            <el-descriptions-item label="缓存">Redis</el-descriptions-item>
          </el-descriptions>
          <el-divider />
          <h3 class="section-title">
            <el-icon><Cpu /></el-icon>技术栈
          </h3>
          <div class="tech-tags">
            <el-tag v-for="tag in techTags" :key="tag.name" :type="tag.type" class="tech-tag" effect="plain" round>
              {{ tag.name }}
            </el-tag>
          </div>
          <el-divider />
          <h3 class="section-title">
            <el-icon><Link /></el-icon>相关链接
          </h3>
          <div class="about-links">
            <el-link type="primary" href="https://cn.vuejs.org/" target="_blank">
              <el-icon><Link /></el-icon>Vue 3 官方文档
            </el-link>
            <el-link type="primary" href="https://element-plus.org/zh-CN/" target="_blank">
              <el-icon><Link /></el-icon>Element Plus
            </el-link>
            <el-link type="primary" href="https://spring.io/projects/spring-boot" target="_blank">
              <el-icon><Link /></el-icon>Spring Boot
            </el-link>
            <el-link type="primary" href="https://sa-token.cc/" target="_blank">
              <el-icon><Link /></el-icon>Sa-Token
            </el-link>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { useSettingsStore } from "@/store/modules/settings";
import {
  Brush, Cpu, InfoFilled, Link, Monitor, RefreshLeft, Setting, View
} from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import { ref } from "vue";

const settingsStore = useSettingsStore();
const activeTab = ref('personalization');

const presetColors = [
  '#409eff', '#722ed1', '#eb2f96', '#f5222d',
  '#fa541c', '#faad14', '#13c2c2', '#52c41a',
];

const techTags = [
  { name: 'Vue 3', type: '' },
  { name: 'Vite', type: 'success' },
  { name: 'Element Plus', type: '' },
  { name: 'Pinia', type: 'warning' },
  { name: 'Vue Router', type: 'success' },
  { name: 'Axios', type: 'info' },
  { name: 'ECharts', type: 'danger' },
  { name: 'Spring Boot 3', type: 'success' },
  { name: 'MyBatis', type: '' },
  { name: 'Sa-Token', type: 'warning' },
  { name: 'MySQL', type: '' },
  { name: 'Redis', type: 'danger' },
  { name: 'Lombok', type: 'info' },
  { name: 'Swagger/Knife4j', type: 'success' },
];

const changeThemeColor = (color) => {
  settingsStore.themeColor = color;
  settingsStore.applyTheme();
  ElMessage.success('主题色已更新');
};

const onThemeColorChange = (val) => {
  if (val) {
    settingsStore.applyTheme();
    ElMessage.success('主题色已更新');
  }
};

const resetPersonalization = () => {
  settingsStore.resetPersonalization();
  settingsStore.applyTheme();
  ElMessage.success('个性化设置已恢复默认');
};

const resetSiteInfo = () => {
  settingsStore.resetSiteInfo();
  ElMessage.success('网站信息已恢复默认');
};
</script>

<style lang="scss" scoped>
@use "@/assets/css/admin/settings";
</style>
