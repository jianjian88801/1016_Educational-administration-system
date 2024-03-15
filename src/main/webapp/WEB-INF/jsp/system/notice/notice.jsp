<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<%--	查看公告页面--%>
    <title>notice.jsp</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<%@include file="/WEB-INF/jsp/common.jsp"%>
</head>
<body class="childrenBody">
  <div class="layui-container" style="width:80%">
	  <div class="layui-col-md9">
		  <h2 id="title" class="title" style="text-align: center"></h2>
	  </div>
	  <h3>发布人：<span id="author"></span></h3>
	  <h3>发布于：<span id="releasedate"></span></h3>
	  <br/>
	  <div id="content" style="font-size: large"></div>
  </div>
</body>
</html>
