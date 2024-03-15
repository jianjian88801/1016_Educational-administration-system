<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <%--	查看公告页面--%>
    <title>主页公告</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%--    <link rel="stylesheet" type="text/css" href="${path}/css/noticestyle.css?v=2.0" />--%>

    <%@include file="/WEB-INF/jsp/common.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path}/css/noticestyle.css" />
</head>
<body>

<c:forEach items="${noticeList}" var="notice">
    <div class="main-container">
        <div class="left-container">
            <div class="title">
                <p><span>名称:</span><br />${notice.title}</p>
                <div class="line"></div>
            </div>
            <div class="profile">
                <img src="${path}/images/320755dbe6849f1c26308e67c5eb2b67.jpeg" alt="user" />
                <p class="username">${notice.author}</p>
            </div>
        </div>

        <div class="right-container">
            <div class="text">
                <p>
                    <span>TEXT</span><br/>${notice.content}
                </p>
            </div>
            <div class="footer">

                <p class="date"><i class="layui-icon layui-icon-tabs"></i> <strong>${notice.releasedate}</strong></p>
                <div class="comments">
                    <a class="layui-icon layui-icon-return quit_i"  onclick="quit()"></a>
<%--                    <button  onclick="quit()" class="layui-icon layui-icon-return">返回</button>--%>
                </div>
                    <%--			<div class="comments">--%>
                    <%--				<img src="img/KvP.svg" alt="comment-icon" />--%>
                    <%--				<p class="comment-count">23</p>--%>
                    <%--				<img src="img/Kuh.svg" alt="more-icon" />--%>
                    <%--			</div>--%>
            </div>
        </div>
    </div>
</c:forEach>

<script>
    function quit(){
        location.href = "${path}/main/homePage";
    }
</script>
</body>
</html>
