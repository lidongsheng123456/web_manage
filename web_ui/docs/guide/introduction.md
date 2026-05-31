# 项目简介

**东神脚手架** 是一套基于 Spring Boot 3 + Vue 3 的全栈管理系统脚手架，采用前后端分离架构，帮助开发者快速搭建标准化的企业级管理后台。

## 项目架构

项目采用多模块 Maven 结构，后端分为 4 个核心模块，前端使用 Vue 3 单页应用：

```
项目根目录/
├── xxx_admin        # 启动入口 + Controller 层
├── xxx_system       # Service + Mapper + Domain（业务逻辑层）
├── xxx_framework    # 框架配置（Redis / Sa-Token / WebMvc）
├── xxx_common       # 公共工具（实体 / 枚举 / 异常 / 工具类）
└── xxx_ui           # Vue 3 前端
```

> `xxx` 为项目前缀，如 `web`、`book`、`car`、`shop` 等，按业务自定义命名。

## 后端模块职责

| 模块 | 职责 | 关键内容 |
|:---|:---|:---|
| `xxx_admin` | 启动入口 + 接口层 | 包含 `controller.admin`（后台）、`controller.user`（前台）、`controller.common`（公共） |
| `xxx_system` | 业务逻辑 + 数据访问 | Service 接口、Mapper 接口、Domain 实体 |
| `xxx_framework` | 框架级配置 | Sa-Token 鉴权、MyBatis Plus、Redis、WebMvc 跨域等 |
| `xxx_common` | 通用工具 | 公共实体、枚举常量、全局异常处理、工具类 |

## 前端目录结构

```
xxx_ui/src/
├── api/                  # 接口请求封装
│   ├── admin_request/    # 后台 API
│   ├── front_request/    # 前台 API
│   └── com_request/      # 公共 API
├── assets/               # 静态资源（CSS / 图片）
├── components/           # 公共组件
├── directive/            # 自定义指令（防抖 / 权限）
├── router/               # 路由配置
├── store/                # Pinia 状态管理
├── views/
│   ├── background/       # 后台管理页面
│   └── front/            # 前台展示页面
└── utils/                # 工具函数（Axios 封装等）
```

## 权限体系

系统采用 **双鉴权体系** 设计：

- **后台管理端**：RBAC 模型（用户 → 角色 → 权限），通过 `sys_permission` 表管理接口级权限
- **前台用户端**：基于登录状态鉴权，无需细粒度接口权限

## 内置功能

| 功能 | 说明 |
|:---|:---|
| 数据中心 | 首页数据统计大屏 |
| 用户管理 | 后台管理员增删改查 |
| 角色管理 | RBAC 角色分配 |
| 权限管理 | 菜单 & 接口权限配置 |
| 字典管理 | 系统枚举值统一管理 |
| 通用查询 | 外键关联下拉选项管理 |
| 日志管理 | 操作日志记录与查询 |
| 通知管理 | 系统公告发布 |
| 文件管理 | 文件上传、预览、删除 |
| 系统设置 | 主题色、水印、站点信息等 |
| 前台用户 | 前台注册用户管理 |
