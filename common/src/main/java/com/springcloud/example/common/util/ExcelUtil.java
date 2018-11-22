package com.springcloud.example.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2018/11/22 16:07
 */
@Slf4j
public class ExcelUtil {
    public static <T> List<T> importExcel(InputStream ins,Map<String, String> titleMap, Class<T> clazz){
        List<T> dataList = Lists.newArrayList();
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(ins);
            int sheetNumber = workbook.getNumberOfSheets();// 总sheet数量
            for (int i = 0; i < sheetNumber; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                if (sheet != null) {
                    int rowTotalNumber = sheet.getLastRowNum();// 获取sheet下总行数
                    log.info("共计{}行", rowTotalNumber);
                    Row titleRow = sheet.getRow(0);// 通过title获取列数
                    int cellTotalNumber = 0;
                    if (titleRow != null) {
                        cellTotalNumber = titleRow.getLastCellNum();
                    }
                    if (cellTotalNumber < 1) {
                        log.error("EXCEL内容错误！");
                    }
                    // 下标对应实体属性 map
                    Map<Integer,String> propertyMap = Maps.newHashMap();
                    for(int title = 0;title < cellTotalNumber;title++){
                        String titleName = getValue(titleRow.getCell(title));
                        String propertyName = titleMap.get(titleName);
                        propertyMap.put(title,propertyName);
                    }

                    JSONArray dataArray = new JSONArray();
                    // 遍历行
                    for (int rowNumber = 1; rowNumber <= rowTotalNumber; rowNumber++) {
                        Row row = sheet.getRow(rowNumber);
                        // 遍历行下面列
                        if (row != null) {
                            JSONObject dataObj = new JSONObject();
                            for (int cellNumber = 0; cellNumber < cellTotalNumber; cellNumber++) {
                                String propertyKey = propertyMap.get(cellNumber);
                                String propertyValue = getValue(row.getCell(cellNumber));
                                dataObj.put(propertyKey,propertyValue);
                            }
                            log.info(dataObj.toString());
                            dataArray.add(dataObj);
                        }
                    }
                    dataList = JSONArray.parseArray(dataArray.toString(),clazz);
                }
            }
        } catch (Exception e) {
            log.error("error",e);
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e1) {
                    log.error("文件导入error", e1);
                }
            }
        }
        return dataList;
    }

    /**
     * 获取值
     *
     * @param cell
     * @return
     */
    private static String getValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == 4) {
                return String.valueOf(cell.getBooleanCellValue()).trim();
            } else if (cell.getCellType() == 0) {
                DecimalFormat df = new DecimalFormat("0");
                return df.format(cell.getNumericCellValue());
            } else {
                return String.valueOf(cell.getStringCellValue()).trim();
            }
        } else {
            return "";
        }
    }
}
