<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <title>Title</title>

    <script>
        //获取元素（两种方式都可以）
        var input = document.querySelector('input')
        var imgs = document.getElementById('eyes');
        var imgs2 = document.getElementById('eyes2');
        //下面是一个判断每次点击的效果
        var flag = 0;
        imgs.onclick = function () {
            if (flag === 0) {
                input.type = 'text';
                eyes.src = '${path}/images/1.png';//闭眼图
                flag = 1;
            } else {
                input.type = 'password';
                eyes.src = '${path}/images/2.png';//睁眼图
                flag = 0;
            }
        }

        imgs2.onclick = function () {
            if (flag === 0) {
                input.type = 'text';
                eyes.src = '${path}/images/2.png';//睁眼图
                flag = 1;
            } else {
                input.type = 'password';
                eyes.src = '${path}/images/1.png';//闭眼图
                flag = 0;
            }
        }
    </script>
</head>
<body>
<%--lay-verify="required" 必填项--%>
<form class="layui-form  layui-form-pane" id="register_form" lay-filter="registerForm" style="padding:30px 100px;">
    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-inline">
            <input type="text" name="username" placeholder="请输入用户名" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <!-- 密码 -->
    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-inline">
            <input type="password" id="password" name="password" placeholder="请输入密码" lay-verify="required" autocomplete="off"  class="layui-input">
            <label><img alt="" id="eyes"></label>
        </div>
    </div>
    <!-- 确认密码 -->
    <div class="layui-form-item">
        <label class="layui-form-label">确认密码</label>
        <div class="layui-input-inline">
            <input type="password" name="password2"  placeholder="请输入密码" lay-verify="required" autocomplete="off" class="layui-input">
            <label><img alt="" id="eyes2"></label>
        </div>
    </div>

    <hr style="width: 85%" />
    <p style="width: 85%"><a href="${path}/easLogin/login" class="fl">已有账号？立即登录</a></p>
</form>

</body>
</html>
