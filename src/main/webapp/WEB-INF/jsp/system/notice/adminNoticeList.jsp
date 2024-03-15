<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>通知页面</title>
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
<form class="layui-form">
    <div class="layui-form-item" style="margin-left: 5%;margin-top: 30px;">
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="text" class="layui-input searchVal" placeholder="请输入搜索的内容" />
            </div>
            <a class="layui-btn search_btn" data-type="reload">搜索</a>
        </div>
        <div class="layui-inline" style="margin-left:30px">
            <a class="layui-btn layui-btn-normal addNotice_btn">写公告</a>
        </div>
        <div class="layui-inline">
            <a class="layui-btn layui-btn-danger layui-btn-normal batchDelete_btn">批量删除</a>
        </div>
    </div>
</form>

<table id="adminNoticeList" lay-filter="adminNoticeList"></table>

<script>
    layui.use(['form','laydate','layedit','table','laytpl'],function(){
        var form = layui.form,
            laytpl = layui.laytpl,
            layedit =layui.layedit
            table = layui.table;

        var colsArray =[[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: 'ID', width:60, align:"center"},
            {field: 'title', title: '公告标题', width:350},
            {field: 'author', title: '发布者', align:'center'},
            {field: 'type', title: '浏览权限',  align:'center',templet:"#noticeStatus"},
            {field: 'releasedate', title: '发布时间', align:'center', minWidth:110 },
            {title: '操作', width:170, templet:'#noticeListBar',fixed:"right",align:"center"}
        ]];

        //列表
        var tableIns = table.render({
            id : "adminNoticeList",
            elem: '#adminNoticeList',
            url : '${path}/easNotice/list',
            // where: {
            //     searchKey: $(".searchVal").val()
            // },
            cellMinWidth : 95,
            toolbar:true,
            defaultToolbar: ['filter', 'exports', 'print'],
            page : true,
            title: '公告信息表',
            height : "full-125",
            cols : colsArray
        });


        //搜索
        $(".search_btn").on("click",function(){
            table.reload("adminNoticeList",{
                page: {curr: 1},
                where: { searchKey: $(".searchVal").val() }
            });
        });

        //编辑公告
        function addNotice(edit){
            var index = layui.layer.open({
                title : "编辑公告",
                type : 2,
                content : "${path}/easNotice/addPage",
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    //编辑公告(通知)
                    if(edit){
                        console.log("通知内容为:"+edit.content);
                        body.find("#opType").val(1);
                        body.find("#id").val(edit.id);
                        body.find("#title").val(edit.title);
                        body.find("#author").val(edit.author);
                        // body.find("#notice_content").val(edit.content);
                        body.find("#content").val(edit.content);
                        body.find("#status").val(edit.type);
                        form.render();

                    } else {
                        // 添加公告(通知)
                        body.find("#opType").val(0);
                    }
                    setTimeout(function(){
                        layui.layer.tips('点击此处返回公告列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    },500);
                }
            });

            layui.layer.full(index);
            window.sessionStorage.setItem("index",index);
            //改变窗口大小时，重置弹窗的宽高
            $(window).on("resize",function(){
                layui.layer.full(window.sessionStorage.getItem("index"));
            });
        }

        //添加公告
        $(".addNotice_btn").click(function(){
            addNotice();
        });

        //批量删除
        $(".batchDelete_btn").click(function(){
            var checkStatus = table.checkStatus('adminNoticeList'),
                data = checkStatus.data,
                nIds = "";
            if(data.length > 0) {
                for (var i in data) {
                    nIds += data[i].id + ",";
                }
                layer.confirm('确定删除选中的公告？', {icon: 3, title: '提示信息'}, function (index) {
                    $.get("${path}/easNotice/deleteList",{
                        nIds : nIds  //将需要删除的newsId作为参数传入
                    },function(res){
                        if (res.result === true) {
                            layer.msg(res.msg, {icon: 1,time:1000},function () {
                                tableIns.reload({page: {curr: 1 }});
                                layer.close(index);
                            });
                        }else {
                            layer.msg(res.msg, {icon: 5,time:1000});
                        }

                    });
                });
            }else{
                layer.msg("请选择需要删除的文章");
            }
        });

        //列表操作
        table.on('tool(adminNoticeList)', function(obj){
            var data = obj.data,
                layEvent = obj.event;
            if(layEvent === 'edit'){ //编辑
                addNotice(data);
            } else if(layEvent === 'del'){ //删除
                layer.confirm('确定删除此公告？',{icon:3, title:'提示信息'},function(index){
                    $.get("${path}/easNotice/deleteNotice",{id : data.id},function(res){
                        if (res.result === true) {
                            layer.msg(res.msg, {icon: 1,time:1000},function () {
                                tableIns.reload();
                                layer.close(index);
                            });

                        }else {
                            layer.msg(res.msg, {icon: 5,time:1000});
                        }

                    });
                });
            } else if(layEvent === 'look'){ //查看
                var index = layui.layer.open({
                    title : "公告详情",
                    type : 2,
                    content : "${path}/easNotice/look",
                    success : function(layero, index){
                        var body = layui.layer.getChildFrame('body', index);
                        body.find("#title").html(data.title);
                        body.find("#author").html(data.author);
                        body.find("#content").html(data.content);
                        body.find("#releasedate").html(data.releasedate);

                        setTimeout(function(){
                            layui.layer.tips('点击此处返回公告列表', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        },500);
                    }
                });

                layui.layer.full(index);
                window.sessionStorage.setItem("index",index);
                $(window).on("resize",function(){
                    layui.layer.full(window.sessionStorage.getItem("index"));
                });
            }
        });
    });
</script>

<!--审核状态-->
<script type="text/html" id="noticeStatus">
    {{#  if(d.type == "3"){ }}
    <span class="layui-red">草稿</span>
    {{#  } else if(d.type == "2"){ }}
    <span class="layui-blue">教师可见</span>
    {{#  } else { }}
    全体可见
    {{#  } }}
</script>

<!--操作-->
<script type="text/html" id="noticeListBar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
    <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="look">查看</a>
</script>
</body>
</html>
