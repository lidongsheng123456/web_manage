# 前端前台用户开发工作流 (Frontend User Workflow)

生成位置: `{业务前缀}_ui/src`

## API 请求文件

路径: `api/front_request/{Entity}Request.js`，引用 `UserRequest`。

```js
import userRequest from "@/utils/UserRequest";

export function queryArticleList(params) {
  return userRequest({ url: '/user/article/list', method: 'get', params });
}
export function queryArticleById(id) {
  return userRequest({ url: `/user/article/${id}`, method: 'get' });
}
```

## 前台页面

### 列表页

路径: `views/front/{module}/{Entity}ListView.vue`

液态玻璃风格卡片列表。

### 详情页

路径: `views/front/{module}/{Entity}DetailView.vue`

液态玻璃风格详情页。

## 液态玻璃 UI 规范 (Liquid Glass)

### 页面背景
```css
.page { background: var(--lg-page-bg); min-height: 100vh; }
/* 或使用 class="lg-page-bg" */
```

### 玻璃卡片
```css
.card {
  background: var(--glass-bg);
  backdrop-filter: blur(var(--glass-blur));
  -webkit-backdrop-filter: blur(var(--glass-blur));
  border: 1px solid var(--glass-border);
  border-radius: var(--glass-radius);
  box-shadow: var(--glass-shadow), var(--glass-inner-glow);
}
```

### Hover 效果
```css
.card:hover {
  transform: translateY(-4px);       /* 禁止 scale */
  box-shadow: var(--glass-shadow-elevated);
}
```

### 过渡动画
```css
transition: all 0.35s cubic-bezier(0.4, 0, 0.2, 1);
```

### 文字颜色
- 标题: `var(--lg-text-primary)`
- 正文: `var(--lg-text-secondary)`
- 辅助: `var(--lg-text-muted)`

### 图片展示
```html
<img :src="item.imgUrl || defaultImg" alt="" />
```
```js
import defaultImgUrl from '@/assets/img/default_scenic.svg';
```

### 基础类速查

| 类名 | 用途 |
|:---|:---|
| `.glass-panel` | 标准玻璃面板 (含 hover) |
| `.glass-card` | 可交互卡片 (cursor:pointer + translateY hover) |
| `.glass-nav` | 导航栏 |
| `.glass-tag` | 标签/徽章 |
| `.glass-btn` / `.glass-btn-primary` | 按钮 |
| `.glass-price` / `.glass-price.free` | 价格标签 |
| `.lg-page-bg` | 页面渐变背景 |
| `.lg-ambient-orb` | 装饰性浮动光斑 |

### 禁止事项

- **禁止 emoji** 作为 UI 图标，使用内联 SVG (Lucide 风格)
- **禁止外部 CDN 图片** 作为默认占位图
- **禁止 Vanta.js / 外部图片** 作为背景
- **禁止 scale** hover 效果

## 路由配置

在 `router/index.js` 中:

```js
// 后台路由 /Manage -> children
{ path: 'article', component: () => import('@/views/background/manage/ManageArticleView.vue') }

// 前台路由 /Front -> children
{ path: 'article', component: () => import('@/views/front/article/ArticleListView.vue') },
{ path: 'article/:id', component: () => import('@/views/front/article/ArticleDetailView.vue') }
```
