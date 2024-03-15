<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%--    基本课程添加表单--%>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form class="layui-form" lay-filter="add_baseCourse_form" id="add_baseCourse_form" action="" style="padding:15px 10px;">
    <input type="hidden" name="id">
    <div class="layui-form-item">
        <label class="layui-form-label">课程名称</label>
        <div class="layui-input-inline">
            <input type="text" name="coursename" lay-verify="required" placeholder="请输入课程名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">课程简介</label>
        <div class="layui-input-block">
            <textarea name="synopsis" placeholder="请输入课程简介" class="layui-textarea"></textarea>
        </div>
    </div>
</form>
</body>
</html>
