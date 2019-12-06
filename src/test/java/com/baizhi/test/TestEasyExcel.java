package com.baizhi.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baizhi.CmfzApplication;
import com.baizhi.entity.ComplexHeadData;
import com.baizhi.entity.ConverterData;
import com.baizhi.entity.DemoData;
import com.baizhi.entity.ImageData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author: DarkSunrise
 * @date: 2019/12/2  14:33
 */
@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class TestEasyExcel {
    private String fileName = "E:\\Dasktop\\Work\\" + new Date().getTime() + "_DemoData.xlsx";

    public List data() {
        List list = new ArrayList();
        for (int i = 0; i < 4; i++) {
            DemoData demoData = new DemoData();
            demoData.setString("ZMF");
            demoData.setDate(new Date());
            demoData.setDoubleData(18.0);
            list.add(demoData);
        }
        return list;
    }

    //导出数据
    @Test
    public void test1() {
        //写法1
        /**
         * .sheet("这里面是表名")
         */
        //EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
        //写法2
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        excelWriter.write(data(), writeSheet);
        //finish
        excelWriter.finish();
    }

    //导入数据
    @Test
    public void test2() {
        String fileName = "E:\\Dasktop\\Work\\1575286948000_DemoData.xlsx";
        //写法1
        //new DemoDataListener() 每次都需要创建新的DemoDataListener() 在Spring框架中使用时需要注意
        //EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
        //写法2
        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
    }

    //忽略|包含某些字段
    @Test
    public void test3() {
        //1、创建一个需要忽略的字段集合
        Set set = new HashSet();
        //2、放入需要忽略|包含的字段名
        set.add("date");
        //忽略 excludeColumnFiledNames
        //EasyExcel.write(fileName,DemoData.class).excludeColumnFiledNames(set).sheet().doWrite(data());
        //包含 includeColumnFiledNames
        EasyExcel.write(fileName, DemoData.class).includeColumnFiledNames(set).sheet().doWrite(data());
    }

    //复杂头写入
    @Test
    public void test4() {
        //EasyExcel通过创建空集合的方式，生成只有表头的文件 可以作为网络使用的模板Excel
        ComplexHeadData complexHeadData = new ComplexHeadData();
        List<ComplexHeadData> complexHeadData1 = Arrays.asList(complexHeadData);
        //注意 doWrite不能给null
        //EasyExcel.write(fileName, ComplexHeadData.class).sheet().doWrite(null);
        EasyExcel.write(fileName, ComplexHeadData.class).sheet().doWrite(complexHeadData1);
    }

    @Test
    public void test5() {
        ConverterData zmf = new ConverterData("ZMF", new Date(), 18.0);
        ConverterData zmf1 = new ConverterData("ZMF", new Date(), 18.0);
        ConverterData zmf2 = new ConverterData("ZMF", new Date(), 18.0);
        EasyExcel.write(fileName, ConverterData.class).sheet().doWrite(Arrays.asList(zmf, zmf1, zmf2));
    }

    @Test
    public void test6() throws IOException {
        ImageData imageData = new ImageData();
        imageData.setFile(new File("E:\\其他\\壁纸\\370.jpg"));
        imageData.setByteArray(FileUtils.readFileToByteArray(new File("E:\\其他\\壁纸\\370.jpg")));
        imageData.setInputStream(new FileInputStream(new File("E:\\其他\\壁纸\\370.jpg")));
        imageData.setString("E:\\其他\\壁纸\\370.jpg");
        imageData.setUrl(new URL("http://5b0988e595225.cdn.sohucs.com/images/20190524/95fe8537ce3242c98cb0e7d5c53f19d4.jpeg"));
        EasyExcel.write(fileName, ImageData.class).sheet().doWrite(Arrays.asList(imageData));
    }
}
