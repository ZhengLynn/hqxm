package com.baizhi.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.entity.DemoData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DarkSunrise
 * @date: 2019/12/2  14:59
 */
public class DemoDataListener extends AnalysisEventListener<DemoData> {
    List list = new ArrayList();

    //每行数据读取完成后会调用invoke
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println(data);
        list.add(data);
    }

    //全部读取之后执行doAfterAllAnalysed
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("over");
    }
}
