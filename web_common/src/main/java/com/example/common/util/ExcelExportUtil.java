package com.example.common.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelExportUtil {

    public static byte[] exportToExcel(List<?> dataList, Class<?> clazz) throws IOException {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(clazz.getSimpleName());

        // 获取类的所有字段
        Field[] fields = clazz.getDeclaredFields();

        // 创建表头
        Row headerRow = sheet.createRow(0);

        // 创建表头样式
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);


        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(field.getName());
            cell.setCellStyle(headerStyle);  // 设置单元格样式单元格样式
        }

        headerRow.setRowStyle(headerStyle);  // 设置行样式

        // 填充数据
        int rowNum = 1;
        for (Object data : dataList) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                try {
                    Object value = field.get(data);
                    if (value instanceof LocalDateTime) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        row.createCell(i).setCellValue(((LocalDateTime) value).format(formatter));
                    } else {
                        row.createCell(i).setCellValue(value == null ? "" : value.toString());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field: " + field.getName(), e);
                }
            }
        }

        // 自动调整列宽
        for (int i = 0; i < fields.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 将工作簿写入字节数组输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        return byteArrayOutputStream.toByteArray();
    }
}
