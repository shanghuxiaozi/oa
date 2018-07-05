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
<link rel="stylesheet"
	href="<%=basePath %>/static/ztree/css/metroStyle/metroStyle.css" />
<script type="text/javascript" charset="utf8"
	src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
</head>
<body class="body">
	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend>用户信息</legend>
	</fieldset>
	<div style="display:none; " id="ids">${ids}</div>
	<form class="layui-form  layui-form-pane" action=""
		style="margin-right: 5%; margin-top: 10px;" id="formperm">
		<input type="hidden" name="type" value="2" />
		<div class="layui-form-item" pane >
			<label class="layui-form-label">上级菜单</label>
			<div class="layui-input-block">
				<input type="hidden" name="permissionName"  value="${parentPerm.permissionName}" />
				<input type="hidden" name="pid" id="pid" value="${parentPerm.id}" />
				<input type="text" name="pname" readonly="readonly" id="pname" value="${parentPerm.name}" autocomplete="off" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item perm" id="perm" pane>
			${permStr}
		</div>
		
	</form>
	<blockquote class="layui-elem-quote layui-quote-nm" style="margin-right:3em;">
		<form class="layui-form  layui-form-pane" action="" style="margin-right: 5%; margin-top: 10px;" id="addperm">
			<input type="hidden" name="permissionName" value="${parentPerm.permissionName}" />
			<input type="hidden" name="pid" value="${parentPerm.id}" />
			<input type="hidden" name="type" value="2" />
			<input type="hidden" name="pname" value="${parentPerm.name}" />
		    <input type="hidden" name="available" value="true" />
		    <div class="layui-form-item" pane >
		        <label class="layui-form-label">名称</label>
			    <div class="layui-input-block">
					<input type="text" name="name"  id="names"  lay-verify="required" onchange="valueChange('name',this)"   autocomplete="off" class="layui-input" />
				</div>
			</div>
			 <div class="layui-form-item" pane >
				<label class="layui-form-label">授权标识</label>
				<div class="layui-input-block">
					<input type="text" name="permissionValue"  lay-verify="required" onchange="valueChange('permission_value',this)"   id="permissionValues" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-form-item" pane >
				<label class="layui-form-label">排序</label>
				<div class="layui-input-block">
					<input type="text" name="orderNum"  lay-verify="required"  id="orderNums" autocomplete="off" class="layui-input" />
				</div>
			</div>
		</form>
	    <button class="layui-btn" type="button" style="width: 95%;"  onclick="addPerm()">添加</button>
	</blockquote>
	<script type="text/javascript"
		src="${basePath }/static/frame/layui/layui.js"></script>
	<script src="<%=basePath %>/static/ztree/jquery.ztree.all.min.js"></script>

	<script type="text/javascript">
	var form ;
    layui.use(['form', 'layedit', 'laydate'], function(){
        form = layui.form()
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;
	        //监听提交
	        form.on('submit(demo1)', function(data){
	            layer.alert(JSON.stringify(data.field), {
	                title: '最终的提交信息'
	            });
	            return false;
	    });
    });
    var formData=function(){
    	var obj=new Array();
    	var data = $("#formperm").serializeArray();
    	obj.name="ids";
    	obj.value=$("#ids").html();
    	data.push(obj);
   	    return data;
    }
    
    function valueChange(field,va){
    	var value=$(va).val();
    	value=value.replace(/\s/g, "");
    	if(""!=value){
	    	$.ajax({
		   	         type: "POST",
		   	         dataType: "json",
		   	         url: js_path+"/sysPermission/validateButton",
		   	         data: {"value":value,"field":field,"pid":"${parentPerm.pid}"},
		   	         success: function (obj) {
		   	            if(obj.code==300){
	  	   	          		layer.msg("“"+obj.data+"”已经存在!");
	  	   	          	    $(va).val("");
	  	   	          		$(va).attr("class","layui-input layui-form-danger").focus();
	  	   	          		
		   	            }
		   	         },
		   	         error: function(data) {
		   	              alert("网络错误");
		   	         }
	      	});
    	}
    	
    }
       	var formSubmit=function(){
    	  return form.verifyField('#formperm');
        }
	    

		function addPerm() {
			var flag = form.verifyField('#addperm');
			if (flag == true) {
				var datas = $("#addperm").serializeArray()
				datas.ids = $("#ids").html();
					$.ajax({
							type : "POST",
							dataType : "json",
							url : js_path + "/sysPermission/buttonPermSave",
							data : datas,
							success : function(obj) {
								if (obj.code == 200) {
									var s = '<input type="checkbox" name="perms"  title="'
											+ $("#names").val()
											+ '" value='
											+ obj.data + '  checked  />';
									$("#perm").append(s);
									$("#ids").html($("#ids").html() + obj.data + ",");
									form.render();
									$("#")
								}
							},
							error : function(data) {
								alert("网络错误");
							}
						});
				}
		}
	</script>
</body>
</html>