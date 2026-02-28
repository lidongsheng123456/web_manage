---
description: 东神脚手架全栈项目自动化开发流程。从脚手架克隆到完整业务系统交付的 4 步工作流。适用于：新建业务项目、毕设项目快速开发、全栈系统搭建。
---

# 全栈项目自动化开发工作流

将东神脚手架 `web_manage` 快速转化为一个完整的业务系统（如课程管理、分支管理、宠物商城等）。

**前置条件**: 已克隆 `web_manage` 脚手架到本地目录。

---

## Step 1: 重命名项目前缀 (Rename Project Prefix)

将所有 `web` 前缀替换为业务名称（如 `branch`、`course`、`pet`），完成项目个性化。

> 用户需提供: **业务前缀名** (如 `course`)，以下用 `{BIZ}` 表示。

### 1.1 重命名文件夹

```
web_manage/     → {BIZ}_manage/     (项目根目录名，需手动重命名或通过 IDE)
web_admin/      → {BIZ}_admin/
web_common/     → {BIZ}_common/
web_framework/  → {BIZ}_framework/
web_system/     → {BIZ}_system/
web_ui/         → {BIZ}_ui/
```

### 1.2 修改根 pom.xml

文件: `pom.xml`

```xml
<!-- artifactId -->
<artifactId>{BIZ}_manage</artifactId>

<!-- modules -->
<modules>
    <module>{BIZ}_admin</module>
    <module>{BIZ}_common</module>
    <module>{BIZ}_framework</module>
    <module>{BIZ}_system</module>
</modules>

<!-- properties 中的内部模块版本变量 -->
<{BIZ}_common.version>0.0.1-SNAPSHOT</{BIZ}_common.version>
<{BIZ}_framework.version>0.0.1-SNAPSHOT</{BIZ}_framework.version>
<{BIZ}_system.version>0.0.1-SNAPSHOT</{BIZ}_system.version>

<!-- dependencyManagement 中的内部模块引用 -->
<artifactId>{BIZ}_common</artifactId>
<version>${{BIZ}_common.version}</version>

<artifactId>{BIZ}_framework</artifactId>
<version>${{BIZ}_framework.version}</version>

<artifactId>{BIZ}_system</artifactId>
<version>${{BIZ}_system.version}</version>
```

### 1.3 修改子模块 pom.xml (4 个)

每个子模块 pom.xml 都需要修改:

**{BIZ}_admin/pom.xml:**
```xml
<parent>
    <artifactId>{BIZ}_manage</artifactId>
</parent>
<artifactId>{BIZ}_admin</artifactId>
<description>{BIZ}服务入口</description>
<!-- 依赖 -->
<artifactId>{BIZ}_framework</artifactId>
<!-- mainClass -->
<mainClass>com.example.{Biz}ManageApplication</mainClass>
```

**{BIZ}_system/pom.xml:**
```xml
<parent>
    <artifactId>{BIZ}_manage</artifactId>
</parent>
<artifactId>{BIZ}_system</artifactId>
<description>{BIZ}系统模块</description>
<artifactId>{BIZ}_common</artifactId>
```

**{BIZ}_framework/pom.xml:**
```xml
<parent>
    <artifactId>{BIZ}_manage</artifactId>
</parent>
<artifactId>{BIZ}_framework</artifactId>
<description>{BIZ}核心框架</description>
<artifactId>{BIZ}_system</artifactId>
```

**{BIZ}_common/pom.xml:**
```xml
<parent>
    <artifactId>{BIZ}_manage</artifactId>
</parent>
<artifactId>{BIZ}_common</artifactId>
<description>{BIZ}公共模块</description>
```

### 1.4 修改启动类

文件: `{BIZ}_admin/src/main/java/com/example/WebManageApplication.java`
- 重命名文件为 `{Biz}ManageApplication.java` (首字母大写驼峰)
- 修改类名: `public class {Biz}ManageApplication`

### 1.5 修改数据库配置

文件: `{BIZ}_admin/src/main/resources/application-develop.yml`

```yaml
web:
  datasource:
    database: {BIZ}_manage    # 数据库名改为业务名
    username: root
    password: root             # 密码统一为 root
```

### 1.6 重命名 SQL 文件

```
web_manage.sql → {BIZ}_manage.sql
```

修改 SQL 文件内第一行的 `CREATE DATABASE` (如有):
```sql
CREATE DATABASE IF NOT EXISTS `{BIZ}_manage`;
USE `{BIZ}_manage`;
```

### 1.7 修改前端环境变量 (如需要)

文件: `{BIZ}_ui/.env.development`
- 确认 `VUE_APP_BASEURL` 指向正确的后端地址

### 1.8 验证

- [ ] 所有 pom.xml 无红线错误
- [ ] Maven reload 成功
- [ ] 启动类可正常运行
- [ ] 数据库可正常连接

---

## Step 2: 实现业务功能 (Implement Business Features)

使用 `web-manage-fullstack` 技能实现所有业务代码。

### 提示词模板

```
帮我实现{业务名称}系统的用户前台和管理员后台，至少五个功能模块，
业务权限需要分配给超级管理员，sql业务表需要有对应测试数据。
```

示例:
```
帮我实现宠物商城系统的用户前台和管理员后台，至少五个功能模块，
业务权限需要分配给超级管理员，sql业务表需要有对应测试数据。
```

### AI 生成后手动操作

1. **导入 SQL**: 将 AI 生成的所有 SQL (建表 + 字典 + 通用查询 + 测试数据 + 权限 + 角色权限分配) 复制到 Navicat/DataGrip 执行
2. **验证权限**: 登录后台管理系统，确认超级管理员可以看到所有新菜单
3. **检查前台**: 访问用户前台，确认页面可正常加载

### 可选：业务图片填充

如果业务表有 `img_url` 字段，使用图片抓取脚本预填充:

```bash
# 使用 uv 运行脚本 (按业务关键词搜索图片)
uv run {skill_scripts_path}/fetch_images.py -k "宠物猫" -n 5 -p {项目根目录} -t sys_pet
uv run {skill_scripts_path}/fetch_images.py -k "宠物狗" -n 5 -p {项目根目录} -t sys_pet --id-start 6
```

将输出的 UPDATE SQL 在数据库中执行即可。

---

## Step 3: 优化前台 UI/UX (Optimize Frontend UI)

使用 `/ui-ux-pro-max` 工作流优化所有前台页面。

### 提示词模板

```
使用uv运行py，移除用户前台首页所有的内容和背景云朵，因为这跟业务无关，
并且重构用户前台所有页面，页面效果：简约、高级、大气，让人眼前一亮的现代化UI/UX
```

### 优化范围

1. **首页 (HomeView)**: 移除无关内容/背景装饰，重构为业务相关的 Hero + 推荐模块
2. **每个业务菜单页面**: 统一液态玻璃风格，卡片列表/网格布局
3. **个人中心**: 现代化个人信息展示
4. **详情页**: 沉浸式详情布局

---

## Step 4: 调整细节完成交付 (Final Adjustments)

最后的个性化调整，避免与其他项目重复。

### 提示词模板

```
请帮我调整以下细节:
1. 修改登录页背景图和系统名称为{业务名称}系统
2. 修改默认用户名和头像
3. 调整后台首页数据可视化的统计对象为{业务相关的统计项}
4. 保留后台管理的入口菜单（确保前台有跳转到后台的入口）
5. 修改网页标题和 favicon 为{业务名称}
```

### 检查清单

- [ ] 登录页: 背景图/系统名称已替换为业务相关
- [ ] 后台首页: 数据可视化统计对象与业务匹配
- [ ] 用户名/头像: 已修改为业务相关默认值
- [ ] 前台导航栏: 有"后台管理"入口菜单
- [ ] 网页标题/favicon: 已修改
- [ ] 与其他项目无重复的 UI 元素

---

## 快速参考：完整流程总结

| 步骤 | 操作 | 耗时预估 |
|:-----|:-----|:---------|
| Step 1 | 重命名前缀 + 修改 pom.xml + 数据库配置 | 10 min |
| Step 2 | AI 生成业务代码 + 手动导入 SQL | 30 min |
| Step 3 | /ui-ux-pro-max 优化所有前台页面 | 20 min |
| Step 4 | 登录页/统计/头像/标题等细节调整 | 10 min |
