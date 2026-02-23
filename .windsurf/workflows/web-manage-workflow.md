# 全栈业务开发工作流 (Full-Stack Development Workflow)

当需要开发一个新的业务模块（例如：`Article` 文章系统）时，请严格按照以下 7 个步骤执行，确保前后台功能闭环。

**开始前**: 阅读 `global_rules.md` 了解项目架构、设计规范、图片上传机制和液态玻璃 UI 体系。

## Step 1: 数据库设计 (Database)
生成 SQL 建表语句。
- **表名**: `sys_article` (建议带前缀)。
- **必须字段**: `id`, `create_time`, `update_time`, `create_user_id`。
- **图片字段**: 若需要图片，添加 `img_url varchar(500) COMMENT '图片URL'`，存储后端返回的完整 URL。
- **示例**:
  ```sql
  CREATE TABLE `sys_article` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title` varchar(255) COMMENT '标题',
    `content` text COMMENT '内容',
    `img_url` varchar(500) COMMENT '封面图URL',
    `status` tinyint DEFAULT 1 COMMENT '状态(1上架 0下架)',
    `sort_order` int DEFAULT 0 COMMENT '排序',
    `create_time` datetime COMMENT '创建时间',
    `update_time` datetime COMMENT '更新时间',
    `create_user_id` bigint COMMENT '发布人ID',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';
  ```

## Step 2: 后端基础层 (Backend Basic)
生成位置: `{业务前缀}_system` 模块。
1.  **Domain**: `com.example.system.domain.Article`
    - 必须注解: `@Data`, `@EqualsAndHashCode(callSuper = false)`, `@Accessors(chain = true)`
    - 主键: `@TableId(value = "id", type = IdType.AUTO)`
    - 图片字段: `private String imgUrl;` (存储完整 URL)
    - 时间字段: 加 `@JsonFormat` + `@JsonSerialize` + `@JsonDeserialize` (参考 rules.md 3.1)
2.  **Mapper**: `com.example.system.mapper.AdminArticleMapper` (及对应 XML)。
3.  **Service**:
    - 接口: `com.example.system.service.AdminArticleService`。
    - 实现: `com.example.system.service.impl.AdminArticleServiceImpl`。

## Step 3: 后端接口层 (Backend Controllers)
生成位置: `{业务前缀}_admin` 模块。**必须生成两个 Controller**：

1.  **后台管理接口 (`AdminController`)**:
    - 路径: `com.example.controller.admin.AdminArticleController`
    - URL: `/admin/article`
    - 功能: 增(Add)、删(Delete)、改(Update)、查(Query Page)、导出(Export)。
    - **规范**: 加上 `@SaCheckPermission`, `@Log`。
    - 用户ID: `StpUtil.getLoginIdAsLong()`

2.  **前台用户接口 (`UserController`)**:
    - 路径: `com.example.controller.user.UserArticleController`
    - URL: `/user/article`
    - 功能: 查列表(List)、查详情(Detail)。
    - **规范**: 使用 `StpUserUtil.getLoginIdAsLong()` 获取当前用户。

## Step 4: 前端后台管理开发 (Frontend Admin)
生成位置: `{业务前缀}_ui/src`。
1.  **API**: `api/admin_request/ArticleRequest.js` (引用 `AdminRequest`)。
2.  **View**: `views/background/manage/ManageArticleView.vue`。
    - 复制 `ManageNoticeView.vue` 模板。
    - 替换字段为 `title`, `content` 等。
    - 确保权限标识为 `admin:article:add` 等。
    - **图片字段 (重要)**: 若有 `imgUrl`，**必须使用 `el-upload` 上传组件**，禁止用 `el-input` 文本框。
      ```html
      <el-form-item label="封面图" prop="imgUrl">
        <el-upload :action="uploadUrl + '/common/files/upload'"
            :before-upload="beforeAvatarUpload" :on-success="handleAvatarSuccess"
            :show-file-list="false" class="img-uploader">
          <img v-if="form.imgUrl" :src="form.imgUrl" alt="" class="upload-preview"/>
          <el-icon v-else class="upload-icon"><Plus/></el-icon>
        </el-upload>
      </el-form-item>
      ```
    - **上传回调**: 添加 `handleAvatarSuccess` (将 `response.data` 赋值给 `form.value.imgUrl`) 和 `beforeAvatarUpload` (校验 JPG/PNG，≤10MB)。
    - **上传样式**: 在 `<style scoped>` 中添加 `.img-uploader`, `.upload-preview`, `.upload-icon` 样式 (178x178)。

## Step 5: 前端前台用户开发 (Frontend User)
生成位置: `{业务前缀}_ui/src`。
1.  **API**: `api/front_request/ArticleRequest.js`。
    - 引用 `import userRequest from "@/utils/UserRequest"`。
    - 封装 `queryArticleList`, `queryArticleById` 方法。
2.  **View**:
    - `views/front/article/ArticleListView.vue`: 液态玻璃风格卡片列表。
    - `views/front/article/ArticleDetailView.vue`: 液态玻璃风格详情页。
3.  **UI 风格要求** (液态玻璃):
    - 页面容器使用 `.lg-page-bg` 类或 `background: var(--lg-page-bg)`。
    - 卡片使用 `backdrop-filter: blur(var(--glass-blur))` + `var(--glass-bg)` 背景 + `var(--glass-border)` 边框。
    - Hover 效果: `translateY(-4px)` + `var(--glass-shadow-elevated)`, **禁止 scale**。
    - 图标使用内联 SVG (Lucide 风格)，**禁止 emoji**。
    - 图片展示: `<img :src="item.imgUrl || defaultImg">`, 默认占位图使用 `import defaultImgUrl from '@/assets/img/default_scenic.svg'`。
    - **禁止使用外部 CDN 图片 URL** 作为默认占位图。
    - 参考 `liquid-glass.css` 中的变量和基础类 (`.glass-panel`, `.glass-card`, `.glass-tag` 等)。
    - 动画过渡: `transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1)`。
    - 文字色: 标题 `var(--lg-text-primary)`, 正文 `var(--lg-text-secondary)`, 辅助 `var(--lg-text-muted)`。

## Step 6: 路由与权限配置 (Configuration)

1.  **路由配置 (`router/index.js`)**:
    - **后台**: 在 `/Manage` -> `children` 添加 `ManageArticleView`。
    - **前台**: 在 `/Front` -> `children` 添加 `ArticleListView` 和 `ArticleDetailView`。
2.  **权限 SQL**:
    - 生成 `INSERT INTO sys_permission` 语句。
    - 必须包含后台管理的 5 个权限点: `admin:article:query`, `add`, `delete`, `update`, `export`。

## Step 7: 自检清单 (Pre-Delivery Checklist)

### 后端
- [ ] Domain 实体注解完整 (`@Data`, `@TableId`, 日期序列化)
- [ ] 图片字段命名为 `imgUrl`，存储完整 URL
- [ ] AdminController 有 `@SaCheckPermission` + `@Log`
- [ ] UserController 使用 `StpUserUtil` (非 `StpUtil`)

### 后台管理前端
- [ ] 图片字段使用 `el-upload` 组件上传，而非 `el-input` 文本框
- [ ] 有 `handleAvatarSuccess` 和 `beforeAvatarUpload` 方法
- [ ] 按钮有 `v-no-more-click` 防抖
- [ ] 导出按钮使用 `uploadUrl + '/admin/模块/export'`

### 前台用户前端
- [ ] 使用液态玻璃 CSS 变量和基础类
- [ ] 图片展示使用 `item.imgUrl || defaultImg`，默认图为本地 SVG
- [ ] 无 emoji 图标，全部使用内联 SVG
- [ ] 无外部 CDN 图片依赖
- [ ] Hover 使用 `translateY` 不使用 `scale`
- [ ] 页面背景为 CSS 渐变，不依赖外部图片