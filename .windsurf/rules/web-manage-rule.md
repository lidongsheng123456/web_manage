# 东神脚手架开发规范 (Project Rules)

## 1. 项目架构概览
- **核心架构**: Spring Boot 3 (后端) + Vue 3 (前端) 前后端分离。
- **业务模式**: 单体后端应用，通过包结构严格区分"前台业务"与"后台管理"。
- **权限体系**:
  - **后台 (Admin)**: RBAC 模型 (用户-角色-权限)，依赖 `sys_permission` 表。
  - **前台 (Front)**: 基于登录状态鉴权，无细粒度接口权限控制。
- **UI 设计体系**: iOS 26 Liquid Glass (液态玻璃) 风格，全局统一。

## 2. 目录与包结构映射 (Directory Mapping)

### 2.1 后端 (Java - 多模块 Maven)
*根包路径: `com.example`*
*模块命名: `{业务前缀}_admin` / `{业务前缀}_system` / `{业务前缀}_framework` / `{业务前缀}_common`*
*示例: 旅游系统用 `tour_*`，初始脚手架用 `web_*`*

| 模块 | 职责 | 说明 |
| :--- | :--- | :--- |
| `{业务前缀}_admin` | 启动入口 + Controller 层 | 包含 `controller.admin` / `controller.user` / `controller.common` |
| `{业务前缀}_system` | Service + Mapper + Domain | 业务逻辑和数据访问层 |
| `{业务前缀}_framework` | 框架配置 | Sa-Token、MyBatis Plus、Redis 等配置 |
| `{业务前缀}_common` | 公共工具 | 通用实体、工具类、常量 |

| 层级 | 模块划分 | 包路径 | 关键说明 |
| :--- | :--- | :--- | :--- |
| **Controller** | **后台管理接口** | `com.example.controller.admin` | 管理员用的 CRUD 接口，需鉴权 |
| | **前台用户接口** | `com.example.controller.user` | 普通用户用的业务接口，需登录 |
| | **公共接口** | `com.example.controller.common` | 文件上传(`FileController`)、通用查询等 |
| **Service** | **业务逻辑** | `com.example.system.service` | 统一存放 Service 接口，供 Admin/User Controller 共用 |
| **DAO** | **数据访问** | `com.example.system.mapper` | MyBatis Plus Mapper 接口 |
| | **XML 映射** | `src/main/resources/mapper` | SQL XML 文件 |
| **Model** | **实体类** | `com.example.system.domain` | 数据库表对应实体 |

### 2.2 前端 (Vue 3 - `{业务前缀}_ui`)
*根路径: `{业务前缀}_ui/src/`*

| 模块 | 资源类型 | 文件路径 | 关键说明 |
| :--- | :--- | :--- | :--- |
| **后台系统** | **页面视图** | `views/background/manage/` | 管理后台的功能页面 (如: `ManageView.vue`) |
| | **API 请求** | `api/admin_request/` | 封装后台接口请求 (引用 `AdminRequest.js`) |
| **前台系统** | **布局框架** | `views/front/FrontView.vue` | 前台整体布局，含统一浮动玻璃导航栏 |
| | **首页** | `views/front/home/HomeView.vue` | 液态玻璃风格 Hero + 景点/路线推荐 |
| | **API 请求** | `api/front_request/` | 封装前台接口请求 (引用 `UserRequest.js`) |
| **设计系统** | **全局样式** | `assets/css/liquid-glass.css` | 液态玻璃 CSS 变量和基础类 |
| | **默认图片** | `assets/img/default_scenic.svg` | 无图片时的占位 SVG |
| **配置** | **路由** | `router/index.js` | 区分 `/Manage` (后台) 和 `/Front` (前台) 路由 |
| | **环境变量** | `.env.development` / `.env.production` | `VUE_APP_BASEURL` 后端地址 |

## 3. 后端开发规范 (Backend Rules)

### 3.1 实体类 (Domain)
- **Lombok**: 必须使用 `@Data`, `@EqualsAndHashCode(callSuper = false)`, `@Accessors(chain = true)`。
- **主键**: `@TableId(value = "id", type = IdType.AUTO)`.
- **图片字段**: 若实体有图片，字段名统一为 `imgUrl`，存储后端返回的**完整 URL**。
- **日期序列化** (强制): 解决 Redis/前端 时间格式问题。
  ```java
  @Schema(description = "创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime createTime;
  ```

### 3.2 文件上传机制
- **上传接口**: `POST /common/files/upload` (`FileController`)
- **存储路径**: `{项目根目录}/files/{时间戳}-{原始文件名}`
- **返回值**: 完整 URL，如 `http://localhost:8088/common/files/1746076011429-cat.png`
- **访问接口**: `GET /common/files/{flag}` 直接下载/预览文件
- **前端上传 URL**: `import.meta.env.VUE_APP_BASEURL + '/common/files/upload'`

### 3.3 Controller 开发模式
#### A. 后台管理 Controller (`controller.admin`)
- **路径**: `@RequestMapping("/admin/模块名")`
- **权限**: 必须加 `@SaCheckPermission("admin:模块:操作")`。
- **日志**: 增删改操作必须加 `@Log(title = "xxx", businessType = BusinessType.XXX)`。
- **用户ID**: `StpUtil.getLoginIdAsLong()`。

#### B. 前台用户 Controller (`controller.user`)
- **路径**: `@RequestMapping("/user/模块名")`
- **鉴权**: 仅需确保用户登录，通常不需要 `@SaCheckPermission`。
- **用户ID**: 必须使用 `StpUserUtil.getLoginIdAsLong()` (注意是 `StpUserUtil`)。
- **功能**: 通常只提供 `query` (查询列表/详情) 或 `submit` (提交数据) 接口，不提供直接的删除/修改管理功能。

### 3.4 字典系统 (Dictionary System)
用于状态、类型等枚举值的统一管理，避免硬编码。
- **表**: `sys_dict_data` (字段: `dict_type`, `dict_label`, `dict_value`, `tag_type`)
- **后端接口**: `GET /common/query-dict/{dictType}` → 返回 `[{dictLabel, dictValue, tagType}]`
- **已有字典类型**:
  | dict_type | 说明 | 值 |
  | :--- | :--- | :--- |
  | `shop_status` | 店铺状态 | 0=打烊(warning), 1=营业(success) |
- **前端使用模式**:
  ```js
  import { queryDictByType } from "@/api/com_request/ComRequest";
  import { selectDictLabel, selectTagType } from "@/utils/env";
  const shopStatusOption = ref([]);
  onMounted(() => { queryDictByType('shop_status').then(res => { shopStatusOption.value = res.data }); });
  ```
  ```html
  <!-- 表格回显 -->
  <el-tag :type="selectTagType(shopStatusOption, scope.row.status)">{{ selectDictLabel(shopStatusOption, scope.row.status) }}</el-tag>
  <!-- 表单下拉 -->
  <el-select v-model="form.status">
    <el-option v-for="item in shopStatusOption" :key="item.dictValue" :label="item.dictLabel" :value="item.dictValue"/>
  </el-select>
  ```
- **规范**: 所有状态/类型字段**禁止硬编码** `el-option` 和三元表达式，必须使用字典查询。

### 3.5 通用查询系统 (Common Query System)
用于外键关联字段的下拉选项加载，如用户名回显、景点名称选择等。
- **表**: `sys_com_query` (字段: `name`, `code`, `custom_sql`)
- **后端接口**: `GET /common/com-query/{code}` → 返回 `[{dictLabel, dictValue}]`
- **SQL 规范**: 必须以 `dictLabel` 和 `dictValue` 命名。
  ```sql
  SELECT username as dictLabel,id as dictValue FROM sys_user
  ```
- **已有通用查询**:
  | code | 说明 | SQL |
  | :--- | :--- | :--- |
  | `user_query` | 后台用户 | `SELECT username as dictLabel,id as dictValue FROM sys_user` |
  | `front_user_query` | 前台用户 | `SELECT username as dictLabel,id as dictValue FROM front_user` |
- **前端使用模式**:
  ```js
  import { queryComQueryByCode } from "@/api/com_request/ComRequest";
  import { selectDictLabel } from "@/utils/env";
  const userOption = ref([]);
  onMounted(() => { queryComQueryByCode('user_query').then(res => { userOption.value = res.data }); });
  ```
  ```html
  <!-- 表格回显外键 -->
  <el-table-column :formatter="userIdFormatter" label="用户" prop="userId"/>
  <!-- 表单选择 (单选) -->
  <el-select v-model="form.userId"><el-option v-for="item in userOption" :key="item.dictValue" :label="item.dictLabel" :value="item.dictValue"/></el-select>
  <!-- 表单选择 (多选，字段为逗号分隔字符串) -->
  <el-select v-model="form.userIds" multiple><el-option v-for="item in userOption" :key="item.dictValue" :label="item.dictLabel" :value="item.dictValue"/></el-select>
  ```
- **多选字段特殊处理**: 后端字段为 `String`（逗号分隔），前端 `el-select multiple` 为数组，需要转换：
  ```js
  // 提交时: 数组 → 字符串
  const data = { ...form.value };
  if (Array.isArray(data.scenicIds)) { data.scenicIds = data.scenicIds.join(','); }
  // 编辑回显时: 字符串 → 数组
  if (form.value.scenicIds && typeof form.value.scenicIds === 'string') {
    form.value.scenicIds = form.value.scenicIds.split(',').map(Number);
  }
  ```

## 4. 前端开发规范 (Frontend Rules)

### 4.1 API 请求封装
- **后台请求**: `import adminRequest from "@/utils/AdminRequest";`
- **前台请求**: `import userRequest from "@/utils/UserRequest";` (注意区分 Request 工具类)
- **上传地址**: `const uploadUrl = import.meta.env.VUE_APP_BASEURL`

### 4.2 图片上传组件 (后台表单)
所有包含 `imgUrl` 字段的后台管理表单，**必须使用 `el-upload` 组件上传图片**，禁止使用文本输入框填写链接。
```html
<el-form-item label="图片" prop="imgUrl">
  <el-upload
      :action="uploadUrl + '/common/files/upload'"
      :before-upload="beforeAvatarUpload"
      :on-success="handleAvatarSuccess"
      :show-file-list="false"
      class="img-uploader">
    <img v-if="form.imgUrl" :src="form.imgUrl" alt="" class="upload-preview"/>
    <el-icon v-else class="upload-icon"><Plus/></el-icon>
  </el-upload>
</el-form-item>
```
```js
const handleAvatarSuccess = (response) => {
  if (response.code !== 200) { ElMessage.error(response.msg); return; }
  form.value.imgUrl = response.data;
};
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('图片必须是 JPG 或 PNG 格式!'); return false;
  }
  if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('图片大小不能超过 10MB!'); return false;
  }
  return true;
};
```

### 4.3 图片展示 (前台页面)
- **默认占位图**: `import defaultImgUrl from '@/assets/img/default_scenic.svg'`
- **模板绑定**: `<img :src="item.imgUrl || defaultImg" alt=""/>`
- **禁止使用外部 CDN 图片地址**作为默认占位图，必须使用本地 SVG。

### 4.4 视图开发 (View)
- **后台页面 (`views/background`)**:
  - 风格: 表格+弹窗+搜索栏 (Element Plus)。
  - 按钮: 必须加 `v-no-more-click` (防抖) 和 `v-permission` (权限)。
  - 图片字段: 使用 `el-upload` 组件（参考 4.2）。
  - **状态/类型字段**: 必须使用字典查询 `queryDictByType`（参考 3.4），**禁止硬编码**。
  - **外键关联字段**: 必须使用通用查询 `queryComQueryByCode`（参考 3.5），**禁止手动输入 ID**。
- **前台页面 (`views/front`)**:
  - 风格: **iOS 26 Liquid Glass** (液态玻璃) 风格，详见第 5 节。
  - 交互: 侧重展示和简单表单提交，无需复杂权限指令。

## 5. 前台 UI 设计规范 — 液态玻璃风格 (Liquid Glass Design System)

### 5.1 全局设计系统
- **样式文件**: `src/assets/css/liquid-glass.css`，定义所有 CSS 变量和基础类。
- **页面背景**: 使用 `--lg-page-bg` 渐变 + `lg-page-bg` 类，禁止使用外部图片 URL 作为背景。
- **图标**: 使用内联 SVG (Lucide 风格)，**禁止使用 emoji** 作为 UI 图标。

### 5.2 核心 CSS 变量
| 变量 | 值 | 用途 |
| :--- | :--- | :--- |
| `--glass-bg` | `rgba(255,255,255,0.55)` | 标准玻璃面板背景 |
| `--glass-blur` | `24px` | 标准模糊强度 |
| `--glass-blur-strong` | `40px` | 导航栏/强调面板模糊 |
| `--glass-border` | `rgba(255,255,255,0.45)` | 玻璃边框 |
| `--glass-radius` | `20px` | 默认圆角 |
| `--glass-shadow` | `0 8px 32px rgba(0,0,0,0.08)` | 标准阴影 |
| `--glass-inner-glow` | `inset 0 1px 1px rgba(255,255,255,0.6)` | 内发光 (液态玻璃核心特征) |
| `--lg-primary` | `#007AFF` | 主色 (iOS 蓝) |
| `--lg-text-primary` | `#1a1a2e` | 主文字色 (深色文字 on 浅色背景) |
| `--lg-page-bg` | 多色渐变 | 页面背景渐变 |

### 5.3 前台导航栏 (`FrontView.vue`)
- **结构**: 单一浮动玻璃胶囊导航栏 `.glass-navbar`
- **定位**: `position: fixed; top: 12px; left: 16px; right: 16px; z-index: 100`
- **圆角**: `border-radius: 50px` (胶囊形)
- **布局**: 左侧品牌 Logo + 中间导航链接 + 右侧用户操作
- **内容偏移**: `.front-container { padding-top: 72px; }` 防止内容被遮挡
- **Hero 全屏**: HomeView hero 使用 `margin-top: -72px; padding-top: 72px;` 延伸到导航栏后方
- **响应式**: ≤768px 隐藏导航链接，显示汉堡菜单

### 5.4 常用基础类
| 类名 | 用途 |
| :--- | :--- |
| `.glass-panel` | 标准玻璃面板 (含 hover 效果) |
| `.glass-card` | 可交互玻璃卡片 (含 cursor:pointer + translateY hover) |
| `.glass-nav` | 玻璃导航栏 |
| `.glass-tag` | 玻璃标签/徽章 |
| `.glass-btn` / `.glass-btn-primary` | 玻璃按钮 |
| `.glass-price` / `.glass-price.free` | 价格标签 (橙色/绿色) |
| `.lg-page-bg` | 页面渐变背景 |
| `.lg-ambient-orb` | 装饰性浮动光斑 |

### 5.5 前台页面编写要点
1. 背景使用纯 CSS 渐变 + 装饰性光斑动画，不依赖外部图片/Vanta.js。
2. 所有卡片/面板使用 `backdrop-filter: blur()` + 半透明白色背景 + 内发光。
3. Hover 效果使用 `translateY(-4px)` + 阴影增强，避免 `scale` 导致布局偏移。
4. 过渡动画统一使用 `cubic-bezier(0.4, 0, 0.2, 1)`，时长 `0.3s~0.35s`。
5. 文字颜色: 标题 `--lg-text-primary`，正文 `--lg-text-secondary`，辅助 `--lg-text-muted`。
6. 前台所有子页面共享 `FrontView.vue` 的浮动玻璃导航栏和 `padding-top: 72px` 偏移。