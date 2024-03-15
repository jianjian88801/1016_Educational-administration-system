<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主页</title>
<%--    <link rel="stylesheet" href="${path}/layui/css/cardTable.css" media="all">--%>
<%--   在CSS文件后面加入 ?v=1.0   会每次更新样式表 防止样式不更新--%>
<%--    <link rel="stylesheet" type="text/css" href="${path}/css/homePagestyle.css?v=1.1" />--%>
<%--    <link rel="stylesheet" type="text/css" href="${path}/css/homePagestyle.css" />--%>
    <%@include file="/WEB-INF/jsp/common.jsp"%>
<%--    <link rel="stylesheet" href="${path}/css/homePagestyle.css" media="all">--%>
    <style>
        * {
            margin: 0;
            padding: 0;
            font-family: "microsoft yahei";
            box-sizing: border-box;
        }

        img {
            border: none;
        }

        ul {
            list-style: none;
        }

        a {
            text-decoration: none;
        }

        .container {
            background: #f3f3f3;
            padding: 50px 0 100px 0;
            overflow: hidden;
        }

        .main {
            width: 1340px;
            margin: 0 auto;
        }

        .main .title {
            margin-bottom: 20px;
            text-align: center;
        }

        .main .title h4 {
            font-size: 28px;
            position: relative;
            font-weight: 700;
            padding-bottom: 5px;
        }

        .main .title h4:before {
            left: 38%;
            content: " ";
            display: block;
            border-bottom: 1px solid #797979;
            width: 68px;
            position: absolute;
            margin-left: -.8rem;
            top: 30%;
            margin-top: -1px;
        }

        .main .title h4:after {
            content: " ";
            display: block;
            border-bottom: 1px solid #797979;
            width: 68px;
            position: absolute;
            margin-left: -.8rem;
            top: 50%;
            margin-top: -1px;
            right: 38%;
        }

        .main .title p {
            font-size: 16px;
            color: #999;
        }

        .main ul li {
            width:1000px;
        }

        .main ul li.two a {
            width: 100%;
            height: 220px;
            padding: 38px 44px;
            margin-bottom: 10px;
            display: block;
            overflow: hidden;
            position: relative;
            background: #fff;
        }

        .main ul li.two .top {
            width: 100%;
            float: initial;
            padding-left: 0;
        }

        .main ul li.two .top h5 {
            font-size: 24px;
            color: #000;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
            font-weight: 400;
        }

        .main ul li.two .top div.p {
            border-bottom: 1px solid #eeeeee;
            padding-bottom: 24px;
        }

        .main ul li.two .top div.p p {
            height: 52px;
            padding-top: 10px;
            font-size: 14px;
            color: #999;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }

        .main ul li.two .top img {
            position: absolute;
            top: 50%;
            right: 67px;
            margin-top: 62px;
        }

        .main ul li.two .bottom {
            width: 100%;
            float: initial;
            border-right: none;
        }

        .main ul li.two .bottom h3 {
            padding-top: 20px;
            font-size: 28px;
            font-weight: 700;
            color: #999;
        }

        .main ul li.two .bottom h3:after {
            content: " ";
            display: block;
            border-bottom: none;
            width: 0;
            margin: 0;
        }

        .main ul li.two .bottom span {
            font-size: 14px;
            display: block;
            color: #999;
        }

        .main ul li.two a:hover,
        .main ul li.three a:hover {
            box-shadow: 0 1px 10px 0 rgba(0, 0, 0, .1);
        }

    </style>
</head>
<body>

<div class="container">
    <div class="main">
        <div class="title">
            <h4>新闻资讯</h4>
            <p >News</p>
        </div>
        <ul style="margin-left:15%;">
            <li class="two" id="notice" style="width:80%">

            </li>
        </ul>
    </div>
</div>


<script type="text/javascript">

    layui.use(['table','layer', 'form', 'jquery','flow'],function () {
        let table = layui.table;
        let form = layui.form;
        let $ = layui.jquery;
        let layer = layui.layer;
        let flow = layui.flow;

        flow.load({
            elem: '#notice' //指定列表容器
            ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
                var lis = [];

                $.get('${path}/main/getNotice?page='+page, function(res){
                    // console.log("返回到前端的list数据为:"+res.data);
                    //假设你的列表返回在data集合中
                    layui.each(res.data, function(index, item){
                        // console.log("返回到前端的id数据为:"+item.id);

                        // lis.push('<li>' + item.title + '</li>');
                        lis.push('<a href="${path}/main/lookNotice?id='+ (item.id) +' " style="width:100%;height:220px;"><div class="top"><h5>'+ item.title +'</h5><div class="p"><p>'+ item.author +'</p></div>\n' +
                            '<img src="${path}/images/new-jiantou.jpg"></div><div class="bottom"><h3>'+item.releasedate +'</h3></div></a>');
                        // <span>2018.03</span>
                        <%--href="${path}/main/look"--%>

                    });

                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    next(lis.join(''), page < res.totalPage);
                });

            }
        });


    });

</script>
</body>
</html>
