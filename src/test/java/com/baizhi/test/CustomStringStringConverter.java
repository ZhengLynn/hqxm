package com.baizhi.test;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author: DarkSunrise
 * @date: 2019/12/2  19:55
 */
public class CustomStringStringConverter implements Converter<String> {
    //转换为Java数据时的数据类型
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    //转换为Excel中的单元格数据类型
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    //如何将Excel中的数据转换为Java中的数据
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        return "自定义:" + stringValue;
    }

    //如何将Java中的数据转换为Excel中的数据
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        CellData cellData = new CellData("自定义:" + value);
        return cellData;
    }
}
