<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>成绩可视化页面</title>
    <%@include file="/WEB-INF/jsp/common.jsp"%>.
    <script src="${path}/js/echarts.min.js"></script>
</head>
<body>
<div style="margin-top: 5%; margin-left: 20%" >
    <form class="layui-form" style="margin-left: 17%">
        <div class="layui-form-item">
            <label class="layui-form-label">请选择要查看的课程</label>
            <div class="layui-input-inline">
                <select id="baseCourseSelect" lay-filter="baseCourse">
                    <option selected="selected" disabled="disabled"  style='display: none' value=''></option>
                </select>
            </div>
        </div>
    </form>
    <div id="scoreEchart" style="width: 800px;height:400px;"></div>
</div>

<script>
    layui.use(["table","form","laytpl"],function() {
        var table = layui.table,
            form = layui.form,
            $ = layui.$,
            laytpl = layui.laytpl; //模板引擎 例:{{ d.checked }}

        $.get("${path}/easBaseCourse/search",function (data) {
            $.each(data,function () {
                var opt = $("<option></option>").appendTo("#baseCourseSelect");
                opt.text(this.coursename).val(this.id);
            });
            //获取数据后再进行渲染，显示未显示的option
            form.render();
        });

        form.on('select(baseCourse)',function (data) {
            // console.log("基本课程id为:"+data.value);

            var myScoreChart = echarts.init(document.getElementById('scoreEchart'));

            $.get('${path}/easEchart/getAllClassScore',{baseCourseId:data.value}).done(function (data) {
                // console.log("返回的数据信息为:"+data);
                myScoreChart.setOption({
                    title: {
                        text: '课程成绩比例图'
                    },
                    tooltip: {},
                    legend:{
                        data: [data.coursename]
                    },
                    xAxis: {

                        data: ['合格', '不合格']
                    },
                    yAxis: {},
                    series: [{
                        name: data.coursename,
                        data: [data.totalPass, data.totalNoPass],
                        type: 'bar',
                        showBackground: true,
                        backgroundStyle: {
                            color: 'rgba(180, 180, 180, 0.2)'
                        }
                    }]
                });

            });
        });

    });

    // 基于准备好的dom，初始化echarts实例
    // var myChart = echarts.init(document.getElementById('scoreEchart'));
    //
    // // 指定图表的配置项和数据
    // var option = {
    //     title: {
    //         text: 'ECharts 入门示例'
    //     },
    //     tooltip: {},
    //     legend: {
    //         data:['销量']
    //     },
    //     xAxis: {
    //         data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
    //     },
    //     yAxis: {},
    //     series: [{
    //         name: '销量',
    //         type: 'bar',
    //         data: [5, 20, 36, 10, 10, 20]
    //     }]
    // };
    //
    // // 使用刚指定的配置项和数据显示图表。
    // myChart.setOption(option);
</script>
</body>
</html>
