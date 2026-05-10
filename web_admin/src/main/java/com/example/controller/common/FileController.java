package com.example.controller.common;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.config.AppConfig;
import com.example.common.entity.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * 文件上传
 */
@Slf4j
@Tag(name = "文件相关接口")
@RestController
@RequestMapping("/common/files")
@RequiredArgsConstructor
public class FileController {

    private final AppConfig appConfig;

    @Value("${server.port}")
    private String port;

    @Value("${server.host}")
    private String ip;

    private String filePath;

    @PostConstruct
    public void init() {
        String configPath = appConfig.getFile().getUploadPath();
        if (configPath.startsWith("./") || configPath.startsWith("../")) {
            filePath = System.getProperty("user.dir") + "/" + configPath.substring(configPath.startsWith("./") ? 2 : 0);
        } else {
            filePath = configPath;
        }
        if (!filePath.endsWith("/")) {
            filePath += "/";
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @SaCheckLogin
    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        if (StrUtil.isBlank(originalName)) {
            throw new BusinessException(ResultCodeEnum.FILES_UPLOAD_ERROR);
        }

        // 清洗文件名：去除路径分隔符，防止路径穿越
        String safeName = Path.of(originalName).getFileName().toString();

        // 扩展名白名单校验
        String ext = FileUtil.extName(safeName);
        if (StrUtil.isBlank(ext) || !appConfig.getFile().getAllowedExtensions().contains(ext.toLowerCase())) {
            throw new BusinessException(ResultCodeEnum.FILES_UPLOAD_ERROR);
        }

        String flag;
        synchronized (FileController.class) {
            flag = System.currentTimeMillis() + "";
            ThreadUtil.sleep(1L);
        }

        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }
        try {
            FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + safeName);
            log.info("文件上传成功: {}", safeName);
        } catch (IOException e) {
            log.error("文件上传失败: {}", safeName, e);
            throw new BusinessException(ResultCodeEnum.FILES_UPLOAD_ERROR);
        }
        String http = "http://" + ip + ":" + port + "/common/files/";
        return Result.success(http + flag + "-" + safeName);
    }

    /**
     * 获取文件
     *
     * @param flag
     * @param response
     */
    @Operation(summary = "获取文件")
    @GetMapping("/{flag}")
    public void imgPath(@PathVariable String flag, HttpServletResponse response) {
        if (StrUtil.isEmpty(flag) || flag.contains("..") || flag.contains("/") || flag.contains("\\")) {
            return;
        }
        try {
            String fullPath = filePath + flag;
            if (!FileUtil.exist(fullPath)) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(flag, StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");
            byte[] bytes = FileUtil.readBytes(fullPath);
            OutputStream os = response.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            log.error("文件下载失败: {}", flag, e);
        }
    }

    /**
     * 获取文件列表（文件管理器）
     */
    @SaCheckLogin
    @Operation(summary = "获取文件列表")
    @GetMapping("/list")
    public Result listFiles() {
        java.io.File dir = new java.io.File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return Result.success(java.util.Collections.emptyList());
        }
        java.io.File[] files = dir.listFiles();
        if (files == null) {
            return Result.success(java.util.Collections.emptyList());
        }
        java.util.List<java.util.Map<String, Object>> list = new java.util.ArrayList<>();
        String http = "http://" + ip + ":" + port + "/common/files/";
        for (java.io.File f : files) {
            if (f.isFile()) {
                java.util.Map<String, Object> item = new java.util.LinkedHashMap<>();
                item.put("name", f.getName());
                item.put("size", f.length());
                item.put("lastModified", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(f.lastModified())));
                item.put("url", http + f.getName());
                String ext = FileUtil.extName(f.getName());
                item.put("ext", ext != null ? ext.toLowerCase() : "");
                list.add(item);
            }
        }
        list.sort((a, b) -> ((String) b.get("lastModified")).compareTo((String) a.get("lastModified")));
        return Result.success(list);
    }

    @SaCheckLogin
    @Operation(summary = "删除文件")
    @DeleteMapping("/{flag}")
    public Result delFile(@PathVariable String flag) {
        if (StrUtil.isEmpty(flag) || flag.contains("..") || flag.contains("/") || flag.contains("\\")) {
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        String fullPath = filePath + flag;
        if (!FileUtil.exist(fullPath)) {
            throw new BusinessException(ResultCodeEnum.NO_DATA_ERROR);
        }
        FileUtil.del(fullPath);
        log.info("删除文件成功: {}", flag);
        return Result.success();
    }
}
