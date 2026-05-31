# 常见问题

## Redis 相关

### Redis 端口被占用

**现象**：启动 Redis 时提示端口 `6379` 已被占用。

**解决**：
```bash
# 查看占用端口的进程
netstat -ano | findstr 6379     # Windows
lsof -i :6379                   # Linux/macOS

# 方案一：停止占用的进程
# 方案二：修改 Redis 端口后重启
```

### Redis 连接失败

**现象**：后端启动报 Redis 连接超时或被拒绝。

**排查步骤**：
1. 确认 Redis 服务是否已启动
2. 检查配置文件中的 Redis 地址、端口、密码是否正确
3. 检查防火墙是否放行了 Redis 端口

---

## 数据库相关

### 启动报 "Unable to find valid certification path"

**原因**：MySQL 开启了 SSL 验证。

**解决**：在数据库连接 URL 中添加 `useSSL=false` 参数。

### 数据库表不存在

**排查**：
1. 确认 `application-*.yml` 中的数据库名是否正确
2. 确认 SQL 脚本是否已成功导入
3. 检查 SQL 脚本中的库名与配置文件是否一致

---

## JDK 版本问题

### 启动报 "UnsupportedClassVersionError"

**原因**：使用了 JDK 8 或 JDK 11，但 Spring Boot 3 要求 JDK 17+。

**解决**：
```bash
# 检查当前 JDK 版本
java -version

# 确保输出 >= 17
```

---

## 前端相关

### 接口请求返回 401 / 403

**排查**：
1. 检查 Sa-Token 配置中的 `token-name` 是否与其他项目冲突
2. 确认前端请求头携带了正确的 Token
3. 检查 Redis 中会话是否过期

### 前端打包失败

**排查**：
1. 确认 Node.js 版本 >= 18
2. 删除 `node_modules` 后重新安装依赖
```bash
rm -rf node_modules package-lock.json
npm install
npm run build
```

---

## Nginx 相关

### 配置不生效

```bash
# 验证配置语法
nginx -t

# 重新加载（不停止服务）
nginx -s reload
```

### 刷新页面 404

**原因**：SPA 应用需要将所有路径回退到 `index.html`。

**解决**：确保 Nginx 配置中包含：
```nginx
location / {
    try_files $uri $uri/ /index.html;
}
```
