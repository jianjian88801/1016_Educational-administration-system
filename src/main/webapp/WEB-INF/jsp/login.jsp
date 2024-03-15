<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>教务管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <%@include file="common.jsp"%>
    <!--[if lt IE 9]>

    <![endif]-->
    <style>
        html, body {width: 100%;height: 100%;overflow: hidden}
        /*body {background: #1E9FFF;}*/
        body {background: #000000;}
        /*body {background: #2f332a;}*/
        body:after {content:'';background-repeat:no-repeat;background-size:cover;-webkit-filter:blur(3px);-moz-filter:blur(3px);-o-filter:blur(3px);-ms-filter:blur(3px);filter:blur(3px);position:absolute;top:0;left:0;right:0;bottom:0;z-index:-1;}
        .layui-container {width: 100%;height: 100%;overflow: hidden}
        .admin-login-background {width:360px;height:300px;position:absolute;left:50%;top:40%;margin-left:-180px;margin-top:-100px;}
        .logo-title {text-align:center;letter-spacing:2px;padding:14px 0;}
        .logo-title h1 {color:#1E9FFF;font-size:25px;font-weight:bold;}
        .login-form {background-color:#fff;border:1px solid #fff;border-radius:3px;padding:14px 20px;box-shadow:0 0 8px #eeeeee;}
        .login-form .layui-form-item {position:relative;}
        .login-form .layui-form-item label {position:absolute;left:1px;top:1px;width:38px;line-height:36px;text-align:center;color:#d2d2d2;}
        .login-form .layui-form-item input {padding-left:36px;}
        .captcha {width:60%;display:inline-block;}
        .captcha-img {display:inline-block;width:34%;float:right;}
        .captcha-img img {height:34px;border:1px solid #e6e6e6;height:36px;width:100%;}
    </style>
    <script>

        //验证码刷新
        function reloadValidateCode(){
            $("#validateCodeImg").attr("src","${path}/validatecode.jsp?data=" + new Date() + Math.floor(Math.random()*24));
        }

    </script>

</head>
<body>
<div class="layui-container">
    <div class="admin-login-background">
        <div class="layui-form login-form">
            <form id="login" class="layui-form" action="${path}/easLogin/login" method="post">
                <div class="layui-form-item logo-title">
                    <h1>教务管理系统</h1>
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-username" ></label>
                    <input type="text" name="username" lay-verify="required|account" placeholder="用户名" autocomplete="off" class="layui-input" >
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-password" ></label>
                    <input type="password" name="password" lay-verify="required|password" placeholder="密码" autocomplete="off" class="layui-input" >
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-vercode" ></label>
                    <input type="text" name="randomCode" lay-verify="required" placeholder="图形验证码" autocomplete="off" class="layui-input verification captcha">
                    <div class="captcha-img">
                        <img   id="validateCodeImg"  src="${path}/validatecode.jsp" onclick="reloadValidateCode()">
                    </div>
                </div>
                <div class="layui-form-item">
                    <input type="checkbox" name="rememberMe" value="true" lay-skin="primary" title="记住密码">
                </div>
                <div class="layui-form-item">
                    <button type="submit"  class="layui-btn layui-btn layui-btn-normal layui-btn-fluid" lay-submit="" lay-filter="login" onclick="subForm();return false;">登 入</button>
                </div>

                <u><u></u><a onclick="registerForm();" style="margin-left: 70%">立即注册</a></u></p>

            </form>
        </div>
    </div>
</div>
<script src="${path}/lib/jq-module/jquery.particleground.min.js" charset="utf-8"></script>
<script>
    layui.use(['form','layer'], function () {
        var form = layui.form,
            layer = layui.layer;

        // 登录过期的时候，跳出ifram框架
        if (top.location !== self.location) top.location = self.location;

        // 粒子线条背景
        $(document).ready(function(){
            $('.layui-container').particleground({
                dotColor:'#7ec7fd',
                lineColor:'#7ec7fd'
            });
        });

    });

    
    function subForm() {
        var params = $("#login").serialize();
        $.post("${path}/easLogin/login",params,function (data) {
            if(data.code != 0){
                layui.layer.msg(data.msg,{icon:5});
            }else{
                layui.layer.msg("登录成功",{icon:1,time:1000},function () {
                    location.href = "${path}/easLogin/main";
                });

            }
        });

    }
    
    function registerForm() {
        $.get("${path}/easRegister/registerForm",function (str) {
            var resisterIndex = layer.open({
                type:1,
                title : '注册页面',
                content : str,
                area:'700px',
                skin:'layui-layer-molv',
                btn:['注册','重置'],
                zIndex:10,
                yes : function (index) {
                    var params2 = $("#register_form").serialize();
                    $.post('${path}/easRegister/registerUser',params2,function (data) {
                        // console.log("我是注册传来的数据:"+params2);
                        if(data.code === 0){
                            layui.layer.close(resisterIndex);
                            layui.layer.msg('注册成功',{icon:1,time:1000})
                        }else if(data.code === 1){
                            layui.layer.msg(data.msg,{zIndex:20,icon:5,time:1000})
                        }

                    });
                }
                ,btn2:function (index){
                    //重置表单add_course_form 为表单id
                     $('#register_form')[0].reset();
                    // form.render();//必须写，不写该按钮会变为关闭页面事件
                    form.render(null, 'registerForm');//更新 lay-filter="courseAddForm" 所在容器内的全部表单状态
                }

            });
        });
    }


</script>


</body>
</html>