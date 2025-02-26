package com.example.controller.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.entity.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件上传
 */
@Tag(name = "文件相关接口")
@RestController
@RequestMapping("/common/files")
public class FileController {
    //文件上传存储路径
    //System.getProperty("user.dir") //当前用户的工作目录:  D:\develop\idea_project\my_blog
    private static final String filePath = System.getProperty("user.dir") + "/files/"; //D:\develop\idea_project\my_blog/files/

    //注入yml配置文件中的配置
    @Value("${server.port}")
    private String port; //8088

    //注入yml配置文件中的配置
    @Value("${server.host}")
    private String ip; //localhost

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Operation(summary = "上传文件")

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String flag;
        synchronized (FileController.class) {
            flag = System.currentTimeMillis() + "";
            ThreadUtil.sleep(1L);
        }
        String fileName = file.getOriginalFilename();
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }
        try {
            FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName); //D:\develop\idea_project\my_blog/files/1732069777447-原始文件名.png
            System.out.println(fileName + "--上传成功");
        } catch (IOException e) {
            System.out.println(fileName + "--文件上传失败");
        }
        String http = "http://" + ip + ":" + port + "/common/files/";
        return Result.success(http + flag + "-" + fileName); //http://localhost:8088/files/1697438073596-img.png
    }

    /**
     * 获取文件
     *
     * @param flag
     * @param response
     */
    @Operation(summary = "获取文件")
    @GetMapping("/{flag}") //1732069777447-原始文件名.png
    public void imgPath(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;
        try {
            if (StrUtil.isNotEmpty(flag)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(flag, StandardCharsets.UTF_8));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + flag);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            System.out.println("文件下载失败");
        }
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/{flag}")
    public void delFile(@PathVariable String flag) {
        FileUtil.del(filePath + flag);
        System.out.println("删除文件" + flag + "成功");
    }
}
