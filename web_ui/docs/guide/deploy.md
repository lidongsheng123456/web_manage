# 部署指南

本文详细介绍项目在 **开发环境** 和 **生产环境** 两种场景下的部署方式。

## 环境要求 {#requirements}

在开始之前，请确保已安装以下依赖：

| 依赖 | 版本要求 | 说明 |
|:---|:---|:---|
| **JDK** | 17+ | Spring Boot 3 强制要求 JDK 17 及以上 |
| **Node.js** | 18+ | 前端构建所需 |
| **MySQL** | 8.0.x | 数据库 |
| **Redis** | 5.0+ | 缓存 & 会话存储 |
| **Maven** | 3.8+ | 后端依赖管理与构建 |

::: warning 注意
**不支持 JDK 8 / JDK 11**，必须使用 JDK 17 或更高版本。
:::

## 部署前准备 {#preparation}

无论开发还是生产，都需要先完成以下步骤：

### 1. 初始化数据库

```bash
# 创建数据库（库名自定义，如 xxx_manage）
mysql -u root -p -e "CREATE DATABASE xxx_manage DEFAULT CHARSET utf8mb4;"

# 导入项目根目录下的数据库脚本
mysql -u root -p xxx_manage < xxx.sql
```

### 2. 修改后端配置

编辑配置文件，路径为 `xxx_admin/src/main/resources/`：

- **开发环境**：`application-develop.yml`
- **生产环境**：`application-production.yml`

需要修改的配置项：

```yaml
web:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    host: localhost          # 数据库地址
    port: 3306               # 数据库端口
    database: xxx_manage     # 数据库名
    username: root           # 数据库用户名
    password: your_password  # 数据库密码
  local:
    datasource:
      host: localhost        # 后端服务地址
      port: 8088             # 后端服务端口
  redis:
    host: localhost          # Redis 地址
    port: 6379               # Redis 端口
    password:                # Redis 密码（无密码留空）
    database: 10             # Redis 数据库编号
```

### 3. 修改前端配置

前端环境变量位于 `xxx_ui/` 目录下：

::: code-group
```ini [.env.development（开发）]
VUE_APP_BASEURL='/api'
VUE_APP_API_TARGET='http://localhost:8088'
```

```ini [.env.production（生产）]
VUE_APP_BASEURL='/api'
VUE_APP_API_TARGET='http://your-server-ip:8088'
```
:::

---

## 开发环境部署 {#development}

适用于 **本地开发调试**，支持代码热更新。

### Step 1：启动 Redis

```bash
# Linux / macOS
redis-server

# Windows：双击 redis-server.exe 或命令行启动
redis-server.exe
```

启动成功后默认监听 `6379` 端口。

### Step 2：启动前端

```bash
# 进入前端目录
cd xxx_ui

# 首次运行需安装依赖
npm install

# 启动开发服务器
npm run dev
```

启动成功后，终端会显示访问地址（默认 `http://localhost:81`）。开发模式下修改代码会自动热更新。

### Step 3：启动后端

**方式一：IDEA 直接运行（推荐）**

1. 用 IntelliJ IDEA 打开项目根目录
2. 等待 Maven 自动下载依赖
3. 找到主类 `xxxManageApplication.java`（如 `WebManageApplication`）
4. 右键 → **Run** 启动

> 优点：支持断点调试、代码热更新。

**方式二：命令行运行**

```bash
mvn spring-boot:run -pl xxx_admin
```

### 开发环境访问地址

| 服务 | 地址 |
|:---|:---|
| 前端页面 | `http://localhost:81` |
| 后端接口 | `http://localhost:8088` |
| 接口文档 | `http://localhost:8088/doc.html` |

---

## 生产环境部署 {#production}

适用于项目开发完成后 **正式上线运行**。

### Step 1：打包后端

```bash
# 在项目根目录执行
mvn clean package -DskipTests
```

打包完成后，JAR 包位置：

```
xxx_admin/target/xxx_admin-0.0.1-SNAPSHOT.jar
```

### Step 2：打包前端

```bash
cd xxx_ui

# 首次部署需安装依赖
npm install

# 执行生产环境打包
npm run build
```

打包产物在 `xxx_ui/dist/` 目录下。

### Step 3：启动后端服务

根据实际需要选择一种方式：

::: code-group
```bash [临时运行]
java -jar xxx_admin-0.0.1-SNAPSHOT.jar
```

```bash [后台运行（推荐）]
nohup java -jar xxx_admin-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
```

```bash [系统服务]
# 参考操作系统的 systemd / supervisor 等服务管理工具配置
```
:::

### Step 4：Nginx 部署前端

将 `xxx_ui/dist/` 目录下的所有文件上传到服务器，配置 Nginx：

```nginx
server {
    listen       80;
    server_name  your_domain.com;

    # 前端静态文件
    location / {
        root   /path/to/dist;
        index  index.html index.htm;
        try_files $uri $uri/ /index.html;
    }

    # 反向代理后端接口
    location /api {
        proxy_pass http://localhost:8088;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

配置完成后重启 Nginx：

```bash
nginx -t        # 检查配置语法
nginx -s reload  # 重新加载配置
```

### 生产环境访问地址

| 服务 | 地址 |
|:---|:---|
| 前端页面 | `http://your_domain.com` |
| 后端接口 | `http://your_domain.com/api` |
| 接口文档 | `http://your_domain.com/doc.html` |

---

## 验证部署是否成功 {#verify}

### 开发环境

- ✅ **Redis**：控制台正常启动，无报错
- ✅ **前端**：终端显示 `Vite server running`，浏览器能正常访问
- ✅ **后端**：控制台显示 `Started xxxManageApplication`，无报错

### 生产环境

- ✅ **后端**：`curl http://localhost:8088/api/xxx` 能返回正常 JSON
- ✅ **前端**：浏览器访问域名能正常显示页面
- ✅ **Nginx**：`nginx -t` 无报错，日志无异常

---

## 开发环境 vs 生产环境对比 {#comparison}

| 对比项 | 开发环境 | 生产环境 |
|:---|:---|:---|
| 后端运行 | IDEA 直接运行 / `mvn spring-boot:run` | `java -jar` / 系统服务 |
| 前端模式 | `npm run dev`（热更新） | `npm run build`（静态文件） |
| 配置文件 | `application-develop.yml` | `application-production.yml` |
| 调试能力 | 支持断点调试 | 不支持调试，需看日志 |
| 性能 | 较低（有调试开销） | 较高（编译优化） |
| 适用场景 | 开发调试、代码修改 | 正式上线、稳定运行 |
