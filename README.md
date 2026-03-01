# 东神脚手架

> **一条指令，全栈交付。** AI 驱动的 7 步全流程自动化开发平台。

---

## 项目定位

东神脚手架是一个 **AI 全流程自动化** 全栈开发平台。从数据库建表到后端三层架构、从后台管理页面到前台液态玻璃 UI、再到权限配置与代码质检 —— **7 步全链路 AI 自动完成，零手动编码**。

- **作者**：李东升
- **架构**：Spring Boot 3 (后端) + Vue 3 (前端) 前后端分离
- **仓库**：[Gitee](https://gitee.com/li-dongshenger/web_manage)
- **运行环境下载**：[百度网盘](https://pan.baidu.com/s/1zU8TWzNMtv5kNSL5H9Q5_w?pwd=ys73)

---

## 全流程自动化 Pipeline

| 步骤 | 阶段 | AI 自动产出 |
|:---:|:---|:---|
| 01 | **数据库设计** | CREATE TABLE、INSERT 字典数据、索引优化 |
| 02 | **后端基础层** | @Data Entity、BaseMapper、IService 实现 |
| 03 | **后端接口层** | Admin + User 双 Controller、@SaCheckPermission、@Log |
| 04 | **后台管理页面** | Element Plus 表格 / 表单 / 字典回显 / 权限按钮 |
| 05 | **前台用户页面** | Liquid Glass 液态玻璃风格、响应式布局、Vanta.js 3D 背景 |
| 06 | **路由与权限配置** | Vue Router 自动注册、sys_permission SQL 生成 |
| 07 | **代码质检** | 7 维度审查：命名规范、权限完整性、字典一致性等 |

---

## 核心架构亮点

### RBAC 细粒度权限
用户-角色-权限三级模型，Sa-Token 实现 **接口级 + 按钮级** 双重控制。后台 Admin 与前台 User 独立鉴权体系。

### 多模块 Maven 分层
`admin` / `system` / `framework` / `common` 四模块职责清晰：
- **admin** — 启动入口 + Controller 层 (`controller.admin` / `controller.user` / `controller.common`)
- **system** — Service + Mapper + Domain 业务逻辑
- **framework** — Sa-Token、MyBatis Plus、Redis 等框架配置
- **common** — 通用实体、工具类、常量

### Redis 缓存加速
首页热点数据缓存，采用开源中国式 **ID 列表 + 条目缓存** 策略，大幅提升查询性能。

### 字典系统
状态/类型等枚举值统一管理 (`sys_dict_data`)，前端自动回显 `el-tag` 颜色标签，告别硬编码。

### 液态玻璃 UI 设计
iOS 26 Liquid Glass 设计语言，`backdrop-filter` 毛玻璃效果 + Vanta.js 3D 云层背景动画，全局响应式适配。

### Knife4j 接口文档
集成 OpenAPI 3 可视化接口文档，支持在线调试，前后端联调零障碍。

---

## 技术栈

### 后端

| 技术 | 版本 | 职责 |
|:---|:---|:---|
| Spring Boot | 3.0.6 | 核心框架 |
| JDK | 17.0.13 | 运行环境 |
| MySQL | 8.0.36 | 关系型数据库 |
| Redis | 5.0.14 | 缓存中间件 |
| Sa-Token | 1.38.0 | 权限认证 |
| MyBatis Plus | 3.0.3 | ORM 框架 |
| Knife4j | 4.3.0 | 接口文档 |
| Hutool | 5.8.26 | 工具集 |

### 前端

| 技术 | 版本 | 职责 |
|:---|:---|:---|
| Vue 3 | 3.2.13 | 核心框架 |
| Element Plus | 2.9.3 | UI 组件库 (后台) |
| Vite | Latest | 构建工具 |
| Vue Router | 4.0.3 | 路由管理 |
| Axios | 1.7.9 | HTTP 请求 |
| Vanta.js | Latest | 3D 背景动画 (前台) |

### AI 工具链

| 工具 | 职责 |
|:---|:---|
| Windsurf IDE | AI 代码生成引擎 |
| AgentSkill | 项目规范强制执行 |
| MCP Server | 后台数据实时联调 |
| UI/UX Pro Max | 设计系统搜索引擎 |

---

## 系统架构

```
┌─────────────────────────────────────────┐
│              Frontend (Vue 3)           │
│   Element Plus · Vite · Liquid Glass    │
└──────────────────┬──────────────────────┘
                   │ Axios / REST API
┌──────────────────▼──────────────────────┐
│           Controller Layer              │
│    admin/*  ·  user/*  ·  common/*      │
└──────────────────┬──────────────────────┘
                   │ Sa-Token RBAC
┌──────────┬───────▼──────┬───────────────┐
│ Service  │   Mapper     │    Redis      │
│ 业务逻辑  │ MyBatis Plus │    缓存       │
└──────────┴───────┬──────┴───────────────┘
                   │ JDBC
┌──────────────────▼──────────────────────┐
│             MySQL 8.0                   │
│  业务表 · sys_permission · sys_role     │
│  sys_dict_data · sys_user              │
└─────────────────────────────────────────┘
```

---

## 功能模块

### 后台管理

| 模块 | 说明 |
|:---|:---|
| 用户管理 | 管理后台用户账户及基本信息 |
| 角色管理 | 多角色配置，RBAC 权限分配 |
| 权限管理 | 接口级 + 按钮级细粒度控制 |
| 字典管理 | 维护状态/类型枚举值，支持颜色标签 |
| 通知管理 | 后台发送通知，前台滚动展示 |
| 接口文档 | Knife4j 可视化接口测试 |
| 通用查询 | 动态字典映射、外键字段转具体值 |

### 前台展示

| 模块 | 说明 |
|:---|:---|
| 首页 | 液态玻璃风格，Vanta.js 3D 云层背景 |
| 通知展示 | 顶部滚动通知栏 |
| 用户登录/注册 | 前台独立鉴权体系 |

---

## AI 工作流

本项目集成 `.windsurf` 目录下的 AI 工作流配置：

| 命令 | 说明 |
|:---|:---|
| `/fullstack-project-dev` | 全栈业务模块开发 7 步工作流 |
| `/ui-ux-pro-max` | UI/UX 设计系统搜索与实现 |

### 工作流核心能力

- **自动化代码生成** — 数据库 → 后端 → 前端 → 配置，全链路覆盖
- **规范强制执行** — AgentSkill 确保代码风格、架构、命名一致性
- **MCP 数据联调** — 通过 MCP Server 直连后台，实时验证接口数据
- **设计系统搜索** — UI/UX Pro Max 提供色彩、排版、布局最佳实践
- **质量自检** — 7 维度代码审查自动执行

---

## License

&copy; 2025 东神脚手架 by 李东升. All rights reserved.
