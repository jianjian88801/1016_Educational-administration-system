<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<%--    管理员课程页面--%>
    <title>Title</title>
    <%@include file="/WEB-INF/jsp/common.jsp"%>
    <style>
        /*解决LayUI数据表格复选框不居中显示的问题*/
        .layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
            top: 50%;
            transform: translateY(-50%);
        }
    </style>
</head>
<body>
<%--<h2>管理课程页面</h2>--%>
<%--<hr>--%>
<form class="layui-form" action="">
    <div class="layui-form-item" style="margin-left: 5%;margin-top: 30px;">
        <div class="layui-inline">
            <label class="layui-form-label">课程名称</label>
            <div class="layui-input-inline" style="width: 200px;">
                <input id="courseName" type="text" autocomplete="off" class="layui-input">
            </div>

            <label class="layui-form-label">教师名称</label>
            <div class="layui-input-inline" style="width: 200px;">
                <input id="teacherName" type="text" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-input-inline" style="width: 100px;">
                <button type="button" class="layui-btn layui-btn-normal" onclick="searchData();"><i class="layui-icon layui-icon-search"></i>
                    查询</button>
            </div>
        </div>

    </div>
</form>

<table class="layui-hide" id="adminCourseTable" lay-filter="adminCourseTable"></table>

<script>
    function searchData(){
        layui.table.reload("adminCourseTable",{
            page:{
                curr : 1
            },
            where:{
                "courseName":$("#courseName").val(),
                "teacherName":$("#teacherName").val()
            }
        });
    }

    layui.use(['table','form'],function(){
        var table = layui.table;


        var colsArray =[[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: '课程编号', minWidth:40, align:"left", sort: true},
            {field: 'courseName', title: '课程名',minWidth:120, align:'left'},
            {field: 'teacherName', title: '任课教师',minWidth:120, align:'left'},
            {field: 'startDate', title: '开始时间', minWidth:100, align:'center'},
            {field: 'endDate', title: '结束时间', minWidth:100, align:'center'},
            {field: 'classHour', title: '课时', minWidth:100, align:'center'},
            {field: 'testMode', title: '考核方式', minWidth:100, align:'center'},
            {field: 'studentNum', title: '最大人数', minWidth:100, align:'center'},
            {field: "choiceNum", title: "已选（人）", minWidth:100, align:'center'},
            {fixed: "right",title: '操作', width:160,toolbar: '#barDemo',align:'center'}
        ]];

        table.render({
            id:'adminCourseTable'
            ,elem: '#adminCourseTable'
            ,url:'${path}/easCourse/getCourseList' //获取数据
            ,even: true
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print']
            ,height : "full-125" //高度将始终铺满
            ,cellMinWidth : 95//最小宽度
            ,title: '课程表'
            ,page: true //开启分页
            ,cols: colsArray
        });

        //监听工具栏的事件
        table.on('toolbar(adminCourseTable)',function (obj) {
            var e = obj.event;
            switch (e){
                case "add":
                    $.get('${path}/easCourse/courseAddForm',function (str) {
                        var addCourseIndex =layer.open({
                            type:1,
                            title :'添加课程',
                            // area : '700px',
                            content : str,
                            skin:'layui-layer-molv',
                            btn : ['提交','重置','关闭页面'],
                            btnAlign:'c', //按钮居中对齐
                            anim: 1, //从上掉落样式
                            yes : function (index) {
                                var params = $("#add_course_form").serialize();
                                console.log("我是课程表单:"+params);

                                $.post('${path}/easCourse/addCourse',params,function (data) {

                                    if(data.result === false){
                                        layui.layer.msg(data.msg,{icon:5});
                                    }else{
                                        //添加成功
                                        layui.layer.msg(data.msg,{icon:1,time:1000},function () {
                                            layer.close(index);
                                            table.reload('adminCourseTable');
                                        });
                                    }

                                });
                            }
                             //重置按钮
                            ,btn2:function (index){
                                //重置表单add_course_form 为表单id
                                $('#add_course_form')[0].reset();
                                // form.render();//必须写，不写该按钮会变为关闭页面事件
                                form.render(null, 'courseAddForm');//更新 lay-filter="courseAddForm" 所在容器内的全部表单状态
                            }
                            //关闭按钮
                            ,btn3:function (index) {
                                layer.close(index);
                            }

                        });

                        layer.full(addCourseIndex);  //填充满整个页面
                        window.sessionStorage.setItem("addCourseIndex",addCourseIndex);
                        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
                        $(window).on("resize",function(){
                            layui.layer.full(window.sessionStorage.getItem("addCourseIndex"));
                        });


                    });
                    break;
                case "batchDelete":
                    var rows = table.checkStatus('adminCourseTable');
                    var data = rows.data;
                    if(data.length === 0){
                        layer.msg("请选择要删除的数据",function(){});
                        return;
                    }
                    layer.confirm('真的干掉这么多数码吗？',{icon:5,title:'友情提示'},function (index) {
                        var params = "";
                        for(let course of data){
                            params += "ids="+course.id+"&";
                        }
                        $.post('${path}/easCourse/batchDeleteCourse',params,function (data) {
                            if (data.result === true ){
                                layer.close(index);
                                table.reload('adminCourseTable');
                            }else{
                                layer.close(index);
                                layer.msg("删除失败！",{icon:5,time:1000});
                            }

                        });
                    });
                    break;

            }
        });

        //监听行工具时间
        table.on("tool(adminCourseTable)",function (obj) {
            var data = obj.data;
            if (obj.event == 'edit'){ //edit
                $.get("${path}/easCourse/courseEditForm",function (str) {
                    var editCourseIndex = layer.open({
                        type:1,
                        title : '修改课程信息',
                        content : str,
                        // area:'700px',
                        skin:'layui-layer-molv',
                        btn : ['修改信息','关闭页面'],
                        btnAlign:'c', //按钮居中对齐
                        anim: 1, //从上掉落样式
                        success:function(){
                            $.get('${path}/easCourse/getCourseById',{id:data.id},function (data) {
                                // console.log("我是返回的课程信息:"+data);
                                //显示数据
                                layui.form.val('courseEditForm',data);
                            });
                        },
                        yes : function (index) {
                            var params2 = $("#edit_course_form").serialize();
                            $.post('${path}/easCourse/editCourse',params2,function (data) {
                                console.log("修改的数据为:"+params2);

                                if(data.result === true){
                                    layer.close(index);
                                    layer.msg('修改成功',{icon:1,time:1000})
                                    table.reload('adminCourseTable');
                                }else {
                                    layer.msg('修改失败',{icon:5,time:1000})
                                }
                            });
                        }

                    });

                    layer.full(editCourseIndex);  //填充满整个页面
                    window.sessionStorage.setItem("editCourseIndex",editCourseIndex);
                    //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
                    $(window).on("resize",function(){
                        layui.layer.full(window.sessionStorage.getItem("editCourseIndex"));
                    });

                });
            }else if(obj.event == 'del'){//delete
                layer.open({
                    time:0,
                    title:'友情提示',
                    content:"确定要删除该行数据吗？",
                    shade: [0.3, '#000'],
                    btn:['确定','取消'],
                    yes : function () {
                        $.post("${path}/easCourse/batchDeleteCourse",{"ids":data.id},function (data) {
                            if (data.result === true ){
                                layer.msg('删除成功');
                                table.reload('adminCourseTable');
                            }else{
                                layer.msg("删除失败！",{icon:5,time:1000});
                            }

                        });
                    }
                })
            }
        })

    });

</script>
<script type="text/html" id="barDemo">
    <shiro:hasPermission name="course:adminUpdate">
        <a href="javascript:void(0)" lay-event="edit"><i class="layui-icon layui-icon-edit"></i></a>
    </shiro:hasPermission>
    <shiro:hasPermission name="course:adminDelete">
        <a href="javascript:void(0)" lay-event="del"><i class="layui-icon layui-icon-delete"></i></a>
    </shiro:hasPermission>

</script>

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <shiro:hasPermission name="course:adminAdd">
            <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-add-circle"></i> 添加课程</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="course:adminDelete">
            <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="batchDelete"><i class="layui-icon layui-icon-delete"></i> 批量删除</button>
        </shiro:hasPermission>
    </div>
</script>
</body>
</html>
