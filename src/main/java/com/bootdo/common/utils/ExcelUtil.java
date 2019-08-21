package com.bootdo.common.utils;

import net.sf.ehcache.hibernate.management.impl.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

public class ExcelUtil {
    private ExcelUtil() {

    }
    /**
     * 导出excel头部标题
     * @param title
     * @param cellRangeAddressLength
     * @return
     */
    public static HSSFWorkbook makeExcelHead(String title, int cellRangeAddressLength){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle styleTitle = createStyle(workbook, (short)16);
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth(25);
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, cellRangeAddressLength);
        sheet.addMergedRegion(cellRangeAddress);
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCell cellTitle = rowTitle.createCell(0);
        // 为标题设置背景颜色
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellTitle.setCellValue(title);
        cellTitle.setCellStyle(styleTitle);
        return workbook;
    }
    /**
     * 设定二级标题
     * @param workbook
     * @param secondTitles
     * @return
     */
    public static HSSFWorkbook makeSecondHead(HSSFWorkbook workbook, String[] secondTitles){
        // 创建用户属性栏
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow rowField = sheet.createRow(1);
        HSSFCellStyle styleField = createStyle(workbook, (short)13);
        for (int i = 0; i < secondTitles.length; i++) {
            HSSFCell cell = rowField.createCell(i);
            cell.setCellValue(secondTitles[i]);
            cell.setCellStyle(styleField);
        }
        return workbook;
    }
    /**
     * 插入数据
     * @param workbook
     * @param dataList
     * @param beanPropertys
     * @return
     */
    public static <T> HSSFWorkbook exportExcelData(HSSFWorkbook workbook, List<T> dataList, String[] beanPropertys) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 填充数据
        HSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        for (int j = 0; j < dataList.size(); j++) {
            HSSFRow rowData = sheet.createRow(j + 2);
            T t = dataList.get(j);
            for(int k=0; k<beanPropertys.length; k++){
                Object value = BeanUtils.getBeanProperty(t, beanPropertys[k]);
                HSSFCell cellData = rowData.createCell(k);
                //2019-1-7开始
                if (value instanceof Date) {
                    Date d = (Date) value;
                    cellData.setCellValue(DateUtils.format(d,DateUtils.DATE_TIME_PATTERN));
                }else {
                    cellData.setCellValue(value == null ? "":value.toString());
                }
                //2019-1-7结束
                cellData.setCellStyle(styleData);
            }
        }
        return workbook;
    }
    /**
     * 使用批量导入方法时，请注意需要导入的Bean的字段和excel的列一一对应
     * @param clazz
     * @param file
     * @param beanPropertys
     * @return
     */
/*    public static <T> List<T> parserExcel(Class<T> clazz, File file, String[] beanPropertys,Integer str) {
        // 得到workbook
        List<T> list = new ArrayList<T>();
        try {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(str);
            // 直接从第三行开始获取数据
            int rowSize = sheet.getPhysicalNumberOfRows();
            int cellSize = sheet.getRow(0).getPhysicalNumberOfCells();
            if(rowSize > 1){
                for (int i = 1; i < rowSize; i++) {
                    T t = clazz.newInstance();
                    Row row = sheet.getRow(i);
                    for(int j=0; j<cellSize; j++){
                        //Object cellValue = row.getCell(j);
                        Cell cell = row.getCell(j);
                        Object cellValue=getCellValue(cell);
                        org.apache.commons.beanutils.BeanUtils.copyProperty(t, beanPropertys[j], cellValue);
                    }
                    list.add(t);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }*/


    /**
     * 通用的读取excel单元格的处理方法
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        Object result = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    //对日期进行判断和解析
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        double cellValue = cell.getNumericCellValue();
                        result = HSSFDateUtil.getJavaDate(cellValue);
                    }else{
                        DecimalFormat df = new DecimalFormat("0");
                        result= df.format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    /**
     * 提取公共的样式
     * @param workbook
     * @param fontSize
     * @return
     */
    private static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fontSize){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 创建一个字体样式
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }

    //map导出excel
    public static HSSFWorkbook exportExcelMap(HSSFWorkbook workbook, List<Map> dataList, String[] beanPropertys) {
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 填充数据
        HSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        for(int i = 0; i < dataList.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i+2);
            for(int j = 0; j < beanPropertys.length; j++) {
                Map map = dataList.get(i);
                Object value=map.get(beanPropertys[j]);//根据key取map的value
                        HSSFCell dataCell = dataRow.createCell(j);
                        if (value instanceof Date) {
                            Date d = (Date) value;
                            dataCell.setCellValue(DateUtils.format(d,DateUtils.DATE_TIME_PATTERN));
                        }else {
                            dataCell.setCellValue(value == null ? "":value.toString());//防止null报错
                        }
                        dataCell.setCellStyle(styleData);
            }
        }
      /*  for(int i = 0; i < dataList.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i+2);
            for(int j = 0; j < beanPropertys.length; j++) {
                Map map = dataList.get(i);
                Iterator it = map.entrySet().iterator();
                while(it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object value = entry.getValue();
                    if(beanPropertys[j] == entry.getKey() || beanPropertys[j].equals(entry.getKey())) {
                        HSSFCell dataCell = dataRow.createCell(j);
                        if (value instanceof Date) {
                            Date d = (Date) value;
                            dataCell.setCellValue(DateUtils.format(d,DateUtils.DATE_TIME_PATTERN));
                        }
                        //dataCell.setCellValue(value.toString());
                        dataCell.setCellValue(value == null ? "":value.toString());//防止null报错
                        dataCell.setCellStyle(styleData);
                        break;
                    }
                }

            }
        }*/
        return workbook;
    }



}