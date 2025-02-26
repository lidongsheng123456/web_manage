# web_ui

## Project setup

```
npm install
```

### Compiles and hot-reloads for development

```
npm run serve
```

### Compiles and minifies for production

```
npm run build
```

### Lints and fixes files

```
npm run lint
```

### Customize configuration

See [Configuration Reference](https://cli.vuejs.org/config/).

* 登录成功默认进入前台页面，触发路由守卫判断当前本地存储xm-user存在不，不存在重新登录，存在判断当前角色，角色===admin重定向到后台否则前台
