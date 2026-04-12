package com.example.common.util;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcelExportUtil {

    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static byte[] exportToExcel(List<?> dataList, Class<?> clazz) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(clazz.getSimpleName());

        // 过滤掉 static / transient 字段
        List<Field> fieldList = new ArrayList<>();
        for (Field f : clazz.getDeclaredFields()) {
            int mod = f.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isTransient(mod)) continue;
            f.setAccessible(true);
            fieldList.add(f);
        }
        Field[] fields = fieldList.toArray(new Field[0]);

        // ==================== 样式 ====================
        // 表头样式：深蓝背景 + 白色粗体 + 居中 + 边框
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerFont.setFontName("Microsoft YaHei");
        headerFont.setFontHeightInPoints((short) 11);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 64, (byte) 158, (byte) 255}, null));
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorders(headerStyle, BorderStyle.THIN, IndexedColors.WHITE.getIndex());

        // 数据行样式：奇数行
        CellStyle oddStyle = createDataStyle(workbook, new byte[]{(byte) 255, (byte) 255, (byte) 255});
        // 数据行样式：偶数行（浅灰交替）
        CellStyle evenStyle = createDataStyle(workbook, new byte[]{(byte) 245, (byte) 247, (byte) 250});

        // ==================== 表头 ====================
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(32);
        int[] maxColWidth = new int[fields.length];

        for (int i = 0; i < fields.length; i++) {
            Cell cell = headerRow.createCell(i);
            String headerText = getHeaderName(fields[i]);
            cell.setCellValue(headerText);
            cell.setCellStyle(headerStyle);
            maxColWidth[i] = calcWidth(headerText);
        }

        // ==================== 数据 ====================
        int rowNum = 1;
        for (Object data : dataList) {
            Row row = sheet.createRow(rowNum);
            row.setHeightInPoints(24);
            CellStyle rowStyle = (rowNum % 2 == 0) ? evenStyle : oddStyle;
            for (int i = 0; i < fields.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(rowStyle);
                try {
                    Object value = fields[i].get(data);
                    String text = formatValue(value);
                    cell.setCellValue(text);
                    maxColWidth[i] = Math.max(maxColWidth[i], calcWidth(text));
                } catch (IllegalAccessException e) {
                    cell.setCellValue("");
                }
            }
            rowNum++;
        }

        // ==================== 列宽 + 冻结表头 ====================
        for (int i = 0; i < fields.length; i++) {
            int width = Math.min(maxColWidth[i] + 512, 20000);
            sheet.setColumnWidth(i, Math.max(width, 3000));
        }
        sheet.createFreezePane(0, 1);

        // 写出
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    /**
     * 从 @Schema(description) 获取中文表头，无则用字段名
     */
    private static String getHeaderName(Field field) {
        Schema schema = field.getAnnotation(Schema.class);
        if (schema != null && !schema.description().isEmpty()) {
            return schema.description();
        }
        return field.getName();
    }

    /**
     * 格式化字段值
     */
    private static String formatValue(Object value) {
        if (value == null) return "";
        if (value instanceof LocalDateTime) return ((LocalDateTime) value).format(DT_FMT);
        return value.toString();
    }

    /**
     * 计算列宽（中文字符按2倍计算）
     */
    private static int calcWidth(String text) {
        if (text == null) return 0;
        int len = 0;
        for (char c : text.toCharArray()) {
            len += (c > 127) ? 512 : 256;
        }
        return len;
    }

    /**
     * 创建数据行样式
     */
    private static CellStyle createDataStyle(Workbook workbook, byte[] rgb) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Microsoft YaHei");
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(new XSSFColor(rgb, null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(false);
        setBorders(style, BorderStyle.THIN, IndexedColors.GREY_25_PERCENT.getIndex());
        return style;
    }

    /**
     * 设置四边边框
     */
    private static void setBorders(CellStyle style, BorderStyle borderStyle, short color) {
        style.setBorderTop(borderStyle);
        style.setBorderBottom(borderStyle);
        style.setBorderLeft(borderStyle);
        style.setBorderRight(borderStyle);
        style.setTopBorderColor(color);
        style.setBottomBorderColor(color);
        style.setLeftBorderColor(color);
        style.setRightBorderColor(color);
    }
}
