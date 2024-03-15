<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%--    班级表单--%>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form class="layui-form" lay-filter="class_form" id="class_form" action="" style="padding:15px 10px;">
    <input type="hidden" name="id">
    <div class="layui-form-item">
        <label class="layui-form-label">班级名称</label>
        <div class="layui-input-inline">
            <input type="text" name="classes" lay-verify="required" placeholder="请输入课程名称" autocomplete="off" class="layui-input">
        </div>
    </div>
</form>
</body>
</html>
