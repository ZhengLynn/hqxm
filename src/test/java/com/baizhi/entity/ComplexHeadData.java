package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: DarkSunrise
 * @date: 2019/12/2  19:24
 */
@Data
public class ComplexHeadData {
    /**
     * 数组：{A,B} A会称为B的头部信息
     */
    @ExcelProperty({"主标题", "字符串标题"})
    private String string;
    @ExcelProperty({"主标题", "日期标题"})
    private Date date;
    @ExcelProperty({"主标题", "数字标题"})
    private Double doubleData;
}
