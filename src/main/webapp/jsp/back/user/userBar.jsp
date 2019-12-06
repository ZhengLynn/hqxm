<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    let goEasy = new GoEasy({
        host: 'hangzhou.goeasy.io',//应用所在的区域地址，杭州：hangzhou.goeasy.io，新加坡：singapore.goeasy.io
        appkey: "BS-adcb90ee5dd34c0d81a0440ea708f1fe",//BS key 只能用来订阅消息
        forceTLS: false, //如果需要使用HTTPS/WSS，请设置为true，默认为false
        onConnected: function () {
            console.log('连接成功！')
        },
        onDisconnected: function () {
            console.log('连接断开！')
        },
        onConnectFailed: function (error) {
            console.log('连接失败或错误！')
        }
    });
    goEasy.subscribe({
        channel: "cmfz",//替换为您自己的channel
        onMessage: function (message) {
            let myChart = initECharts();
            flushECharts(myChart, message.content.statTime);
        }
    });
</script>
<script type="text/javascript">
    $(function () {
        let myChart = initECharts();
        $.get(
            '${pageContext.request.contextPath}/user/statTime',
            function (map) {
                flushECharts(myChart, map)
            }, 'json'
        )
    })

    function initECharts() {
        // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(document.getElementById('userBar'));
        // 指定图表的配置项和数据
        let option = {
            title: {
                text: '用户注册趋势图'
            },
            tooltip: {},
            legend: {
                data: ['男', '女']
            },
            xAxis: {
                data: ["最近1天", "最近7天", "最近30天", "最近1年"]
            },
            yAxis: {},
            series: [{
                name: '男',
                type: 'bar',
                data: []
            }, {
                name: '女',
                type: 'bar',
                data: []
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        return myChart;
    }

    function flushECharts(myChart, map) {
        myChart.setOption({
            series: [{
                name: '男',
                type: 'bar',
                data: map.man
            }, {
                name: '女',
                type: 'bar',
                data: map.woman
            }]
        });
    }
</script>
<div class="col-sm-10">
    <div id="userBar" style="width: 600px;height:400px;"></div>
</div>