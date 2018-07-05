<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/layui-v2.1.4/layui/common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<title>单身认证</title>
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

<form class="layui-form" action="" target="nm_iframe">
	<blockquote class="layui-elem-quote">
	   	<div class="layui-form-item">
	        <span class="layui-form-label">审核状态：</span>
		    <div class="layui-input-inline">
		    	<select id="status" lay-verify="" >
		    	<option value="">--全部--</option>
				<option value="0">待审核</option>
				<option value="1">审核通过</option>
				<option value="2">审核未通过</option>
				</select>
		    </div>
	   		<button type="button" data-method="queryHandler" class="layui-btn" id="query">查询</button>
	    </div>
    </blockquote>
</form>

<!-- 设置表单容器 --> 
<table id="dateTable" lay-filter="demo"></table> 
 
<!-- 设置操作按钮 --> 
<script type="text/html" id="barDemo">
  	<a class="layui-btn layui-btn-mini" lay-event="detail">查看详情</a>
</script>

<!-- 	<a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
  	<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a> -->


<!-- 查看信息页面 -->
<div style="display: none" id="detail" >
	<!-- <table id="activityDetail" lay-filter="activityDetail"></table> -->
	<form class="layui-form layui-form-pane" action="####" style="margin-right: 5%; margin-top: 10px;height: 1000px"  id="formperm">
				
		<div class="layui-form-item" pane>
			<label class="layui-form-label">用户姓名:</label>
			<div class="layui-input-block">
				<input type="text" id="userName" lay-verify="required"
					autocomplete="off" readonly="readonly" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">用户性别:</label>
			<div class="layui-input-block">
				<input type="text" id="userSex" lay-verify="required"
					autocomplete="off" readonly="readonly" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">用户号码:</label>
			<div class="layui-input-block">
				<input type="text" id="userPhone" lay-verify="required"
					autocomplete="off" readonly="readonly" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">真实姓名:</label>
			<div class="layui-input-block">
				<input type="text" id="realName" lay-verify="required"
					autocomplete="off" readonly="readonly" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">身份证号码:</label>
			<div class="layui-input-block">
				<input type="text" id="idcard" lay-verify="required"
					autocomplete="off" readonly="readonly" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">审核状态:</label>
			<div class="layui-input-block">
				<input type="text" id="authStatus" lay-verify="required"
					autocomplete="off" templet='#imgTpl' readonly="readonly" class="layui-input" />
			</div>
		</div>
		
		<div class="layui-form-item" pane>
			<label class="layui-form-label">审核照片:</label>
			<div class="layui-input-block" style="height:500px;">
				<figure style="width: 50%;height: 93%;" class="zoo-item" id="idcardImg2" data-zoo-scale="3" data-zoo-image=""></figure>
			</div>
			<div class="item">
				
			</div>
				
		</div>
		<hr class="layui-bg-black"></hr>
		
	</form>	
	
</div>
<script src="${basePath }/layui-v2.1.4/layui/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="${basePath }/layui-v2.1.4/layui/zoomove.min.js" type="text/javascript"></script>   
<script src="${basePath }/layui-v2.1.4/layui/layui.js?t=1506699022911"></script>
<script type="text/html" id="titleTpl">
	{{# if(d.sex =='1'){ }}
		男
	{{#  } else { }}
		女
	{{#  } }}
</script>
<script type="text/html" id="titleTpl2" >
	{{#  if(d.userType ==1){ }}
 		注册用户
	{{#  } }}
	{{#  if(d.userType ==2){ }}
 		单身用户
	{{#  } }}
	{{#  if(d.userType ==3){ }}
 		会员用户
	{{#  } }} 
</script>
<script type="text/html" id="imgTpl" >
	<img src="{{d.idcardImg }}" style=" width: 200px;height: 200px;" />
</script>
<script type="text/html" id="authTpl" >
	{{#  if(d.authResult == '0'){ }}
 		待审核
	{{#  } }}
	{{#  if(d.authResult == '1'){ }}
 		审核通过
	{{#  } }}
	{{#  if(d.authResult == '2'){ }}
 		审核未通过
	{{#  } }} 
</script>
<script type="">

</script>
<script>
var currentPage=1;
layui.use(['layedit', 'jquery','laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
	var $ = layui.jquery;  			//引用自身的JQuery
	var laydate = layui.laydate 	//日期
  	,laypage = layui.laypage 		//分页
  	,layer = layui.layer 			//弹层
  	,table = layui.table 			//表格
  	,carousel = layui.carousel 		//轮播
 	,upload = layui.upload 			//上传
 	,layedit = layui.layedit 		//文本编辑器
 	,element = layui.element; 		//元素操作
 	var editorFirst;				//富文本编辑器 全局变量!
  	//向世界问个好
//  	layer.msg('Hello World');
  
 	
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
	 	  ,url: js_path+'/singleAuth/queryDynamic'
	 	  //设置表头  进行数据渲染
	 	  ,cols:[[
	 	        {field: 'nickname', title: '姓名', width: 80, align:'center'}
	 	       ,{field: 'sex', title: '性别', templet:'#titleTpl' , width: 70, align:'center'}
		       ,{field: 'phone', title: '号码', width: 120, align:'center' }
		       ,{field: 'userType', title: '用户类型',templet:'#titleTpl2', width: 100, align:'center'}
		       ,{field: 'realName', title: '真实姓名', width: 100, align:'center'}
		       ,{field: 'idcard', title: '身份证号码', width: 180, align:'center'}
		       ,{field: 'creationTime', title: '申请时间', width: 160, align:'center'}
		       ,{field: 'authResult', title: '审核状态', width: 100,templet:'#authTpl',align:'center'}
		       ,{field: 'idcardImg', title: '审核照片', width: 100, templet:'#imgTpl',align:'center'}
		       ,{fixed: 'right', width:200,height:100, align:'center',title:'操作',toolbar:'#barDemo'}
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
	//================数据渲染   end======================

	// 初始化加载查询方法	
	queryDynamic();
	
	//查询按钮事件
	$('#query').click(function(e){
		queryDynamic();
	});
	
	//监听工具条
	table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var data = obj.data 		//获得当前行数据
		,layEvent = obj.event; 		//获得 lay-event 对应的值
		if(layEvent === 'detail'){
			// 赋值操作
			$('#userName').val(data.nickname);
			$('#userSex').val(data.sex=='1'?'男':'女');
			$('#userPhone').val(data.phone);
			$('#realName').val(data.realName);
			$('#idcard').val(data.idcard);
			$('#idcardImg').attr('src',data.idcardImg);
			$('#idcardImg2').attr('data-zoo-image',data.idcardImg);
			var status;
			var buttonArray; 
			if(data.authResult == '0'){
				status="待审核";
				buttonArray = ['取消'];
			}
			if(data.authResult == '1'){
				status="审核通过";
			}
			if(data.authResult == '2'){
				status="审核未通过";
			}
			$('#authStatus').val(status);
			
			// 打开详情页面
			layer.open({
    			type: 1
    			,skin: 'demo-class'
    			,title: '认证信息详情'
    			,area: ['800px', '400px']
    			,offset: '100px' 			//只定义top坐标，水平保持居中
    			,shade:['0.3','#000']
    			,maxmin: true
    			,content:$('#detail')
    			,btn: ['审核通过','审核未通过','取消']
    			,yes: function(index,layero){
    				if(data.authResult != '0'){
    					layer.msg('该用户已经被审核过,不能进行重复操作!');
    					return false;
    				}
    				// 写审核通过的逻辑
    				$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/singleAuth/singleAuth',
    				    data:{'id':data.id,'authResult':1},		// 1审核通过
    				    success: function(data) {
    				    	layer.msg('审核成功!', {icon: 6}); 
    				    	queryDynamic();
    				    }
    				});
    				layer.closeAll();
    			},
    			btn2:function(index,layero){
    				if(data.authResult != '0'){
    					layer.msg('该用户已经被审核过,不能进行重复操作!');	
    					return false;
    				}
    				// 写审核未通过逻辑 
    				$.ajax({
    					type:"POST",
    			        dataType: 'json',
    			        url:js_path+'/singleAuth/singleAuth',
    				    data:{'id':data.id,'authResult':2},		// 2审核未通过
    				    success: function(data) {
    				    	layer.msg('审核成功!', {icon: 6}); 
    				    	queryDynamic();
    				    }
    				});
    				layer.closeAll();
    			},
    			btn3:function(index,layero){
    				layer.closeAll();
    			}
    		});
			
			$('.zoo-item').ZooMove({
				cursor: 'true',
				scale: '1.2',
			});
			
		}
	});
	
	//添加按钮事件
	$('#add').click(function(e){
		// 打开页面
		layer.open({
			type: 1
			,skin: 'demo-class'
			,title: '添加用户'
			,offset: '100px' 			//只定义top坐标，水平保持居中
			,area: ['800px', '400px']
			,shade:['0.3','#000']
			,maxmin: true
			,content:$('#addActivity')
			,btn: ['确认', '取消']
			,yes: function(index,layero){
			 	$.ajax({
			        type:"POST",
			        dataType: 'json',
			        url:js_path+'/sysUserinfo/add',
				    data:{'json':JSON.stringify(getUserInfo())},
				    success: function(data) {
				    	if(data.code == 200){
				    		//confirm('添加横过!!!');
				    		layer.msg('添加成功!!!', {icon: 6}); 
				    	}
				    },
				}); 
				layer.closeAll();
			}
			,btn2: function(){
                layer.closeAll();
            }
			,zIndex: layer.zIndex
			,success: function(layero){
           	 	element.init;
           	 	layer.setTop(layero);
            }
			
		});		
		//===========用layui.open打开的页面，必须打开后zai初始化 富文本编辑器,否则会报错！==============
		layedit.set({
			uploadImage:{
				url: js_path+'/activity/upload2'
				,type: 'post'
				,size: 200    			//图片大小 		
			}
		});
		editorFirst = layedit.build('LAY_editor1',{height: 180}); 	//建立编辑器
//		layedit.build('LAY_editor2'); 	//建立编辑器
		
	});
	
	//将日期直接嵌套在指定容器中
	laydate.render({ 
		elem: '#birthdateId'
		,type: 'date'
		//,range: true //或 range: '~' 来自定义分割字符
	});
	
	// 获取查询参数
	function getQueryParam(){
		//此处仅供测试，实际需要读取标签值
		var t = new Object();
		var userSingleAuthVo = new Object();
		userSingleAuthVo.page=currentPage;
		userSingleAuthVo.pageSize=10;
		// 认证表，默认查询待审核状态的
		t.authResult=$.trim($("#status").val());
		userSingleAuthVo.t=t;
		return JSON.stringify(userSingleAuthVo);
	}
	
	
});
	  	  
</script>
</body>
</html>