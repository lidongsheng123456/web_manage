package com.example.util;

import com.example.common.util.ExcelExportUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ExcelExportUtil 单元测试
 */
@DisplayName("ExcelExportUtil 导出工具测试")
class ExcelExportUtilTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class SampleEntity {
        @Schema(description = "编号")
        private Long id;

        @Schema(description = "名称")
        private String name;

        @Schema(description = "创建时间")
        private LocalDateTime createTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class NoSchemaEntity {
        private Long id;
        private String value;
    }

    @Test
    @DisplayName("导出非空列表生成正确的Excel字节数组")
    void exportNonEmptyList() throws IOException {
        List<SampleEntity> data = List.of(
                new SampleEntity(1L, "测试A", LocalDateTime.of(2025, 1, 1, 10, 0, 0)),
                new SampleEntity(2L, "测试B", LocalDateTime.of(2025, 6, 15, 14, 30, 0))
        );

        byte[] bytes = ExcelExportUtil.exportToExcel(data, SampleEntity.class);
        assertNotNull(bytes);
        assertTrue(bytes.length > 0);

        // 解析验证内容
        try (Workbook wb = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
            Sheet sheet = wb.getSheetAt(0);
            assertEquals("SampleEntity", sheet.getSheetName());

            // 表头行
            Row header = sheet.getRow(0);
            assertEquals("编号", header.getCell(0).getStringCellValue());
            assertEquals("名称", header.getCell(1).getStringCellValue());
            assertEquals("创建时间", header.getCell(2).getStringCellValue());

            // 数据行
            Row row1 = sheet.getRow(1);
            assertEquals("1", row1.getCell(0).getStringCellValue());
            assertEquals("测试A", row1.getCell(1).getStringCellValue());
            assertEquals("2025-01-01 10:00:00", row1.getCell(2).getStringCellValue());

            Row row2 = sheet.getRow(2);
            assertEquals("2", row2.getCell(0).getStringCellValue());
            assertEquals("测试B", row2.getCell(1).getStringCellValue());
        }
    }

    @Test
    @DisplayName("导出空列表只包含表头")
    void exportEmptyList() throws IOException {
        byte[] bytes = ExcelExportUtil.exportToExcel(List.of(), SampleEntity.class);
        assertNotNull(bytes);

        try (Workbook wb = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
            Sheet sheet = wb.getSheetAt(0);
            // 只有表头行
            assertNotNull(sheet.getRow(0));
            assertNull(sheet.getRow(1));
        }
    }

    @Test
    @DisplayName("无 @Schema 注解时使用字段名作为表头")
    void exportNoSchemaAnnotation() throws IOException {
        List<NoSchemaEntity> data = List.of(new NoSchemaEntity(1L, "val"));

        byte[] bytes = ExcelExportUtil.exportToExcel(data, NoSchemaEntity.class);

        try (Workbook wb = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
            Row header = wb.getSheetAt(0).getRow(0);
            assertEquals("id", header.getCell(0).getStringCellValue());
            assertEquals("value", header.getCell(1).getStringCellValue());
        }
    }
}
