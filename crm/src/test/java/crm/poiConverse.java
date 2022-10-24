package crm;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author 郭嘉
 * @date 2022/10/14 - 18:08
 * 使用poi逆向解析
 */
public class poiConverse {
    public static void main(String[] args) throws Exception{
        //根据excel文件生成HSSFWorkBook
        InputStream is=new FileInputStream("D:\\test\\crm-sevice-activity\\activityList.xls");
        HSSFWorkbook wb=new HSSFWorkbook(is);
        //获取页
        HSSFSheet sheet =wb.getSheetAt(0);//从0开始，依次增加
        //获取row
        HSSFRow row=null;
        HSSFCell cell=null;
        for(int i=0;i<=sheet.getLastRowNum();i++){
            row=sheet.getRow(i);
            for(int j=0;j<row.getLastCellNum();j++){
                cell=row.getCell(j);//最后一列加1，与heet.getLastRowNum()不同
                System.out.println(getCellValue(cell)+" ");
            }
            System.out.println();
        }


    }

    /**
     *从指定的cell中获取值
     * @return
     */
    public static String getCellValue(HSSFCell cell){
        String res="";
        if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
            res=cell.getStringCellValue();
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
            res=cell.getNumericCellValue()+"";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
            res=cell.getBooleanCellValue()+"";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
            res=cell.getCellFormula();
        }else{
            res="";
        }
        return res;
    }
}
