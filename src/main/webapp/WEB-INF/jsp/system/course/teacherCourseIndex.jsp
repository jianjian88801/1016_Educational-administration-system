<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<%--    教师课程页面--%>
    <title>Title</title>
    <%@include file="/WEB-INF/jsp/common.jsp"%>
</head>
<body>
<%--<h2>教师课程页面</h2>--%>
<hr>
<%--<form class="layui-form" action="">--%>
<%--    <div class="layui-form-item">--%>
<%--        <div class="layui-inline">--%>
<%--            <label class="layui-form-label">课程名称</label>--%>
<%--            <div class="layui-input-inline" style="width: 200px;">--%>
<%--                <input id="courseName" type="text" autocomplete="off" class="layui-input">--%>
<%--            </div>--%>

<%--            <div class="layui-input-inline" style="width: 100px;">--%>
<%--                <button type="button" class="layui-btn layui-btn-normal" onclick="searchData();"><i class="layui-icon layui-icon-search"></i>--%>
<%--                    查询</button>--%>
<%--            </div>--%>
<%--        </div>--%>

<%--    </div>--%>
<%--</form>--%>

<table class="layui-hide" id="teacherCourseTable" lay-filter="teacherCourseTable"></table>

<script>
    // function searchData(){
    //     layui.table.reload("teacherCourseTable",{
    //         page:{
    //             curr : 1
    //         },
    //         where:{
    //             "courseName":$("#courseName").val()
    //         }
    //     });
    // }

    layui.use(['table','form'],function(){
        var table = layui.table;

        var colsArray =[[
            // {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: '课程编号', width:40, align:"left", sort: true},
            {field: 'courseName', title: '课程名',width:100, align:'left'},
            {field: 'teacherName', title: '任课教师',minWidth:120, align:'left'},
            {field: 'startDate', title: '开始时间', minWidth:100, align:'center'},
            {field: 'endDate', title: '结束时间', minWidth:100, align:'center'},
            {field: 'classHour', title: '课时', minWidth:100, align:'center'},
            {field: 'testMode', title: '考核方式', minWidth:100, align:'center'},
            {field: 'studentNum', title: '最大人数', minWidth:100, align:'center'},
            {field: "choiceNum", title: "已选（人）", minWidth:100, align:'center'},
            {field: "complete", title: "课程是否结束", minWidth:100, align:'center',templet:'#lockedTem'},
            {fixed: "right",title: '操作', width:160,toolbar: '#barDemo',align:'center'}
        ]];

        tableIns = table.render({
            id:'teacherCourseTable'
            ,elem: '#teacherCourseTable'
            ,url:'${path}/easCourse/getMyCourseList' //获取教师对应课程数据
            ,even: true
            // ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,toolbar: true //不自定义也需要 开启
            ,defaultToolbar: ['filter', 'exports', 'print']
            ,title: '课程表'
            ,height : "full-125"
            ,page: true //开启分页
            ,cols: colsArray
        });

        table.on("toolbar(teacherCourseTable)");

        //监听行工具时间
        table.on("tool(teacherCourseTable)",function (obj) {
            var data = obj.data;
            if (obj.event == 'edit'){ //edit
                layer.confirm('请确保结束课程前已提交学生成绩！确定结束此课程？', {icon: 3, title: '提示信息'},function (index) {
                    $.ajax({
                        type: "get",
                        data:{id: data.id,complete:data.complete},
                        url:'${path}/easCourse/complete',
                        success:function (data){
                            if (data.code === 0) {
                                tableIns.reload(); //重载表格tableIns
                                layer.close(index);
                            }else if(data.code === 1){
                                layer.msg(data.msg, {icon: 5,time:1000});
                                layer.close(index);
                            }else if(data.code === 2){
                                layer.msg(data.msg, {icon: 5,time:1000});
                                layer.close(index);

                            }
                        }
                    });
                });
            }

        })

    });

</script>
<script type="text/html" id="lockedTem">
<%--    <input type="checkbox" readonly  title="锁定" {{d.complete == 0 ? 'checked' : ''}}>--%>
    <input type="checkbox" disabled="disabled"  lay-skin="switch"  lay-text="进行中|已结束" {{d.complete == 0 ? 'checked' : ''}}>
</script>

<script type="text/html" id="barDemo">
    <shiro:hasPermission name="course:teacherEndCourse">
        <a href="javascript:void(0)" lay-event="edit" class="layui-btn layui-btn-xs" >结束课程</a>
    </shiro:hasPermission>
</script>

<%--<script type="text/html" id="toolbarDemo">--%>
<%--    <div class="layui-btn-container">--%>
<%--        <shiro:hasPermission name="course:adminAdd">--%>
<%--            <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-add-circle"></i> 添加课程</button>--%>
<%--        </shiro:hasPermission>--%>
<%--        <shiro:hasPermission name="course:adminDelete">--%>
<%--            <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="batchDelete"><i class="layui-icon layui-icon-delete"></i> 批量删除</button>--%>
<%--        </shiro:hasPermission>--%>
<%--    </div>--%>
<%--</script>--%>
</body>
</html>
