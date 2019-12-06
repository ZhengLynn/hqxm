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
            let myChart = initMap();
            flushECharts(myChart, message.content.statMap);
        }
    });
</script>
<script>
    $(function () {
        let myChart = initMap();
        $.get("${pageContext.request.contextPath}/user/statCity", function (data) {
            myChart.setOption({
                series: [{
                    name: '用户',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: data
                }]
            })
        }, "json")
    })

    function initMap() {
        // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(document.getElementById('userMap'));
        let option = {
            title: {
                text: '用户分布图',
                subtext: '精准统计',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['用户']
            },
            visualMap: {
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: []
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        return myChart;
    }
</script>
<div class="col-sm-10">
    <div id="userMap" style="width: 600px;height:400px;"></div>
</div>