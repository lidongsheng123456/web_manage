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
public class DictVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典标签")
    private String dictLabel;

    @Schema(description = "字典键值")
    private Integer dictValue;

    @Schema(description = "标签类型")
    private String tagType;
}
