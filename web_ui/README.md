# web_ui — 东神脚手架前端

> Vue 3 + Element Plus + Liquid Glass UI，AI 驱动的全栈前端工程。

---

## 快速开始

```bash
# 安装依赖
npm install

# 开发环境启动
npm run dev

# 生产环境构建
npm run build

# 代码检查与修复
npm run lint
```

---

## 技术栈

| 技术 | 版本 | 职责 |
|:---|:---|:---|
| Vue 3 | 3.2.13 | 核心框架 |
| Element Plus | 2.9.3 | 后台 UI 组件库 |
| Vite | Latest | 构建工具 |
| Vue Router | 4.0.3 | 路由管理 |
| Axios | 1.7.9 | HTTP 请求 |
| Vanta.js | Latest | 3D 背景动画 (前台) |
| ECharts | Latest | 数据大屏图表 |
| vue-i18n | Latest | 国际化多语言 |

---

## 功能模块

### 后台管理 (`views/admin/`)

| 模块 | 说明 |
|:---|:---|
| 用户管理 | 管理后台用户账户及基本信息 |
| 角色管理 | 多角色配置，RBAC 权限分配 |
| 权限管理 | 接口级 + 按钮级细粒度控制 |
| 字典管理 | 维护状态/类型枚举值，Pinia 全局缓存 |
| 通知管理 | 后台发送通知，前台滚动展示 |
| 在线用户管理 | Sa-Token 查询在线用户，支持强制踢下线 |
| 登录日志 | IP 归属地解析 + 浏览器/OS 信息采集 |
| Excel 导入导出 | EasyExcel + el-upload 批量数据导入与模板导出 |
| 数据大屏 | ECharts Dashboard 多维度统计图表 |
| 个人消息中心 | 已读/未读状态 + 小红点实时提醒 |
| 文件管理器 | 可视化文件管理、在线预览、下载 |

### 前台展示 (`views/front/`)

| 模块 | 说明 |
|:---|:---|
| 首页 | Liquid Glass 液态玻璃风格，Vanta.js 3D 云层背景 |
| 通知展示 | 顶部滚动通知栏 |
| WebSocket 实时通知 | STOMP 协议 + el-notification 弹窗推送 |
| 国际化 i18n | vue-i18n + Element Plus locale 多语言切换 |
| 用户登录/注册 | 前台独立鉴权体系 |

### 全局特性

| 特性 | 说明 |
|:---|:---|
| 暗黑模式 | `useDark()` + Element Plus Dark Theme 全局切换 |
| 页面水印 | Canvas 生成 + MutationObserver 防删除 |
| 接口限流提示 | 配合后端 Redis 注解式限流，前端友好提示 |
| 数据字典缓存 | Pinia Store 全局缓存字典数据，减少重复请求 |

---

## 项目结构

```
src/
├── api/           # 接口请求模块
├── assets/        # 静态资源 & 样式
├── views/
│   ├── admin/     # 后台管理页面 (Element Plus)
│   └── front/     # 前台用户页面 (Liquid Glass UI)
├── App.vue        # 根组件
├── main.ts        # 入口文件
└── env.d.ts       # 类型声明
```

---

## 环境配置

| 文件 | 说明 |
|:---|:---|
| `.env.development` | 开发环境变量 |
| `.env.production` | 生产环境变量 |

---

## License

&copy; 2025-2026 东神脚手架 by 李东升. All rights reserved.
