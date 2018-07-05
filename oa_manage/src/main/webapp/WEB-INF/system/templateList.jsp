<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <title>模板列表</title>
    <style type="text/css">
    	.layui-elem-quote {
		    margin-bottom: 3px;
		    padding: 10px;
		    line-height: 22px;
		    line-height: 22px;
		    border-left: 5px solid #009688;
		    border-radius: 0 2px 2px 0;
		    background-color: #f2f2f2;
		}
    </style>
</head>
<body class="body">
	<form class="layui-form" action="" target="nm_iframe">
		<blockquote class="layui-elem-quote">
		   	<div class="layui-form-item">
		        <span class="layui-form-label">模板名称：</span>
			    <div class="layui-input-inline">
			   		<input id="templateName" type="text" autocomplete="off" placeholder="请输入模板名称" class="layui-input" />
			    </div>
		   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
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
		        <th name="id">模板ID</th>
		        <th name="templateName">模板名称</th>
		        <th name="templateStatus" laya-type="status" kh-key="0,1" 	kh-value="未使用,使用中">模板状态</th>
		        <th name="templateType"  laya-type="status" kh-key="0,1,2" 	kh-value="模板一,模板二,模板三">模块名称</th>
		        <th name="createTime" laya-format="yyyy-mm-dd hh:mm">创建时间</th>
		        <!-- operButton非接口字段，只是表示此栏是存放按钮 -->
		        <th name="operButton" buttons="table-details,table-edit,table-delete">操作</th>
		    </tr>
		    </thead>
		    <tbody>
		    </tbody>
		</table>
	</div>
	<!-- 分页组件 -->
	<span class="fr"><div id="pagingComponent"></div></span>
	<script type="text/javascript" src="${basePath }/static/js/template.js"></script>
	</body>
	</html>