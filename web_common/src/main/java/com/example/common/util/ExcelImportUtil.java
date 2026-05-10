package com.example.common.util;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Excel 导入工具 — 基于 Apache POI，自动根据 @Schema(description) 匹配列名
 */
public class ExcelImportUtil {

    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 从 Excel InputStream 解析为对象列表
     *
     * @param inputStream Excel 文件流
     * @param clazz       目标实体类
     * @return 解析出的对象列表
     */
    public static <T> List<T> importFromExcel(InputStream inputStream, Class<T> clazz) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        // 收集实体字段
        List<Field> fieldList = new ArrayList<>();
        for (Field f : clazz.getDeclaredFields()) {
            int mod = f.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isTransient(mod)) continue;
            f.setAccessible(true);
            fieldList.add(f);
        }

        // 建立 表头名 -> Field 映射（优先 @Schema(description)，回退字段名）
        Map<String, Field> headerFieldMap = new LinkedHashMap<>();
        for (Field f : fieldList) {
            Schema schema = f.getAnnotation(Schema.class);
            if (schema != null && !schema.description().isEmpty()) {
                headerFieldMap.put(schema.description(), f);
            }
            headerFieldMap.put(f.getName(), f);
        }

        // 读取表头行，确定列索引 -> Field 映射
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            workbook.close();
            return Collections.emptyList();
        }

        Map<Integer, Field> colFieldMap = new LinkedHashMap<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null) continue;
            String headerText = cell.getStringCellValue().trim();
            Field f = headerFieldMap.get(headerText);
            if (f != null) {
                colFieldMap.put(i, f);
            }
        }

        // 逐行解析数据
        List<T> result = new ArrayList<>();
        for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) continue;

            T obj = clazz.getDeclaredConstructor().newInstance();
            boolean hasData = false;

            for (Map.Entry<Integer, Field> entry : colFieldMap.entrySet()) {
                Cell cell = row.getCell(entry.getKey());
                if (cell == null) continue;
                Field field = entry.getValue();
                String cellValue = getCellValueAsString(cell);
                if (cellValue == null || cellValue.isEmpty()) continue;

                hasData = true;
                setFieldValue(obj, field, cellValue);
            }

            if (hasData) {
                result.add(obj);
            }
        }

        workbook.close();
        return result;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getLocalDateTimeCellValue().format(DT_FMT);
                }
                double d = cell.getNumericCellValue();
                if (d == Math.floor(d) && !Double.isInfinite(d)) {
                    yield String.valueOf((long) d);
                }
                yield String.valueOf(d);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getStringCellValue();
            default -> null;
        };
    }

    private static void setFieldValue(Object obj, Field field, String value) {
        try {
            Class<?> type = field.getType();
            if (type == String.class) {
                field.set(obj, value);
            } else if (type == Long.class || type == long.class) {
                field.set(obj, Long.parseLong(value));
            } else if (type == Integer.class || type == int.class) {
                field.set(obj, Integer.parseInt(value));
            } else if (type == Double.class || type == double.class) {
                field.set(obj, Double.parseDouble(value));
            } else if (type == LocalDateTime.class) {
                field.set(obj, LocalDateTime.parse(value, DT_FMT));
            }
        } catch (Exception ignored) {
            // 类型转换失败则跳过
        }
    }
}
