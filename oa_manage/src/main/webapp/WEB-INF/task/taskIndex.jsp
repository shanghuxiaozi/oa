<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日报</title>
<link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/zoomove.min.css" />
	<link rel="stylesheet" href="${basePath }/layui-v2.1.4/layui/css/layui.css?t=1506699022911" media="all" />
	<style>
	  body{margin: 10px;}
	  .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
	  .layui-table-body.layui-table-main{height:508.99px;}
	  .layui-table-view .layui-table td, .layui-table-view .layui-table th{padding:10px 0;}
	  .layui-input-block .zoo-item .zoo-img{background-size: 100% 100%;}
	</style>
</head>
<body>
<form class="layui-form" action="" target="nm_iframe" id="i_form">
	<blockquote class="layui-elem-quote">
	   	<div class="layui-form-item">
	        <span class="layui-form-label" style="width: 85px;">账号：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="account" autocomplete="off" placeholder="账号" class="layui-input" />
		    </div>
		    <span class="layui-form-label">日期：</span>
		    <div class="layui-input-inline">
		   		<input type="text" id="writeTime" autocomplete="off" placeholder="填写日期" class="layui-input" />
		    </div>
		
			
		</div>
	

	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
	   		<button type="button" data-method="queryHandler" class="layui-btn" id="submit">提交</button>
	    </div>
    </blockquote>


</form>

<!-- 设置表单容器 --> 
<table id="dateTable" lay-filter="demo"></table>
<script src="${basePath }/layui-v2.1.4/layui/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="${basePath }/layui-v2.1.4/layui/zoomove.min.js" type="text/javascript"></script>   
<script src="${basePath }/layui-v2.1.4/layui/layui.js?t=1506699022911"></script>
<script>
var currentPage=1;
layui.use(['layedit', 'jquery','laydate', 'laypage', 'layer','form', 'table', 'carousel', 'upload', 'element'], function(){
	var table = layui.table;
	
	//================数据渲染     方法一Start====================
 	function queryDynamic(){	
	table.render({
		  elem: '#dateTable' 		//指定原始表格元素选择器（推荐id选择器）
		  ,height: 590 		 		//容器高度
		  ,page:true 		 		//开启分页 
		  ,even: true 				//开启隔行背景
		  ,method:'post'
		  ,request: {		 		//分页   设置分页名称
			  pageName: 'page' 		//页码的参数名称，默认：page
		 	 ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
		  }
			  	  					//分页参数设置  	
	  	  ,limits:[10]
	  	  ,limit: 10 				//默认采用60	
		  ,response: {
			  statusName: 'code'  	//数据状态的字段名称，默认：code
			  ,statusCode: 200 		//成功的状态码，默认：0
			  ,msgName: 'msg'  		//状态信息的字段名称，默认：msg
			  ,countName: 'total' 	//数据总数的字段名称，默认：count
			  ,dataName: 'data' 	//数据列表的字段名称，默认：data
		  } 	
	 	  ,where:{queryRestraintJson:getQueryParam()}
	 	  ,url: js_path+'/task/queryDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
				{field: 'userName', title: '账号', width: 180, align:'center'}
			   ,{field: 'writeTime', title: '提交时间', width: 100,align:'center'}
			   ,{field: 'content', title: '提交内容', width: 100, align:'center'}
			   
			   ,{fixed: 'right', width:120, align:'center',title:'操作',toolbar:'#zz'}
	 		    ]]
  		  // 数据渲染完的回调   无论是异步请求数据，还是直接赋值数据，都会触发该回调.
	 	  ,done: function(res, curr, count){
	 		  //如果是异步请求数据方式，res即为你接口返回的信息。
	 		  //如果是直接赋值的方式，res即为：{code: 200, msg: "查询成功", total: 17, data: Array(10)} 
	 		  console.log(res);
	 		  //得到当前页码
	 		  console.log(curr);
	 		  //得到数据总量
	 		  console.log(count);
		  }
	}); 
 	}	
 	queryDynamic();
});


//获取查询参数
function getQueryParam(){
	//此处仅供测试，实际需要读取标签值
	var t = new Object();
	var invitationUserVo = new Object();
	invitationUserVo.page=currentPage;
	invitationUserVo.pageSize=10;
	// 认证表，默认查询待审核状态的
	t.idcard=$.trim($("#idcard").val());
	t.phone=$.trim($("#phoneNumber").val());
	t.userId = $.trim($("#status").val());
	t.workUnit=$.trim($("#unit").val());		//工作单位
	t.unitAttribute = $("#s1").val();			//单位属性
	t.postNature = $("#s2").val();				//岗位性质
	invitationUserVo.t=t;
	return JSON.stringify(invitationUserVo);
}
</script>
</body>
</html>