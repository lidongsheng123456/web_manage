package com.example.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "通知ID")
    private Long id;

    @Schema(description = "通知标题")
    private String noticeTitle;

    @Schema(description = "通知内容")
    private String noticeContent;

    @Schema(description = "通知创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class) //该注解解决LocalDateTime类型序列化为redis字符串的报错
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //该注解解决redis字符串反序列化为LocalDateTime的报错
    private LocalDateTime createTime;

    @Schema(description = "通知更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class) //该注解解决LocalDateTime类型序列化为redis字符串的报错
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //该注解解决redis字符串反序列化为LocalDateTime的报错
    private LocalDateTime updateTime;

    @Schema(description = "通知创建用户ID")
    private Long createUserId;

    @Schema(description = "通知创建用户ID")
    private String username;
}
