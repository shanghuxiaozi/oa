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
	content="width=device-width, initial-scale=1, maximum-scale=1"/>
<title>活动详情</title>
<link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/layui.css"  media="all" />
<link rel="stylesheet" href="${basePath }/static/css/style.css" />
<link rel="icon" href="${basePath }/static/image/code.png" />
<link rel="stylesheet" href="${basePath }/static/ztree/css/metroStyle/metroStyle.css" />
<script type="text/javascript" charset="utf8" src="${basePath }/static/js/jquery-1.10.2.min.js"></script>
</head>
<body class="body">
	<fieldset class="layui-elem-field layui-field-title"
		style="margin-top: 20px;">
		<legend>活动详情</legend>
	</fieldset>

	<form class="layui-form  layui-form-pane" action="${basePath }/activity/save"
		style="margin-right: 5%; margin-top: 10px; " id="formperm" method="get"> 
		<input type="hidden" name="type" value="1" />
				
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动名称</label>
			<div class="layui-input-block">
				<input type="text" name="activity.title" id="title" lay-verify="required"
					autocomplete="off" class="layui-input" />
			</div>
		</div>	
		 
		<!-- 时间选择器 -->	
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动时间</label>
			     <div class="layui-input-inline">
				            <input type="text" class="layui-input" id="test3" placeholder="活动开始时间" name="activity.activityStartTime">
				  </div>
			     <div class="layui-input-inline">
				            <input type="text" class="layui-input" id="test4" placeholder="活动结束时间" name="activity.activityEndTime">
				  </div>
		</div>
				
		<div class="layui-form-item" pane>
			     <label class="layui-form-label">报名时间</label>
			     <div class="layui-input-inline">
				            <input type="text" class="layui-input" id="test1" placeholder="报名开始时间" name="activity.applyStartTime">
				  </div>
			     <div class="layui-input-inline">
				            <input type="text" class="layui-input" id="test2" placeholder="报名结束时间" name="activity.applyEndTime">
				  </div>
		</div>
		<!-- 信誉分，积分
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动所需信誉分</label>
			<div class="layui-input-block">
				<input type="text" name="title" lay-verify="required"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动奖励信誉分</label>
			<div class="layui-input-block">
				<input type="text" name="rewardCredit" lay-verify="required"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动奖励积分</label>
			<div class="layui-input-block">
				<input type="text" name="rewardIntegral" lay-verify="required"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动所需信誉分</label>
			<div class="layui-input-block">
				<input type="text" name="requiredCredit" lay-verify="required"
					autocomplete="off" class="layui-input"/>
			</div>
		</div>    -->
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动人数</label>
			<div class="layui-input-block">
				<input type="text"  lay-verify="required"
					autocomplete="off" class="layui-input"
					  name="activity.applyNumber" id="applyNumber" />
			</div>
		</div>
				
		<div class="layui-form-item" pane>
			<label class="layui-form-label">活动地点</label>
			<div class="layui-input-block">
				<input type="text"  lay-verify="required"
					autocomplete="off" class="layui-input"
					name="activity.activityAddress" id="activityAddress" />
			</div>
		</div>
		 
		<div class="layui-form-item" pane>
			<label class="layui-form-label">联系人方式</label>
			<div class="layui-input-block">
				<input type="text" name="activity.activityPhone" lay-verify="required" placeholder="张三：18888888888" 
					autocomplete="off" class="layui-input" id="activityPhone"/>
			</div>
		</div>
		
		<div class="layui-form-item layui-form-text">
		      <div class="layui-form-item layui-form-text">  		
		           <label class="layui-form-label">活动内容</label>  		
		               <div style="margin: 20px"><p id="content"></p></div>
		     </div>           
		</div>               
				     
	
		<div class="layui-form-item layui-form-text">
		      <div class="layui-form-item layui-form-text">  		
		           <label class="layui-form-label">活动流程</label>  		
		               <div style="margin: 20px"><p id="activityProgress"></p></div>
		     </div>           
		</div>   	

		  
		<div class="layui-form-item layui-form-text">
		    <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label">注意事项</label>
				    <div class="layui-input-block">
				         <textarea placeholder="请输入内容" class="layui-textarea"  name="activity.activityAttentionMatter" id="activityAttentionMatter"></textarea>
				    </div>
             </div>           
		</div>
		
		<div class="layui-form-item layui-form-text">
		      <div class="layui-form-item layui-form-text">  		
		           <label class="layui-form-label">首页图片</label>  		
		               <div style="margin: 20px"><p id="activityImage"></p></div>
		     </div>           
		</div>   	
		
		
		
		</form>
	<!-- 选择菜单 -->
	<div id="menuLayer" style="padding: 10px; display: none;">
		<ul id="menuTree" class="ztree"></ul>
	</div>
	 <script type="text/javascript"
		src="${basePath }/layui-v2.1.4/layui/layui.js" charset="utf-8"></script>
	<script src="${basePath }/static/ztree/jquery.ztree.all.min.js"></script>
   <script type="text/javascript" src="${basePath }/static/js/friendsActivity/activity.js"></script>
   <script type="text/javascript">
	function getData(a){	
		
		console.log(a);
		 $('#title').val(a.title);	
		 $('#test3').val(a.activityStartTime);	
		 $('#test4').val(a.activityEndTime);	
		 $('#test1').val(a.applyStartTime);	
		 $('#test2').val(a.applyEndTime);	
		 $('#applyNumber').val(a.applyNumber);	
		 $('#activityAddress').val(a.activityAddress);	
		 $('#activityPhone').val(a.activityPhone);		 
		 $('#content').html('<div>'+a.content+'<div/>');
		 $('#activityProgress').html('<div>'+a.activityProgress+'<div/>');
		 $('#activityAttentionMatter').val(a.activityAttentionMatter);	
		 $('#activityImage').html('<img src ='+a.activityImage+'>');	
		}
	</script>	
</body>
</html>