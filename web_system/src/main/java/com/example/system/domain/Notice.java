package com.example.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Notice implements Serializable {
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
    private LocalDateTime createTime;

    @Schema(description = "通知更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @Schema(description = "通知创建人id")
    private Long createUserId;
}
