<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <title>报名详情</title>
    <link rel="stylesheet" href="${basePath }/static/frame/layui/css/layui.css" />
    <link rel="stylesheet" href="${basePath }/static/css/style.css" />
    <link rel="icon" href="${basePath }/static/image/code.png" />
</head>
<body class="body">
	
	<!-- <blockquote class="layui-elem-quote">		
		<button class="layui-btn layui-btn-warm" id="add">已到</button>
		<button class="layui-btn layui-btn-danger" id="minus">未到</button> 
	</blockquote>  -->
	

<!-- -----------------------------------------表格----------------------------------------- -->
<div class=layui-form>
<!-- 显示层元信息-数据层元信息映射配置 -->
	<table id="dateTable" class="layui-table">
	    <input type="hidden" id="ids" name="activityId"/>   
	    <thead>
	    <tr>
	    	<!-- <th name="checked"><input type=checkbox lay-filter="allChoose" lay-skin="primary" /><div class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin><i class="layui-icon"></i></div></th>    -->
	        <th name="name" id="name">用户名</th>     
	        <th name="phone">手机号</th>
	        <th name ="type" id="type" laya-type="status" kh-key="0,1,2" kh-value="待定,已到,未到" >状态</th>
			<th name="operButton" buttons="table-edit,table-details" laya-conditions='[{"field":{"key":"type","value":"1"},"button":0},{"field":{"key":"type","value":"1"},"button":1},{"field":{"key":"type","value":"2"},"button":0},{"field":{"key":"type","value":"2"},"button":1}]' >操作</th>
	    </tr>
	    </thead>
	    <tbody>   
	    </tbody>
	</table>
</div>
<!-- 分页组件 -->
 <span class="fr"><div id="pagingComponent"></div></span>
 <script type="text/javascript" src="${basePath }/static/js/friendsActivity/enroll.js"></script>
 <script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
 <script>
	        $(function (){      	
	        	var a =getQueryString("id");
	            $("#ids").val(a);	            	            
	        })
			//获取window.location.url后面的字段
    		function getQueryString(name){
    		     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
    		     var r = window.location.search.substr(1).match(reg);  
    		     if (r != null) return unescape(r[2]);  
    		     return null;  
    		}
</script>
</body>
</html>