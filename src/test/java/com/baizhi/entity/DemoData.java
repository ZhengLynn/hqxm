package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: DarkSunrise
 * @date: 2019/12/2  14:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoData {
    /**
     * @ExcelProperty("这里面是表头") value="" 定义表头  index="" 定义列号
     */
    @ExcelProperty(value = "字符串标题")
    private String string;
    @ExcelProperty(value = "日期标题")
    @DateTimeFormat("yyyy年MM月dd日 HH时mm分ss秒")
    private Date date;
    @ExcelProperty(value = "数字标题")
    @NumberFormat("#.##%")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
