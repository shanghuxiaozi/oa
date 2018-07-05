<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
<meta charset="UTF-8" />
<meta name="renderer" content="webkit" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<title>自动预订订单</title>
<link rel="stylesheet"
	href="${basePath }/static/frame/layui/css/layui.css" />
<link rel="stylesheet" href="${basePath }/static/css/style.css" />
<link rel="icon" href="${basePath }/static/image/code.png" />
<link rel="stylesheet" href="<%=basePath%>/static/ztree/css/metroStyle/metroStyle.css" />
<script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
</head>
<body class="body">
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>用户信息</legend>
	</fieldset>
		<form class="layui-form  layui-form-pane" id="formperm" action="" style="margin-right: 5%; margin-top: 10px;" id="addperm">
			<div class="layui-form-item" pane>
				<label class="layui-form-label">名称</label>
				<div class="layui-input-block">
				    <input type="hidden"  name="id"  value="${perm.id}"  />
					<input type="text" name="name" alt="${perm.name}" title="" value="${perm.name}" lay-verify="required"  onchange="valueChange('name',this)" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-form-item" pane>
				<label class="layui-form-label">授权标识</label>
				<div class="layui-input-block">
					<input type="text" name="permissionValue" alt="${perm.permissionValue}" value="${perm.permissionValue}" onchange="valueChange('permission_value',this)" lay-verify="required" id="permissionValues" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-form-item" pane>
				<label class="layui-form-label">排序</label>
				<div class="layui-input-block">
					<input type="text" name="orderNum" value="${perm.orderNum}" lay-verify="required" id="orderNums" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div  class="layui-form-item" pane>
		        <label class="layui-form-label">是否启用</label>
		        <div class="layui-input-block">
		            <input type="radio" name="available" title="启用" value="true" <c:if test="${perm.available==true}">checked="checked"</c:if>/>
		            <input type="radio" name="available" title="禁用"  value="false" <c:if test="${perm.available==false}">checked="checked"</c:if>/>
		        </div>
		    </div>
		</form>

	<script type="text/javascript" src="${basePath }/static/frame/layui/layui.js"></script>
	<script type="text/javascript">
		var form;
		layui.use([ 'form', 'layedit', 'laydate' ], function() {
			form = layui.form(), layer = layui.layer, layedit = layui.layedit,
					laydate = layui.laydate;
			//监听提交
			form.on('submit(demo1)', function(data) {
				layer.alert(JSON.stringify(data.field), {
					title : '最终的提交信息'
				});
				return false;
			});
		});
		var formData = function() {
			var data = $("#formperm").serializeArray();
			return data;
		}

		function valueChange(field, va) {
			var value = $(va).val();
			var alt = $(va).attr("alt");
			if(alt==value){
				return;
			}
			value = value.replace(/\s/g, "");
			if ("" != value) {
				$.ajax({
					type : "POST",
					dataType : "json",
					url : js_path + "/sysPermission/validateButton",
					data: {"value":value,"field":field,"pid":"${perm.pid}"},
					success : function(obj) {
						if (obj.code == 300) {
							layer.msg("“" + obj.data + "”已经存在!");
							$(va).val("");
							$(va).attr("class","layui-input layui-form-danger").focus();
						}
					},
					error : function(data) {
						alert("网络错误");
					}
				});
			}

		}
		var formSubmit = function() {
			return form.verifyField('#formperm');
		}
	</script>
</body>
</html>