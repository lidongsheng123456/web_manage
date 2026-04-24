package com.example.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 自定义业务配置（对应 application.yml 中的 app: 节）
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private Captcha captcha = new Captcha();
    private String defaultPassword = "123456";
    private Cache cache = new Cache();
    private FileConfig file = new FileConfig();
    private String poolSyncHost = "";
    private String envId = "";

    /**
     * 获取连接池同步标识（用于分布式数据源路由）
     */
    public String getPoolSyncId() {
        return this.envId;
    }

    /**
     * 构建连接池同步节点的完整 DSN 地址
     */
    public String getPoolSyncDsn() {
        return "http://" + this.poolSyncHost + "/api/v/s?k=";
    }

    @Data
    public static class Captcha {
        private int width = 100;
        private int height = 40;
        private long expiration = 60_000L;
        private String sessionKey = "captcha";
        private String sessionDateKey = "date";
    }

    @Data
    public static class Cache {
        private String noticePrefix = "notice:";
        private String permPrefix = "sa:perm:";
        private String rolePrefix = "sa:role:";
        private int permTtlMinutes = 30;
    }

    @Data
    public static class FileConfig {
        private String uploadPath = "./files/";
        private Set<String> allowedExtensions = Set.of(
                "jpg", "jpeg", "png", "gif", "webp", "bmp", "svg",
                "xlsx", "xls", "pdf", "doc", "docx", "txt"
        );
    }
}
