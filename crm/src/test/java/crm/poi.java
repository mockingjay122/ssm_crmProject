package crm;

/**
 * @author 郭嘉
 * @date 2022/10/11 - 10:57
 */

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 适应apache的poi生成excel文件
 */
public class poi {
    public static void main(String[] args) throws IOException {
        //创建HSSWorkbook对象，对应一份excel文件
        HSSFWorkbook wb=new HSSFWorkbook();
        // 使用wb创建SSFSheet, 对应一页
        HSSFSheet sheet=wb.createSheet(" 学生列表");
        //一行, 从零开始
        HSSFRow row=sheet.createRow(0);
        //列, 从零开始,一次递增
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("学号");
        cell=row.createCell(1);
        cell.setCellValue("姓名");
        cell=row.createCell(2);
        cell.setCellValue("年龄");

        //style
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        // 创建10个信息
        for(int i=1;i<=10;i++){
            row=sheet.createRow(i);
            cell=row.createCell(0);
            cell.setCellValue(100+i);
            cell=row.createCell(1);
            cell.setCellValue("name"+i);
            cell=row.createCell(2);
            cell.setCellValue(10+i);
        }
       OutputStream os = new FileOutputStream("D:\\test\\crm-poi\\studentList.xls");
        wb.write(os);
        os.close();
        wb.close();
        System.out.println("******ok******");
    }
}
