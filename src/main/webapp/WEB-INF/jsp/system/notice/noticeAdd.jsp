<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>公告</title>
	<%@include file="/WEB-INF/jsp/common.jsp"%>
</head>
<body class="childrenBody">
<form class="layui-form" style="margin-top: 30px">
	<input type="hidden" id="opType" value="">
	<input type="hidden" id="id" value="">
	<div class="layui-row">
		<div class="layui-inline">
			<label class="layui-form-label">公开度</label>
			<div class="layui-input-block">
				<select id="status" name="status" lay-verify="required">
					<option selected="selected" disabled="disabled"  style='display: none' value=''></option>
					<option value="3">保存草稿</option>
					<option value="2">教师可见</option>
					<option value="1">全体可见</option>
				</select>
			</div>
		</div>

		<div class="layui-inline">
			<a class="layui-btn layui-btn-sm" lay-filter="addNotice" lay-submit>
				<i class="layui-icon">&#xe609;</i>上传</a>
		</div>
	</div><br/>
	<div class="layui-row">
		<div class="layui-inline" style="width:29%">
			<label class="layui-form-label">公告标题</label>
			<div class="layui-input-block">
				<input id="title" type="text" class="layui-input" lay-verify="title" placeholder="请输入公告标题">
			</div>
		</div>
		
		<div class="layui-inline" style="width:20%;">
			<label class="layui-form-label">发布人</label>
			<div class="layui-input-block">
				<input id="author" type="text" class="layui-input" lay-verify="author" placeholder="发布人">
			</div>
		</div>
	</div>
	<br/>
	<div class="layui-form-item layui-col-xs9 ">
		<label class="layui-form-label">公告内容</label>
		<div class="layui-input-block">
<%--			<textarea id="notice_content" name="content" class="layui-textarea layui-hide" lay-verify="content" ></textarea>--%>
<%--			<input id="content" type="text" class="layui-input" lay-verify="content" placeholder="请输入公告" style="width: 60%;height: 50%">--%>
				<textarea id="content" class="layui-textarea" style="height: 300px;width: 70%;"></textarea>
		</div>
	</div>
</form>

<script type="text/javascript">
	layui.use(['form','layer','layedit','upload'],function(){
		var form = layui.form,
				upload = layui.upload,
				layedit = layui.layedit,
				$ = layui.jquery;

		//对lay-verify 进行检验
		form.verify({
			title : function(val){
				if(val == ''){
					return "标题不能为空";
				}
			}
			// ,content:function (val) {
			// 	layedit.sync(editIndex);
			// }
		});

		//设置富文本中图片上传接口，一定要放在 build 前面，否则配置全局接口将无效。
		<%--layedit.set({--%>
		<%--	uploadImage: {--%>
		<%--		url: '${path}/easNotice/uploadImg',--%>
		<%--		type: 'post' //默认post--%>
		<%--	}--%>
		<%--});--%>

		//创建一个编辑器
		<%--var editIndex = layedit.build('content',{--%>
		<%--	height : 500,--%>
		<%--	&lt;%&ndash;uploadImage : { url : "${path}/easNotice/uploadImg" }&ndash;%&gt;--%>
		<%--	tool: [--%>
		<%--		'strong' //加粗--%>
		<%--		,'italic' //斜体--%>
		<%--		,'underline' //下划线--%>
		<%--		,'|' //分割线--%>
		<%--		,'left' //左对齐--%>
		<%--		,'center' //居中对齐--%>
		<%--		,'right' //右对齐--%>
		<%--		,'link' //超链接--%>
		<%--		,'unlink' //清除链接--%>
		<%--	]--%>
		<%--});--%>

		//用于同步编辑器内容到textarea
		// layedit.sync(editIndex);

		//执行提交按钮
		form.on("submit(addNotice)",function(data){

			console.log(data);
			console.log("点击提交时通知id为:"+$("#id").val());

			//弹出loading
			var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
			// 实际使用时的提交信息
			$.ajax({
				type: 'post',
				url: "${path}/easNotice/addNotice",
				data : {
					opType: $("#opType").val(),
					id : $("#id").val(),
					title : $("#title").val(),  //标题
					author: $("#author").val(),
					//layedit.getContent获得编辑器的内容 参数 index： 即执行layedit.build返回的值
					// content : layedit.getContent(editIndex).split('<audio controls="controls" style="display: none;"></audio>')[0],
					// content : layedit.getContent(editIndex),
					content: $("#content").val(),
					type : $("#status").val()  //允许谁看 3草稿 2教师可见 1全体可见
				},
				success: function(res){
					top.layer.close(index);
					if (res.result === true) {
						top.layer.msg("保存成功！");
						// layer.closeAll("iframe");
						parent.location.reload();
					} else {
						layer.msg("操作失败！", {icon: 5,time:1000});
					}
				},
				error:function() {
					layer.msg("操作失败！", {icon: 5,time:1000});
					top.layer.close(index);
				}
			});
			return false;
		});

	});
</script>
</body>
</html>