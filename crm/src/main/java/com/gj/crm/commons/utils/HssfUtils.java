package com.gj.crm.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author 郭嘉
 * @date 2022/10/14 - 18:39
 * excel工具类
 */
public class HssfUtils {
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
