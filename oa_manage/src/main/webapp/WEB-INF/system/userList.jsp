<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <title>用户列表</title>
    <link rel="stylesheet" href="${basePath }/static/frame/layui/css/layui.css" />
    <link rel="stylesheet" href="${basePath }/static/css/style.css" />
    <link rel="icon" href="${basePath }/static/image/code.png" />
</head>
<body class="body">
     <form class="layui-form" action="" target="nm_iframe">
		<blockquote class="layui-elem-quote">
		   	<div class="layui-form-item">
		        <span class="layui-form-label">用户名：</span>
			    <div class="layui-input-inline">
			   		<input type="text" name="userName" id="userName" autocomplete="off" placeholder="用户名" class="layui-input" />
			    </div>
		   		<label class="layui-form-label">状态：</label>
		        <div class="layui-input-inline">
		            <select id="locked" >
		            	<option value=""></option>
		                <option value="">全部</option>
		                <option value="1">启用</option>
		                <option value="0">禁用</option>
		            </select>
		        </div>
		   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
		   		<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		    </div>
	    </blockquote>
	</form>
	<blockquote class="layui-elem-quote">
		<button class="layui-btn" id="add">添加</button>
		<button class="layui-btn layui-btn-warm" id="edit">编辑</button>
		<button class="layui-btn layui-btn-danger" id="delete">批量删除</button>
	</blockquote>
<!-- -----------------------------------------表格----------------------------------------- -->
<div class=layui-form>
<!-- 显示层元信息-数据层元信息映射配置 -->
<table id="dateTable" class="layui-table">
    <thead>
    <tr>
    	<th name="checked"><input type=checkbox lay-filter="allChoose" lay-skin="primary" /><div class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin><i class="layui-icon"></i></div></th>
        <th name="userName">用户名</th>
        <th name="phone">手机</th>
        <th name="email">邮箱</th>
        <th name="createDate">创建时间</th>
        <th name="locked" laya-type="status" kh-key="1,0" kh-value="启用,禁用">状态</th>
		<th name="operButton" buttons="table-edit,table-delete" >操作</th>
    </tr>
    </thead>
    <tbody>
    
    </tbody>
</table>
</div>
<!-- 分页组件 -->
 <span class="fr"><div id="pagingComponent"></div></span>

<script type="text/javascript" src="${basePath }/static/js/system/userList.js"></script>
</body>
</html>