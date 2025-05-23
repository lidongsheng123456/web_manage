package com.example.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataCountTypeVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "星期几")
    private String dayOfWeek;

    @Schema(description = "总数")
    private Long count;
}
