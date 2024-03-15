<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
<%--    课程编辑(开始结束时间、老师等)--%>
    <title>Title</title>
</head>
<body>
<form class="layui-form" lay-filter="courseEditForm" id="edit_course_form"  style="padding:15px 10px;">
    <input type="hidden" name="id" value="" >
    <div class="layui-row">
        <div id="baseCourseDiv" class=" layui-col-md4 layui-col-xs12" style="margin-bottom:15px;">
            <label class="layui-form-label">课程</label>
            <div class="layui-input-block">
                <select id="baseCourseSelect" name="baseCourseId"  disabled="disabled">
                    <option selected="selected" disabled="disabled"  style='display: none' value=''></option>
                </select>
            </div>
        </div>
        <div id="teacherDiv" class=" layui-col-md4 layui-col-xs12" lay-filter="teacherSelect" style="margin-bottom:15px;">
            <label class="layui-form-label">任课教师</label>
            <div class="layui-input-block">
                <select id="teacherSelect" name="tId"  disabled="disabled" >
                    <option selected="selected" disabled="disabled"  style='display: none' value=''></option>
                </select>
            </div>
        </div>
    </div>

    <div class="layui-row">
        <div class=" layui-col-md4 layui-col-xs12" style="margin-bottom:15px;">
            <label class="layui-form-label">开始时间</label>
            <div class="layui-input-block">
                <input type="date" value="" placeholder="请选择开始时间" id="startDate" name="startDate" class="layui-input" >
<%--                data--%>
            </div>
        </div>
        <div class=" layui-col-md4 layui-col-xs12" style="margin-bottom:15px;">
            <label class="layui-form-label">结束时间</label>
            <div class="layui-input-block">
                <input type="date" value="" placeholder="请选择结束时间" id="endDate" name="endDate" class="layui-input" >
            </div>
        </div>
    </div>

    <div class="layui-row">
        <div class=" layui-col-md4 layui-col-xs12" style="margin-bottom:15px;">
            <label class="layui-form-label">课时</label>
            <div class="layui-input-block">
                <input type="text" value="" placeholder="请输入课时" name="classHour" class="layui-input" >
            </div>
        </div>
        <div class=" layui-col-md4 layui-col-xs12" style="margin-bottom:15px;">
            <label class="layui-form-label">考核方式</label>
            <div class="layui-input-block">
                <input type="text" value="" placeholder="请输入考核方式" name="testMode" class="layui-input" >
            </div>
        </div>
    </div>

    <div class="layui-row">
        <div class=" layui-col-md4 layui-col-xs12" style="margin-bottom:15px;">
            <label class="layui-form-label">人数</label>
            <div class="layui-input-block">
                <input type="text" value="" placeholder="请输入人数" name="studentNum" class="layui-input" >
            </div>
        </div>
    </div>
</form>

<script>

    layui.form.render();//渲染表格加载 radio 以及下拉框select
    $.get("${path}/easBaseCourse/search",function (data) {
        $.each(data,function () {
            var opt = $("<option></option>").appendTo("#baseCourseSelect");
            opt.text(this.coursename).val(this.id);
        });
        //获取数据后再进行渲染，显示未显示的option
        layui.form.render();
    });
    $.get("${path}/easTeacher/search",function (data) {
        $.each(data,function () {
            var opt = $("<option></option>").appendTo("#teacherSelect");
            opt.text(this.name).val(this.id);
        });
        //获取数据后再进行渲染，显示未显示的option
        layui.form.render();
    });

</script>
</body>
</html>
