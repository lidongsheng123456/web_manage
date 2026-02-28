---
name: web-manage-fullstack
description: "东神脚手架全栈业务二次开发工作流。用于在 Spring Boot 3 + Vue 3 前后端分离项目中开发新的业务模块。涵盖：(1) 数据库设计与字典/通用查询 SQL 生成，(2) 后端 Domain/Mapper/Service/Controller 全套代码生成，(3) 前端后台管理页面(Element Plus)开发，(4) 前端前台用户页面(液态玻璃 Liquid Glass UI)开发，(5) 路由与权限配置，(6) 业务图片批量抓取与 img_url 填充。触发场景：用户要求新增业务模块、CRUD 功能、全栈开发、业务图片下载、img_url 填充等。"
---

# 东神脚手架全栈业务开发 Skill

## 快速概览：7 步开发流程

| Step | 任务 | 详情参考 |
|:-----|:-----|:---------|
| 1 | 数据库设计 (建表 + 字典 + 通用查询 SQL) | [backend-workflow.md](references/backend-workflow.md) |
| 2 | 后端基础层 (Domain + Mapper + Service) | [backend-workflow.md](references/backend-workflow.md) |
| 3 | 后端接口层 (AdminController + UserController) | [backend-workflow.md](references/backend-workflow.md) |
| 4 | 前端后台管理 (API + ManageView) | [frontend-admin-workflow.md](references/frontend-admin-workflow.md) |
| 5 | 前端前台用户 (API + 液态玻璃页面) | [frontend-user-workflow.md](references/frontend-user-workflow.md) |
| 6 | 路由与权限配置 | [frontend-user-workflow.md](references/frontend-user-workflow.md) |
| 7 | 自检清单 | 见下方 |

## 业务图片抓取工具

当业务表有 `img_url` 字段且需要预填充图片时，运行 `scripts/fetch_images.py`。

### 基本用法

```bash
# 自动检测 *_ui 目录 (web_ui, branch_ui, course_ui 等均可)
python scripts/fetch_images.py -k "猫咪" -n 5 -p D:/project/web_manage
python scripts/fetch_images.py -k "laptop" -n 3 -p D:/project/branch_manage -t sys_product
python scripts/fetch_images.py -k "课程" -n 4 -p D:/project/course_manage -t sys_course

# 手动指定前端图片目录 (跳过自动检测)
python scripts/fetch_images.py -k "电源" -n 4 -p D:/project/course_manage --img-dir D:/project/course_manage/course_ui/src/assets/img

# 使用 Pexels API (更高质量)
python scripts/fetch_images.py -k "动物" -n 6 -p D:/project/web_manage --source pexels --api-key YOUR_KEY
```

### 脚本功能

1. **搜索图片**: 支持 Bing (默认，无需 API Key)、Pexels (需 API Key)、Unsplash
2. **自动检测**: 扫描项目根目录下的 `*_ui/src/assets/img` 目录，适配任意业务前缀
3. **双路径保存**:
   - 前端: `{xxx}_ui/src/assets/img/{keyword}_{n}.jpg` (自动检测)
   - 后端: `{project_root}/files/{keyword}_{n}.jpg`
3. **SQL 生成**: 输出 `img_url` 格式为 `http://localhost:{port}/common/files/{filename}`
4. **可选 UPDATE SQL**: 使用 `--table` 参数指定表名，自动生成 `UPDATE ... SET img_url = ...`

### 参数说明

| 参数 | 必须 | 默认值 | 说明 |
|:-----|:-----|:-------|:-----|
| `-k, --keyword` | ✅ | - | 搜索关键词 |
| `-n, --count` | ❌ | 5 | 下载数量 |
| `-p, --project-root` | ✅ | - | 项目根目录 |
| `--img-dir` | ❌ | 自动检测 | 直接指定前端图片目录 (跳过自动检测) |
| `--files-dir` | ❌ | {root}/files | 直接指定后端文件目录 |
| `--backend-port` | ❌ | 8088 | 后端端口 |
| `--source` | ❌ | bing | 图片来源 (bing/pexels/unsplash) |
| `--api-key` | ❌ | - | Pexels API Key |
| `-t, --table` | ❌ | - | 目标表名 (生成 SQL) |
| `--id-start` | ❌ | 1 | SQL id 起始值 |
| `--filename-prefix` | ❌ | keyword | 文件名前缀 |

### img_url 存储规范

- 数据库存储格式: `http://localhost:8088/common/files/{filename}.jpg`
- 后端文件服务路径: `{project_root}/files/{filename}`
- 上传接口: `POST /common/files/upload` → 返回完整 URL
- 访问接口: `GET /common/files/{flag}` → 直接预览

## 自检清单

### 数据库
- [ ] 状态/类型字段已插入 `sys_dict_data`
- [ ] 外键关联字段已插入 `sys_com_query`

### 后端
- [ ] Domain: `@Data`, `@TableId`, 日期序列化注解完整
- [ ] 图片字段命名 `imgUrl`，存储完整 URL
- [ ] AdminController: `@SaCheckPermission` + `@Log`
- [ ] UserController: 使用 `StpUserUtil` (非 `StpUtil`)

### 后台管理前端
- [ ] 字典字段: `queryDictByType` + `selectDictLabel` + `selectTagType`
- [ ] 外键字段: `queryComQueryByCode`
- [ ] 多选字段: 数组 ↔ 字符串转换
- [ ] 图片字段: `el-upload` 组件 (非 `el-input`)
- [ ] 按钮: `v-no-more-click` 防抖
- [ ] 导出: `uploadUrl + '/admin/模块/export'`

### 前台用户前端
- [ ] 液态玻璃 CSS 变量和基础类
- [ ] 图片: `item.imgUrl || defaultImg` (本地 SVG 占位)
- [ ] 图标: 内联 SVG (无 emoji)
- [ ] 无外部 CDN 依赖
- [ ] Hover: `translateY` (无 `scale`)
