package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: DarkSunrise
 * @date: 2019/11/26  19:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Banner implements Serializable {
    @Id
    //@KeySql(sql = "select uuid()", order = ORDER.BEFORE)
    @ExcelProperty("ID")
    private String id;
    @ExcelProperty("图片名称")
    private String name;
    @ExcelProperty("图片路径")
    private String url;
    @ExcelProperty("简介")
    private String intro;
    @ExcelProperty("图片链接")
    private String link;
    @ExcelProperty("状态")
    private String status;
    @ExcelProperty("上传时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;
}
