<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/jsp/common.jsp"%>
</head>
<body>
<%--<h2>权限列表</h2>--%>
<%--<hr>--%>
<table id="permissionTable" lay-filter="permissionTable"></table>

<script type="text/javascript">
    layui.config({
        base: '${path}/layui/lay/modules/'
    }).extend({
        treeGrid: 'treeGrid'
    });

    layui.use('treeGrid',function () {
        var treeGrid = layui.treeGrid;

        $.get("${path}/easPermission/list",function (d) {
            $.each(d.data,function () {
               this.isOpen = false;
            });
            // console.log(d.data);
            treeGrid.render({
                id : 'perTable',
                elem : '#permissionTable',
                data : d.data,
                idField : 'id',
                treeId : 'id',
                treeUpId : 'parentid',
                treeShowName : 'text',
                height : "full-125",//高度将始终铺满
                // toolbar:'#toolbarDemo',
                toolbar: true,
                defaultToolbar:['filter', 'exports', 'print'],
                cols : [[
                    {field:'text', title:'权限名称'},
                    {field:'type', title:'权限类型'},
                    {field:'url', title:'连接地址'}
                ]]
            });
        })

    });
</script>

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon layui-icon-add-circle"></i> 添加</button>
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="batchDelete"><i class="layui-icon layui-icon-delete"></i> 批量删除</button>

    </div>
</script>
</body>
</html>
