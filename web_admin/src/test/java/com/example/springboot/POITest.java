package com.example.springboot;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 使用POI操作Excel文件
 */
public class POITest {

    /**
     * 通过POI创建Excel文件并且写入文件内容
     */
    public static void write() throws Exception {
        // 在内存中创建一个Excel文件
        XSSFWorkbook excel = new XSSFWorkbook();

        // 在Excel文件中创建一个Sheet页，名称为 "info"
        XSSFSheet sheet = excel.createSheet("info");

        // 在Sheet中创建行对象，rownum 编号从0开始
        XSSFRow row = sheet.createRow(1);

        // 创建单元格并且写入文件内容
        row.createCell(1).setCellValue("姓名"); // 在第1行第1列写入 "姓名"
        row.createCell(2).setCellValue("城市"); // 在第1行第2列写入 "城市"

        // 创建一个新的行
        row = sheet.createRow(2);
        row.createCell(1).setCellValue("张三"); // 在第2行第1列写入 "张三"
        row.createCell(2).setCellValue("北京"); // 在第2行第2列写入 "北京"

        // 创建另一个新的行
        row = sheet.createRow(3);
        row.createCell(1).setCellValue("李四"); // 在第3行第1列写入 "李四"
        row.createCell(2).setCellValue("南京"); // 在第3行第2列写入 "南京"

        // 通过输出流将内存中的Excel文件写入到磁盘
        FileOutputStream out = new FileOutputStream(new File("D:\\info.xlsx"));
        excel.write(out);

        // 关闭资源
        out.close();
        excel.close();
    }

    /**
     * 通过POI读取Excel文件中的内容
     *
     * @throws Exception
     */
    public static void read() throws Exception {
        // 读取磁盘上已经存在的Excel文件
        InputStream in = new FileInputStream(new File("D:\\info.xlsx"));

        // 读取磁盘上的Excel文件
        XSSFWorkbook excel = new XSSFWorkbook(in);

        // 读取Excel文件中的第一个Sheet页
        XSSFSheet sheet = excel.getSheetAt(0);

        // 获取Sheet中最后一行的行号
        int lastRowNum = sheet.getLastRowNum();

        // 遍历每一行
        for (int i = 1; i <= lastRowNum; i++) {
            // 获得某一行
            XSSFRow row = sheet.getRow(i);

            // 获得单元格对象
            String cellValue1 = row.getCell(1).getStringCellValue(); // 获取第1列的值
            String cellValue2 = row.getCell(2).getStringCellValue(); // 获取第2列的值

            // 打印单元格的值
            System.out.println(cellValue1 + " " + cellValue2);
        }

        // 关闭资源
        in.close();
        excel.close();
    }
}
