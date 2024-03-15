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
<%--<h2>班级列表</h2>--%>
<%--<hr>--%>
<form class="layui-form" action="">
    <div class="layui-form-item" style="margin-left: 5%;margin-top: 30px;">
        <div class="layui-inline">
            <label class="layui-form-label">班级名称</label>
            <div class="layui-input-inline" style="width: 200px;">
                <input id="classes" type="text" autocomplete="off" class="layui-input">
            </div>

            <div class="layui-input-inline" style="width: 100px;">
                <button type="button" class="layui-btn layui-btn-normal" onclick="searchData();"><i class="layui-icon layui-icon-search"></i>
                    查询</button>
            </div>
        </div>

    </div>
</form>

<table class="layui-hide" id="classTable" lay-filter="classTable"></table>

<script>

    function searchData(){
        layui.table.reload("classTable",{
            page:{
                curr : 1
            },
            where:{
                "classes":$("#classes").val()
            }
        });
    }

    layui.use(["table","form"],function(){
        var table = layui.table;
        table.render({
             id:'classTable'
            ,elem: '#classTable'
            ,url:'${path}/easClass/list'
            ,even: true
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print']
            ,title: '班级信息表'
            ,page: true
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'id', title:'ID', width:80, fixed: 'left', unresize: true, sort: true,align:"center"}
                ,{field:'classes', title:'课程名称', minWidth:180,align:"center"}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', minWidth:150,align:"center"}
            ]]

        });

        //监听工具栏的事件
        table.on('toolbar(classTable)',function (obj) {
            var e = obj.event;
            switch (e){
                case "add":
                    $.get('${path}/easClass/classForm',function (str) {
                        layer.open({
                            type:1,
                            title :'添加基本课程',
                            area : '700px',
                            content : str,
                            skin:'layui-layer-molv',
                            btn : ['确定','取消'],
                            yes : function (index) {
                                var params = $("#class_form").serialize();
                                $.post('${path}/easClass/addClass',params,function (data) {

                                    if(data.result === false){
                                        layui.layer.msg(data.msg,{icon:5});
                                    }else{
                                        layui.layer.msg("添加成功",{icon:1,time:1000},function () {
                                            layer.close(index);
                                            table.reload('classTable');
                                        });

                                    }

                                });
                            }
                        });
                    });
                    break;
                case "batchDelete":
                    var rows = table.checkStatus('classTable');
                    var data = rows.data;
                    if(data.length == 0){
                        layer.msg("请选择要删除的数据",function(){});
                        return;
                    }
                    layer.confirm('真的干掉这么多基本课程吗？',{icon:5,title:'友情提示'},function (index) {
                        var params = "";
                        for(let classes of data){
                            params += "ids="+classes.id+"&";
                        }
                        // console.log("我的id值为:"+params);

                        $.post('${path}/easClass/batchDeleteClass',params,function (data) {

                            layui.layer.msg(data.msg,{icon:5,time:1000},function () {
                                layer.close(index);
                                table.reload('classTable');
                            });

                        });
                    });
                    break;

            }
        });

        //监听行工具时间
        table.on("tool(classTable)",function (obj) {
            var data = obj.data;
            if (obj.event == 'edit'){ //edit
                $.get("${path}/easClass/classForm",function (str) {
                    layer.open({
                        type:1,
                        title : '修改班级',
                        content : str,
                        area:'700px',
                        skin:'layui-layer-molv',
                        btn:['确定','取消'],
                        success:function(){
                            $.get('${path}/easClass/getClassView',{id:data.id},function (data) {
                                //显示数据
                                layui.form.val('class_form',data);
                            });
                        },
                        yes : function (index) {
                            var params2 = $("#class_form").serialize();
                            $.post('${path}/easClass/editClass',params2,function () {
                                layer.close(index);
                                layer.msg('修改成功',{icon:5,time:1000})
                                table.reload('classTable');
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
                        $.post("${path}/easClass/batchDeleteClass",{"ids":data.id},function () {
                            layer.msg('删除成功',{icon:1,time:1000});
                            table.reload('classTable');
                        });
                    }
                })
            }
        })
    });
</script>


<script type="text/html" id="barDemo">
    <shiro:hasPermission name="class:update">
        <a href="javascript:void(0)" lay-event="edit"><i class="layui-icon layui-icon-edit"></i></a>
    </shiro:hasPermission>
    <shiro:hasPermission name="class:delete">
        <a href="javascript:void(0)" lay-event="del"><i class="layui-icon layui-icon-delete"></i></a>
    </shiro:hasPermission>
</script>

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <shiro:hasPermission name="class:add">
            <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-add-circle"></i> 添加</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="class:delete">
            <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="batchDelete"><i class="layui-icon layui-icon-delete"></i> 批量删除</button>
        </shiro:hasPermission>
  </div>
</script>
</body>
</html>
