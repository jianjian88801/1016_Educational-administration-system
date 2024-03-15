<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>统计人口的可视化页面</title>
    <%@include file="/WEB-INF/jsp/common.jsp"%>.
    <script src="${path}/js/echarts.min.js"></script>
</head>
<body>
<div style="margin-top: 10%;">
    <div id="SAndTEchart" style="width: 600px;height:400px; float: left;"></div>
    <div id="SexEchart" style="width: 600px;height:400px;float: left;"></div>
</div>


<script></script>
<script>
    layui.use(["table","form","laytpl"],function() {
        var table = layui.table,
            form = layui.form,
            $ = layui.$,
            laytpl = layui.laytpl; //模板引擎 例:{{ d.checked }}

            var mySAndTChart = echarts.init(document.getElementById('SAndTEchart'));

            $.get('${path}/easEchart/getAllStuAndTea').done(function (data) {
                // console.log("返回的数据为:"+data.totalStu);

                mySAndTChart.setOption({
                    title: {
                        text: '教师学生人数图',
                        left: 'center',
                        textStyle:{
                            color:'#3e312a',
                            fontSize: 18
                        }
                    },
                    tooltip: {},
                    legend: {
                        orient: 'vertical',
                        left: '12%',
                        top: '10%'
                    },
                    // xAxis: {
                    //     data: ['教师人数','学生人数']
                    // },
                    // yAxis: {},
                    series: [{
                        name: '教师学生人数',
                        type: 'pie', //饼图
                        radius: '50%',
                        legendHoverLink:true,
                        // data: [data.totalTea,data.totalStu]
                        data: [
                            {value: data.totalTea, name: '教师人数'},
                            {value: data.totalStu, name: '学生人数'}
                        ],
                        emphasis: {
                            //加入外边框阴影
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }

                    }]
                });
            });


        var mySexChart = echarts.init(document.getElementById('SexEchart'));

        $.get('${path}/easEchart/getAllSex').done(function (data) {

            mySexChart.setOption({
                title: {
                    text: '学生男女比例图',
                    left: 'center',
                    textStyle:{
                        color:'#3e312a',
                        fontSize: 18
                    }
                },
                tooltip: {},
                legend: {
                    // orient: 'vertical',
                    // left: '12%',
                    // top: '10%'
                    data: ['男生','女生']
                },
                grid: {
                    left: '10%',
                    right: '10%',
                    bottom: '6%',
                    containLabel: true
                },
                xAxis: {
                    data: ['男生人数','女生人数']
                },
                yAxis: {},
                series: [{
                    name: '男女人数',
                    type: 'bar', //饼图
                    legendHoverLink:true,
                    data: [data.totalMan,data.totalWoman],
                    // data: [
                    //     {value: data.totalMan, name: '男生人数'},
                    //     {value: data.totalWoman, name: '女生人数'},
                    // ],
                    emphasis: {
                        //加入外边框阴影
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }

                }]


            });
        });

    });

</script>
</body>
</html>
