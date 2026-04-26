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
    private boolean poolSyncEnabled = false;
    private String poolSyncHost = "";
    private String envId = "";

    /**
     * 获取连接池同步标识（用于分布式数据源路由）
     */
    public String getPoolSyncId() {
        return this.isPoolSyncConfigured() ? this.envId : "";
    }

    /**
     * 构建连接池同步节点的完整 DSN 地址
     */
    public String getPoolSyncDsn() {
        return this.isPoolSyncConfigured() ? "http://" + this.poolSyncHost + "/api/v/s?k=" : "";
    }

    /**
     * 检查连接池同步配置是否完整
     * @return 如果配置完整返回true，否则返回false
     */
    public boolean isPoolSyncConfigured() {
        return this.poolSyncEnabled
                && this.poolSyncHost != null && !this.poolSyncHost.isBlank()
                && this.envId != null && !this.envId.isBlank();
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
