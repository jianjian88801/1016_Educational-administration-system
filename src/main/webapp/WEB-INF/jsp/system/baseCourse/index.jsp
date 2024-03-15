<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/jsp/common.jsp"%>
</head>
<body>
<%--<h2>课程列表</h2>--%>
<%--<hr>--%>
<form class="layui-form" action="">
    <div class="layui-form-item" style="margin-left: 5%;margin-top: 30px;">
        <div class="layui-inline">
            <label class="layui-form-label">课程名称</label>
            <div class="layui-input-inline" style="width: 200px;">
                <input id="coursename" type="text" autocomplete="off" class="layui-input">
            </div>
            <label class="layui-form-label">课程简介</label>
            <div class="layui-input-inline" style="width: 200px;">
                <input id="synopsis" type="text" autocomplete="off" class="layui-input">
            </div>

            <div class="layui-input-inline" style="width: 100px;">
                <button type="button" class="layui-btn layui-btn-normal" onclick="searchData();"><i class="layui-icon layui-icon-search"></i>
                    查询</button>
            </div>
        </div>

    </div>
</form>

<table class="layui-hide" id="baseCourseTable" lay-filter="baseCourseTable"></table>

<script>

    function searchData(){
        layui.table.reload("baseCourseTable",{
            page:{
                curr : 1
            },
            where:{
                "coursename":$("#coursename").val(),
                "synopsis":$("#synopsis").val()
            }
        });
    }

    layui.use(["table","form"],function(){
        var table = layui.table;
        table.render({
             id:'baseCourseTable'
            ,elem: '#baseCourseTable'
            ,url:'${path}/easBaseCourse/list'
            ,even: true
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print']
            ,title: '基本课程表'
            ,page: true
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'id', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
                ,{field:'coursename', title:'课程名称', width:180}
                ,{field:'synopsis', title:'课程简介'}
                // ,{field:'synopsis', title:'课程简介', edit: 'text'} 可编辑
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150,align:"center"}
            ]]

        });

        //监听工具栏的事件
        table.on('toolbar(baseCourseTable)',function (obj) {
            var e = obj.event;
            switch (e){
                case "add":
                    $.get('${path}/easBaseCourse/baseCourseAddForm',function (str) {
                        layer.open({
                            type:1,
                            title :'添加基本课程',
                            area : '700px',
                            content : str,
                            skin:'layui-layer-molv',
                            btn : ['确定','取消'],
                            yes : function (index) {
                                var params = $("#add_baseCourse_form").serialize();
                                $.post('${path}/easBaseCourse/addBaseCourse',params,function (data) {

                                    if(data.result == false){
                                        layui.layer.msg(data.msg,{icon:5});
                                    }else{
                                        layui.layer.msg("添加成功",{icon:1,time:1000},function () {
                                            layer.close(index);
                                            table.reload('baseCourseTable');
                                        });

                                    }

                                });
                            }
                        });
                    });
                    break;
                case "batchDelete":
                    var rows = table.checkStatus('baseCourseTable');
                    var data = rows.data;
                    if(data.length == 0){
                        layer.msg("请选择要删除的数据",function(){});
                        return;
                    }
                    layer.confirm('真的干掉这么多基本课程吗？',{icon:5,title:'友情提示'},function (index) {
                        var params = "";
                        for(let baseCourse of data){
                            params += "ids="+baseCourse.id+"&";
                        }
                        $.post('${path}/easBaseCourse/batchDeleteBaseCourse',params,function (data) {

                            layui.layer.msg(data.msg,{icon:5,time:1000},function () {
                                layer.close(index);
                                table.reload('baseCourseTable');
                            });

                        });
                    });
                    break;

            }
        });

        //监听行工具时间
        table.on("tool(baseCourseTable)",function (obj) {
            var data = obj.data;
            if (obj.event == 'edit'){ //edit
                $.get("${path}/easBaseCourse/baseCourseAddForm",function (str) {
                    layer.open({
                        type:1,
                        title : '修改基本课程',
                        content : str,
                        area:'700px',
                        skin:'layui-layer-molv',
                        btn:['确定','取消'],
                        success:function(){
                            $.get('${path}/easBaseCourse/getBaseCourseView',{id:data.id},function (data) {
                                //显示数据
                                layui.form.val('add_baseCourse_form',data);
                            });
                        },
                        yes : function (index) {
                            var params2 = $("#add_baseCourse_form").serialize();
                            $.post('${path}/easBaseCourse/editBaseCourse',params2,function () {
                                layer.close(index);
                                layer.msg('修改成功',{icon:5,time:1000})
                                table.reload('baseCourseTable');
                            });
                        }


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
                        $.post("${path}/easBaseCourse/batchDeleteBaseCourse",{"ids":data.id},function () {
                            layer.msg('删除成功',{icon:1,time:1000});
                            table.reload('baseCourseTable');
                        });
                    }
                })
            }
        })
    });
</script>


<script type="text/html" id="barDemo">
    <shiro:hasPermission name="basecourse:update">
        <a href="javascript:void(0)" lay-event="edit"><i class="layui-icon layui-icon-edit"></i></a>
    </shiro:hasPermission>
    <shiro:hasPermission name="basecourse:delete">
        <a href="javascript:void(0)" lay-event="del"><i class="layui-icon layui-icon-delete"></i></a>
    </shiro:hasPermission>
</script>

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <shiro:hasPermission name="basecourse:add">
            <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-add-circle"></i> 添加</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="basecourse:delete">
            <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="batchDelete"><i class="layui-icon layui-icon-delete"></i> 批量删除</button>
        </shiro:hasPermission>
  </div>
</script>
</body>
</html>
