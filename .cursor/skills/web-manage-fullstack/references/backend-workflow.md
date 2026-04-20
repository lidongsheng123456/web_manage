# 后端开发工作流 (Backend Workflow)

## Step 1: 数据库设计

生成 SQL 建表语句 + 字典/通用查询数据。

- **表名**: `sys_{模块名}` (带前缀)
- **必须字段**: `id`, `create_time`, `update_time`, `create_user_id`
- **图片字段**: `img_url varchar(500) COMMENT '图片URL'`，存储完整 URL
- **状态字段**: 同时生成 `sys_dict_data` 插入 SQL，禁止前端硬编码
- **外键字段**: 同时生成 `sys_com_query` 插入 SQL

```sql
-- 建表示例
CREATE TABLE `sys_article` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) COMMENT '标题',
  `content` text COMMENT '内容',
  `img_url` varchar(500) COMMENT '封面图URL',
  `status` tinyint DEFAULT 1 COMMENT '状态',
  `sort_order` int DEFAULT 0 COMMENT '排序',
  `create_time` datetime COMMENT '创建时间',
  `update_time` datetime COMMENT '更新时间',
  `create_user_id` bigint COMMENT '发布人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- 字典数据 (状态字段必须配套)
INSERT INTO `sys_dict_data` VALUES (NULL, 'article_status', '下架', 0, 'danger', '下架', NOW(), NOW());
INSERT INTO `sys_dict_data` VALUES (NULL, 'article_status', '上架', 1, 'success', '上架', NOW(), NOW());

-- 通用查询 (若其他模块需关联此表)
INSERT INTO `sys_com_query` VALUES (NULL, '文章查询', 'article_query', 'SELECT title as dictLabel,id as dictValue FROM sys_article', NULL, NOW(), NOW());
```

## Step 2: 后端基础层 (Domain + Mapper + Service)

生成位置: `{业务前缀}_system` 模块。

### Domain 实体类

路径: `com.example.system.domain.{Entity}`

必须注解:
```java
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_article")
public class Article {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private String title;
    private String content;
    private String imgUrl;  // 存储完整 URL
    private Integer status;
    private Integer sortOrder;
    
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;
    
    private Long createUserId;
}
```

### Mapper

路径: `com.example.system.mapper.Admin{Entity}Mapper` + XML

### Service

- 接口: `com.example.system.service.Admin{Entity}Service`
- 实现: `com.example.system.service.impl.Admin{Entity}ServiceImpl`

## Step 3: 后端接口层 (Controllers)

**必须生成两个 Controller**:

### 后台管理 AdminController

路径: `com.example.controller.admin.Admin{Entity}Controller`

```java
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {
    
    @SaCheckPermission("admin:article:query")
    @GetMapping("/list")
    public Result list(Article article, Page page) { ... }
    
    @SaCheckPermission("admin:article:add")
    @Log(title = "文章管理", businessType = BusinessType.INSERT)
    @PostMapping
    public Result add(@RequestBody Article article) {
        article.setCreateUserId(StpUtil.getLoginIdAsLong());
        // ...
    }
    
    @SaCheckPermission("admin:article:update")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public Result update(@RequestBody Article article) { ... }
    
    @SaCheckPermission("admin:article:delete")
    @Log(title = "文章管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/batchDelete")
    public Result delete(@RequestParam List<Long> ids) { ... }
    
    @SaCheckPermission("admin:article:export")
    @Log(title = "文章管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(Article article, HttpServletResponse response) { ... }
}
```

### 前台用户 UserController

路径: `com.example.controller.user.User{Entity}Controller`

```java
@RestController
@RequestMapping("/user/article")
public class UserArticleController {
    
    @GetMapping("/list")
    public Result list(Article article, Page page) { ... }
    
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) { ... }
}
```

关键区别:
- **Admin**: `StpUtil.getLoginIdAsLong()` + `@SaCheckPermission` + `@Log`
- **User**: `StpUserUtil.getLoginIdAsLong()` (注意是 StpUserUtil)，无细粒度权限

## 权限 SQL

```sql
-- 必须包含 5 个权限点
INSERT INTO sys_permission VALUES (NULL, '文章查询', 'admin:article:query', ...);
INSERT INTO sys_permission VALUES (NULL, '文章新增', 'admin:article:add', ...);
INSERT INTO sys_permission VALUES (NULL, '文章删除', 'admin:article:delete', ...);
INSERT INTO sys_permission VALUES (NULL, '文章修改', 'admin:article:update', ...);
INSERT INTO sys_permission VALUES (NULL, '文章导出', 'admin:article:export', ...);
```
